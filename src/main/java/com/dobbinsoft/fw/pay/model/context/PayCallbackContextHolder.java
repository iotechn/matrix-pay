package com.dobbinsoft.fw.pay.model.context;

public class PayCallbackContextHolder {

    private static ThreadLocal<PayCallbackContext> threadLocal = new ThreadLocal<>();

    public static PayCallbackContext get() {
        return threadLocal.get();
    }

    public static void set(PayCallbackContext context) {
        threadLocal.set(context);
    }

    public static void setPayId(String payId) {
        PayCallbackContext context = threadLocal.get();
        if (context != null) {
            context.setPayId(payId);
        }
    }

    public static void clear() {
        threadLocal.remove();
    }

}
