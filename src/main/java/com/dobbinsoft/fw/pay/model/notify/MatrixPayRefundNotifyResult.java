package com.dobbinsoft.fw.pay.model.notify;

import com.dobbinsoft.fw.pay.model.result.MatrixBasePayResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * <pre>
 *  退款结果通知对象.
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayRefundNotifyResult extends MatrixBasePayResult implements Serializable {
    private static final long serialVersionUID = 4651725860079259186L;


    /**
     * <pre>
     * 字段名：加密信息.
     * 变量名：req_info
     * 是否必填：是
     * 类型：String(1024)
     * 描述：加密信息请用商户证书与商户秘钥进行解密
     * </pre>
     */
    private String reqInfoString;

    private ReqInfo reqInfo;

    /**
     * 加密信息字段解密后的内容.
     */
    @Data
    @NoArgsConstructor
    public static class ReqInfo {
        @Override
        public String toString() {
            return WxGsonBuilder.create().toJson(this);
        }

        /**
         * <pre>
         * 字段名：微信订单号.
         * 变量名：transaction_id
         * 是否必填：是
         * 类型：String(32)
         * 示例值：1217752501201407033233368018
         * 描述：微信订单号
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
         * 字段名：微信退款单号.
         * 变量名：refund_id
         * 是否必填：是
         * 类型：String(28)
         * 示例值：1217752501201407033233368018
         * 描述：微信退款单号
         * </pre>
         */
        private String refundId;

        /**
         * <pre>
         * 字段名：商户退款单号.
         * 变量名：out_refund_no
         * 是否必填：是
         * 类型：String(64)
         * 示例值：1217752501201407033233368018
         * 描述：商户退款单号
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
         * 字段名：结订单金额.
         * 变量名：settlement_total_fee
         * 是否必填：否
         * 类型：Int
         * 示例值：100
         * 描述：当该订单有使用非充值券时，返回此字段。应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
         * </pre>
         */
        private Integer settlementTotalFee;

        /**
         * <pre>
         * 字段名：申请退款金额.
         * 变量名：refund_fee
         * 是否必填：是
         * 类型：Int
         * 示例值：100
         * 描述：退款总金额,单位为分
         * </pre>
         */
        private Integer refundFee;

        /**
         * <pre>
         * 字段名：退款金额.
         * 变量名：settlement_refund_fee
         * 是否必填：是
         * 类型：Int
         * 示例值：100
         * 描述：退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
         * </pre>
         */
        private Integer settlementRefundFee;

        /**
         * <pre>
         * 字段名：退款状态.
         * 变量名：refund_status
         * 是否必填：是
         * 类型：String(16)
         * 示例值：SUCCESS
         * 描述：SUCCESS-退款成功，CHANGE-退款异常，REFUNDCLOSE—退款关闭
         * </pre>
         */
        private String refundStatus;

        /**
         * <pre>
         * 字段名：退款成功时间.
         * 变量名：success_time
         * 是否必填：否
         * 类型： String(20)
         * 示例值：2017-12-15 09:46:01
         * 资金退款至用户帐号的时间，格式2017-12-15 09:46:01
         * </pre>
         */
        private String successTime;

        /**
         * <pre>
         * 字段名：退款入账账户.
         * 变量名：refund_recv_accout
         * 是否必填：是
         * 类型：String(64)
         * 示例值：招商银行信用卡0403
         * 描述：取当前退款单的退款入账方，1）退回银行卡：{银行名称}{卡类型}{卡尾号}，2）退回支付用户零钱:支付用户零钱 ，3）退还商户: 商户基本账户，商户结算银行账户，4）退回支付用户零钱通: 支付用户零钱通
         * </pre>
         */
        private String refundRecvAccout;

        /**
         * <pre>
         * 字段名：退款资金来源.
         * 变量名：refund_account
         * 是否必填：是
         * 类型：String(30)
         * 示例值：REFUND_SOURCE_RECHARGE_FUNDS
         * 描述：REFUND_SOURCE_RECHARGE_FUNDS 可用余额退款/基本账户，REFUND_SOURCE_UNSETTLED_FUNDS 未结算资金退款
         * </pre>
         */
        private String refundAccount;

        /**
         * <pre>
         * 字段名：退款发起来源.
         * 变量名：refund_request_source
         * 是否必填：是
         * 类型：String(30)
         * 示例值：API
         * 描述：API接口，VENDOR_PLATFORM商户平台
         * </pre>
         */
        private String refundRequestSource;

    }

}
