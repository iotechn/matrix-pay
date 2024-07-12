package com.dobbinsoft.fw.pay.model.notify;

import com.dobbinsoft.fw.support.utils.JacksonXmlUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信支付订单和退款的异步通知共用的响应类
 */
@Data
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayNotifyResponse {

    private static final String FAIL = "FAIL";
    private static final String SUCCESS = "SUCCESS";

//    @JsonProperty("return_code")
    private String returnCode;

//    @JsonProperty("return_msg")
    private String returnMsg;

    /**
     * Fail string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String fail(String msg) {
        MatrixPayNotifyResponse response = new MatrixPayNotifyResponse(FAIL, msg);
        return JacksonXmlUtil.toXmlString(response);
    }

    /**
     * Success string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String success(String msg) {
        MatrixPayNotifyResponse response = new MatrixPayNotifyResponse(SUCCESS, msg);
        return JacksonXmlUtil.toXmlString(response);
    }

}
