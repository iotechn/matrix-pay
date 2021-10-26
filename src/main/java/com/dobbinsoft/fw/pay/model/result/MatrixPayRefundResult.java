package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 微信支付-申请退款返回结果.
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
 * </pre>
 *
 * @author liukaitj & Binary Wang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPayRefundResult extends MatrixBasePayResult implements Serializable {
    private static final long serialVersionUID = -3392333879907788033L;
    /**
     * 支付订单号.
     */
    private String transactionId;

    /**
     * 商户订单号.
     */
    private String outTradeNo;

    /**
     * 商户退款单号.
     */
    private String outRefundNo;

    /**
     * 支付退款单号.
     */
    private String refundId;

    /**
     * 退款金额.
     */
    private Integer refundFee;

    /**
     * 应结退款金额.
     */
    private Integer settlementRefundFee;

    /**
     * 标价金额.
     */
    private Integer totalFee;

    /**
     * 应结订单金额.
     */
    private Integer settlementTotalFee;

    /**
     * 标价币种.
     */
    private String feeType;

    /**
     * 现金支付金额.
     */
    private Integer cashFee;

    /**
     * 现金支付币种.
     */
    private String cashFeeType;

    /**
     * 现金退款金额，单位为分，只能为整数，详见支付金额.
     */
    private Integer cashRefundFee;

    /**
     * 退款代金券使用数量.
     */
    private Integer couponRefundCount;

    /**
     * <pre>
     * 字段名：代金券退款总金额.
     * 变量名：coupon_refund_fee
     * 是否必填：否
     * 类型：Int
     * 示例值：100
     * 描述：代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠
     * </pre>
     */
    private Integer couponRefundFee;

    private List<MatrixPayRefundCouponInfo> refundCoupons;

}
