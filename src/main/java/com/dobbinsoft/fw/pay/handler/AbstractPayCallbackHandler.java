package com.dobbinsoft.fw.pay.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * ClassName: AbstractPayCallbackHandler
 * Description: 支付回调处理责任链
 *
 * @author: e-weichaozheng
 * @date: 2021-04-22
 */
public abstract class AbstractPayCallbackHandler<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPayCallbackHandler.class);

    public static final String DEFAULT_CHAIN = "DEFAULT_CHAIN";

    public static Map<String, LinkedList<AbstractPayCallbackHandler>> chainHolder = new HashMap<>();

    public static void append(AbstractPayCallbackHandler handler) {
        append(handler, DEFAULT_CHAIN);
    }

    public static void append(AbstractPayCallbackHandler handler, String chainName) {
        synchronized (chainHolder) {
            logger.info("[支付回调责任链] 添加成功； Group=" + DEFAULT_CHAIN + " ;Handler=" + handler.getClass().getName());
            LinkedList<AbstractPayCallbackHandler> chain = chainHolder.putIfAbsent(chainName, new LinkedList<>());
            if (chain == null) {
                chain = chainHolder.get(chainName);
            }
            if (!chain.contains(handler)) {
                chain.add(handler);
            }
        }
    }

    protected void doChain(Object object) {
        LinkedList<AbstractPayCallbackHandler> chain = chainHolder.putIfAbsent(this.getChainName(), new LinkedList<>());
        if (chain == null) {
            chain = chainHolder.get(this.getChainName());
        }
        int i = chain.indexOf(this);
        if (chain.size() > i + 1) {
            chain.get(i + 1).handle(object);
        }
    }

    public static Object doFirstChain(Object object) {
        return doFirstChain(object, DEFAULT_CHAIN);
    }

    public static Object doFirstChain(Object object, String chainName) {
        LinkedList<AbstractPayCallbackHandler> chain = chainHolder.putIfAbsent(chainName, new LinkedList<>());
        if (chain == null) {
            chain = chainHolder.get(chainName);
        }
        return chain.get(0).handle(object);
    }

    /**
     * 默认入参 PayOrderNotifyResult
     * @param t
     * @return
     */
    public abstract Object handle(T t);

    /**
     * 获取处理器 所属责任链名称
     * @return
     */
    public abstract String getChainName();

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass();
    }

}
