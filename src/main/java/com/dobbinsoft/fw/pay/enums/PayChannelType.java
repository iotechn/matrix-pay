package com.dobbinsoft.fw.pay.enums;

/**
 * ClassName: PayChannel
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public enum PayChannelType {
//    (1:支付宝 2:微信 3:银联)
    ALI(1, "支付宝"),
    WX(2, "微信"),
    UNION(3, "银联")
    ;

    private int code;
    private String msg;

    PayChannelType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
