package com.dobbinsoft.fw.pay.model.coupon;

import com.dobbinsoft.fw.pay.model.request.MatrixBasePayRequest;
import lombok.*;

/**
 * <pre>
 * 查询代金券信息请求对象类
 * Created by Binary Wang on 2017-7-15.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayCouponInfoQueryRequest extends MatrixBasePayRequest {
    /**
     * <pre>
     * 字段名：代金券id
     * 变量名：coupon_id
     * 是否必填：是
     * 示例值：1757
     * 类型：String
     * 说明：代金券id
     * </pre>
     */
    private String couponId;

    /**
     * <pre>
     * 字段名：代金券批次号
     * 变量名：stock_id
     * 是否必填：是
     * 示例值：58818
     * 类型：String
     * 说明：代金劵对应的批次号
     * </pre>
     */
    private String stockId;

    /**
     * <pre>
     * 字段名：用户openid
     * 变量名：openid
     * 是否必填：是
     * 示例值：onqOjjrXT-776SpHnfexGm1_P7iE
     * 类型：String
     * 说明：Openid信息，用户在appid下的openid。
     * </pre>
     */
    private String openid;

    /**
     * <pre>
     * 字段名：操作员
     * 变量名：op_user_id
     * 是否必填：否
     * 示例值：10000098
     * 类型：String(32)
     * 说明：操作员帐号, 默认为商户号,可在商户平台配置操作员对应的api权限
     * </pre>
     */
    private String opUserId;

    /**
     * <pre>
     * 字段名：设备号
     * 变量名：device_info
     * 是否必填：否
     * 示例值：
     * 类型：String(32)
     * 说明：微信支付分配的终端设备号
     * </pre>
     */
    private String deviceInfo;

    /**
     * <pre>
     * 字段名：协议版本
     * 变量名：version
     * 是否必填：否
     * 示例值：1.0
     * 类型：String(32)
     * 说明：默认1.0
     * </pre>
     */
    private String version;

    /**
     * <pre>
     * 字段名：协议类型
     * 变量名：type
     * 是否必填：否
     * 示例值：XML
     * 类型：String(32)
     * 说明：XML【目前仅支持默认XML】
     * </pre>
     */
    private String type;


}
