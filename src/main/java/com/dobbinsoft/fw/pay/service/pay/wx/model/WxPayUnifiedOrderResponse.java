package com.dobbinsoft.fw.pay.service.pay.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
@JacksonXmlRootElement(localName = "xml")
public class WxPayUnifiedOrderResponse {

    @JsonProperty("return_code")
    private String returnCode;
    @JsonProperty("return_msg")
    private String returnMsg;
    @JsonProperty("result_code")
    private String resultCode;
    @JsonProperty("mch_id")
    private String mchId;
    @JsonProperty("appid")
    private String appid;
    @JsonProperty("nonce_str")
    private String nonceStr;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("prepay_id")
    private String prepayId;
    @JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("err_code")
    private String errCode;
    @JsonProperty("err_code_des")
    private String errCodeDes;

}
