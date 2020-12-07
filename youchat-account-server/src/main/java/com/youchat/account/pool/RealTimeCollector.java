package com.youchat.account.pool;

import java.util.List;

/**
 * account collector
 *
 * collect account real time info
 */
public class RealTimeCollector {

    private static void base() {

    }

    public static void collect(Runnable runnable) {
        runnable.run();
    }

    public static void collect(Long accountId) {
         base();
    }

    public static void collect(List<Long> accountIds) {
        base();
    }


}
