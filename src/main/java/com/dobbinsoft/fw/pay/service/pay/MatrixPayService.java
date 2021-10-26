package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.coupon.*;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayRefundNotifyResult;
import com.dobbinsoft.fw.pay.model.notify.MatrixScanPayNotifyResult;
import com.dobbinsoft.fw.pay.model.request.*;
import com.dobbinsoft.fw.pay.model.result.*;
import com.github.binarywang.wxpay.bean.WxPayApiData;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 微信支付相关接口.
 * Created by Binary Wang on 2016/7/28.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface MatrixPayService {

    /**
     * 配置预热，将一些需要被管理的APPID置入其中。后面便无需通过 PayProperties动态传入了
     * 一般在系统初始化IoC加载时预热
     *
     * @param list 配置列表
     */
    public void configWarmUp(List<PayProperties> list);

    /**
     * 获取企业付款服务类.
     *
     * @return the ent pay service
     */
    MatrixEntPayService getEntPayService();

    /**
     * 设置企业付款服务类，允许开发者自定义实现类.
     *
     * @param entPayService the ent pay service
     */
    void setEntPayService(MatrixEntPayService entPayService);

    /**
     * <pre>
     * 查询订单（适合于需要自定义子商户号和子商户appid的情形）.
     * 微信详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     * 支付宝详见：
     * 云闪付详见：
     * 该接口提供所有支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     * </pre>
     *
     * @param request 查询订单请求对象
     * @return the pay order query result
     * @throws MatrixPayException the pay exception
     */
    MatrixPayOrderQueryResult queryOrder(MatrixPayOrderQueryRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 关闭订单（适合于需要自定义子商户号和子商户appid的情形）.
     * 应用场景
     * 以下情况需要调用关单接口：
     * 1. 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     * 2. 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
     * 是否需要证书：   不需要。
     * </pre>
     *
     * @param request 关闭订单请求对象
     * @return the pay order close result
     * @throws MatrixPayException the pay exception
     */
    MatrixPayOrderCloseResult closeOrder(MatrixPayOrderCloseRequest request) throws MatrixPayException;

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param request 统一下单请求参数
     * @return 返回 前端支付请求所需对象
     * @throws MatrixPayException the wx pay exception
     */
    <T> T createOrder(MatrixPayUnifiedOrderRequest request) throws MatrixPayException;


    /**
     * <pre>
     * 支付-申请退款.
     * 微信详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
     * 支付宝详见
     * 云闪付详见
     * </pre>
     *
     * @param request 请求对象
     * @return 退款操作结果 wx pay refund result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayRefundResult refund(MatrixPayRefundRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 微信支付-查询退款.
     * 应用场景：
     *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
     *  银行卡支付的退款3个工作日后重新查询退款状态。
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
     * </pre>
     * 以下四个参数四选一
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @param outRefundNo   商户退款单号
     * @param refundId      微信退款单号
     * @return 退款信息 wx pay refund query result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayRefundQueryResult refundQuery(String transactionId, String outTradeNo, String outRefundNo, String refundId)
            throws MatrixPayException;

    /**
     * <pre>
     * 微信支付-查询退款（适合于需要自定义子商户号和子商户appid的情形）.
     * 应用场景：
     *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
     *  银行卡支付的退款3个工作日后重新查询退款状态。
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
     * </pre>
     *
     * @param request 微信退款单号
     * @return 退款信息 wx pay refund query result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayRefundQueryResult refundQuery(MatrixPayRefundQueryRequest request) throws MatrixPayException;

    /**
     * 解析支付结果通知.
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
     *
     * @param xmlData the xml data
     * @return the wx pay order notify result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws MatrixPayException;

    /**
     * 解析退款结果通知
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_16&index=9
     *
     * @param xmlData the xml data
     * @return the wx pay refund notify result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayRefundNotifyResult parseRefundNotifyResult(String xmlData) throws MatrixPayException;

    /**
     * 解析扫码支付回调通知
     * 详见https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
     *
     * @param xmlData the xml data
     * @return the wx scan pay notify result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixScanPayNotifyResult parseScanPayNotifyResult(String xmlData) throws MatrixPayException;

    /**
     * 发送微信红包给个人用户.
     * <pre>
     * 文档详见:
     * 发送普通红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3
     *  接口地址：https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack
     * 发送裂变红包 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5&index=4
     *  接口地址：https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack
     * </pre>
     *
     * @param request 请求对象
     * @return the wx pay send redpack result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPaySendRedpackResult sendRedpack(MatrixPaySendRedpackRequest request) throws MatrixPayException;

    /**
     * <pre>
     *   查询红包记录.
     *   用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
     *   请求Url：https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo
     *   是否需要证书：是（证书及使用说明详见商户证书）
     *   请求方式：POST
     * </pre>
     *
     * @param mchBillNo 商户发放红包的商户订单号，比如10000098201411111234567890
     * @return the wx pay redpack query result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayRedpackQueryResult queryRedpack(String mchBillNo) throws MatrixPayException;

    /**
     * <pre>
     *   查询红包记录.
     *   用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
     *   请求Url：https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo
     *   是否需要证书：是（证书及使用说明详见商户证书）
     *   请求方式：POST
     * </pre>
     *
     * @param request 红包查询请求
     * @return the wx pay redpack query result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayRedpackQueryResult queryRedpack(MatrixPayRedpackQueryRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 扫码支付模式一生成二维码的方法。
     * 二维码中的内容为链接，形式为：
     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
     * </pre>
     *
     * @param productId  产品Id
     * @param logoFile   商户logo图片的文件对象，可以为空
     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
     * @return 生成的二维码的字节数组 byte [ ]
     */
    byte[] createScanPayQrcodeMode1(String productId, File logoFile, Integer sideLength);

    /**
     * <pre>
     * 扫码支付模式一生成二维码的方法.
     * 二维码中的内容为链接，形式为：
     * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 其中XXXXX为商户需要填写的内容，商户将该链接生成二维码，如需要打印发布二维码，需要采用此格式。商户可调用第三方库生成二维码图片。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
     * </pre>
     *
     * @param productId 产品Id
     * @return 生成的二维码URL连接 string
     */
    String createScanPayQrcodeMode1(String productId);

    /**
     * <pre>
     * 扫码支付模式二生成二维码的方法.
     * 对应链接格式：weixin：//wxpay/bizpayurl?sr=XXXXX。请商户调用第三方库将code_url生成二维码图片。
     * 该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
     * 文档详见: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5
     * </pre>
     *
     * @param codeUrl    微信返回的交易会话的二维码链接
     * @param logoFile   商户logo图片的文件对象，可以为空
     * @param sideLength 要生成的二维码的边长，如果为空，则取默认值400
     * @return 生成的二维码的字节数组 byte [ ]
     */
    byte[] createScanPayQrcodeMode2(String codeUrl, File logoFile, Integer sideLength);

    /**
     * <pre>
     * 交易保障.
     * 应用场景：
     *  商户在调用微信支付提供的相关接口时，会得到微信支付返回的相关信息以及获得整个接口的响应时间。
     *  为提高整体的服务水平，协助商户一起提高服务质量，微信支付提供了相关接口调用耗时和返回信息的主动上报接口，
     *  微信支付可以根据商户侧上报的数据进一步优化网络部署，完善服务监控，和商户更好的协作为用户提供更好的业务体验。
     * 接口地址： https://api.mch.weixin.qq.com/payitil/report
     * 是否需要证书：不需要
     * </pre>
     *
     * @param request the request
     * @throws MatrixPayException the wx pay exception
     */
    void report(MatrixPayReportRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 下载对账单.
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     * 注意：
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为“元”。
     * 4、对账单接口只能下载三个月以内的账单。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
     * </pre>
     *
     * @param billDate   对账单日期 bill_date 下载对账单的日期，格式：20140603
     * @param billType   账单类型 bill_type ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
     * @param tarType    压缩账单 tar_type 非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * @param deviceInfo 设备号 device_info 非必传参数，终端设备号
     * @return 对账内容原始字符串
     * @throws MatrixPayException the wx pay exception
     */
    String downloadRawBill(String billDate, String billType, String tarType, String deviceInfo) throws MatrixPayException;

    /**
     * <pre>
     * 下载对账单（适合于需要自定义子商户号和子商户appid的情形）.
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     * 注意：
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为“元”。
     * 4、对账单接口只能下载三个月以内的账单。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
     * </pre>
     *
     * @param request 下载对账单请求
     * @return 对账内容原始字符串
     * @throws MatrixPayException the wx pay exception
     */
    String downloadRawBill(MatrixPayDownloadBillRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 下载对账单.
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     * 注意：
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为“元”。
     * 4、对账单接口只能下载三个月以内的账单。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
     * </pre>
     *
     * @param billDate   对账单日期 bill_date 下载对账单的日期，格式：20140603
     * @param billType   账单类型 bill_type ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
     * @param tarType    压缩账单 tar_type 非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * @param deviceInfo 设备号 device_info 非必传参数，终端设备号
     * @return WxPayBillResult对象 wx pay bill result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayBillResult downloadBill(String billDate, String billType, String tarType, String deviceInfo) throws MatrixPayException;

    /**
     * <pre>
     * 下载对账单（适合于需要自定义子商户号和子商户appid的情形）.
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     * 注意：
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为“元”。
     * 4、对账单接口只能下载三个月以内的账单。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
     * </pre>
     *
     * @param request 下载对账单请求
     * @return WxPayBillResult对象 wx pay bill result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayBillResult downloadBill(MatrixPayDownloadBillRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 下载资金账单.
     * 商户可以通过该接口下载自2017年6月1日起 的历史资金流水账单。
     * 注意：
     * 1、资金账单中的数据反映的是商户微信账户资金变动情况；
     * 2、当日账单在次日上午9点开始生成，建议商户在上午10点以后获取；
     * 3、资金账单中涉及金额的字段单位为“元”。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadfundflow
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_18">下载对账单</a>
     * </pre>
     *
     * @param billDate    资金账单日期 bill_date 下载对账单的日期，格式：20140603
     * @param accountType 资金账户类型 account_type Basic，基本账户，Operation，运营账户，Fees，手续费账户
     * @param tarType     压缩账单 tar_type 非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * @return WxPayFundFlowResult对象 wx pay fund flow result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayFundFlowResult downloadFundFlow(String billDate, String accountType, String tarType) throws MatrixPayException;

    /**
     * <pre>
     * 下载资金账单.
     * 商户可以通过该接口下载自2017年6月1日起 的历史资金流水账单。
     * 注意：
     * 1、资金账单中的数据反映的是商户微信账户资金变动情况；
     * 2、当日账单在次日上午9点开始生成，建议商户在上午10点以后获取；
     * 3、资金账单中涉及金额的字段单位为“元”。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadfundflow
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_18">下载对账单</a>
     * </pre>
     *
     * @param request 下载资金流水请求
     * @return WxPayFundFlowResult对象 wx pay fund flow result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayFundFlowResult downloadFundFlow(MatrixPayDownloadFundFlowRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 提交刷卡支付.
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1
     * 应用场景：
     * 收银员使用扫码设备读取微信用户刷卡授权码以后，二维码或条码信息传送至商户收银台，由商户收银台或者商户后台调用该接口发起支付。
     * 提醒1：提交支付请求后微信会同步返回支付结果。当返回结果为“系统错误”时，商户系统等待5秒后调用【查询订单API】，查询支付实际交易结果；当返回结果为“USERPAYING”时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，直到支付成功或超时(建议30秒)；
     * 提醒2：在调用查询接口返回后，如果交易状况不明晰，请调用【撤销订单API】，此时如果交易失败则关闭订单，该单不能再支付成功；如果交易成功，则将扣款退回到用户账户。当撤销无返回或错误时，请再次调用。注意：请勿扣款后立即调用【撤销订单API】,建议至少15秒后再调用。撤销订单API需要双向证书。
     * 接口地址：   https://api.mch.weixin.qq.com/pay/micropay
     * 是否需要证书：不需要。
     * </pre>
     *
     * @param request the request
     * @return the wx pay micropay result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayMicropayResult micropay(MatrixPayMicropayRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 撤销订单API.
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
     * 应用场景：
     *  支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，微信支付系统会将此订单关闭；
     *  如果用户支付成功，微信支付系统会将此订单资金退还给用户。
     *  注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。
     *  提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
     *  调用支付接口后请勿立即调用撤销订单API，建议支付后至少15s后再调用撤销订单接口。
     *  接口链接 ：https://api.mch.weixin.qq.com/secapi/pay/reverse
     *  是否需要证书：请求需要双向证书。
     * </pre>
     *
     * @param request the request
     * @return the wx pay order reverse result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayOrderReverseResult reverseOrder(MatrixPayOrderReverseRequest request) throws MatrixPayException;

    /**
     * <pre>
     *  转换短链接.
     *  文档地址：
     *     https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_9&index=8
     *  应用场景：
     *     该接口主要用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)，减小二维码数据量，提升扫描速度和精确度。
     *  接口地址：https://api.mch.weixin.qq.com/tools/shorturl
     *  是否需要证书：否
     * </pre>
     *
     * @param request 请求对象
     * @return the string
     * @throws MatrixPayException the wx pay exception
     */
    String shorturl(MatrixPayShorturlRequest request) throws MatrixPayException;

    /**
     * <pre>
     *  转换短链接.
     * </pre>
     *
     * @param longUrl 需要被压缩的网址
     * @return the string
     * @throws MatrixPayException the wx pay exception
     * @see MatrixPayService#shorturl(MatrixPayShorturlRequest) WxPayService#shorturl(WxPayShorturlRequest)
     */
    String shorturl(String longUrl) throws MatrixPayException;

    /**
     * <pre>
     * 授权码查询OPENID接口.
     *    通过授权码查询公众号Openid，调用查询后，该授权码只能由此商户号发起扣款，直至授权码更新。
     * 文档地址：
     *    https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_13&index=9
     * 接口链接:
     *    https://api.mch.weixin.qq.com/tools/authcodetoopenid
     * </pre>
     *
     * @param request 请求对象
     * @return openid string
     * @throws MatrixPayException the wx pay exception
     */
    String authcode2Openid(MatrixPayAuthcode2OpenidRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 授权码查询OPENID接口.
     * </pre>
     *
     * @param authCode 授权码
     * @return openid string
     * @throws MatrixPayException the wx pay exception
     * @see MatrixPayService#authcode2Openid(MatrixPayAuthcode2OpenidRequest) WxPayService#authcode2Openid(WxPayAuthcode2OpenidRequest)
     */
    String authcode2Openid(String authCode) throws MatrixPayException;

    /**
     * <pre>
     * 获取仿真测试系统的验签密钥.
     * 请求Url： https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey
     * 是否需要证书： 否
     * 请求方式： POST
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=23_1
     * </pre>
     *
     * @return the sandbox sign key
     * @throws MatrixPayException the wx pay exception
     */
    String getSandboxSignKey() throws MatrixPayException;

    /**
     * <pre>
     * 发放代金券
     * 接口请求链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/send_coupon
     * 是否需要证书：请求需要双向证书。
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_3
     * </pre>
     *
     * @param request the request
     * @return the wx pay coupon send result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayCouponSendResult sendCoupon(MatrixPayCouponSendRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 查询代金券批次.
     * 接口请求链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/query_coupon_stock
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_4
     * </pre>
     *
     * @param request the request
     * @return the wx pay coupon stock query result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayCouponStockQueryResult queryCouponStock(MatrixPayCouponStockQueryRequest request) throws MatrixPayException;

    /**
     * <pre>
     * 查询代金券信息.
     * 接口请求链接：https://api.mch.weixin.qq.com/mmpaymkttransfers/querycouponsinfo
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_5
     * </pre>
     *
     * @param request the request
     * @return the wx pay coupon info query result
     * @throws MatrixPayException the wx pay exception
     */
    MatrixPayCouponInfoQueryResult queryCouponInfo(MatrixPayCouponInfoQueryRequest request) throws MatrixPayException;

    /**
     * 获取微信请求数据，方便接口调用方获取处理.
     *
     * @return the wx api data
     */
    WxPayApiData getWxApiData();

    /**
     * <pre>
     * 拉取订单评价数据.
     * 商户可以通过该接口拉取用户在微信支付交易记录中针对你的支付记录进行的评价内容。商户可结合商户系统逻辑对该内容数据进行存储、分析、展示、客服回访以及其他使用。如商户业务对评价内容有依赖，可主动引导用户进入微信支付交易记录进行评价。
     * 注意：
     * 1. 该内容所有权为提供内容的微信用户，商户在使用内容的过程中应遵从用户意愿
     * 2. 一次最多拉取200条评价数据，可根据时间区间分批次拉取
     * 3. 接口只能拉取最近三个月以内的评价数据
     * 接口链接：https://api.mch.weixin.qq.com/billcommentsp/batchquerycomment
     * 是否需要证书：需要
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_17&index=10
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param offset    位移
     * @param limit     条数，建议填null，否则接口会报签名错误
     * @return the string
     * @throws MatrixPayException the wx pay exception
     */
    String queryComment(Date beginDate, Date endDate, Integer offset, Integer limit) throws MatrixPayException;

    /**
     * <pre>
     * 拉取订单评价数据.
     * 商户可以通过该接口拉取用户在微信支付交易记录中针对你的支付记录进行的评价内容。商户可结合商户系统逻辑对该内容数据进行存储、分析、展示、客服回访以及其他使用。如商户业务对评价内容有依赖，可主动引导用户进入微信支付交易记录进行评价。
     * 注意：
     * 1. 该内容所有权为提供内容的微信用户，商户在使用内容的过程中应遵从用户意愿
     * 2. 一次最多拉取200条评价数据，可根据时间区间分批次拉取
     * 3. 接口只能拉取最近三个月以内的评价数据
     * 接口链接：https://api.mch.weixin.qq.com/billcommentsp/batchquerycomment
     * 是否需要证书：需要
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_17&index=10
     * </pre>
     *
     * @param request 查询请求
     * @return the string
     * @throws MatrixPayException the wx pay exception
     */
    String queryComment(MatrixPayQueryCommentRequest request) throws MatrixPayException;


    /**
     * 回调验签
     * @param request PayService 只需要将request传入即可
     * @return
     * @throws PayServiceException
     */
    MatrixPayOrderNotifyResult checkSign(Object request) throws MatrixPayException;

}
