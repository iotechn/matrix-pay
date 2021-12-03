package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 转换短链接结果对象类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MatrixPayShorturlResult extends MatrixBasePayResult {
    /**
     * <pre>
     * URL链接
     * short_url
     * 是
     * String(64)
     * weixin：//wxpay/s/XXXXXX
     * 转换后的URL
     * </pre>
     */
    private String shortUrl;

}
