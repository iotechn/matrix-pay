package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import org.w3c.dom.Document;

import java.io.Serializable;

/**
 * <pre>
 * 微信支付结果共用属性类.
 * </pre>
 *
 */
@Data
public abstract class BasePayResult implements Serializable {
  /**
   * 返回状态码.
   */
  protected String returnCode;
  /**
   * 返回信息.
   */
  protected String returnMsg;

  //当return_code为SUCCESS的时候，还会包括以下字段：

  /**
   * 业务结果.
   */
  private String resultCode;
  /**
   * 错误代码.
   */
  private String errCode;
  /**
   * 错误代码描述.
   */
  private String errCodeDes;
  /**
   * APP ID.
   */
  private String appid;
  /**
   * 商户号.
   */
  private String mchId;
  /**
   * 服务商模式下的子公众账号ID.
   */
  private String subAppId;
  /**
   * 服务商模式下的子商户号.
   */
  private String subMchId;
  /**
   * 随机字符串.
   */
  private String nonceStr;
  /**
   * 签名.
   */
  private String sign;

  //以下为辅助属性
  /**
   * xml字符串.
   */
  private String xmlString;

  /**
   * xml的Document对象，用于解析xml文本.
   */
  private Document xmlDoc;

}
