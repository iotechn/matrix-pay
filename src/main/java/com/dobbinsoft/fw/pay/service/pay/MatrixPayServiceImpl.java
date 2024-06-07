package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.anntation.MatrixNotNull;
import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContext;
import com.dobbinsoft.fw.pay.model.context.PayCallbackContextHolder;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.request.MatrixBasePayRequest;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: PayServiceImpl
 * Description: TODO
 */
@Slf4j
public class MatrixPayServiceImpl implements MatrixPayService {

    private AliPayServiceImpl aliPayService;

    private WxPayServiceImpl wxPayService;

    public MatrixPayServiceImpl(PayProperties payProperties) {
        this.aliPayService = new AliPayServiceImpl(payProperties);
        this.wxPayService = new WxPayServiceImpl(payProperties);
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws MatrixPayException {
        this.checkParam(entity);
        if (entity.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.createOrder(entity);
        } else if (entity.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.createOrder(entity);
        } else {
            throw new MatrixPayException("支付渠道不支持");
        }
    }


    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest request) throws MatrixPayException {
        this.checkParam(request);
        if (request.getPayChannel() == PayChannelType.ALI) {
            return this.aliPayService.refund(request);
        } else if (request.getPayChannel() == PayChannelType.WX) {
            return this.wxPayService.refund(request);
        } else {
            throw new MatrixPayException("支付渠道不支持");
        }
    }

    @Override
    public MatrixPayOrderNotifyResult checkParsePayResult(Object obj) throws MatrixPayException {
        HttpServletRequest servletRequest = (HttpServletRequest) obj;
        MatrixPayOrderNotifyResult result;
        if ("RSA2".equals(servletRequest.getParameter("sign_type"))) {
            // 支付宝依据
            Map<String, String> map = new HashMap<>();
            servletRequest.getParameterMap().forEach((k, v) -> {
                map.put(k, v[0]);
            });
            PayCallbackContext payCallbackContext = new PayCallbackContext();
            payCallbackContext.setPayChannelType(PayChannelType.ALI);
            PayCallbackContextHolder.set(payCallbackContext);
            log.info("[支付宝] 统一支付回调原始报文：" + JacksonUtil.toJSONString(map));
            result = aliPayService.checkParsePayResult(map);
            PayCallbackContextHolder.setPayId(result.getTransactionId());
            result.setPayChannel(PayChannelType.ALI);
        } else {
            ServletInputStream is = null;
            try {
                is = servletRequest.getInputStream();
                byte[] bytes = new byte[servletRequest.getContentLength()];
                is.read(bytes);
                String str = new String(bytes);
                PayCallbackContext payCallbackContext = new PayCallbackContext();
                payCallbackContext.setPayChannelType(PayChannelType.WX);
                PayCallbackContextHolder.set(payCallbackContext);
                log.info("[微信] 统一支付回调原始报文：" + str);
                result = wxPayService.checkParsePayResult(str);
                PayCallbackContextHolder.setPayId(result.getTransactionId());
                result.setPayChannel(PayChannelType.WX);
            } catch (IOException e) {
                throw new MatrixPayException("支付回调，网络异常");
            } finally {
                if (is == null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return result;
    }


    /**
     * 校验请求中，平台不能为空
     * @param request
     */
    private void checkParam(MatrixBasePayRequest request) {
        PayChannelType payChannel = request.getPayChannel();
        if (payChannel == null) {
            throw new MatrixPayException("支付渠道不可为空");
        }
        Class<?> clazz = request.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            MatrixNotNull matrixNotNull = field.getAnnotation(MatrixNotNull.class);
            if (matrixNotNull != null) {
                PayChannelType[] types = matrixNotNull.channels();
                Method methodCache = null;
                for (PayChannelType type : types) {
                    if (payChannel == type) {
                        try {
                            if (methodCache == null) {
                                methodCache = clazz.getMethod(this.getMethodName("get", field.getName()));
                            }
                            Object res = methodCache.invoke(request);
                            if (res == null) {
                                throw new MatrixPayException(matrixNotNull.msg().replace("{channel}", type.getMsg()));
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
                        }
                    }
                }
            }
        }
    }

    private String getMethodName(String fieldName, String prefix) {
        char[] dst = new char[fieldName.length() + 3];
        prefix.getChars(0, 3, dst, 0);
        fieldName.getChars(0, fieldName.length(), dst, 3);
        if ('a' <= dst[3] && 'z' >= dst[3]) {
            dst[3] = (char)(dst[3] - 32);
        }

        return new String(dst);
    }

}
