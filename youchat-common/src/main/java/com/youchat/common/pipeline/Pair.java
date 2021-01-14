package com.youchat.common.pipeline;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class Pair<S, T> {
    private final String name;

    private final S predicate;

    private final T function;

    public static <S, T> Pair<S, T> of(String name, S s, T t) {
        return new Pair<>(name, s, t);
    }

}
