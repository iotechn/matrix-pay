package com.dobbinsoft.fw.pay.model.request;

/**
 * <pre>
 *  支付请求默认对象类
 *  Created by BinaryWang on 2017/6/18.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class MatrixPayDefaultRequest extends MatrixBasePayRequest {
    @Override
    protected void checkConstraints() {
        //do nothing
    }

    @Override
    protected boolean ignoreAppid() {
        return true;
    }
}
