package com.dobbinsoft.fw.pay.model.request;

import lombok.*;

import java.io.Serial;

/**
 * <pre>
 * 微信支付-申请退款请求参数
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayRefundRequest extends MatrixBasePayRequest {

    @Serial
    private static final long serialVersionUID = -1999065466412312068L;

    /**
     * <pre>
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 类型：String(32)
     * 示例值：13467007045764
     * 描述：终端设备号
     * </pre>
     */
    private String deviceInfo;
    /**
     * <pre>
     * 字段名：支付平台订单号.
     * 变量名：transaction_id
     * 是否必填：跟out_trade_no二选一
     * 类型：String(28)
     * 示例值：1217752501201400000000000000
     * 描述：支付平台生成的订单号，在支付通知中有返回
     * </pre>
     */
    private String transactionId;
    /**
     * <pre>
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：跟transaction_id二选一
     * 类型：String(32)
     * 示例值：1217752501201400000000000000
     * 描述：商户侧传给支付平台的订单号
     * </pre>
     */
    private String outTradeNo;
    /**
     * <pre>
     * 字段名：商户退款单号.
     * 变量名：out_refund_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1217752501201400000000000000
     * 描述：商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
     * </pre>
     */
    private String outRefundNo;
    /**
     * <pre>
     * 字段名：订单金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：100
     * 描述：订单总金额，单位为分，只能为整数，详见支付金额
     * </pre>
     */
    private Integer totalFee;
    /**
     * <pre>
     * 字段名：退款金额.
     * 变量名：refund_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：100
     * 描述：退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
     * </pre>
     */
    private Integer refundFee;
    /**
     * <pre>
     * 字段名：货币种类.
     * 变量名：refund_fee_type
     * 是否必填：否
     * 类型：String(8)
     * 示例值：CNY
     * 描述：货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     */
    private String refundFeeType;
    /**
     * <pre>
     * 字段名：操作员.
     * 变量名：op_user_id
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1900000109
     * 描述：操作员帐号, 默认为商户号
     * </pre>
     */
    private String opUserId;

    /**
     * <pre>
     * 字段名：退款原因.
     * 变量名：refund_account
     * 是否必填：否
     * 类型：String(80)
     * 示例值：商品已售完
     * 描述：若商户传入，会在下发给用户的退款消息中体现退款原因
     * </pre>
     */
    private String refundDesc;

}
