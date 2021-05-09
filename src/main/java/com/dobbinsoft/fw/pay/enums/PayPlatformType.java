package com.dobbinsoft.fw.pay.enums;

/**
 * ClassName: PayPlatform
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public enum PayPlatformType {
    APP(1, "APP"),
    WEB(2, "Web"),
    MP(3, "小程序");

    private int code;
    private String msg;

    PayPlatformType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static PayPlatformType getByCode(int code) {
        for (PayPlatformType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
