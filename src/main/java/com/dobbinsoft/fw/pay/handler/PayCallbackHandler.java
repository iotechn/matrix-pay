package com.dobbinsoft.fw.pay.handler;

import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;

/**
 * ClassName: AbstractPayCallbackHandler
 * Description: 支付回调处理责任链
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public interface PayCallbackHandler {

    /**
     * 默认入参 PayOrderNotifyResult
     * @param result
     * @return
     */
    Object handle(MatrixPayOrderNotifyResult result);

}
