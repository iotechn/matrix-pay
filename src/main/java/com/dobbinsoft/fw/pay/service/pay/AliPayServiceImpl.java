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
import com.dobbinsoft.fw.pay.model.request.PayFace2FaceRequest;
import com.dobbinsoft.fw.pay.model.request.PayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.PayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.PayFace2FaceResult;
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
public class AliPayServiceImpl implements PayService {

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
    public Object createOrder(PayUnifiedOrderRequest entity) throws PayServiceException {
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
    public PayRefundResult refundOrder(PayRefundRequest entity) throws PayServiceException {
        this.config.appId = entity.getAppid();
        try {
            AlipayTradeRefundResponse response =
                    Factory.Payment.Common().refund(entity.getOutTradeNo(), fenToYuan(entity.getRefundFee()));
            PayRefundResult result = new PayRefundResult();
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
    public PayFace2FaceResult createFaceToFace(PayFace2FaceRequest request) throws PayServiceException {
        this.config.appId = request.getAppid();
        try {
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(request.getBody(), request.getOutTradeNo(), fenToYuan(request.getTotalFee()));
            PayFace2FaceResult result = new PayFace2FaceResult();
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
