package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;

/**
 * ClassName: BasePayService
 * Description: 基本支付服务
 *
 */
public abstract class BasePayService {

    protected PayProperties payProperties;

    public BasePayService(PayProperties payProperties) {
        this.payProperties = payProperties;
    }

}
