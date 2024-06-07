package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;

/**
 * <pre>
 * 微信支付相关接口.
 * </pre>
 *
 */
public interface MatrixPayService {

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     * 关闭订单（适合于需要自定义子商户号和子商户appid的情形）.
     * 微信详见：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     * 支付宝详见：
     * 云闪付详见：
     *
     * @param request 统一下单请求参数
     * @return 返回 前端支付请求所需对象
     * @throws MatrixPayException the pay exception
     */
    <T> T createOrder(MatrixPayUnifiedOrderRequest request) throws MatrixPayException;


    /**
     * <pre>
     * 支付-申请退款.
     * 微信详见: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
     * 支付宝详见:
     * 云闪付详见:
     * </pre>
     *
     * @param request 请求对象
     * @return 退款操作结果 pay refund result
     * @throws MatrixPayException the pay exception
     */
    MatrixPayRefundResult refund(MatrixPayRefundRequest request) throws MatrixPayException;


    /**
     * 统一支付回调验签
     * @param request PayService 只需要将request传入即可
     * @return
     * @throws PayServiceException
     */
    MatrixPayOrderNotifyResult checkParsePayResult(Object request) throws MatrixPayException;


}
