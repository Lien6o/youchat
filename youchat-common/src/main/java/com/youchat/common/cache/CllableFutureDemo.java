package com.youchat.common.cache;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-05-16 10:30
 * @version: v1.0
 */
@Slf4j
public class CllableFutureDemo {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 4, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("CultivateGameLogicService_%d").build(),
            new CllableFutureDemo.CustomRejectedExecutionHandler());

    private static final ListeningExecutorService LISTENING_DECORATOR = MoreExecutors.listeningDecorator(EXECUTOR);

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info(" k=s act=cu_thread_pool_executor executorActiveCount={}", executor.getActiveCount());
        }
    }

    private void sumbmit() {
        ListenableFuture<String> activityEntryFuture = LISTENING_DECORATOR.submit(() -> "");
        Futures.addCallback(activityEntryFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String s) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        },LISTENING_DECORATOR);
    }



}
