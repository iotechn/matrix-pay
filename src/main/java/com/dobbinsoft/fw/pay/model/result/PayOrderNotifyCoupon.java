package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付异步通知代金券详细.
 *
 * @author aimilin
 */
@Data
@NoArgsConstructor
public class PayOrderNotifyCoupon implements Serializable {
  private static final long serialVersionUID = -4165343733538156097L;

  private String couponId;

  private String couponType;

  private Integer couponFee;

}
