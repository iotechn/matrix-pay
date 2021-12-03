package com.dobbinsoft.fw.pay.model.request;

import lombok.*;

/**
 * <pre>
 * 订单查询请求对象
 * 注释中各行每个字段描述对应如下：
 * <li>字段名
 * <li>变量名
 * <li>是否必填
 * <li>类型
 * <li>示例值
 * <li>描述
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayOrderQueryRequest extends MatrixBasePayRequest {

    private static final long serialVersionUID = 6798898450357743059L;
    /**
     * <pre>
     * 字段名：接口版本号.
     * 变量名：version
     * 是否必填：单品优惠必填
     * 类型：String(32)
     * 示例值：1.0
     * 描述：单品优惠新增字段，区分原接口，固定填写1.0，
     * 查单接口上传version后查询结果才返回单品信息，不上传不返回单品信息。
     * 更多信息，详见文档：https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
     * </pre>
     */
    private String version;

    /**
     * <pre>
     * 微信订单号
     * transaction_id
     * 二选一
     * String(32)
     * 1009660380201506130728806387
     * 微信的订单号，优先使用
     * </pre>
     */
    private String transactionId;

    /**
     * <pre>
     * 商户订单号
     * out_trade_no
     * 二选一
     * String(32)
     * 20150806125346
     * 商户系统内部的订单号，当没提供transaction_id时需要传这个。
     * </pre>
     */
    private String outTradeNo;

}
