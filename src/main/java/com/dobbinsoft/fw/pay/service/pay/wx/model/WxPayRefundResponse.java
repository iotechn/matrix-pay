package com.dobbinsoft.fw.pay.service.pay.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "xml")
public class WxPayRefundResponse {

    @JsonProperty("return_code")
    private String returnCode;
    @JsonProperty("return_msg")
    private String returnMsg;
    @JsonProperty("appid")
    private String appid;
    @JsonProperty("mch_id")
    private String mchId;
    @JsonProperty("nonce_str")
    private String nonceStr;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("result_code")
    private String resultCode;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    @JsonProperty("out_refund_no")
    private String outRefundNo;
    @JsonProperty("refund_id")
    private String refundId;
    @JsonProperty("refund_fee")
    private String refundFee;
}
