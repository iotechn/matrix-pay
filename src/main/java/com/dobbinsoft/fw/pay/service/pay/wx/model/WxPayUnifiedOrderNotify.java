package com.dobbinsoft.fw.pay.service.pay.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "xml")
public class WxPayUnifiedOrderNotify {

    @JsonProperty("appid")
    private String appid;

    @JsonProperty("mch_id")
    private String mchId;

    @JsonProperty("device_info")
    private String deviceInfo;

    @JsonProperty("nonce_str")
    private String nonceStr;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("sign_type")
    private String signType;

    @JsonProperty("body")
    private String body;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("attach")
    private String attach;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("total_fee")
    private int totalFee;

    @JsonProperty("spbill_create_ip")
    private String spbillCreateIp;

    @JsonProperty("time_start")
    private String timeStart;

    @JsonProperty("time_expire")
    private String timeExpire;

    @JsonProperty("goods_tag")
    private String goodsTag;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("trade_type")
    private String tradeType;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("limit_pay")
    private String limitPay;

    @JsonProperty("openid")
    private String openid;

    @JsonProperty("receipt")
    private String receipt;

    @JsonProperty("profit_sharing")
    private String profitSharing;

    @JsonProperty("scene_info")
    private String sceneInfo;

    // The following fields are for notification specific parameters
    @JsonProperty("bank_type")
    private String bankType;

    @JsonProperty("is_subscribe")
    private String isSubscribe;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("time_end")
    private String timeEnd;

    @JsonProperty("result_code")
    private String resultCode;

    @JsonProperty("return_code")
    private String returnCode;

    @JsonProperty("coupon_fee")
    private String couponFee;

    @JsonProperty("coupon_count")
    private String couponCount;

    @JsonProperty("coupon_type")
    private String couponType;

    @JsonProperty("coupon_id")
    private String couponId;
}
