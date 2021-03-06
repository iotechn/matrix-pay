package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 提交刷卡支付接口响应结果对象类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPayMicropayResult extends MatrixBasePayResult {
    private static final long serialVersionUID = 529670965722059189L;

    /**
     * <pre>
     * 用户标识.
     * openid
     * 是
     * String(128)
     * Y
     * 用户在商户appid 下的唯一标识
     * </pre>
     **/
    private String openid;

    /**
     * <pre>
     * 是否关注公众账号.
     * is_subscribe
     * 是
     * String(1)
     * Y
     * 用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注
     * </pre>
     **/
    private String isSubscribe;

    /**
     * <pre>
     * 付款银行.
     * bank_type
     * 是
     * String(32)
     * CMC
     * 银行类型，采用字符串类型的银行标识，值列表详见银行类型
     * </pre>
     **/
    private String bankType;

    /**
     * <pre>
     * 货币类型.
     * fee_type
     * 否
     * String(16)
     * CNY
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     **/
    private String feeType;

    /**
     * <pre>
     * 订单金额.
     * total_fee
     * 是
     * Int
     * 888
     * 订单总金额，单位为分，只能为整数，详见支付金额
     * </pre>
     **/
    private Integer totalFee;

    /**
     * <pre>
     * 应结订单金额.
     * settlement_total_fee
     * 否
     * Int
     * 100
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     * </pre>
     **/
    private Integer settlementTotalFee;

    /**
     * <pre>
     * 代金券金额.
     * coupon_fee
     * 否
     * Int
     * 100
     * “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额，详见支付金额
     * </pre>
     **/
    private Integer couponFee;

    /**
     * <pre>
     * 现金支付货币类型.
     * cash_fee_type
     * 否
     * String(16)
     * CNY
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     **/
    private String cashFeeType;

    /**
     * <pre>
     * 现金支付金额.
     * cash_fee
     * 是
     * Int
     * 100
     * 订单现金支付金额，详见支付金额
     * </pre>
     **/
    private Integer cashFee;

    /**
     * <pre>
     * 微信支付订单号.
     * transaction_id
     * 是
     * String(32)
     * 1217752501201407033233368018
     * 微信支付订单号
     * </pre>
     **/
    private String transactionId;

    /**
     * <pre>
     * 商户订单号.
     * out_trade_no
     * 是
     * String(32)
     * 1217752501201407033233368018
     * 商户系统的订单号，与请求一致。
     * </pre>
     **/
    private String outTradeNo;

    /**
     * <pre>
     * 商家数据包.
     * attach
     * 否
     * String(128)
     * 123456
     * 商家数据包，原样返回
     * </pre>
     **/
    private String attach;

    /**
     * <pre>
     * 支付完成时间.
     * time_end
     * 是
     * String(14)
     * 20141030133525
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。详见时间规则
     * </pre>
     **/
    private String timeEnd;

    /**
     * <pre>
     * 营销详情.
     * promotion_detail
     * 否
     * String(6000)
     * 示例见下文
     * 新增返回，单品优惠功能字段，需要接入请见详细说明
     * </pre>
     **/
    private String promotionDetail;

}
