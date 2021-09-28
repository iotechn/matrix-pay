package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.request.*;
import com.dobbinsoft.fw.pay.model.result.*;
import com.github.binarywang.wxpay.service.EntPayService;

/**
 * ClassName: MatrixPayService
 * Description:
 * 统一支付服务： 负责路由到每个具体的实现中，相当于是代理模式中的一个代理
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public interface PayService {

    /**
     * 获取支付请求url前缀，沙箱环境可能不一样.
     *
     * @return the pay base url
     */
    String getPayBaseUrl();

    /**
     * 获取企业付款服务类.
     *
     * @return the ent pay service
     */
    EntPayService getEntPayService();

    /**
     * 统一下单接口
     * @param entity
     * @return 前端需要的东西
     * @throws PayServiceException
     */
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws PayServiceException;

    /**
     * 退款订单
     * @param entity
     * @return
     * @throws PayServiceException
     */
    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws PayServiceException;

    /**
     * 当面支付订单
     * @param request
     * @return
     * @throws PayServiceException
     */
    public MatrixPayMicropayResult micropay(MatrixPayMicropayRequest request) throws PayServiceException;


    /**
     * 回调验签
     * @param request PayService 只需要将request传入即可
     * @return
     * @throws PayServiceException
     */
    public MatrixPayOrderNotifyResult checkSign(Object request) throws PayServiceException;


}
