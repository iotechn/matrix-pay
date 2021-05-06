package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.model.request.PayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.PayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.PayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.result.PayRefundResult;

/**
 * ClassName: PayService
 * Description:
 * 统一支付服务： 负责路由到每个具体的实现中，相当于是代理模式中的一个代理
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public interface PayService {

    /**
     * 统一下单接口
     * @param entity
     * @return 前端需要的东西
     * @throws PayServiceException
     */
    public Object createOrder(PayUnifiedOrderRequest entity) throws PayServiceException;

    /**
     * 退款订单
     * @param entity
     * @return
     * @throws PayServiceException
     */
    public PayRefundResult refundOrder(PayRefundRequest entity) throws PayServiceException;

    /**
     * 回调验签
     * @param request PayService 只需要将request传入即可
     * @return
     * @throws PayServiceException
     */
    public PayOrderNotifyResult checkSign(Object request) throws PayServiceException;

}
