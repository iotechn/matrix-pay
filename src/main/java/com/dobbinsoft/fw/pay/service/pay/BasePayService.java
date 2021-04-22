package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayConfig;

/**
 * ClassName: BasePayService
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public abstract class BasePayService {

    protected PayConfig propertiesHolder;

    public BasePayService(PayConfig propertiesHolder) {
        this.propertiesHolder = propertiesHolder;
    }

}
