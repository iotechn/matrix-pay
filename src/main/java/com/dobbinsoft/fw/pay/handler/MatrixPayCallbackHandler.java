package com.dobbinsoft.fw.pay.handler;

import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;

import jakarta.servlet.http.HttpServletRequest;

/**
 * ClassName: MatrixPayCallbackHandler
 * Description: 普通支付，成功通知
 *
 */
public interface MatrixPayCallbackHandler {

    /**
     * 在校验回调之前调用，可以在此添加上下文
     * @param request
     */
    void beforeCheckSign(HttpServletRequest request);

    /**
     * 默认入参 PayOrderNotifyResult
     * @param result
     * @param request 原始请求
     * @return
     */
    Object handle(MatrixPayOrderNotifyResult result, HttpServletRequest request);

}
