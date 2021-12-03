package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPaySandboxSignKeyResult extends MatrixBasePayResult {

    /**
     * <pre>
     * 沙箱密钥
     * sandbox_signkey
     * 否
     * 013467007045764
     * String(32)
     * 返回的沙箱密钥
     * </pre>
     */
    private String sandboxSignKey;

}
