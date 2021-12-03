package com.dobbinsoft.fw.pay.model.entpay;

import com.dobbinsoft.fw.pay.model.request.MatrixBasePayRequest;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import lombok.*;
import me.chanjar.weixin.common.annotation.Required;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

/**
 * <pre>
 * 企业付款请求对象.
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixEntPayQueryRequest extends MatrixBasePayRequest {
  private static final long serialVersionUID = 1972288742207813985L;

  /**
   * <pre>
   * 字段名：商户订单号.
   * 变量名：partner_trade_no
   * 是否必填：是
   * 示例值：10000098201411111234567890
   * 类型：String
   * 描述商户订单号
   * </pre>
   */
  @Required
  private String partnerTradeNo;

}
