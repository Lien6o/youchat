package com.youchat.common.vavr;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author enboli
 */
@Slf4j
public class BatchCallSupport {

    private static final LongAdder THREAD_ADDER = new LongAdder();
    private static final String THREAD_NAME = "local_cache_refresh_";

    /**
     * 线程池
     */
    private static final ExecutorService executorService = new ThreadPoolExecutor(20
            , 40
            , 0
            , TimeUnit.MINUTES
            , new LinkedBlockingQueue<>(2),
            runnable -> {
                THREAD_ADDER.increment();
                return new Thread(runnable, THREAD_NAME + THREAD_ADDER.intValue());
            }, (runnable, executor) -> {
    });

    public static <T, R> List<R> concurrentCall(List<T> requestList, Integer ableSize, Function<List<T>, R> function, ExecutorService executorService, Long eachTimeOutMills) {
        List<CompletableFuture<R>> futures = Lists.partition(requestList, ableSize).stream().map(list -> CompletableFuture.supplyAsync(FunctionSupporter.toSupplier(list, function), executorService)).collect(Collectors.toList());
        return futures.stream().map(call(eachTimeOutMills)).collect(Collectors.toList());
    }

    /**
     * 串行化执行
     */
    @SneakyThrows
    public static <T, R> List<R> serializationCall(List<T> target, Integer ableSize, Function<List<T>, R> function) {
        return concurrentCall(target, ableSize, function, MoreExecutors.newDirectExecutorService(), 0L);
    }

    private static <R> Function<CompletableFuture<R>, R> call(Long eachTimeOutMills) {
        return completableFuture -> {
            try {
                return completableFuture.get(eachTimeOutMills, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                log.error("concurrentCall ", e);
                return null;
            }
        };
    }

    public static <T, R> List<R> concurrentCall2(List<T> requestList, Integer ableSize, Function<List<T>, R> function, ExecutorService executorService) {
        List<CompletableFuture<R>> collect = Lists.partition(requestList, ableSize).stream().map(ts -> CompletableFuture.supplyAsync(FunctionSupporter.toSupplier(ts, function), executorService)).collect(Collectors.toList());
        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }


    public static void main(String[] args) {
        LongAdder count = new LongAdder();
        ArrayList<Integer> integers = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        List<String> strings = concurrentCall(integers, 2, new Function<List<Integer>, String>() {
            @SneakyThrows
            @Override
            public String apply(List<Integer> integers) {
                count.increment();
                System.out.println("Thread.currentThread() = " + Thread.currentThread().getName() + " Time=" + System.currentTimeMillis() + " count" + count.intValue());

                Thread.sleep(1000);
                System.err.println("Thread.currentThread() = " + Thread.currentThread().getName() + " Time=" + System.currentTimeMillis() + " count" + count.intValue());

                return integers.toString();
            }
        }, executorService, 20L);
    }
}
