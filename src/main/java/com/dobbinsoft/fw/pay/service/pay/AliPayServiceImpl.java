package com.dobbinsoft.fw.pay.service.pay;

import com.alipay.easysdk.kernel.Client;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.*;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.dobbinsoft.fw.pay.client.AliClient;
import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.model.coupon.*;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayRefundNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixScanPayNotifyResult;
import com.dobbinsoft.fw.pay.model.request.*;
import com.dobbinsoft.fw.pay.model.result.*;
import com.github.binarywang.wxpay.bean.WxPayApiData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * ClassName: AliPayServiceImpl
 * Description: 支付宝支付实现
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public class AliPayServiceImpl implements MatrixPayService {

    private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);

    private Map<String, AliClient> configMap = new ConcurrentHashMap<>();

    private PayProperties payProperties;

    public AliPayServiceImpl(PayProperties payProperties) {
        this.payProperties = payProperties;
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws MatrixPayException {
        AliClient aliClient = getClient(entity.getAppid());
        try {
            List<Map<String, Object>> detailMaps = null;
            if (!CollectionUtils.isEmpty(entity.getDetail())) {
                List<MatrixPayUnifiedOrderRequestGoodsDetail> detail = entity.getDetail();
                detailMaps = detail.stream().map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("goods_id", item.getGoodsId());
                    map.put("goods_name", item.getGoodsName());
                    map.put("quantity", item.getGoodsNum());
                    map.put("price", fenToYuan(item.getPrice()));
                    map.put("goods_category", item.getGoodsCategory());
                    return map;
                }).collect(Collectors.toList());

            }
            if (entity.getPayPlatform() == PayPlatformType.APP) {
                AlipayTradeAppPayResponse appPayResponse = aliClient.payment.App()
                        .optional("body", entity.getAttach())
                        .optional("time_expire", transToAliTime(entity.getTimeExpire()))
                        .optional("goods_detail", detailMaps)
                        .optional("terminal_id", entity.getDeviceInfo())
                        .pay(entity.getBody(), entity.getOutTradeNo(), this.fenToYuan(entity.getTotalFee()));
                return appPayResponse.body;
            } else if (entity.getPayPlatform() == PayPlatformType.WEB) {
                AlipayTradePagePayResponse pagePayResponse = aliClient.payment.Page()
                        .optional("body", entity.getAttach())
                        .optional("time_expire", transToAliTime(entity.getTimeExpire()))
                        .optional("goods_detail", detailMaps)
                        .optional("terminal_id", entity.getDeviceInfo())
                        .pay(entity.getBody(), entity.getOutTradeNo(), this.fenToYuan(entity.getTotalFee()), entity.getReturnUrl());
                return pagePayResponse.body;
            } else {
                AlipayTradeCreateResponse alipayTradeCreateResponse = aliClient.payment.Common()
                        .optional("body", entity.getAttach())
                        .optional("time_expire", transToAliTime(entity.getTimeExpire()))
                        .optional("goods_detail", detailMaps)
                        .optional("terminal_id", entity.getDeviceInfo())
                        .create(entity.getBody(), entity.getOutTradeNo(), this.fenToYuan(entity.getTotalFee()), entity.getOpenid());
                return alipayTradeCreateResponse;
            }
        } catch (Exception e) {
            logger.info("[支付宝] 创建统一支付订单 异常 outTradeNo=" + entity.getOutTradeNo(), e);
            throw new MatrixPayException(e.getMessage());
        }
    }

    /**
     * 将微信的时间，转化为ali的时间
     *
     * @param time
     * @return
     */
    private String transToAliTime(String time) throws ParseException {
        if (time == null) {
            return null;
        }
        SimpleDateFormat wxSdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parse = wxSdf.parse(time);
        SimpleDateFormat aliSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return aliSdf.format(parse);
    }

    private String transToWxTime(String time) throws ParseException {
        if (time == null) {
            return null;
        }
        SimpleDateFormat aliSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = aliSdf.parse(time);
        SimpleDateFormat wxSdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return wxSdf.format(parse);
    }

    @Override
    public void configWarmUp(List<PayProperties> list) {
        if (!CollectionUtils.isEmpty(list)) {
            for (PayProperties properties : list) {
                if (StringUtils.isNotBlank(properties.getAliAppId())) {
                    AliClient aliClient = new AliClient(this.buildNewConfig(properties));
                    this.configMap.put(payProperties.getAliAppId(), aliClient);
                }
            }
        }
    }

    @Override
    public MatrixEntPayService getEntPayService() {
        return null;
    }

    @Override
    public void setEntPayService(MatrixEntPayService entPayService) {

    }

    @Override
    public MatrixPayOrderQueryResult queryOrder(MatrixPayOrderQueryRequest request) throws MatrixPayException {
        if (StringUtils.isBlank(request.getOutTradeNo()) && StringUtils.isBlank(request.getTransactionId())) {
            throw new MatrixPayException("支付宝仅支持outTradeNo查询");
        }
        AliClient aliClient = this.getClient(request.getAppid());
        try {
            AlipayTradeQueryResponse response = aliClient.payment.Common()
                    .optional("trade_no", request.getTransactionId())
                    .query(StringUtils.isBlank(request.getOutTradeNo()) ? "" : request.getOutTradeNo());
            if (response != null) {
                // TODO payPlatform 如何确定
                MatrixPayOrderQueryResult matrixPayOrderQueryResult = new MatrixPayOrderQueryResult();
                matrixPayOrderQueryResult.setPayChannel(PayChannelType.ALI);
                matrixPayOrderQueryResult.setAppid(request.getAppid());
                matrixPayOrderQueryResult.setOutTradeNo(response.getOutTradeNo());
                matrixPayOrderQueryResult.setTransactionId(response.getTradeNo());
                matrixPayOrderQueryResult.setOpenid(response.getBuyerUserId());
                matrixPayOrderQueryResult.setTotalFee(yuanToFen(response.getTotalAmount()));
                matrixPayOrderQueryResult.setFeeType(response.getTransCurrency());
                String tradeStatus = response.getTradeStatus();
                matrixPayOrderQueryResult.setTradeState(toMatrixStatus(tradeStatus));
                matrixPayOrderQueryResult.setAttach(response.getBody());
                matrixPayOrderQueryResult.setDeviceInfo(response.getTerminalId());
                matrixPayOrderQueryResult.setTimeEnd(transToWxTime(response.getSendPayDate()));
                return matrixPayOrderQueryResult;
            }
        } catch (Exception e) {
            logger.error("[支付宝] 支付订单查询 异常 outTradeNo=" + request.getOutTradeNo() + " ;transactionId=" + request.getTransactionId(), e);
            throw new MatrixPayException(e.getMessage());
        }
        return null;
    }

    @Override
    public MatrixPayOrderCloseResult closeOrder(MatrixPayOrderCloseRequest request) throws MatrixPayException {
        try {
            AliClient aliClient = getClient(request.getAppid());
            AlipayTradeCloseResponse response = aliClient.payment.Common().close(request.getOutTradeNo());
            if (response != null) {
                MatrixPayOrderCloseResult matrixPayOrderCloseResult = new MatrixPayOrderCloseResult();
                matrixPayOrderCloseResult.setAppid(request.getAppid());
                return matrixPayOrderCloseResult;
            }
        } catch (Exception e) {
            logger.info("[支付宝] 关闭订单 异常 outTradeNo=" + request.getOutTradeNo(), e);
            throw new MatrixPayException(e.getMessage());
        }
        return null;
    }

    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws MatrixPayException {
        AliClient aliClient = getClient(entity.getAppid());
        try {
            AlipayTradeRefundResponse response =
                    aliClient.payment.Common().refund(entity.getOutTradeNo(), fenToYuan(entity.getRefundFee()));
            MatrixPayRefundResult result = new MatrixPayRefundResult();
            result.setAppid(entity.getAppid());
            result.setRefundFee(yuanToFen(response.refundFee));
            result.setTransactionId(response.tradeNo);
            result.setOutTradeNo(response.outTradeNo);
            result.setFeeType(response.refundCurrency);
            result.setRefundId(response.refundSettlementId);
            return result;
        } catch (Exception e) {
            logger.error("[支付宝] 创建退款单 异常 orderNo=" + entity.getOutTradeNo(), e);
            throw new MatrixPayException(e.getMessage());
        }
    }

    @Override
    public MatrixPayRefundQueryResult refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws MatrixPayException {
        if ((outTradeNo == null || "".equals(outTradeNo)) || (outRefundNo == null || "".equals(outRefundNo))) {
            throw new MatrixPayException("支付宝仅支持 outTradeNo | outRefundNo 查询");
        }
        Set<String> strings = configMap.keySet();
        try {
            for (String appId : strings) {
                AliClient aliClient = getClient(appId);
                AlipayTradeFastpayRefundQueryResponse response = aliClient.payment.Common().queryRefund(outTradeNo, outRefundNo);
                if (response != null) {
                    MatrixPayRefundQueryResult result = new MatrixPayRefundQueryResult();
                    result.setAppid(appId);
                    result.setTransactionId(response.getTradeNo());
                    result.setOutTradeNo(outTradeNo);
                    result.setCashFee(yuanToFen(response.getTotalAmount()));
                    // TODO
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error("[支付宝] 退款单查询 异常 orderNo=" + outTradeNo, e);
            throw new MatrixPayException(e.getMessage());
        }
        return null;
    }

    @Override
    public MatrixPayRefundQueryResult refundQuery(MatrixPayRefundQueryRequest request) throws MatrixPayException {
        return this.refundQuery(request.getTransactionId(), request.getOutTradeNo(), request.getOutRefundNo(), request.getRefundId());
    }

    @Override
    public MatrixPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayRefundNotifyResult parseRefundNotifyResult(String xmlData) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixScanPayNotifyResult parseScanPayNotifyResult(String xmlData) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPaySendRedpackResult sendRedpack(MatrixPaySendRedpackRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayRedpackQueryResult queryRedpack(String mchBillNo) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayRedpackQueryResult queryRedpack(MatrixPayRedpackQueryRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public byte[] createScanPayQrcodeMode1(String productId, File logoFile, Integer sideLength) {
        return new byte[0];
    }

    @Override
    public String createScanPayQrcodeMode1(String productId) {
        return null;
    }

    @Override
    public byte[] createScanPayQrcodeMode2(String codeUrl, File logoFile, Integer sideLength) {
        return new byte[0];
    }

    @Override
    public void report(MatrixPayReportRequest request) throws MatrixPayException {

    }

    @Override
    public String downloadRawBill(String billDate, String billType, String tarType, String deviceInfo) throws MatrixPayException {
        return null;
    }

    @Override
    public String downloadRawBill(MatrixPayDownloadBillRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayBillResult downloadBill(String billDate, String billType, String tarType, String deviceInfo) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayBillResult downloadBill(MatrixPayDownloadBillRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayFundFlowResult downloadFundFlow(String billDate, String accountType, String tarType) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayFundFlowResult downloadFundFlow(MatrixPayDownloadFundFlowRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayMicropayResult micropay(MatrixPayMicropayRequest request) throws MatrixPayException {
        AliClient aliClient = getClient(request.getAppid());
        try {
            AlipayTradePayResponse response = aliClient.payment.FaceToFace().pay(request.getBody(), request.getOutTradeNo(), fenToYuan(request.getTotalFee()), request.getAuthCode());
            MatrixPayMicropayResult result = new MatrixPayMicropayResult();
            result.setAppid(request.getAppid());
            result.setTransactionId(response.getTradeNo());
//        result.setTransactionId(response.get);
            result.setOutTradeNo(response.getOutTradeNo());
            if (response.getCode().equals("10000")) {
                return result;
            }
            throw new MatrixPayException(response.getMsg());
//        result.setFeeType(response.getCur);
        } catch (Exception e) {
            logger.error("[支付宝] 提交当面支付 异常 outTradeNo=" + request.getOutTradeNo(), e);
            throw new MatrixPayException(e.getMessage());
        }
    }

    @Override
    public MatrixPayOrderReverseResult reverseOrder(MatrixPayOrderReverseRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public String shorturl(MatrixPayShorturlRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public String shorturl(String longUrl) throws MatrixPayException {
        return null;
    }

    @Override
    public String authcode2Openid(MatrixPayAuthcode2OpenidRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public String authcode2Openid(String authCode) throws MatrixPayException {
        return null;
    }

    @Override
    public String getSandboxSignKey() throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayCouponSendResult sendCoupon(MatrixPayCouponSendRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayCouponStockQueryResult queryCouponStock(MatrixPayCouponStockQueryRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayCouponInfoQueryResult queryCouponInfo(MatrixPayCouponInfoQueryRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public WxPayApiData getWxApiData() {
        return null;
    }

    @Override
    public String queryComment(Date beginDate, Date endDate, Integer offset, Integer limit) throws MatrixPayException {
        return null;
    }

    @Override
    public String queryComment(MatrixPayQueryCommentRequest request) throws MatrixPayException {
        return null;
    }


    @Override
    public MatrixPayOrderNotifyResult checkSign(Object request) throws MatrixPayException {
        try {
            Map<String, String> map = (Map<String, String>) request;
            AliClient aliClient = getClient(map.get("app_id"));
            aliClient.payment.Common().verifyNotify(map);
            MatrixPayOrderNotifyResult result = new MatrixPayOrderNotifyResult();
            result.setOpenid(map.get("buyer_id"));
            result.setTotalFee(yuanToFen(map.get("invoice_amount")));
            result.setOutTradeNo(map.get("out_trade_no"));
            result.setAppid("app_id");
            result.setTransactionId(map.get("trade_no"));
            result.setVersion(map.get("version"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MatrixPayException(e.getMessage());
        }
    }

    private String fenToYuan(Integer fen) {
        return "" + (fen / 100.0);
    }

    private String toMatrixStatus(String aliOrderStatus) {
        if ("WAIT_BUYER_PAY".equals(aliOrderStatus)) {
            return "NOTPAY";
        } else if ("TRADE_CLOSED".equals(aliOrderStatus)) {
            return "CLOSED";
        } else if ("TRADE_SUCCESS".equals(aliOrderStatus) || "TRADE_FINISHED".equals(aliOrderStatus)) {
            return "SUCCESS";
        }
        return null;
    }

    public static Integer yuanToFen(String yuan) {
        return (new BigDecimal(yuan)).setScale(2, 4).multiply(new BigDecimal(100)).intValue();
    }

    private AliClient getClient(String appId) {
        AliClient aliClient = this.configMap.get(appId);
        if (aliClient == null) {
            String aliAppId = this.payProperties.getAliAppId();
            if (StringUtils.equals(appId, aliAppId)) {
                aliClient = new AliClient(this.buildNewConfig(this.payProperties));
                this.configMap.put(payProperties.getAliAppId(), aliClient);
            } else {
                throw new MatrixPayException("加载APPID失败，请再PayProperties接口中返回对应的APPID");
            }
        } else {
            // 动态判断APPID是否被更新
            Config config = aliClient.getConfig();
            if (config.appId.hashCode() != payProperties.getAliAppId().hashCode()
                    || config.gatewayHost.hashCode() != payProperties.getAliGateway().hashCode()
                    || config.merchantPrivateKey.hashCode() != payProperties.getAliMchPrivateKey().hashCode()
                    || config.alipayPublicKey.hashCode() != payProperties.getAliAliPublicKey().hashCode()
                    || config.notifyUrl.hashCode() != payProperties.getAliNotifyUrl().hashCode()
                    || config.alipayPublicKey.hashCode() != payProperties.getAliAliPublicKey().hashCode()) {
                aliClient = new AliClient(this.buildNewConfig(this.payProperties));
                this.configMap.put(payProperties.getAliAppId(), aliClient);
            }
        }
        return aliClient;
    }

    private Config buildNewConfig(PayProperties properties) {
        Config config = new Config();
        config.protocol = "https";
        config.appId = properties.getAliAppId();
        config.gatewayHost = properties.getAliGateway();
        config.signType = "RSA2";
        config.merchantPrivateKey = properties.getAliMchPrivateKey();
        config.alipayPublicKey = properties.getAliAliPublicKey();
        config.notifyUrl = properties.getAliNotifyUrl();
        return config;
    }
}
