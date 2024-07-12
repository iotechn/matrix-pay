//package com.dobbinsoft.fw.pay.service.pay.ali;
//
//import com.dobbinsoft.fw.pay.config.PayProperties;
//import com.dobbinsoft.fw.pay.enums.PayPlatformType;
//import com.dobbinsoft.fw.pay.exception.MatrixPayException;
//import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
//import com.dobbinsoft.fw.pay.model.request.BasePayRequest;
//import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
//import com.dobbinsoft.fw.pay.model.request.MatrixPayRequestGoodsDetail;
//import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
//import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
//import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
//import com.dobbinsoft.fw.pay.service.pay.ali.model.AliPayBaseRequest;
//import com.dobbinsoft.fw.pay.service.pay.ali.model.AliPayUnifiedOrderRequest;
//import com.dobbinsoft.fw.support.utils.*;
//import com.fasterxml.jackson.core.type.TypeReference;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.security.*;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Base64;
//import java.util.Date;
//import java.util.List;
//import java.util.TreeMap;
//import java.util.stream.Collectors;
//
///**
// * ClassName: AliPayServiceImpl
// * Description: 支付宝支付实现
// */
//@Slf4j
//public class AliPayServiceImpl implements MatrixPayService<Object> {
//
//    private final OkHttpClient client = new OkHttpClient.Builder()
//            .followRedirects(true)  // 启用重定向
//            .followSslRedirects(true)  // 启用HTTPS重定向
//            .build();
//
//    private static final BigDecimal HUNDRED = new BigDecimal(100);
//
//    private final PayProperties payProperties;
//
//    public AliPayServiceImpl(PayProperties payProperties) {
//        this.payProperties = payProperties;
//    }
//
//    @Override
//    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws MatrixPayException {
//        LocalDateTime now = LocalDateTime.now();
//        AliPayBaseRequest orderRequest = new AliPayBaseRequest();
//        // 1. 设置Base
//        if (StringUtils.isNotEmpty(entity.getAppid())) {
//            orderRequest.setAppId(entity.getAppid());
//        } else {
//            orderRequest.setAppId(payProperties.getAliAppId());
//        }
//        orderRequest.setCharset("UTF-8");
//        orderRequest.setSignType("RSA2");
//        orderRequest.setFormat("JSON");
//        orderRequest.setTimestamp(TimeUtils.localDateTimeToString(now));
//        orderRequest.setVersion("1.0");
//
//        String certSN = CertificateUtils.getCertSN(payProperties.getAliMchPublicKey());
//        orderRequest.setAppCertSn(certSN);
//
//        String rootCertSN = CertificateUtils.getRootCertSN(payProperties.getAliAliRootCertPath());
//        orderRequest.setAlipayRootCertSn(rootCertSN);
//
//        if (StringUtils.isNotEmpty(entity.getNotifyUrl())) {
//            orderRequest.setNotifyUrl(entity.getNotifyUrl());
//        } else {
//            orderRequest.setNotifyUrl(payProperties.getAliNotifyUrl());
//        }
//        // 2. 设置业务
//        if (entity.getPayPlatform() == PayPlatformType.APP) {
//            orderRequest.setMethod("alipay.trade.app.pay");
//            AliPayUnifiedOrderRequest bizContent = new AliPayUnifiedOrderRequest();
//            bizContent.setOutTradeNo(entity.getOutTradeNo());
//            bizContent.setTotalAmount(fenToYuan(entity.getTotalFee()));
//            bizContent.setSubject(entity.getBody());
//            if (CollectionUtils.isNotEmpty(entity.getDetail())) {
//                // 转化为支付宝商品格式
//                List<AliPayUnifiedOrderRequest.GoodsDetail> goodsDetails = entity.getDetail().stream().map(item -> {
//                    AliPayUnifiedOrderRequest.GoodsDetail goodsDetail = new AliPayUnifiedOrderRequest.GoodsDetail();
//                    goodsDetail.setGoodsId(item.getGoodsId());
//                    goodsDetail.setGoodsName(item.getGoodsName());
//                    goodsDetail.setQuantity(Integer.parseInt(item.getGoodsNum()));
//                    goodsDetail.setPrice(fenToYuan(item.getPrice()));
//                    goodsDetail.setAlipayGoodsId(item.getPayGoodsId());
//                    goodsDetail.setCategoriesTree(item.getGoodsCategory());
//                    return goodsDetail;
//                }).collect(Collectors.toList());
//                bizContent.setGoodsDetail(goodsDetails);
//            }
//            if (entity.getTimeExpire() != null) {
//                bizContent.setTimeExpire(TimeUtils.localDateTimeToString(entity.getTimeExpire()));
//            }
//            if (StringUtils.isNotEmpty(entity.getAttach())) {
//                bizContent.setPassbackParams(URLEncoder.encode(entity.getAttach(), StandardCharsets.UTF_8));
//            }
//            orderRequest.setBizContent(JacksonUtil.toJSONString(bizContent));
//        } else {
//            throw new MatrixPayException("暂时不支持支付平台:%s".formatted(entity.getPayPlatform().getMsg()));
//        }
//        // 3. 签名
//        String jsonStringWithoutNull = JacksonUtil.toJSONStringWithoutNull(orderRequest);
//        TreeMap<String, Object> stringObjectMap = JacksonUtil.parseObject(jsonStringWithoutNull, new TypeReference<TreeMap<String, Object>>() {
//        });
//        String params = stringObjectMap.keySet().stream().map(item -> item + "=" + stringObjectMap.get(item)).collect(Collectors.joining("&"));
//        String sign = sign(params);
//        try {
//            // 发送请求
////            FormBody.Builder builder = new FormBody.Builder();
////            for (String key : stringObjectMap.keySet()) {
////                Object o = stringObjectMap.get(key);
////                builder.addEncoded(key, URLEncoder.encode(o.toString(), StandardCharsets.UTF_8));
////            }
////            builder.addEncoded("sign", URLEncoder.encode(sign, StandardCharsets.UTF_8));
//////            MediaType mediaType = MediaType.parse("application/json");
//////            orderRequest.setSign(sign);
//////            RequestBody body = RequestBody.create(JacksonUtil.toJSONStringWithoutNull(orderRequest), mediaType);
////            Response response = client.newCall(new Request
////                            .Builder()
////                            .url(payProperties.getAliGateway())
////                            .post(builder.build())
////                            .build())
////                    .execute();
//            String param = stringObjectMap.keySet().stream().map(k -> k + "=" + URLEncoder.encode(stringObjectMap.get(k).toString(), StandardCharsets.UTF_8)).collect(Collectors.joining("&"));
//
//            Response response = client.newCall(new Request
//                            .Builder()
//                            .url(payProperties.getAliGateway() + "?" + param + "&sign=" + URLEncoder.encode(sign, StandardCharsets.UTF_8))
//                            .build())
//                    .execute();
//            String json = response.body().string();
//            log.info("[支付宝] 统一下单回复:{}", json);
//        } catch (IOException e) {
//            throw new MatrixPayException("[支付宝] 统一下单发生网络异常");
//        }
//        return null;
//    }
//
//
//    @Override
//    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws MatrixPayException {
//        return null;
//    }
//
//
//    @Override
//    public MatrixPayOrderNotifyResult checkParsePayResult(Object request) throws MatrixPayException {
//        return null;
//    }
//
//
//    private String fenToYuan(Integer fen) {
//        return new BigDecimal(fen).divide(HUNDRED,  2, RoundingMode.HALF_UP).toString();
//    }
//
//    private String sign(String params) {
//        try {
//            // 从Base64编码的字符串中加载私钥
//            byte[] privateKeyBytes = Base64.getDecoder().decode(payProperties.getAliMchPrivateKey());
//            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//
//            // 生成签名
//            Signature signature = Signature.getInstance("SHA256withRSA");
//            signature.initSign(privateKey);
//            signature.update(params.getBytes());
//            byte[] signedData = signature.sign();
//
//            // 打印签名结果
//            return Base64.getEncoder().encodeToString(signedData);
//        } catch (Exception e) {
//            log.error("[支付宝] 签名异常", e);
//            throw new MatrixPayException("[支付宝] 签名异常");
//        }
//    }
//
//}
