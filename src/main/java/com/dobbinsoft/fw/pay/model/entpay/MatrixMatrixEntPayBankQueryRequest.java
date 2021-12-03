package com.dobbinsoft.fw.pay.model.entpay;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 企业付款到银行卡的请求对象
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixMatrixEntPayBankQueryRequest extends MatrixEntPayQueryRequest {
  private static final long serialVersionUID = -479088843124447119L;

}
