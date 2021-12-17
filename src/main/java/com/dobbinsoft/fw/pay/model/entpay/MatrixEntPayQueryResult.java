package com.dobbinsoft.fw.pay.model.entpay;

import com.dobbinsoft.fw.pay.model.result.MatrixBasePayResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 企业付款查询返回结果.
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixEntPayQueryResult extends MatrixBasePayResult {
  private static final long serialVersionUID = 3948485732447456947L;

  /**
   * 商户订单号.
   */
  private String partnerTradeNo;

  /**
   * 付款单号.
   */
  private String detailId;

  /**
   * 转账状态.
   */
  private String status;

  /**
   * 失败原因.
   */
  private String reason;

  /**
   * 收款用户openid.
   */
  private String openid;

  /**
   * 收款用户姓名.
   */
  private String transferName;

  /**
   * 付款金额.
   */
  private Integer paymentAmount;

  /**
   * 发起转账的时间.
   */
  private String transferTime;

  /**
   * 企业付款成功时间.
   */
  private String paymentTime;

  /**
   * 付款描述.
   */
  private String desc;

}
