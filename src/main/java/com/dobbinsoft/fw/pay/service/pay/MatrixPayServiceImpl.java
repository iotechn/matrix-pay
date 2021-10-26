package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContext;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContextHolder;
import com.dobbinsoft.fw.pay.model.coupon.*;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayRefundNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixScanPayNotifyResult;
import com.dobbinsoft.fw.pay.model.request.*;
import com.dobbinsoft.fw.pay.model.result.*;
import com.github.binarywang.wxpay.bean.WxPayApiData;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: PayServiceImpl
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public class MatrixPayServiceImpl implements MatrixPayService {

    private AliPayServiceImpl aliPayService;

    private WxPayServiceImpl wxPayService;

    public MatrixPayServiceImpl(PayProperties payProperties) {
        this.aliPayService = new AliPayServiceImpl(payProperties);
        this.wxPayService = new WxPayServiceImpl(payProperties);
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws PayServiceException {
        if (entity.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.createOrder(entity);
        } else if (entity.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.createOrder(entity);
        } else {
            throw new PayServiceException("支付渠道不支持");
        }
    }

    @Override
    public void configWarmUp(List<PayProperties> list) {
        // 仅支付宝需要预热
        this.aliPayService.configWarmUp(list);
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
        if (request.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.queryOrder(request);
        } else if (request.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.queryOrder(request);
        } else {
            throw new PayServiceException("支付渠道不支持");
        }
    }

    @Override
    public MatrixPayOrderCloseResult closeOrder(MatrixPayOrderCloseRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest request) throws MatrixPayException {
        if (request.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.refund(request);
        } else if (request.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.refund(request);
        } else {
            throw new PayServiceException("支付渠道不支持");
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
        if (request.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.micropay(request);
        } else if (request.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.micropay(request);
        } else {
            throw new PayServiceException("支付渠道不支持");
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
    public MatrixPayOrderNotifyResult checkSign(Object obj) throws MatrixPayException {
        HttpServletRequest servletRequest = (HttpServletRequest) obj;
        MatrixPayOrderNotifyResult result;
        if ("RSA2".equals(servletRequest.getParameter("sign_type"))) {
            // 支付宝依据
            Map<String, String> map = new HashMap<>();
            servletRequest.getParameterMap().forEach((k, v) -> {
                map.put(k, v[0]);
            });
            PayCallbackContext payCallbackContext = new PayCallbackContext();
            payCallbackContext.setPayChannelType(PayChannelType.ALI);
            PayCallbackContextHolder.set(payCallbackContext);
            result = aliPayService.checkSign(map);
            PayCallbackContextHolder.setPayId(result.getTransactionId());
            result.setPayChannel(PayChannelType.ALI);
        } else {
            ServletInputStream is = null;
            try {
                is = servletRequest.getInputStream();
                byte[] bytes = new byte[servletRequest.getContentLength()];
                is.read(bytes);
                String str = new String(bytes);
                PayCallbackContext payCallbackContext = new PayCallbackContext();
                payCallbackContext.setPayChannelType(PayChannelType.WX);
                PayCallbackContextHolder.set(payCallbackContext);
                result = wxPayService.checkSign(str);
                PayCallbackContextHolder.setPayId(result.getTransactionId());
                result.setPayChannel(PayChannelType.WX);
            } catch (IOException e) {
                throw new PayServiceException("支付回调，网络异常");
            } finally {
                if (is == null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return result;
    }
}
