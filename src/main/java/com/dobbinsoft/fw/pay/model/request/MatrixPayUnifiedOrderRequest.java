package com.dobbinsoft.fw.pay.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * 统一下单请求参数对象.
 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * </pre>
 *
 */
@Getter
@Setter
public class MatrixPayUnifiedOrderRequest extends MatrixBasePayRequest {

    @Serial
    private static final long serialVersionUID = -3173551488619526785L;


    /**
     * <pre>
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 类型：String(32)
     * 示例值：013467007045764
     * 描述：终端设备号(门店号或收银设备Id)，注意：PC网页或公众号内支付请传"WEB"
     * </pre>
     */
    private String deviceInfo;

    /**
     * <pre>
     * 字段名：商品描述.
     * 变量名：body
     * 是否必填：是
     * 类型：String(128)
     * 示例值： 腾讯充值中心-QQ会员充值
     * 描述：商品简单描述，该字段须严格按照规范传递，具体请见参数规定
     * </pre>
     */
    private String body;

    /**
     * <pre>
     * 字段名：商品详情.
     * 变量名：detail
     * 是否必填：否
     * 类型：String(6000)
     * 描述：商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
     * </pre>
     */
    private List<MatrixPayRequestGoodsDetail> detail;

    /**
     * <pre>
     * 字段名：附加数据.
     * 变量名：attach
     * 是否必填：否
     * 类型：String(127)
     * 示例值： 深圳分店
     * 描述：  附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * </pre>
     */
    private String attach;

    /**
     * <pre>
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：20150806125346
     * 描述：商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
     * </pre>
     */
    private String outTradeNo;

    /**
     * <pre>
     * 字段名：货币类型.
     * 变量名：fee_type
     * 是否必填：否
     * 类型：String(16)
     * 示例值：CNY
     * 描述： 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     */
    private String feeType;

    /**
     * <pre>
     * 字段名：总金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值： 888
     * 描述：订单总金额，单位为分，详见支付金额
     * </pre>
     */
    private Integer totalFee;

    /**
     * <pre>
     * 字段名：终端IP.
     * 变量名：spbill_create_ip
     * 是否必填：是
     * 类型：String(16)
     * 示例值：123.12.12.123
     * 描述：APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
     * </pre>
     */
    private String spbillCreateIp;

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
    private LocalDateTime timeStart;

    /**
     * <pre>
     * 字段名：交易结束时间.
     * 变量名：time_expire
     * 是否必填：否
     * 类型：String(14)
     * 示例值：20091227091010
     * 描述：订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
     *   注意：最短失效时间间隔必须大于5分钟
     * </pre>
     */
    private LocalDateTime timeExpire;


    /**
     * <pre>
     * 字段名：通知地址.
     * 变量名：notify_url
     * 是否必填：是
     * 类型：String(256)
     * 示例值：http://www.weixin.qq.com/wxpay/pay.php
     * 描述：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     * </pre>
     */
    private String notifyUrl;


    /**
     * <pre>
     * 字段名：指定支付方式.
     * 变量名：limit_pay
     * 是否必填：否
     * 类型：String(32)
     * 示例值：no_credit
     * 描述：no_credit--指定不能使用信用卡支付
     * </pre>
     */
    private String limitPay;

    /**
     * <pre>
     * 字段名：用户标识.
     * 变量名：openid
     * 是否必填：否
     * 类型：String(128)
     * 示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 描述：trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
     * openid如何获取，可参考【获取openid】。
     * 企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     * </pre>
     */
    private String openid;

    /**
     * <pre>
     * 字段名：用户子标识.
     * 变量名：sub_openid
     * 是否必填：否
     * 类型：String(128)
     * 示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 描述：trade_type=JSAPI，此参数必传，用户在子商户appid下的唯一标识。
     * openid和sub_openid可以选传其中之一，如果选择传sub_openid,则必须传sub_appid。
     * 下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
     * </pre>
     */
    private String subOpenid;

    /**
     * <pre>
     * 字段名：电子发票入口开放标识.
     * 变量名：	receipt
     * 是否必填：否
     * 类型：String(8)
     * 示例值：Y
     * 描述：	Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     * </pre>
     */
    private String receipt;

    /**
     * <pre>
     * 字段名：场景信息.
     * 变量名：scene_info
     * 是否必填：否，对H5支付来说是必填
     * 类型：String(256)
     * 示例值：{
     * "store_id": "SZT10000",
     * "store_name":"腾讯大厦腾大餐厅"
     * }
     * 描述：该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。
     * {
     * "store_id": "", //门店唯一标识，选填，String(32)
     * "store_name":"”//门店名称，选填，String(64)
     * }
     * </pre>
     */
    private String sceneInfo;
    /**
     * <pre>
     * 字段名：浏览器指纹.
     * 变量名：fingerprint
     * 是否必填：否
     * 详细参考 https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=15_7&index=6
     * </pre>
     */
    private String fingerprint;

    /**
     * <pre>
     * 字段名：重定向地址
     * 变量名：returnUrl
     * 描述：Web支付成功后，
     * </pre>
     */
    private String returnUrl;

}
