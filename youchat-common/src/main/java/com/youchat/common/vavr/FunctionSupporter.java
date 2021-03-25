package com.youchat.common.vavr;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author enboli
 */
public interface FunctionSupporter {
    /***
     * function -> supplier
     */
    static <T, R> Supplier<R> toSupplier(T t, Function<T, R> function) {
        return () -> function.apply(t);
    }

    /**
     * 覆盖
     */
    static <T> BinaryOperator<T> cover() {
        return (v1, v2) -> v2;
    }
}
