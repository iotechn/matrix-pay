package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 向微信用户个人发现金红包返回结果
 * https://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=13_5
 *
 * @author kane
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPaySendRedpackResult extends MatrixBasePayResult implements Serializable {
    private static final long serialVersionUID = -4837415036337132073L;

    private String mchBillno;

    private String wxappid;

    private String reOpenid;

    private int totalAmount;

    private String sendTime;

    private String sendListid;

}
