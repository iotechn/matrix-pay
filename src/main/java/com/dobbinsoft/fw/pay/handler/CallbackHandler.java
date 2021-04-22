package com.dobbinsoft.fw.pay.handler;

import java.util.LinkedList;

/**
 * ClassName: CallbackHandler
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public abstract class CallbackHandler {

    public static LinkedList<CallbackHandler> chain = new LinkedList<>();

    public static void append(CallbackHandler handler) {
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

    public static void doFirstChain(Object object) {
        chain.get(0).handle(object);
    }

    /**
     * 默认入参 PayOrderNotifyResult
     * @param payOrderNotifyResult
     * @return
     */
    public abstract void handle(Object payOrderNotifyResult);

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass();
    }
}
