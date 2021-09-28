package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  Created by BinaryWang on 2017/6/18.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
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
