package com.dobbinsoft.fw.pay.service.pay.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "xml")
public class WxPayRefundRequest {

    @JsonProperty("appid")
    private String appid;

    @JsonProperty("mch_id")
    private String mchId;

    @JsonProperty("nonce_str")
    private String nonceStr;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("sign_type")
    private String signType;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("out_refund_no")
    private String outRefundNo;

    @JsonProperty("total_fee")
    private Integer totalFee;

    @JsonProperty("refund_fee")
    private Integer refundFee;

    @JsonProperty("refund_fee_type")
    private String refundFeeType;

    @JsonProperty("refund_desc")
    private String refundDesc;

    @JsonProperty("refund_account")
    private String refundAccount;

    @JsonProperty("notify_url")
    private String notifyUrl;
}
