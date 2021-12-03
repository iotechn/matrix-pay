package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  授权码查询openid接口请求结果类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPayAuthcode2OpenidResult extends MatrixBasePayResult {
    /**
     * <pre>
     *   用户标识.
     *   openid
     *   是
     *   String(128)
     *   用户在商户appid下的唯一标识
     * </pre>
     */
    private String openid;

}
