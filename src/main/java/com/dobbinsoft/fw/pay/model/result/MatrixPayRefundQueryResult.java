package com.dobbinsoft.fw.pay.model.result;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

/**
 * <pre>
 * 支付-退款查询返回结果
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPayRefundQueryResult extends MatrixBasePayResult {
    /**
     * <pre>
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 类型：String(32)
     * 示例值：013467007045764
     * 描述：终端设备号
     * </pre>
     */
    private String deviceInfo;

    /**
     * <pre>
     * 字段名：支付平台订单号.
     * 变量名：transaction_id
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1217752501201407033233368018
     * 描述：支付平台订单号
     * </pre>
     */
    private String transactionId;

    /**
     * <pre>
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1217752501201407033233368018
     * 描述：商户系统内部的订单号
     * </pre>
     */
    private String outTradeNo;

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
     * 字段名：应结订单金额.
     * 变量名：settlement_total_fee
     * 是否必填：否
     * 类型：Int
     * 示例值：100
     * 描述：应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     * </pre>
     */
    private Integer settlementTotalFee;

    /**
     * <pre>
     * 字段名：货币种类.
     * 变量名：fee_type
     * 是否必填：否
     * 类型：String(8)
     * 示例值：CNY
     * 描述：订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     */
    private String feeType;

    /**
     * <pre>
     * 字段名：现金支付金额.
     * 变量名：cash_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：100
     * 描述：现金支付金额，单位为分，只能为整数，详见支付金额
     * </pre>
     */
    private Integer cashFee;

    /**
     * <pre>
     * 字段名：退款笔数.
     * 变量名：refund_count
     * 是否必填：是
     * 类型：Int
     * 示例值：1
     * 描述：退款记录数
     * </pre>
     */
    private Integer refundCount;

    private List<RefundRecord> refundRecords;

    /**
     * The type Refund record.
     */
    @Data
    @Builder(builderMethodName = "newBuilder")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundRecord {
        /**
         * <pre>
         * 字段名：商户退款单号.
         * 变量名：out_refund_no_$n
         * 是否必填：是
         * 类型：String(32)
         * 示例值：1217752501201407033233368018
         * 描述：商户退款单号
         * </pre>
         */
        private String outRefundNo;

        /**
         * <pre>
         * 字段名：支付平台退款单号.
         * 变量名：refund_id_$n
         * 是否必填：是
         * 类型：String(28)
         * 示例值：1217752501201407033233368018
         * 描述：支付平台退款单号
         * </pre>
         */
        private String refundId;

        /**
         * <pre>
         * 字段名：退款渠道.
         * 变量名：refund_channel_$n
         * 是否必填：否
         * 类型：String(16)
         * 示例值：ORIGINAL
         * 描述：ORIGINAL—原路退款 BALANCE—退回到余额
         * </pre>
         */
        private String refundChannel;

        /**
         * <pre>
         * 字段名：申请退款金额.
         * 变量名：refund_fee_$n
         * 是否必填：是
         * 类型：Int
         * 示例值：100
         * 描述：退款总金额,单位为分,可以做部分退款
         * </pre>
         */
        private Integer refundFee;

        /**
         * <pre>
         * 字段名：退款金额.
         * 变量名：settlement_refund_fee_$n
         * 是否必填：否
         * 类型：Int
         * 示例值：100
         * 描述：退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
         * </pre>
         */
        private Integer settlementRefundFee;

        /**
         * <pre>
         * 字段名：退款资金来源.
         * 变量名：refund_account
         * 是否必填：否
         * 类型：String(30)
         * 示例值：REFUND_SOURCE_RECHARGE_FUNDS
         * 描述：REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款/基本账户, REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款
         * </pre>
         */
        private String refundAccount;

        /**
         * <pre>
         * 字段名：代金券退款金额.
         * 变量名：coupon_refund_fee_$n
         * 是否必填：否
         * 类型：Int
         * 示例值：100
         * 描述：代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠
         * </pre>
         */
        private Integer couponRefundFee;

        /**
         * <pre>
         * 字段名：退款代金券使用数量.
         * 变量名：coupon_refund_count_$n
         * 是否必填：否
         * 类型：Int
         * 示例值：1
         * 描述：退款代金券使用数量 ,$n为下标,从0开始编号
         * </pre>
         */
        private Integer couponRefundCount;

        private List<MatrixPayRefundCouponInfo> refundCoupons;

        /**
         * <pre>
         * 字段名：退款状态.
         * 变量名：refund_status_$n
         * 是否必填：是
         * 类型：String(16)
         * 示例值：SUCCESS
         * 描述：退款状态：
         *  SUCCESS—退款成功，
         *  FAIL—退款失败，
         *  PROCESSING—退款处理中，
         *  CHANGE—转入代发，
         * 退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
         * </pre>
         */
        private String refundStatus;

        /**
         * <pre>
         * 字段名：退款入账账户.
         * 变量名：refund_recv_accout_$n
         * 是否必填：是
         * 类型：String(64)
         * 示例值：招商银行信用卡0403
         * 描述：取当前退款单的退款入账方，1）退回银行卡：{银行名称}{卡类型}{卡尾号}，2）退回支付用户零钱:支付用户零钱
         * </pre>
         */
        private String refundRecvAccount;

        /**
         * <pre>
         * 字段名：退款成功时间.
         * 变量名：refund_success_time_$n
         * 是否必填：否
         * 类型：String(20)
         * 示例值：2016-07-25 15:26:26
         * 描述：退款成功时间，当退款状态为退款成功时有返回。$n为下标，从0开始编号。
         * </pre>
         */
        private String refundSuccessTime;

    }

}

