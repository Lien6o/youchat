package com.youchat.common;

import java.util.Collection;

public class CollectionUtil {

    public static <R> boolean isNotEmpty(Collection<R> result) {
        return !isEmpty(result);
    }

    public static <R> boolean isEmpty(Collection<R> result) {
        return result == null || result.isEmpty();
    }

    public static int size(Collection<?> result) {
        if (result == null) {
            return 0;
        }
        return result.size();
    }

}
