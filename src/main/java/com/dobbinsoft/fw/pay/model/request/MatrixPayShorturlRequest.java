package com.dobbinsoft.fw.pay.model.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

/**
 * <pre>
 * 转换短链接请求对象类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayShorturlRequest extends MatrixBasePayRequest {
    private static final long serialVersionUID = 6132876278336201100L;
    /**
     * <pre>
     * URL链接
     * long_url
     * 是
     * String(512)
     * weixin：//wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 需要转换的URL，签名用原串，传输需URLencode
     * </pre>
     */
    private String longUrl;

}
