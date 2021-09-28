package com.dobbinsoft.fw.pay.model.entpay;

import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import lombok.*;
import me.chanjar.weixin.common.annotation.Required;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

/**
 * <pre>
 * 企业付款请求对象.
 * Created by Binary Wang on 2016/10/19.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixEntPayQueryRequest extends BaseWxPayRequest {
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

  @Override
  protected void checkConstraints() {
    //do nothing
  }

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }

  @Override
  protected String[] getIgnoredParamsForSign() {
    return new String[]{"sign_type"};
  }
}
