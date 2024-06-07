package com.dobbinsoft.fw.pay.service.pay.wx;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import com.dobbinsoft.fw.pay.service.pay.BasePayService;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * ClassName: WxPayServiceImpl
 * Description: 微信支付实现
 */
@Slf4j
public class WxPayServiceImpl extends BasePayService implements MatrixPayService {

    private final OkHttpClient client = new OkHttpClient();

    private static final String BASE_URL = "https://api.mch.weixin.qq.com";

    public WxPayServiceImpl(PayProperties payProperties) {
        super(payProperties);
    }


    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws MatrixPayException {
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
            return JacksonXmlUtil.parseObject(xml, WxPayUnifiedOrderResponse.class);
        } catch (IOException e) {
            // TODO matrix exception
            throw new RuntimeException(e);
        }

    }


    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayOrderNotifyResult checkParsePayResult(Object request) throws MatrixPayException {
        return null;
    }
}
