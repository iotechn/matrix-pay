package com.dobbinsoft.fw.pay.model.request;

import lombok.*;

/**
 * <pre>
 * 授权码查询openid接口请求对象类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayAuthcode2OpenidRequest extends MatrixBasePayRequest {

    private static final long serialVersionUID = 4128065899247330943L;
    /**
     * <pre>
     *     授权码
     *     auth_code
     *     是
     *     String(128)
     *     扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     * </pre>
     */
    private String authCode;

}
