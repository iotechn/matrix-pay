package com.dobbinsoft.fw.pay.service.pay.wx;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import com.dobbinsoft.fw.pay.service.pay.BasePayService;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.pay.service.pay.wx.model.*;
import com.dobbinsoft.fw.support.utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: WxPayServiceImpl
 * Description: 微信支付实现
 */
@Slf4j
public class WxPayServiceImpl extends BasePayService implements MatrixPayService<WxPayUnifiedPrepayModel> {

    private final OkHttpClient client = new OkHttpClient();

    private OkHttpClient clientWithP12;


    private static final String BASE_URL = "https://api.mch.weixin.qq.com";

    public WxPayServiceImpl(PayProperties payProperties) {
        super(payProperties);
        // Load the p12 certificate
        if (payProperties.getWxCert() != null && payProperties.getWxCert().length > 0) {
            try {
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                keyStore.load(new ByteArrayInputStream(payProperties.getWxCert()), payProperties.getWxMchId().toCharArray());

                // Create a KeyManagerFactory and initialize it with the keyStore
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, payProperties.getWxMchId().toCharArray());

                // Create a TrustManagerFactory and initialize it with the keyStore
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);

                // Create an SSLContext and initialize it with the KeyManager and TrustManager
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

                // Create and return an OkHttpClient configured with the SSLContext
                clientWithP12 = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build();
            } catch (Exception e) {
                log.error("[微信支付] 加载P12证书失败");
            }
        } else {
            log.info("[微信支付] 未加载P12证书");
        }
    }


    @Override
    public WxPayUnifiedPrepayModel createOrder(MatrixPayUnifiedOrderRequest entity) throws MatrixPayException {
        WxPayUnifiedOrderRequest wxEntity = new WxPayUnifiedOrderRequest();
        BeanUtils.copyProperties(entity, wxEntity);
        // 设置默认值
        if (StringUtils.isEmpty(wxEntity.getAppid())) {
            wxEntity.setAppid(payProperties.getWxAppId());
        }
        if (StringUtils.isEmpty(wxEntity.getMchId())) {
            wxEntity.setMchId(payProperties.getWxMchId());
        }
        if (StringUtils.isEmpty(wxEntity.getNotifyUrl())) {
            wxEntity.setNotifyUrl(payProperties.getWxNotifyUrl());
        }
        if (StringUtils.isEmpty(wxEntity.getNonceStr())) {
            wxEntity.setNonceStr(StringUtils.uuid());
        }
        // TODO 路由类型
        wxEntity.setTradeType("JSAPI");

        wxEntity.setDetail(JacksonUtil.toJSONString(entity.getDetail()));
        // 签名
        String jsonStringWithoutNull = JacksonUtil.toJSONStringWithoutNull(wxEntity);
        TreeMap<String, Object> stringObjectMap = JacksonUtil.parseObject(jsonStringWithoutNull, new TypeReference<TreeMap<String, Object>>() {
        });
        String params = stringObjectMap.keySet().stream().map(item -> item + "=" + stringObjectMap.get(item)).collect(Collectors.joining("&"));
        params += ("&key=" + payProperties.getWxMchKey());
        wxEntity.setSign(DigestUtils.md5Hex(params).toUpperCase());
        // 发送请求
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(JacksonXmlUtil.toXmlStringWithoutNull(wxEntity), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + "/pay/unifiedorder")
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String xml = response.body().string();
            WxPayUnifiedOrderResponse wxPayUnifiedOrderResponse = JacksonXmlUtil.parseObject(xml, WxPayUnifiedOrderResponse.class);
            WxPayUnifiedPrepayModel prepayModel = new WxPayUnifiedPrepayModel();
            prepayModel.setAppId(wxPayUnifiedOrderResponse.getAppid());
            prepayModel.setNonceStr(wxPayUnifiedOrderResponse.getNonceStr());
            prepayModel.setPackageValue("prepay_id=" + wxPayUnifiedOrderResponse.getPrepayId());
            prepayModel.setSignType("MD5");
            prepayModel.setTimeStamp((System.currentTimeMillis() / 1000) + "");
            StringBuilder sb = new StringBuilder();
            sb.append("appId=");
            sb.append(prepayModel.getAppId());
            sb.append("&nonceStr=");
            sb.append(prepayModel.getNonceStr());
            sb.append("&package=");
            sb.append(prepayModel.getPackageValue());
            sb.append("&signType=");
            sb.append(prepayModel.getSignType());
            sb.append("&timeStamp=");
            sb.append(prepayModel.getTimeStamp());
            sb.append("&key=");
            sb.append(payProperties.getWxMchKey());
            prepayModel.setPaySign(DigestUtils.md5Hex(sb.toString()).toUpperCase());
            return prepayModel;
        } catch (IOException e) {
            // TODO matrix exception
            throw new RuntimeException(e);
        }

    }


    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws MatrixPayException {
        if (clientWithP12 == null){
            throw new MatrixPayException("未配置P12证书，无法申请退款");
        }
        WxPayRefundRequest wxEntity = new WxPayRefundRequest();
        BeanUtils.copyProperties(entity, wxEntity);
        // 设置默认值
        if (StringUtils.isEmpty(wxEntity.getAppid())) {
            wxEntity.setAppid(payProperties.getWxAppId());
        }
        if (StringUtils.isEmpty(wxEntity.getMchId())) {
            wxEntity.setMchId(payProperties.getWxMchId());
        }
        if (StringUtils.isEmpty(wxEntity.getNonceStr())) {
            wxEntity.setNonceStr(StringUtils.uuid());
        }
        // 签名
        String jsonStringWithoutNull = JacksonUtil.toJSONStringWithoutNull(wxEntity);
        TreeMap<String, Object> stringObjectMap = JacksonUtil.parseObject(jsonStringWithoutNull, new TypeReference<TreeMap<String, Object>>() {
        });
        String params = stringObjectMap.keySet().stream().map(item -> item + "=" + stringObjectMap.get(item)).collect(Collectors.joining("&"));
        params += ("&key=" + payProperties.getWxMchKey());
        wxEntity.setSign(DigestUtils.md5Hex(params).toUpperCase());
        // 发送请求
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(JacksonXmlUtil.toXmlStringWithoutNull(wxEntity), mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + "/pay/unifiedorder")
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String xml = response.body().string();
            WxPayRefundResponse wxPayRefundResponse = JacksonXmlUtil.parseObject(xml, WxPayRefundResponse.class);
            MatrixPayRefundResult matrixPayRefundResult = new MatrixPayRefundResult();
            BeanUtils.copyProperties(wxPayRefundResponse, matrixPayRefundResult);
            return matrixPayRefundResult;
        } catch (IOException e) {
            // TODO matrix exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public MatrixPayOrderNotifyResult checkParsePayResult(Object notify) throws MatrixPayException {
        TreeMap<String, String> stringObjectMap = JacksonXmlUtil.parseObject(notify.toString(), new TypeReference<TreeMap<String, String>>() {
        });
        String params = stringObjectMap.keySet().stream()
                .filter(item -> !"sign".equals(item))
                .map(item -> item + "=" + stringObjectMap.get(item))
                .collect(Collectors.joining("&"));
        params += ("&key=" + payProperties.getWxMchKey());
        String sign = DigestUtils.md5Hex(params);
        if (!sign.equalsIgnoreCase(stringObjectMap.get("sign"))) {
            throw new MatrixPayException("签名错误");
        }
        MatrixPayOrderNotifyResult result = new MatrixPayOrderNotifyResult();
        WxPayUnifiedOrderNotify wxPayUnifiedOrderNotify = JacksonXmlUtil.parseObject(notify.toString(), WxPayUnifiedOrderNotify.class);
        BeanUtils.copyProperties(wxPayUnifiedOrderNotify, result);
        return result;
    }
}
