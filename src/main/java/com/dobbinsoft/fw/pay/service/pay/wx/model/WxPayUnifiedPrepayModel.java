package com.dobbinsoft.fw.pay.service.pay.wx.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxPayUnifiedPrepayModel {

    private String appId;
    private String nonceStr;
    private String packageValue;
    private String paySign;
    private String signType;
    private String timeStamp;

}
