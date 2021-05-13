package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.handler.AbstractPayCallbackHandler;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContext;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContextHolder;
import com.dobbinsoft.fw.pay.model.request.PayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.PayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.PayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.result.PayRefundResult;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: PayServiceImpl
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public class PayServiceImpl implements PayService {

    private AliPayServiceImpl aliPayService;

    private WxPayServiceImpl wxPayService;

    public PayServiceImpl(PayProperties payProperties) {
        this.aliPayService = new AliPayServiceImpl(payProperties);
        this.wxPayService = new WxPayServiceImpl(payProperties);
    }

    @Override
    public Object createOrder(PayUnifiedOrderRequest entity) throws PayServiceException {
        if (entity.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.createOrder(entity);
        } else if (entity.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.createOrder(entity);
        } else {
            throw new PayServiceException("支付渠道不支持");
        }
    }

    @Override
    public PayRefundResult refundOrder(PayRefundRequest entity) throws PayServiceException {
        if (entity.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.refundOrder(entity);
        } else if (entity.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.refundOrder(entity);
        } else {
            throw new PayServiceException("支付渠道不支持");
        }
    }

    @Override
    public PayOrderNotifyResult checkSign(Object obj) throws PayServiceException {
        HttpServletRequest servletRequest = (HttpServletRequest) obj;
        PayOrderNotifyResult result;
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
            result.setPayChannelType(PayChannelType.ALI);
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
                result.setPayChannelType(PayChannelType.WX);
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
