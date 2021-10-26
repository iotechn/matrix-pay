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

    public String getAliAppId();

    public String getAliMchPrivateKey();

    public String getAliAliPublicKey();

    public String getAliNotifyUrl();

    /***************** 配置预热 将部分配置直接放入缓存 *********************/

}
