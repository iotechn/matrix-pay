package com.dobbinsoft.fw.pay.model.coupon;

import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 查询代金券批次响应结果类.
 * Created by Binary Wang on 2017-7-15.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayCouponStockQueryResult extends BaseWxPayResult {
    private static final long serialVersionUID = 4644274730788451926L;
    /**
     * <pre>
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 示例值：123456sb
     * 类型：String(32)
     * 说明：微信支付分配的终端设备号
     * </pre>
     */
    private String deviceInfo;

    /**
     * <pre>
     * 字段名：代金券批次ID.
     * 变量名：coupon_stock_id
     * 是否必填：是
     * 示例值：1757
     * 类型：String
     * 说明：代金券批次Id
     * </pre>
     */
    private String couponStockId;

    /**
     * <pre>
     * 字段名：代金券名称.
     * 变量名：coupon_name
     * 是否必填：否
     * 示例值：测试代金券
     * 类型：String
     * 说明：代金券名称
     * </pre>
     */
    private String couponName;

    /**
     * <pre>
     * 字段名：代金券面额.
     * 变量名：coupon_value
     * 是否必填：是
     * 示例值：5
     * 类型：Unsinged int
     * 说明：代金券面值,单位是分
     * </pre>
     */
    private Integer couponValue;

    /**
     * <pre>
     * 字段名：代金券使用最低限额.
     * 变量名：coupon_mininumn
     * 是否必填：否
     * 示例值：10
     * 类型：Unsinged int
     * 说明：代金券使用最低限额,单位是分
     * </pre>
     */
    private Integer couponMinimum;

    /**
     * <pre>
     * 字段名：代金券批次状态.
     * 变量名：coupon_stock_status
     * 是否必填：是
     * 示例值：4
     * 类型：int
     * 说明：批次状态： 1-未激活；2-审批中；4-已激活；8-已作废；16-中止发放；
     * </pre>
     */
    private Integer couponStockStatus;

    /**
     * <pre>
     * 字段名：代金券数量.
     * 变量名：coupon_total
     * 是否必填：是
     * 示例值：100
     * 类型：Unsigned int
     * 说明：代金券数量
     * </pre>
     */
    private Integer couponTotal;

    /**
     * <pre>
     * 字段名：代金券最大领取数量.
     * 变量名：max_quota
     * 是否必填：否
     * 示例值：1
     * 类型：Unsigned int
     * 说明：代金券每个人最多能领取的数量, 如果为0，则表示没有限制
     * </pre>
     */
    private Integer maxQuota;

    /**
     * <pre>
     * 字段名：代金券已经发送的数量.
     * 变量名：is_send_num
     * 是否必填：否
     * 示例值：0
     * 类型：Unsigned int
     * 说明：代金券已经发送的数量
     * </pre>
     */
    private Integer isSendNum;

    /**
     * <pre>
     * 字段名：生效开始时间.
     * 变量名：begin_time
     * 是否必填：是
     * 示例值：1943787483
     * 类型：String
     * 说明：格式为时间戳
     * </pre>
     */
    private String beginTime;

    /**
     * <pre>
     * 字段名：生效结束时间.
     * 变量名：end_time
     * 是否必填：是
     * 示例值：1943787490
     * 类型：String
     * 说明：格式为时间戳
     * </pre>
     */
    private String endTime;

    /**
     * <pre>
     * 字段名：创建时间.
     * 变量名：create_time
     * 是否必填：是
     * 示例值：1943787420
     * 类型：String
     * 说明：格式为时间戳
     * </pre>
     */
    private String createTime;

    /**
     * <pre>
     * 字段名：代金券预算额度.
     * 变量名：coupon_budget
     * 是否必填：否
     * 示例值：500
     * 类型：Unsigned int
     * 说明：代金券预算额度
     * </pre>
     */
    private Integer couponBudget;

}
