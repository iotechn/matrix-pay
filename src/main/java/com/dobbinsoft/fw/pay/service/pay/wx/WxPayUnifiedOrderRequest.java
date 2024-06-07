package com.dobbinsoft.fw.pay.service.pay.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JacksonXmlRootElement(localName = "xml")
public class WxPayUnifiedOrderRequest {

    /**
     * <pre>
     * 字段名：公众账号ID.
     * 变量名：appid
     * 是否必填：是
     * 类型：String(32)
     * 示例值：wxd678efh567hg6787
     * 描述：微信分配的公众账号ID（企业号corpid即为此appId）
     * </pre>
     * -- SETTER --
     *  如果配置中已经设置，可以不设置值.
     *
     * @param appid 微信公众号appid

     */
    private String appid;
    /**
     * <pre>
     * 字段名：商户号.
     * 变量名：mch_id
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1230000109
     * 描述：微信支付分配的商户号
     * </pre>
     * -- SETTER --
     *  如果配置中已经设置，可以不设置值.
     *
     * @param mchId 微信商户号

     */
    @JsonProperty("mch_id")
    private String mchId;
    /**
     * <pre>
     * 字段名：服务商模式下的子商户公众账号ID.
     * 变量名：sub_appid
     * 是否必填：是
     * 类型：String(32)
     * 示例值：wxd678efh567hg6787
     * 描述：微信分配的子商户公众账号ID
     * </pre>
     */
    @JsonProperty("sub_app_id")
    private String subAppId;
    /**
     * <pre>
     * 字段名：服务商模式下的子商户号.
     * 变量名：sub_mch_id
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1230000109
     * 描述：微信支付分配的子商户号，开发者模式下必填
     * </pre>
     */
    @JsonProperty("sub_mch_id")
    private String subMchId;
    /**
     * <pre>
     * 字段名：随机字符串.
     * 变量名：nonce_str
     * 是否必填：是
     * 类型：String(32)
     * 示例值：5K8264ILTKCH16CQ2502SI8ZNMTM67VS
     * 描述：随机字符串，不长于32位。推荐随机数生成算法
     * </pre>
     */
    @JsonProperty("nonce_str")
    private String nonceStr;
    /**
     * <pre>
     * 字段名：签名.
     * 变量名：sign
     * 是否必填：是
     * 类型：String(32)
     * 示例值：C380BEC2BFD727A4B6845133519F3AD6
     * 描述：签名，详见签名生成算法
     * </pre>
     */
    private String sign;

    /**
     * <pre>
     * 签名类型.
     * sign_type
     * 否
     * String(32)
     * HMAC-SHA256
     * 签名类型，目前支持HMAC-SHA256和MD5
     * </pre>
     */
    @JsonProperty("sign_type")
    private String signType;


    /**
     * <pre>
     * 字段名：接口版本号.
     * 变量名：version
     * 是否必填：单品优惠必填
     * 类型：String(32)
     * 示例值：1.0
     * 描述：单品优惠新增字段，接口版本号，区分原接口，默认填写1.0。
     * 入参新增version后，则支付通知接口也将返回单品优惠信息字段promotion_detail，请确保支付通知的签名验证能通过。
     * 更多信息，详见文档：https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
     * </pre>
     */
    private String version;

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
    @JsonProperty("device_info")
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
    @JacksonXmlCData
    private String detail;

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
    @JsonProperty("out_trade_no")
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
    @JsonProperty("fee_type")
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
    @JsonProperty("total_fee")
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
    @JsonProperty("spbill_create_ip")
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
    @JsonProperty("time_start")
    private String timeStart;

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
    @JsonProperty("time_expire")
    private String timeExpire;

    /**
     * <pre>
     * 字段名：商品标记.
     * 变量名：goods_tag
     * 是否必填：否
     * 类型：String(32)
     * 示例值：WXG
     * 描述：商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     * </pre>
     */
    @JsonProperty("goods_tag")
    private String goodsTag;

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
    @JsonProperty("notify_url")
    private String notifyUrl;

    /**
     * <pre>
     * 字段名：交易类型.
     * 变量名：trade_type
     * 是否必填：是
     * 类型：String(16)
     * 示例值： JSAPI
     * 描述： 取值如下：JSAPI，NATIVE，APP，详细说明见参数规定:
     * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
     * </pre>
     */
    @JsonProperty("trade_type")
    private String tradeType;

    /**
     * <pre>
     * 字段名：商品Id.
     * 变量名：product_id
     * 是否必填：否
     * 类型：String(32)
     * 示例值：12235413214070356458058
     * 描述：trade_type=NATIVE，此参数必传。此id为二维码中包含的商品Id，商户自行定义。
     * </pre>
     */
    @JsonProperty("product_id")
    private String productId;

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
    @JsonProperty("limit_pay")
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
    @JsonProperty("sub_openid")
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
    @JsonProperty("scene_info")
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
    @JsonProperty("return_url")
    private String returnUrl;

}
