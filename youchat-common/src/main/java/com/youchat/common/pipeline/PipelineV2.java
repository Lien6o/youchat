package com.youchat.common.pipeline;

import java.util.function.Consumer;

/**
 * @author Lien6o
 * @date 2021/5/10 4:09 下午
 */
public class PipelineV2<T> {

    private T request;

    private PipelineV2() {
        // noop
    }

    private static <T> PipelineV2<T> of(T request) {
        return new PipelineV2<>();
    }

    /**
     * 参数校验
     */
    private PipelineV2<T> checkParam(Consumer<T> consumer) {
        consumer.accept(request);
        return this;
    }



}
