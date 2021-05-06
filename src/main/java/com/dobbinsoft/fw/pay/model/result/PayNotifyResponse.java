package com.dobbinsoft.fw.pay.model.result;

import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContext;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContextHolder;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;

public class PayNotifyResponse {

    public static Object success(String msg) {
        PayCallbackContext payCallbackContext = PayCallbackContextHolder.get();
        if (payCallbackContext.getPayChannelType() == PayChannelType.WX) {
            return WxPayNotifyResponse.success(msg);
        }
        throw new RuntimeException("待完善");
    }

    public static Object fail(String msg) {
        PayCallbackContext payCallbackContext = PayCallbackContextHolder.get();
        if (payCallbackContext.getPayChannelType() == PayChannelType.WX) {
            return WxPayNotifyResponse.fail(msg);
        }
        throw new RuntimeException("待完善");
    }

}
