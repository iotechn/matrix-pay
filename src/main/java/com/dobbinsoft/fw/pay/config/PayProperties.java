package com.dobbinsoft.fw.pay.config;

/**
 * ClassName: PayProperties
 * Description: 支付配置属性
 *
 */
public interface PayProperties {

    /***************** 微信支付所需配置 *********************/

    public String getWxAppId();

    public String getWxMchId();

    public String getWxMchKey();

    public String getWxNotifyUrl();

    public byte[] getWxCert();

    /***************** 阿里支付所需配置 *********************/

    public String getAliGateway();

    public String getAliAppId();

    public String getAliMchPublicKey();

    public String getAliMchPrivateKey();

    public String getAliAliPublicKey();

    public String getAliAliRootCertPath();

    public String getAliNotifyUrl();

    /***************** 配置预热 将部分配置直接放入缓存 *********************/

}
