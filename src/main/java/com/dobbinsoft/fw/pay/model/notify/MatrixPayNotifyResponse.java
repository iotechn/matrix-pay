package com.dobbinsoft.fw.pay.model.notify;

import com.thoughtworks.xstream.XStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;

/**
 * 微信支付订单和退款的异步通知共用的响应类
 */
@Data
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayNotifyResponse {

    private transient static final String FAIL = "FAIL";
    private transient static final String SUCCESS = "SUCCESS";

    private String returnCode;

    private String returnMsg;

    /**
     * Fail string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String fail(String msg) {
        MatrixPayNotifyResponse response = new MatrixPayNotifyResponse(FAIL, msg);
        XStream xstream = XStreamInitializer.getInstance();
        xstream.autodetectAnnotations(true);
        return xstream.toXML(response);
    }

    /**
     * Success string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String success(String msg) {
        MatrixPayNotifyResponse response = new MatrixPayNotifyResponse(SUCCESS, msg);
        XStream xstream = XStreamInitializer.getInstance();
        xstream.autodetectAnnotations(true);
        return xstream.toXML(response);
    }

}
