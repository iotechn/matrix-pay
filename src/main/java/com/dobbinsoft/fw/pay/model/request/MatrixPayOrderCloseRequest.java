package com.dobbinsoft.fw.pay.model.request;

import lombok.*;

/**
 * <pre>
 *  关闭订单请求对象类
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayOrderCloseRequest extends MatrixBasePayRequest {

    /**
     * <pre>
     * 商户订单号
     * out_trade_no
     * 二选一
     * String(32)
     * 20150806125346
     * 商户系统内部的订单号，当没提供transaction_id时需要传这个。
     * </pre>
     */
    private String outTradeNo;
}
