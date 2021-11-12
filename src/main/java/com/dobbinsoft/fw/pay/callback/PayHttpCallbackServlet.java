package com.dobbinsoft.fw.pay.callback;

import com.dobbinsoft.fw.pay.handler.PayCallbackHandler;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class PayHttpCallbackServlet extends HttpServlet {

    private MatrixPayService matrixPayService;

    private Map<String, PayCallbackHandler> payHandlerMap;

    public PayHttpCallbackServlet(MatrixPayService matrixPayService, Map<String, PayCallbackHandler> urlHandlerMap) {
        this.matrixPayService = matrixPayService;
        this.payHandlerMap = urlHandlerMap;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MatrixPayOrderNotifyResult payOrderNotifyResult = this.matrixPayService.checkSign(req);
        String requestURI = req.getRequestURI();
        Object res = this.payHandlerMap.get(requestURI).handle(payOrderNotifyResult);
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            resp.setContentType("application/json");
            writer.write(new Gson().toJson(res));
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
