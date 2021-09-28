package com.dobbinsoft.fw.pay.service.pay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.request.*;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import com.dobbinsoft.fw.pay.model.result.PayMicropayResult;
import com.dobbinsoft.fw.pay.model.result.PayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.result.PayRefundResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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

    private Config config;

    public AliPayServiceImpl(PayProperties payProperties) {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = payProperties.getAliGateway();
        config.signType = "RSA2";
        config.merchantPrivateKey = payProperties.getAliMchPrivateKey();
        config.alipayPublicKey = payProperties.getAliAliPublicKey();
        config.notifyUrl = payProperties.getAliNotifyUrl();
        this.config = config;
        Factory.setOptions(this.config);
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws PayServiceException {
        this.config.appId = entity.getAppid();
        Factory.setOptions(this.config);
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
    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws PayServiceException {
        this.config.appId = entity.getAppid();
        try {
            AlipayTradeRefundResponse response =
                    Factory.Payment.Common().refund(entity.getOutTradeNo(), fenToYuan(entity.getRefundFee()));
            MatrixPayRefundResult result = new MatrixPayRefundResult();
            result.setAppid(this.config.appId);
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
    public PayMicropayResult micropay(PayFace2FaceRequest request) throws PayServiceException {
        this.config.appId = request.getAppid();
        try {
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(request.getBody(), request.getOutTradeNo(), fenToYuan(request.getTotalFee()));
            PayMicropayResult result = new PayMicropayResult();
            result.setAppid(this.config.appId);
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
    public PayOrderNotifyResult checkSign(Object request) throws PayServiceException {
        try {
            Map<String, String> map = (Map<String, String>) request;
            Factory.Payment.Common().verifyNotify(map);
            PayOrderNotifyResult result = new PayOrderNotifyResult();
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
