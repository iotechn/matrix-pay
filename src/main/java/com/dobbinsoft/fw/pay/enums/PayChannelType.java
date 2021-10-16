package com.dobbinsoft.fw.pay.enums;

/**
 * ClassName: PayChannel
 * Description: 支付渠道类型枚举
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public enum PayChannelType {
    WX("WX", "微信支付"),
    ALI("ALI", "支付宝"),
    YSF("YSF", "云闪付"),
    OFFLINE("OFFLINE", "线下支付"),
    ;

    private String code;
    private String msg;

    PayChannelType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static PayChannelType getByCode(String code) {
        for (PayChannelType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
