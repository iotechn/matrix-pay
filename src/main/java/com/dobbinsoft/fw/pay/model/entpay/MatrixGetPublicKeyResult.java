package com.dobbinsoft.fw.pay.model.entpay;

import com.dobbinsoft.fw.pay.model.result.MatrixBasePayResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 *  企业付款获取RSA加密公钥接口返回结果类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MatrixGetPublicKeyResult extends MatrixBasePayResult {
  /**
   * 商户号.
   */
  private String mchId;

  /**
   * 密钥
   */
  private String pubKey;
}
