package com.dobbinsoft.fw.pay.callback;

import com.dobbinsoft.fw.pay.handler.MatrixPayCallbackHandler;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.fw.support.utils.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class PayHttpCallbackServlet extends HttpServlet {

    private final String servletContext;

    private final MatrixPayService matrixPayService;

    private final Map<String, MatrixPayCallbackHandler> payHandlerMap;

    public PayHttpCallbackServlet(MatrixPayService matrixPayService, Map<String, MatrixPayCallbackHandler> urlHandlerMap, String servletContext) {
        this.servletContext = servletContext;
        this.matrixPayService = matrixPayService;
        this.payHandlerMap = urlHandlerMap;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String key = req.getRequestURI();
        if (StringUtils.isNotEmpty(servletContext) && key.startsWith(servletContext)) {
            key = key.substring(servletContext.length());
        }
        MatrixPayCallbackHandler matrixPayCallbackHandler = this.payHandlerMap.get(key);
        matrixPayCallbackHandler.beforeCheckSign(req);
        MatrixPayOrderNotifyResult payOrderNotifyResult = this.matrixPayService.checkParsePayResult(req);
        Object res = matrixPayCallbackHandler.handle(payOrderNotifyResult, req);
        try (PrintWriter writer = resp.getWriter()) {
            resp.setContentType("application/json");
            writer.write(JacksonUtil.toJSONString(res));
        }
    }
}
