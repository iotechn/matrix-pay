package com.dobbinsoft.fw.pay.model.entpay;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 企业付款到银行卡的请求对象
 * Created by Binary Wang on 2017/12/21.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixMatrixEntPayBankQueryRequest extends MatrixEntPayQueryRequest {
  private static final long serialVersionUID = -479088843124447119L;

  @Override
  protected boolean ignoreAppid() {
    return true;
  }
}
