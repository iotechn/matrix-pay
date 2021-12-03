package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 撤销订单响应结果类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPayOrderReverseResult extends MatrixBasePayResult {

    /**
     * <pre>
     * 是否重调
     * recall
     * 是
     * String(1)
     * Y
     * 是否需要继续调用撤销，Y-需要，N-不需要
     * </pre>
     **/
    private String isRecall;

}
