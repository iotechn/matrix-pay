package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;

/**
 * ClassName: BasePayService
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public abstract class BasePayService {

    protected PayProperties payProperties;

    public BasePayService(PayProperties payProperties) {
        this.payProperties = payProperties;
    }

}
