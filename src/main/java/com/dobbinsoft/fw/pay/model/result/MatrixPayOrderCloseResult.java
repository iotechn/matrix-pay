package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 关闭订单结果对象类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPayOrderCloseResult extends MatrixBasePayResult {

    /**
     * 业务结果描述
     */
    private String resultMsg;

}
