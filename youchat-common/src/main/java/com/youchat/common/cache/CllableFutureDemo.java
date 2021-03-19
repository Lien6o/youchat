package com.youchat.common.cache;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.concurrent.*;

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
            // log.info(" k=s act=cu_thread_pool_executor executorActiveCount={}", executor.getActiveCount());
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
        if (!Uninterruptibles.awaitUninterruptibly(new CountDownLatch(1), 1, TimeUnit.SECONDS)) {

        }
    }

}
