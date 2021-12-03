package com.dobbinsoft.fw.pay.model.request;

import lombok.*;

/**
 * <pre>
 *   注释中各行对应含义：
 *   字段名
 *   字段
 *   必填
 *   示例值
 *   类型
 *   说明
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayRedpackQueryRequest extends MatrixBasePayRequest {

    private static final long serialVersionUID = -597674982497146841L;
    /**
     * 商户订单号
     * mch_billno
     * 是
     * 10000098201411111234567890
     * String(28)
     * 商户发放红包的商户订单号
     */
    private String mchBillNo;

    /**
     * 订单类型
     * bill_type
     * 是
     * MCHT
     * String(32)
     * MCHT:通过商户订单号获取红包信息。
     */
    private String billType;

}
