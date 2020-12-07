package com.youchat.account.collector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Collector {

    public static void collect(Long accountId, Runnable runnable) {
        log.info("accountId={}", accountId);

    }
}
