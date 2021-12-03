package com.dobbinsoft.fw.pay.anntation;

import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatrixNotNull {

    /**
     * 需要非空的平台
     * @return
     */
    PayChannelType[] channels() default {PayChannelType.WX, PayChannelType.ALI, PayChannelType.YSF};

    /**
     * 若为空，返回的异常信息
     * 可使用转义占位符 {channel}
     * @return
     */
    String msg();

}
