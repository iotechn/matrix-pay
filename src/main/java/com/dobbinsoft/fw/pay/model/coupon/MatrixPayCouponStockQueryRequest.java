package com.dobbinsoft.fw.pay.model.coupon;

import com.dobbinsoft.fw.pay.model.request.MatrixBasePayRequest;
import lombok.*;

/**
 * <pre>
 * 查询代金券批次请求对象类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayCouponStockQueryRequest extends MatrixBasePayRequest {
    /**
     * <pre>
     * 字段名：代金券批次id
     * 变量名：coupon_stock_id
     * 是否必填：是
     * 示例值：1757
     * 类型：String
     * 说明：代金券批次id
     * </pre>
     */
    private String couponStockId;

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
