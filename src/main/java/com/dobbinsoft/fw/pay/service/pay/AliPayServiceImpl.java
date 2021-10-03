package com.dobbinsoft.fw.pay.service.pay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.coupon.*;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayRefundNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixScanPayNotifyResult;
import com.dobbinsoft.fw.pay.model.request.*;
import com.dobbinsoft.fw.pay.model.result.*;
import com.github.binarywang.wxpay.bean.WxPayApiData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AliPayServiceImpl
 * Description: 支付宝支付实现
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public class AliPayServiceImpl implements MatrixPayService {

    private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);

    private Map<String, Config> configMap = new HashMap<>();

    private PayProperties payProperties;

    // TODO 这个地方 new的对象，无法获取动态配置，需要解决
    public AliPayServiceImpl(PayProperties payProperties) {
        this.payProperties = payProperties;
    }

    private void init() {
        if (configMap.get(payProperties.getAliMiniAppId()) == null && StringUtils.isNotBlank(payProperties.getAliMiniAppId())) {
            Config configMini = new Config();
            configMini.protocol = "https";
            configMini.appId = payProperties.getAliMiniAppId();
            configMini.gatewayHost = payProperties.getAliGateway();
            configMini.signType = "RSA2";
            configMini.merchantPrivateKey = payProperties.getAliMchMiniPrivateKey();
            configMini.alipayPublicKey = payProperties.getAliAliMiniPublicKey();
            configMini.notifyUrl = payProperties.getAliMiniNotifyUrl();
            this.configMap.put(payProperties.getAliMiniAppId(), configMini);
        }
        if (configMap.get(payProperties.getAliAppAppId()) == null && StringUtils.isNotBlank(payProperties.getAliAppAppId())) {
            Config configApp = new Config();
            configApp.protocol = "https";
            configApp.appId = payProperties.getAliAppAppId();
            configApp.gatewayHost = payProperties.getAliGateway();
            configApp.signType = "RSA2";
            configApp.merchantPrivateKey = payProperties.getAliMchAppPrivateKey();
            configApp.alipayPublicKey = payProperties.getAliAliAppPublicKey();
            configApp.notifyUrl = payProperties.getAliAppNotifyUrl();
            this.configMap.put(payProperties.getAliAppAppId(), configApp);
        }
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws PayServiceException {
        this.init();
        Config config = configMap.get(entity.getAppid());
        Factory.setOptions(config);
        try {
            if (entity.getPayPlatform() == PayPlatformType.APP) {
                AlipayTradeAppPayResponse appPayResponse = Factory.Payment.App()
                        .pay(entity.getBody(), entity.getOutTradeNo(), this.fenToYuan(entity.getTotalFee()));
                return appPayResponse.body;
            } else {
                AlipayTradeCreateResponse alipayTradeCreateResponse = Factory.Payment.Common().create(entity.getBody(),
                        entity.getOutTradeNo(),
                        this.fenToYuan(entity.getTotalFee()), entity.getOpenid());
                return alipayTradeCreateResponse;
            }
        } catch (Exception e) {
            logger.info("[支付宝] 创建统一支付订单 异常 orderNo=" + entity.getOutTradeNo(), e);
            throw new PayServiceException(e.getMessage());
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
    public MatrixPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayOrderQueryResult queryOrder(MatrixPayOrderQueryRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayOrderCloseResult closeOrder(String outTradeNo) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayOrderCloseResult closeOrder(MatrixPayOrderCloseRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayUnifiedOrderResult unifiedOrder(MatrixPayUnifiedOrderRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws PayServiceException {
        Config config = configMap.get(entity.getAppid());
        Factory.setOptions(config);
        try {
            AlipayTradeRefundResponse response =
                    Factory.Payment.Common().refund(entity.getOutTradeNo(), fenToYuan(entity.getRefundFee()));
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
            throw new PayServiceException(e.getMessage());
        }
    }

    @Override
    public MatrixPayRefundQueryResult refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayRefundQueryResult refundQuery(MatrixPayRefundQueryRequest request) throws MatrixPayException {
        return null;
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
        Config config = configMap.get(request.getAppid());
        Factory.setOptions(config);
        try {
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(request.getBody(), request.getOutTradeNo(), fenToYuan(request.getTotalFee()));
            MatrixPayMicropayResult result = new MatrixPayMicropayResult();
            result.setAppid(request.getAppid());
//        result.setTransactionId(response.get);
            result.setOutTradeNo(response.getOutTradeNo());
//        result.setFeeType(response.getCur);
            return result;
        } catch (Exception e) {
            logger.error("[支付宝] 提交当面支付 异常 orderNo=" + request.getOutTradeNo());
            throw new PayServiceException(e.getMessage());
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
            Factory.Payment.Common().verifyNotify(map);
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
            throw new PayServiceException(e.getMessage());
        }
    }

    private String fenToYuan(Integer fen) {
        return "" + (fen / 100.0);
    }

    public static Integer yuanToFen(String yuan) {
        return (new BigDecimal(yuan)).setScale(2, 4).multiply(new BigDecimal(100)).intValue();
    }
}
