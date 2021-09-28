package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 撤销订单响应结果类
 * Created by Binary Wang on 2017-3-23.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
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
