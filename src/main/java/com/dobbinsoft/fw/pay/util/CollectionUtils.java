package com.dobbinsoft.fw.pay.util;

import java.util.Collection;

/**
 * ClassName: CollectionUtils
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-04-20
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

}
