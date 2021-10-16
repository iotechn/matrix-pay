package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.coupon.*;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyCoupon;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayRefundNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixScanPayNotifyResult;
import com.dobbinsoft.fw.pay.model.request.*;
import com.dobbinsoft.fw.pay.model.result.*;
import com.dobbinsoft.fw.pay.util.MatrixBeanUtils;
import com.github.binarywang.wxpay.bean.WxPayApiData;
import com.github.binarywang.wxpay.bean.coupon.*;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxScanPayNotifyResult;
import com.github.binarywang.wxpay.bean.request.*;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.EntPayService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.EntPayServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: WxPayServiceImpl
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public class WxPayServiceImpl implements MatrixPayService {

    private static final Logger logger = LoggerFactory.getLogger(WxPayServiceImpl.class);

    private WxPayService wxPayService;

    // TODO
    private MatrixEntPayService matrixEntPayService;

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
        EntPayService entPayService = new EntPayServiceImpl(this.wxPayService);
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws PayServiceException {
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        MatrixBeanUtils.copyWxProperties(entity, wxPayUnifiedOrderRequest);
        try {
            return wxPayService.createOrder(wxPayUnifiedOrderRequest);
        } catch (WxPayException e) {
            throw new PayServiceException(e.getMessage());
        }
    }

    @Override
    public MatrixEntPayService getEntPayService() {
        return matrixEntPayService;
    }

    @Override
    public void setEntPayService(MatrixEntPayService entPayService) {
        this.matrixEntPayService = entPayService;
    }

    @Override
    public MatrixPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws MatrixPayException {
        WxPayOrderQueryResult wxPayOrderQueryResult = null;
        try {
            wxPayOrderQueryResult = this.wxPayService.queryOrder(transactionId, outTradeNo);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        return buildPayOrderQueryResult(wxPayOrderQueryResult);
    }

    @Override
    public MatrixPayOrderQueryResult queryOrder(MatrixPayOrderQueryRequest request) throws MatrixPayException {
        WxPayOrderQueryRequest wxPayOrderQueryRequest = new WxPayOrderQueryRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayOrderQueryRequest);
        WxPayOrderQueryResult wxPayOrderQueryResult = null;
        try {
            wxPayOrderQueryResult = this.wxPayService.queryOrder(wxPayOrderQueryRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        return buildPayOrderQueryResult(wxPayOrderQueryResult);
    }

    /**
     * 构建订单查询结果，抽取方法
     * @param wxPayOrderQueryResult
     * @return
     */
    private MatrixPayOrderQueryResult buildPayOrderQueryResult(WxPayOrderQueryResult wxPayOrderQueryResult) {
        MatrixPayOrderQueryResult result = new MatrixPayOrderQueryResult();
        MatrixBeanUtils.copyWxProperties(wxPayOrderQueryResult, result);
        if (wxPayOrderQueryResult.getCoupons() != null) {
            List<MatrixPayOrderQueryResult.Coupon> list = wxPayOrderQueryResult.getCoupons().stream().map(item -> {
                MatrixPayOrderQueryResult.Coupon coupon = new MatrixPayOrderQueryResult.Coupon();
                MatrixBeanUtils.copyWxProperties(item, coupon);
                return coupon;
            }).collect(Collectors.toList());
            result.setCoupons(list);
        }
        return result;
    }

    @Override
    public MatrixPayOrderCloseResult closeOrder(String outTradeNo) throws MatrixPayException {
        WxPayOrderCloseResult wxPayOrderCloseResult = null;
        try {
            wxPayOrderCloseResult = wxPayService.closeOrder(outTradeNo);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayOrderCloseResult result = new MatrixPayOrderCloseResult();
        MatrixBeanUtils.copyWxProperties(wxPayOrderCloseResult, result);
        return result;
    }

    @Override
    public MatrixPayOrderCloseResult closeOrder(MatrixPayOrderCloseRequest request) throws MatrixPayException {
        WxPayOrderCloseRequest wxPayOrderCloseRequest = new WxPayOrderCloseRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayOrderCloseRequest);
        WxPayOrderCloseResult wxPayOrderCloseResult = null;
        try {
            wxPayOrderCloseResult = wxPayService.closeOrder(wxPayOrderCloseRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayOrderCloseResult result = new MatrixPayOrderCloseResult();
        MatrixBeanUtils.copyWxProperties(wxPayOrderCloseResult, result);
        return result;
    }

    @Override
    public MatrixPayUnifiedOrderResult unifiedOrder(MatrixPayUnifiedOrderRequest request) throws MatrixPayException {
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayUnifiedOrderRequest);
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = null;
        try {
            wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayUnifiedOrderResult result = new MatrixPayUnifiedOrderResult();
        MatrixBeanUtils.copyWxProperties(wxPayUnifiedOrderResult, result);
        return result;
    }

    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws MatrixPayException {
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        MatrixBeanUtils.copyWxProperties(entity, refundRequest);
        WxPayRefundResult result = null;
        try {
            result = wxPayService.refund(refundRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayRefundResult matrixPayRefundResult = new MatrixPayRefundResult();
        MatrixBeanUtils.copyWxProperties(result, matrixPayRefundResult);
        List<MatrixPayRefundCouponInfo> list = result.getRefundCoupons().stream().map(item -> {
            MatrixPayRefundCouponInfo payRefundCouponInfo = new MatrixPayRefundCouponInfo();
            payRefundCouponInfo.setCouponRefundFee(item.getCouponRefundFee());
            payRefundCouponInfo.setCouponRefundId(item.getCouponRefundId());
            payRefundCouponInfo.setCouponType(item.getCouponType());
            return payRefundCouponInfo;
        }).collect(Collectors.toList());
        matrixPayRefundResult.setRefundCoupons(list);
        return matrixPayRefundResult;
    }

    @Override
    public MatrixPayRefundQueryResult refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws MatrixPayException {
        WxPayRefundQueryResult wxPayRefundQueryResult = null;
        try {
            wxPayRefundQueryResult = wxPayService.refundQuery(transactionId, outTradeNo, outRefundNo, refundId);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        return buildRefundQueryResult(wxPayRefundQueryResult);
    }

    @Override
    public MatrixPayRefundQueryResult refundQuery(MatrixPayRefundQueryRequest request) throws MatrixPayException {
        WxPayRefundQueryRequest wxPayRefundQueryRequest = new WxPayRefundQueryRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayRefundQueryRequest);
        WxPayRefundQueryResult wxPayRefundQueryResult = null;
        try {
            wxPayRefundQueryResult = wxPayService.refundQuery(wxPayRefundQueryRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        return buildRefundQueryResult(wxPayRefundQueryResult);
    }

    /**
     * 构建退款查询结果
     * @param wxPayRefundQueryResult
     * @return
     */
    private MatrixPayRefundQueryResult buildRefundQueryResult(WxPayRefundQueryResult wxPayRefundQueryResult) {
        MatrixPayRefundQueryResult result = new MatrixPayRefundQueryResult();
        MatrixBeanUtils.copyWxProperties(wxPayRefundQueryResult, result);
        if (wxPayRefundQueryResult.getRefundRecords() != null) {
            List<MatrixPayRefundQueryResult.RefundRecord> list = wxPayRefundQueryResult.getRefundRecords().stream().map(item -> {
                MatrixPayRefundQueryResult.RefundRecord record = new MatrixPayRefundQueryResult.RefundRecord();
                MatrixBeanUtils.copyWxProperties(item, record);
                return record;
            }).collect(Collectors.toList());
            result.setRefundRecords(list);
        }
        return result;
    }

    @Override
    public MatrixPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws MatrixPayException {
        WxPayOrderNotifyResult wxPayOrderNotifyResult = null;
        try {
            wxPayOrderNotifyResult = wxPayService.parseOrderNotifyResult(xmlData);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayOrderNotifyResult result = new MatrixPayOrderNotifyResult();
        MatrixBeanUtils.copyWxProperties(wxPayOrderNotifyResult, result);
        return result;
    }

    @Override
    public MatrixPayRefundNotifyResult parseRefundNotifyResult(String xmlData) throws MatrixPayException {
        WxPayRefundNotifyResult wxPayRefundNotifyResult = null;
        try {
            wxPayRefundNotifyResult = wxPayService.parseRefundNotifyResult(xmlData);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayRefundNotifyResult result = new MatrixPayRefundNotifyResult();
        MatrixBeanUtils.copyWxProperties(wxPayRefundNotifyResult, result);
        return result;
    }

    @Override
    public MatrixScanPayNotifyResult parseScanPayNotifyResult(String xmlData) throws MatrixPayException {
        WxScanPayNotifyResult wxScanPayNotifyResult = null;
        try {
            wxScanPayNotifyResult = wxPayService.parseScanPayNotifyResult(xmlData);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixScanPayNotifyResult result = new MatrixScanPayNotifyResult();
        MatrixBeanUtils.copyWxProperties(wxScanPayNotifyResult, result);
        return result;
    }

    @Override
    public MatrixPaySendRedpackResult sendRedpack(MatrixPaySendRedpackRequest request) throws MatrixPayException {
        WxPaySendRedpackRequest wxPaySendRedpackRequest = new WxPaySendRedpackRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPaySendRedpackRequest);
        WxPaySendRedpackResult wxPaySendRedpackResult = null;
        try {
            wxPaySendRedpackResult = wxPayService.sendRedpack(wxPaySendRedpackRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPaySendRedpackResult result = new MatrixPaySendRedpackResult();
        MatrixBeanUtils.copyWxProperties(wxPaySendRedpackResult, request);
        return result;
    }

    @Override
    public MatrixPayRedpackQueryResult queryRedpack(String mchBillNo) throws MatrixPayException {
        WxPayRedpackQueryResult wxPayRedpackQueryResult = null;
        try {
            wxPayRedpackQueryResult = wxPayService.queryRedpack(mchBillNo);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        return buildPayRedpackQueryResult(wxPayRedpackQueryResult);
    }

    @Override
    public MatrixPayRedpackQueryResult queryRedpack(MatrixPayRedpackQueryRequest request) throws MatrixPayException {

        return null;
    }

    /**
     * 创建红包查询结果
     * @param wxPayRedpackQueryResult
     * @return
     */
    private MatrixPayRedpackQueryResult buildPayRedpackQueryResult(WxPayRedpackQueryResult wxPayRedpackQueryResult) {
        MatrixPayRedpackQueryResult result = new MatrixPayRedpackQueryResult();
        MatrixBeanUtils.copyWxProperties(wxPayRedpackQueryResult, result);
        if (wxPayRedpackQueryResult.getRedpackList() != null) {
            List<MatrixPayRedpackQueryResult.RedpackInfo> list = wxPayRedpackQueryResult.getRedpackList().stream().map(item -> {
                MatrixPayRedpackQueryResult.RedpackInfo info = new MatrixPayRedpackQueryResult.RedpackInfo();
                MatrixBeanUtils.copyWxProperties(item, info);
                return info;
            }).collect(Collectors.toList());
            result.setRedpackList(list);
        }
        return result;
    }

    @Override
    public byte[] createScanPayQrcodeMode1(String productId, File logoFile, Integer sideLength) {
        return wxPayService.createScanPayQrcodeMode1(productId, logoFile, sideLength);
    }

    @Override
    public String createScanPayQrcodeMode1(String productId) {
        return wxPayService.createScanPayQrcodeMode1(productId);
    }

    @Override
    public byte[] createScanPayQrcodeMode2(String codeUrl, File logoFile, Integer sideLength) {
        return wxPayService.createScanPayQrcodeMode2(codeUrl, logoFile, sideLength);
    }

    @Override
    public void report(MatrixPayReportRequest request) throws MatrixPayException {
        WxPayReportRequest wxPayReportRequest = new WxPayReportRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayReportRequest);
        try {
            wxPayService.report(wxPayReportRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public String downloadRawBill(String billDate, String billType, String tarType, String deviceInfo) throws MatrixPayException {
        try {
            return wxPayService.downloadRawBill(billDate, billType, tarType, deviceInfo);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public String downloadRawBill(MatrixPayDownloadBillRequest request) throws MatrixPayException {
        WxPayDownloadBillRequest wxPayDownloadBillRequest = new WxPayDownloadBillRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayDownloadBillRequest);
        try {
            return wxPayService.downloadRawBill(wxPayDownloadBillRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public MatrixPayBillResult downloadBill(String billDate, String billType, String tarType, String deviceInfo) throws MatrixPayException {
        WxPayBillResult wxPayBillResult = null;
        try {
            wxPayBillResult = wxPayService.downloadBill(billDate, billType, tarType, deviceInfo);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayBillResult result = new MatrixPayBillResult();
        MatrixBeanUtils.copyWxProperties(wxPayBillResult, result);
        return result;
    }

    @Override
    public MatrixPayBillResult downloadBill(MatrixPayDownloadBillRequest request) throws MatrixPayException {
        WxPayDownloadBillRequest wxPayDownloadBillRequest = new WxPayDownloadBillRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayDownloadBillRequest);
        WxPayBillResult wxPayBillResult = null;
        try {
            wxPayBillResult = wxPayService.downloadBill(wxPayDownloadBillRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayBillResult result = new MatrixPayBillResult();
        MatrixBeanUtils.copyWxProperties(wxPayBillResult, request);
        return result;
    }

    @Override
    public MatrixPayFundFlowResult downloadFundFlow(String billDate, String accountType, String tarType) throws MatrixPayException {
        WxPayFundFlowResult wxPayFundFlowResult = null;
        try {
            wxPayFundFlowResult = wxPayService.downloadFundFlow(billDate, accountType, tarType);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayFundFlowResult result = new MatrixPayFundFlowResult();
        MatrixBeanUtils.copyWxProperties(wxPayFundFlowResult, result);
        return result;
    }

    @Override
    public MatrixPayFundFlowResult downloadFundFlow(MatrixPayDownloadFundFlowRequest request) throws MatrixPayException {
        WxPayDownloadFundFlowRequest wxPayDownloadFundFlowRequest = new WxPayDownloadFundFlowRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayDownloadFundFlowRequest);
        WxPayFundFlowResult wxPayFundFlowResult = null;
        try {
            wxPayFundFlowResult = wxPayService.downloadFundFlow(wxPayDownloadFundFlowRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayFundFlowResult result = new MatrixPayFundFlowResult();
        MatrixBeanUtils.copyWxProperties(wxPayFundFlowResult, result);
        return result;
    }

    @Override
    public MatrixPayMicropayResult micropay(MatrixPayMicropayRequest request) throws MatrixPayException {
        WxPayMicropayRequest wxPayMicropayRequest = new WxPayMicropayRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayMicropayRequest);
        WxPayMicropayResult microPayResult = null;
        try {
            microPayResult = wxPayService.micropay(wxPayMicropayRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayMicropayResult matrixPayMicropayResult = new MatrixPayMicropayResult();
        MatrixBeanUtils.copyWxProperties(matrixPayMicropayResult, microPayResult);
        return matrixPayMicropayResult;
    }

    @Override
    public MatrixPayOrderReverseResult reverseOrder(MatrixPayOrderReverseRequest request) throws MatrixPayException {
        WxPayOrderReverseRequest wxPayOrderReverseRequest = new WxPayOrderReverseRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayOrderReverseRequest);
        WxPayOrderReverseResult wxPayOrderReverseResult = null;
        try {
            wxPayOrderReverseResult = wxPayService.reverseOrder(wxPayOrderReverseRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayOrderReverseResult reverseResult = new MatrixPayOrderReverseResult();
        MatrixBeanUtils.copyWxProperties(wxPayOrderReverseResult, reverseResult);
        return reverseResult;
    }

    @Override
    public String shorturl(MatrixPayShorturlRequest request) throws MatrixPayException {
        WxPayShorturlRequest wxPayShorturlRequest = new WxPayShorturlRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayShorturlRequest);
        try {
            return wxPayService.shorturl(wxPayShorturlRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public String shorturl(String longUrl) throws MatrixPayException {
        try {
            return wxPayService.shorturl(longUrl);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public String authcode2Openid(MatrixPayAuthcode2OpenidRequest request) throws MatrixPayException {
        WxPayAuthcode2OpenidRequest wxPayAuthcode2OpenidRequest = new WxPayAuthcode2OpenidRequest();
        try {
            return wxPayService.authcode2Openid(wxPayAuthcode2OpenidRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public String authcode2Openid(String authCode) throws MatrixPayException {
        try {
            return wxPayService.authcode2Openid(authCode);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public String getSandboxSignKey() throws MatrixPayException {
        try {
            return wxPayService.getSandboxSignKey();
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public MatrixPayCouponSendResult sendCoupon(MatrixPayCouponSendRequest request) throws MatrixPayException {
        WxPayCouponSendRequest wxPayCouponSendRequest = new WxPayCouponSendRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayCouponSendRequest);
        WxPayCouponSendResult wxPayCouponSendResult = null;
        try {
            wxPayCouponSendResult = wxPayService.sendCoupon(wxPayCouponSendRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayCouponSendResult result = new MatrixPayCouponSendResult();
        MatrixBeanUtils.copyWxProperties(wxPayCouponSendResult, request);
        return result;
    }

    @Override
    public MatrixPayCouponStockQueryResult queryCouponStock(MatrixPayCouponStockQueryRequest request) throws MatrixPayException {
        WxPayCouponStockQueryRequest wxPayCouponStockQueryRequest = new WxPayCouponStockQueryRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayCouponStockQueryRequest);
        WxPayCouponStockQueryResult wxPayCouponStockQueryResult = null;
        try {
            wxPayCouponStockQueryResult = wxPayService.queryCouponStock(wxPayCouponStockQueryRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayCouponStockQueryResult result = new MatrixPayCouponStockQueryResult();
        MatrixBeanUtils.copyWxProperties(wxPayCouponStockQueryResult, result);
        return result;
    }

    @Override
    public MatrixPayCouponInfoQueryResult queryCouponInfo(MatrixPayCouponInfoQueryRequest request) throws MatrixPayException {
        WxPayCouponInfoQueryRequest wxPayCouponInfoQueryRequest = new WxPayCouponInfoQueryRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayCouponInfoQueryRequest);
        WxPayCouponInfoQueryResult wxPayCouponInfoQueryResult = null;
        try {
            wxPayCouponInfoQueryResult = wxPayService.queryCouponInfo(wxPayCouponInfoQueryRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayCouponInfoQueryResult result = new MatrixPayCouponInfoQueryResult();
        MatrixBeanUtils.copyWxProperties(wxPayCouponInfoQueryResult, result);
        return result;
    }

    @Override
    public WxPayApiData getWxApiData() {
        return wxPayService.getWxApiData();
    }

    @Override
    public String queryComment(Date beginDate, Date endDate, Integer offset, Integer limit) throws MatrixPayException {
        try {
            return wxPayService.queryComment(beginDate, endDate, offset, limit);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public String queryComment(MatrixPayQueryCommentRequest request) throws MatrixPayException {
        WxPayQueryCommentRequest wxPayQueryCommentRequest = new WxPayQueryCommentRequest();
        MatrixBeanUtils.copyWxProperties(request, wxPayQueryCommentRequest);
        try {
            return wxPayService.queryComment(wxPayQueryCommentRequest);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
    }

    @Override
    public MatrixPayOrderNotifyResult checkSign(Object request) throws MatrixPayException {
        String body = (String) request;
        WxPayOrderNotifyResult wxPayOrderNotifyResult = null;
        try {
            wxPayOrderNotifyResult = wxPayService.parseOrderNotifyResult(body);
        } catch (WxPayException e) {
            throw new MatrixPayException(e);
        }
        MatrixPayOrderNotifyResult matrixPayOrderNotifyResult = new MatrixPayOrderNotifyResult();
        MatrixBeanUtils.copyWxProperties(wxPayOrderNotifyResult, matrixPayOrderNotifyResult);
        if (wxPayOrderNotifyResult.getCouponCount() != null && wxPayOrderNotifyResult.getCouponCount() > 0) {
            List<MatrixPayOrderNotifyCoupon> list = wxPayOrderNotifyResult.getCouponList().stream().map(item -> {
                MatrixPayOrderNotifyCoupon coupon = new MatrixPayOrderNotifyCoupon();
                coupon.setCouponFee(item.getCouponFee());
                coupon.setCouponId(item.getCouponId());
                coupon.setCouponType(item.getCouponType());
                return coupon;
            }).collect(Collectors.toList());
            matrixPayOrderNotifyResult.setCouponList(list);
        }
        return matrixPayOrderNotifyResult;
    }

}
