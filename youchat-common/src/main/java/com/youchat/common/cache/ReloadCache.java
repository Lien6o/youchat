package com.youchat.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * @program: youchat-common
 * @description:
 * @author: lien6o
 * @create: 2018-08-17 13:53
 **/
public class ReloadCache {
    private static LongAdder threadAdder = new LongAdder();
    private static final String THREAD_NAME = "local_cache_refresh_";
    /**
     * 线程池
     */
       private static final ExecutorService executorService = new ThreadPoolExecutor(2, 4, 0, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10),
            new ThreadFactory() {

                @Override
                public Thread newThread(Runnable r) {
                    threadAdder.increment();
                    return new Thread(r, THREAD_NAME
                            + threadAdder.intValue());
                }
            }, new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r,
                                      ThreadPoolExecutor executor) {
        }
    });
    /**
     * 异步刷新线程池
     *
     * @author lienbo
     */
    private static ListeningExecutorService backgroundRefreshPools = MoreExecutors.listeningDecorator(executorService);

    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/8/21
     */
    public static final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .refreshAfterWrite(100,
                    TimeUnit.MILLISECONDS)
            .build(new CacheLoader<String, String>() {

                @Override
                public String load(
                        String key) {
                    return getNewValue();
                }

                @Override
                public ListenableFuture<String> reload(
                        String key,
                        String oldValue) {
                    return backgroundRefreshPools.submit(ReloadCache::getNewValue);
                }
            });

    private static String getNewValue() {
        return System.currentTimeMillis() + "";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        cache.put("aaa", "1");
        String aaa1 = cache.get("aaa");
        System.out.println(aaa1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100L);
            Future<String> aaa = executorService.submit(() -> cache.get("aaa"));
            String s = aaa.get();
            System.out.println(s);
        }
        executorService.shutdown();
    }
}
