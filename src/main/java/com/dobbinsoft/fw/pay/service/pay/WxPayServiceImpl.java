package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.request.PayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.PayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.PayOrderNotifyCoupon;
import com.dobbinsoft.fw.pay.model.result.PayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.result.PayRefundCouponInfo;
import com.dobbinsoft.fw.pay.model.result.PayRefundResult;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: WxPayServiceImpl
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public class WxPayServiceImpl implements PayService {

    private WxPayService wxPayService;

    public WxPayServiceImpl(final PayProperties payProperties) {
        this.wxPayService = new com.github.binarywang.wxpay.service.impl.WxPayServiceImpl() {
            @Override
            public WxPayConfig getConfig() {
                WxPayConfig wxPayConfig = new WxPayConfig();
                wxPayConfig.setMchId(payProperties.getWxMchId());
                wxPayConfig.setMchKey(payProperties.getWxMchKey());
                wxPayConfig.setNotifyUrl(payProperties.getWxNotifyUrl());
                wxPayConfig.setKeyPath(payProperties.getWxKeyPath());
                return wxPayConfig;
            }
        };
    }

    @Override
    public Object createOrder(PayUnifiedOrderRequest entity) throws PayServiceException {
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setAppid(entity.getAppid());
        wxPayUnifiedOrderRequest.setMchId(entity.getMchId());
        wxPayUnifiedOrderRequest.setSubAppId(entity.getSubAppId());
        wxPayUnifiedOrderRequest.setSubMchId(entity.getSubMchId());
        wxPayUnifiedOrderRequest.setNonceStr(entity.getNonceStr());
        wxPayUnifiedOrderRequest.setSign(entity.getSign());
        wxPayUnifiedOrderRequest.setSignType(entity.getSignType());
        wxPayUnifiedOrderRequest.setVersion(entity.getVersion());
        wxPayUnifiedOrderRequest.setDeviceInfo(entity.getDeviceInfo());
        wxPayUnifiedOrderRequest.setBody(entity.getBody());
        wxPayUnifiedOrderRequest.setDetail(entity.getDetail());
        wxPayUnifiedOrderRequest.setAttach(entity.getAttach());
        wxPayUnifiedOrderRequest.setOutTradeNo(entity.getOutTradeNo());
        wxPayUnifiedOrderRequest.setFeeType(entity.getFeeType());
        wxPayUnifiedOrderRequest.setTotalFee(entity.getTotalFee());
        wxPayUnifiedOrderRequest.setSpbillCreateIp(entity.getSpbillCreateIp());
        wxPayUnifiedOrderRequest.setTimeStart(entity.getTimeStart());
        wxPayUnifiedOrderRequest.setTimeExpire(entity.getTimeExpire());
        wxPayUnifiedOrderRequest.setGoodsTag(entity.getGoodsTag());
        wxPayUnifiedOrderRequest.setNotifyUrl(entity.getNotifyUrl());
        wxPayUnifiedOrderRequest.setTradeType(entity.getTradeType());
        wxPayUnifiedOrderRequest.setProductId(entity.getProductId());
        wxPayUnifiedOrderRequest.setLimitPay(entity.getLimitPay());
        wxPayUnifiedOrderRequest.setOpenid(entity.getOpenid());
        wxPayUnifiedOrderRequest.setSubOpenid(entity.getSubOpenid());
        wxPayUnifiedOrderRequest.setReceipt(entity.getReceipt());
        wxPayUnifiedOrderRequest.setSceneInfo(entity.getSceneInfo());
        wxPayUnifiedOrderRequest.setFingerprint(entity.getFingerprint());
        try {
            return wxPayService.createOrder(wxPayUnifiedOrderRequest);
        } catch (WxPayException e) {
            throw new PayServiceException(e.getMessage());
        }
    }

    @Override
    public PayRefundResult refundOrder(PayRefundRequest entity) throws PayServiceException {
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        refundRequest.setAppid(entity.getAppid());
        refundRequest.setMchId(entity.getMchId());
        refundRequest.setSubAppId(entity.getSubAppId());
        refundRequest.setSubMchId(entity.getSubMchId());
        refundRequest.setNonceStr(entity.getNonceStr());
        refundRequest.setSign(entity.getSign());
        refundRequest.setSignType(entity.getSignType());
        refundRequest.setDeviceInfo(entity.getDeviceInfo());
        refundRequest.setTransactionId(entity.getTransactionId());
        refundRequest.setOutTradeNo(entity.getOutTradeNo());
        refundRequest.setOutRefundNo(entity.getOutRefundNo());
        refundRequest.setTotalFee(entity.getTotalFee());
        refundRequest.setRefundFeeType(entity.getRefundFeeType());
        refundRequest.setRefundFee(entity.getRefundFee());
        refundRequest.setOpUserId(entity.getOpUserId());
        refundRequest.setRefundAccount(entity.getRefundAccount());
        refundRequest.setRefundDesc(entity.getRefundDesc());
        refundRequest.setNotifyUrl(entity.getNotifyUrl());
        try {
            WxPayRefundResult result = wxPayService.refund(refundRequest);
            PayRefundResult payRefundResult = new PayRefundResult();
            payRefundResult.setReturnCode(result.getReturnCode());
            payRefundResult.setReturnMsg(result.getReturnMsg());
            payRefundResult.setResultCode(result.getResultCode());
            payRefundResult.setErrCode(result.getErrCode());
            payRefundResult.setErrCodeDes(result.getErrCodeDes());
            payRefundResult.setAppid(result.getAppid());
            payRefundResult.setMchId(result.getMchId());
            payRefundResult.setSubAppId(result.getSubAppId());
            payRefundResult.setSubMchId(result.getSubMchId());
            payRefundResult.setNonceStr(result.getNonceStr());
            payRefundResult.setSign(result.getSign());
            payRefundResult.setXmlString(result.getXmlString());

            payRefundResult.setTransactionId(result.getTransactionId());
            payRefundResult.setOutTradeNo(result.getOutTradeNo());
            payRefundResult.setOutRefundNo(result.getOutRefundNo());
            payRefundResult.setRefundId(result.getRefundId());
            payRefundResult.setRefundFee(result.getRefundFee());
            payRefundResult.setSettlementRefundFee(result.getSettlementRefundFee());
            payRefundResult.setTotalFee(result.getTotalFee());
            payRefundResult.setSettlementTotalFee(result.getSettlementTotalFee());
            payRefundResult.setFeeType(result.getFeeType());
            payRefundResult.setCashFee(result.getCashFee());
            payRefundResult.setCashFeeType(result.getCashFeeType());
            payRefundResult.setCashRefundFee(result.getCashRefundFee());
            payRefundResult.setCouponRefundCount(result.getCouponRefundCount());
            payRefundResult.setCouponRefundFee(result.getCouponRefundFee());

            List<PayRefundCouponInfo> list = result.getRefundCoupons().stream().map(item -> {
                PayRefundCouponInfo payRefundCouponInfo = new PayRefundCouponInfo();
                payRefundCouponInfo.setCouponRefundFee(item.getCouponRefundFee());
                payRefundCouponInfo.setCouponRefundId(item.getCouponRefundId());
                payRefundCouponInfo.setCouponType(item.getCouponType());
                return payRefundCouponInfo;
            }).collect(Collectors.toList());
            payRefundResult.setRefundCoupons(list);
            return payRefundResult;
        } catch (WxPayException e) {
            throw new PayServiceException(e.getMessage());
        }
    }

    @Override
    public PayOrderNotifyResult checkSign(Object request) throws PayServiceException {
        String body = (String) request;
        try {
            WxPayOrderNotifyResult wxPayOrderNotifyResult = wxPayService.parseOrderNotifyResult(body);
            PayOrderNotifyResult payOrderNotifyResult = new PayOrderNotifyResult();
            payOrderNotifyResult.setReturnCode(wxPayOrderNotifyResult.getReturnCode());
            payOrderNotifyResult.setReturnMsg(wxPayOrderNotifyResult.getReturnMsg());
            payOrderNotifyResult.setResultCode(wxPayOrderNotifyResult.getResultCode());
            payOrderNotifyResult.setErrCode(wxPayOrderNotifyResult.getErrCode());
            payOrderNotifyResult.setErrCodeDes(wxPayOrderNotifyResult.getErrCodeDes());
            payOrderNotifyResult.setAppid(wxPayOrderNotifyResult.getAppid());
            payOrderNotifyResult.setSubAppId(wxPayOrderNotifyResult.getSubAppId());
            payOrderNotifyResult.setMchId(wxPayOrderNotifyResult.getSubMchId());
            payOrderNotifyResult.setSubMchId(wxPayOrderNotifyResult.getSubMchId());
            payOrderNotifyResult.setNonceStr(wxPayOrderNotifyResult.getNonceStr());
            payOrderNotifyResult.setSign(wxPayOrderNotifyResult.getSign());
            payOrderNotifyResult.setXmlString(wxPayOrderNotifyResult.getXmlString());

            payOrderNotifyResult.setPromotionDetail(wxPayOrderNotifyResult.getPromotionDetail());
            payOrderNotifyResult.setDeviceInfo(wxPayOrderNotifyResult.getDeviceInfo());
            payOrderNotifyResult.setOpenid(wxPayOrderNotifyResult.getOpenid());
            payOrderNotifyResult.setIsSubscribe(wxPayOrderNotifyResult.getIsSubscribe());
            payOrderNotifyResult.setSubOpenid(wxPayOrderNotifyResult.getSubOpenid());
            payOrderNotifyResult.setSubIsSubscribe(wxPayOrderNotifyResult.getSubIsSubscribe());
            payOrderNotifyResult.setTradeType(wxPayOrderNotifyResult.getTradeType());
            payOrderNotifyResult.setBankType(wxPayOrderNotifyResult.getBankType());
            payOrderNotifyResult.setTotalFee(wxPayOrderNotifyResult.getTotalFee());
            payOrderNotifyResult.setSettlementTotalFee(wxPayOrderNotifyResult.getSettlementTotalFee());
            payOrderNotifyResult.setFeeType(wxPayOrderNotifyResult.getFeeType());
            payOrderNotifyResult.setCashFee(wxPayOrderNotifyResult.getCashFee());
            payOrderNotifyResult.setCashFeeType(wxPayOrderNotifyResult.getCashFeeType());
            payOrderNotifyResult.setCouponFee(wxPayOrderNotifyResult.getCouponFee());
            payOrderNotifyResult.setCouponCount(wxPayOrderNotifyResult.getCouponCount());
            if (wxPayOrderNotifyResult.getCouponCount() != null && wxPayOrderNotifyResult.getCouponCount() > 0) {
                List<PayOrderNotifyCoupon> list = wxPayOrderNotifyResult.getCouponList().stream().map(item -> {
                    PayOrderNotifyCoupon coupon = new PayOrderNotifyCoupon();
                    coupon.setCouponFee(item.getCouponFee());
                    coupon.setCouponId(item.getCouponId());
                    coupon.setCouponType(item.getCouponType());
                    return coupon;
                }).collect(Collectors.toList());
                payOrderNotifyResult.setCouponList(list);
            }
            payOrderNotifyResult.setTransactionId(wxPayOrderNotifyResult.getTransactionId());
            payOrderNotifyResult.setOutTradeNo(wxPayOrderNotifyResult.getOutTradeNo());
            payOrderNotifyResult.setAttach(wxPayOrderNotifyResult.getAttach());
            payOrderNotifyResult.setTimeEnd(wxPayOrderNotifyResult.getTimeEnd());
            payOrderNotifyResult.setVersion(wxPayOrderNotifyResult.getVersion());
            return payOrderNotifyResult;
        } catch (WxPayException e) {
            throw new PayServiceException("验证回调签名失败");
        }
    }

}
