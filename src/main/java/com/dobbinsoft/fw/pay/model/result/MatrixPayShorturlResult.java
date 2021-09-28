package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 转换短链接结果对象类
 * Created by Binary Wang on 2017-3-27.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
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
