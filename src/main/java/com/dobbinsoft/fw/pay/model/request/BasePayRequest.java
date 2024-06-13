package com.dobbinsoft.fw.pay.model.request;

import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  微信支付请求对象共用的参数存放类
 * </pre>
 *
 */
@Data
public abstract class BasePayRequest implements Serializable {

    private static final long serialVersionUID = -7362095583633100077L;
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
    protected String appid;
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
    protected String mchId;
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
    protected String subAppId;
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
    protected String subMchId;

}
