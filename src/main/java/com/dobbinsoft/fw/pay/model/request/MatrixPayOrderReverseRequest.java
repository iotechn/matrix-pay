package com.dobbinsoft.fw.pay.model.request;

import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 撤销订单请求类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayOrderReverseRequest extends MatrixBasePayRequest {

    private static final long serialVersionUID = 2329190322989767093L;
    /**
     * <pre>
     * 微信订单号
     * transaction_id
     * String(28)
     * 1217752501201400000000000000
     * 微信的订单号，优先使用
     * </pre>
     */
    private String transactionId;

    /**
     * <pre>
     * 商户订单号
     * out_trade_no
     * String(32)
     * 1217752501201400000000000000
     * 商户系统内部的订单号
     * transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no
     * </pre>
     */
    private String outTradeNo;

}
