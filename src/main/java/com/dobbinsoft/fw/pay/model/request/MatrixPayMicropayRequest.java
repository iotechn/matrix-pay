package com.dobbinsoft.fw.pay.model.request;

import com.dobbinsoft.fw.pay.anntation.MatrixIgnoreCopy;
import lombok.*;

import java.util.List;

/**
 * <pre>
 *  提交刷卡支付请求对象类
 * </pre>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayMicropayRequest extends MatrixBasePayRequest {
    private static final long serialVersionUID = -1450884482408501121L;
    /**
     * <pre>
     * 字段名：接口版本号.
     * 变量名：version
     * 是否必填：单品优惠必填
     * 类型：String(32)
     * 示例值：1.0
     * 描述：单品优惠新增字段，区分原接口，固定填写1.0
     * 更多信息，详见文档：https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_101&index=1
     * </pre>
     */
    private String version;

    /**
     * <pre>
     * 字段名：商品描述.
     * 变量名：body
     * 是否必填：是
     * 类型：String(128)
     * 示例值：image形象店-深圳腾大- QQ公仔
     * 描述：商品简单描述，该字段须严格按照规范传递，具体请见参数规定
     * </pre>
     **/
    private String body;

    /**
     * <pre>
     * 字段名：商品详情.
     * 变量名：detail
     * 是否必填：否
     * 类型：String(6000)
     * 示例值：
     * 描述：单品优惠功能字段，需要接入请见详细说明</pre>
     **/
    @MatrixIgnoreCopy
    private List<MatrixPayRequestGoodsDetail> detail;

    /**
     * <pre>
     * 字段名：附加数据.
     * 变量名：attach
     * 是否必填：否
     * 类型：String(127)
     * 示例值：说明
     * 描述：附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * </pre>
     **/
    private String attach;

    /**
     * <pre>
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1217752501201407033233368018
     * 描述：商户系统内部的订单号,32个字符内、可包含字母,其他说明见商户订单号
     * </pre>
     **/
    private String outTradeNo;

    /**
     * <pre>
     * 字段名：订单金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：888
     * 描述：订单总金额，单位为分，只能为整数，详见支付金额
     * </pre>
     **/
    private Integer totalFee;

    /**
     * <pre>
     * 字段名：货币类型.
     * 变量名：fee_type
     * 是否必填：否
     * 类型：String(16)
     * 示例值：CNY
     * 描述：符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     **/
    private String feeType;

    /**
     * <pre>
     * 字段名：终端IP.
     * 变量名：spbill_create_ip
     * 是否必填：是
     * 类型：String(16)
     * 示例值：8.8.8.8
     * 描述：调用支付API的机器IP
     * </pre>
     **/
    private String spbillCreateIp;

    /**
     * <pre>
     * 字段名：商品标记.
     * 变量名：goods_tag
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1234
     * 描述：商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     * </pre>
     **/
    private String goodsTag;

    /**
     * <pre>
     * 字段名：指定支付方式.
     * 变量名：limit_pay
     * 是否必填：否
     * 类型：String(32)
     * 示例值：no_credit
     * 描述：no_credit--指定不能使用信用卡支付
     * </pre>
     **/
    private String limitPay;

    /**
     * <pre>
     * 字段名：交易起始时间.
     * 变量名：time_start
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20091225091010
     * 描述：订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     * </pre>
     */
    private String timeStart;

    /**
     * <pre>
     * 字段名：交易结束时间.
     * 变量名：time_expire
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20091227091010
     * 描述：订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
     * 注意：最短失效时间间隔必须大于5分钟
     * </pre>
     */
    private String timeExpire;

    /**
     * <pre>
     * 字段名：授权码.
     * 变量名：auth_code
     * 是否必填：是
     * 类型：String(128)
     * 示例值：120061098828009406
     * 描述：扫码支付授权码，设备读取用户客户端中的条码或者二维码信息注：
     * 微信：18位纯数字以10、11、12、13、14、15开头）
     * 支付宝：
     * 云闪付
     * </pre>
     **/
    private String authCode;

    /**
     * <pre>
     * 字段名：场景信息.
     * 变量名：scene_info
     * 是否必填：否
     * 类型：String(256)
     * 示例值：{"store_info" : {
     * "id": "SZTX001",
     * "name": "腾大餐厅",
     * "area_code": "440305",
     * "address": "科技园中一路腾讯大厦" }}
     * 描述：该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }}
     * </pre>
     */
    private String sceneInfo;

}
