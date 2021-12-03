package com.dobbinsoft.fw.pay.model.entpay;

import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 企业付款到银行卡的响应结果.
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixEntPayBankResult extends BaseWxPayResult {
  private static final long serialVersionUID = 3449707749935227689L;

  /**
   * 代付金额.
   */
  private Integer amount;

  /**
   * 商户企业付款单号.
   */
  private String partnerTradeNo;

  //############以下字段在return_code 和result_code都为SUCCESS的时候有返回##############
  /**
   * 微信企业付款单号.
   * 代付成功后，返回的内部业务单号
   */
  private String paymentNo;

  /**
   * 手续费金额.
   * RMB：分
   */
  private Integer cmmsAmount;

}
