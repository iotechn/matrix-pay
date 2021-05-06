package com.dobbinsoft.fw.pay.handler;

import java.util.LinkedList;

/**
 * ClassName: AbstractPayCallbackHandler
 * Description: 支付回调处理责任链
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public abstract class AbstractPayCallbackHandler<T> {

    public static LinkedList<AbstractPayCallbackHandler> chain = new LinkedList<>();

    public static void append(AbstractPayCallbackHandler handler) {
        synchronized (chain) {
            if (!chain.contains(handler)) {
                chain.add(handler);
            }
        }
    }

    protected void doChain(Object object) {
        int i = chain.indexOf(this);
        if (chain.size() > i + 1) {
            chain.get(i + 1).handle(object);
        }
    }

    public static Object doFirstChain(Object object) {
        return chain.get(0).handle(object);
    }

    /**
     * 默认入参 PayOrderNotifyResult
     * @param t
     * @return
     */
    public abstract Object handle(T t);

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass();
    }
}
