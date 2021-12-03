package com.dobbinsoft.fw.pay.model.request;

import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import lombok.Data;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *  微信支付请求对象共用的参数存放类
 * </pre>
 */
@Data
public abstract class MatrixBasePayRequest implements Serializable {

    private static final long serialVersionUID = -7550724004813511502L;
    /**
     * 支付渠道
     */
    private PayChannelType payChannel;

    /**
     * 支付平台
     */
    private PayPlatformType payPlatform;

    /**
     * <pre>
     * 字段名：公众账号ID.
     * 变量名：appid
     * 是否必填：是
     * 类型：String(32)
     * 示例值：wxd678efh567hg6787
     * 描述：微信分配的公众账号ID（企业号corpid即为此appId）
     * </pre>
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
     */
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
    private String signType;

    /**
     * 将单位为元转换为单位为分.
     *
     * @param yuan 将要转换的元的数值字符串
     * @return the integer
     */
    public static Integer yuanToFen(String yuan) {
        return new BigDecimal(yuan).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
    }


    /**
     * 如果配置中已经设置，可以不设置值.
     *
     * @param appid 微信公众号appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 如果配置中已经设置，可以不设置值.
     *
     * @param mchId 微信商户号
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * 默认采用时间戳为随机字符串，可以不设置.
     *
     * @param nonceStr 随机字符串
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

}
