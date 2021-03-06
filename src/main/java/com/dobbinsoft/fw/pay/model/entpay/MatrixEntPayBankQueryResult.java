package com.dobbinsoft.fw.pay.model.entpay;

import com.dobbinsoft.fw.pay.model.result.MatrixBasePayResult;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 企业付款到银行卡查询返回结果.
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixEntPayBankQueryResult extends MatrixBasePayResult {

  private static final long serialVersionUID = -8336631015989500746L;

  /**
   * 商户企业付款单号
   */
  private String partnerTradeNo;

  /**
   * 微信企业付款单号.
   * 即为微信内部业务单号
   */
  private String paymentNo;

  /**
   * 银行卡号.
   * 收款用户银行卡号(MD5加密)
   */
  private String bankNoMd5;

  /**
   * 用户真实姓名.
   * 收款人真实姓名（MD5加密）
   */
  private String trueNameMd5;

  /**
   * 付款金额.
   */
  private Integer amount;

  /**
   * 代付单状态.
   * <pre>
   * PROCESSING（处理中，如有明确失败，则返回额外失败原因；否则没有错误原因）
   * SUCCESS（付款成功）
   * FAILED（付款失败）
   * BANK_FAIL（银行退票，订单状态由付款成功流转至退票,退票时付款金额和手续费会自动退还）
   * </pre>
   */
  private String status;

  /**
   * 手续费金额
   */
  private Integer cmmsAmount;

  /**
   * 商户下单时间.
   * 微信侧订单创建时间
   */
  private String createTime;

  /**
   * 成功付款时间.
   * 微信侧付款成功时间（但无法保证银行不会退票）
   */
  private String paySuccessTime;

  /**
   * 失败原因.
   * 订单失败原因（如：余额不足）
   */
  private String failReason;

}
