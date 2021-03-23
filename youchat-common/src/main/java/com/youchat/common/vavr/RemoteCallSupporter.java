package com.youchat.common.vavr;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author enboli
 */
@Slf4j
public class RemoteCallSupporter {


    public static <T, R> List<R> concurrentCall(List<T> requestList, Integer ableSize, Function<List<T>, R> function, ExecutorService executorService, Long eachTimeOutMills) {
        List<CompletableFuture<R>> completableFutures = Lists.partition(requestList, ableSize).stream()
                .map(list -> CompletableFuture.supplyAsync(FunctionSupporter.toSupplier(list, function), executorService))
                .collect(Collectors.toList());
        return completableFutures.stream().map(x -> {
            try {
                return x.get(eachTimeOutMills, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                log.error("concurrentCall ", e);
                return null;
            }
        }).collect(Collectors.toList());
    }

    public static <T, R> List<R> concurrentCall2(List<T> requestList, Integer ableSize, Function<List<T>, R> function, ExecutorService executorService, Long eachTimeOutMills) {
        List<CompletableFuture<R>> completableFutures = Lists.partition(requestList, ableSize).stream()
                .map(list -> CompletableFuture.supplyAsync(FunctionSupporter.toSupplier(list, function), executorService))
                .collect(Collectors.toList());
        return completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * 串行化执行
     */
    @SneakyThrows
    public static <T, R> List<R> serializationCall(List<T> target, Integer ableSize, Function<List<T>, R> function) {
        return concurrentCall(target, ableSize, function, MoreExecutors.newDirectExecutorService(),0L);
    }

}
