package com.dobbinsoft.fw.pay.model.entpay;

import com.dobbinsoft.fw.pay.model.result.MatrixBasePayResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 企业付款返回结果
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixEntPayResult extends MatrixBasePayResult {
  /**
   * 商户号.
   */
  private String mchId;

  /**
   * 商户appid.
   */
  private String mchAppid;

  /**
   * 设备号.
   */
  private String deviceInfo;

  //############以下字段在return_code 和result_code都为SUCCESS的时候有返回##############
  /**
   * 商户订单号.
   */
  private String partnerTradeNo;

  /**
   * 微信订单号.
   */
  private String paymentNo;

  /**
   * 微信支付成功时间.
   */
  private String paymentTime;

}
