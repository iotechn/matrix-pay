package com.dobbinsoft.fw.pay.config;

/**
 * ClassName: MerchantPropertiesHolder
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public interface PayProperties {

    /***************** 微信支付所需配置 *********************/

    public String getWxMchId();

    public String getWxMchKey();

    public String getWxNotifyUrl();

    public String getWxKeyPath();

    /***************** 阿里支付所需配置 *********************/

    public String getAliGateway();

    public String getAliMiniAppId();

    public String getAliMchMiniPrivateKey();

    public String getAliAliMiniPublicKey();

    public String getAliMiniNotifyUrl();

    public String getAliAppAppId();

    public String getAliMchAppPrivateKey();

    public String getAliAliAppPublicKey();

    public String getAliAppNotifyUrl();

    public String getAliWebAppId();

    public String getAliMchWebPrivateKey();

    public String getAliAliWebPublicKey();

    public String getAliWebNotifyUrl();

}
