package com.youchat.common.pipeline;

import java.util.function.Function;

/**
 * @author fishzhao
 * @since 2020-01-21
 */
public final class FunctionalPipelineBuilder<T, P extends Pipeline<T>> {

  private final Function<Pipeline<? super T>, P> factory;

  @SuppressWarnings("unchecked")
  public FunctionalPipelineBuilder() {
    this((Pipeline<? super T> next) -> (P) next);
  }

  private FunctionalPipelineBuilder(Function<Pipeline<? super T>, P> nextFactory) {
    this.factory = nextFactory;
  }

  public FunctionalPipelineBuilder<T, P> add(Function<Pipeline<? super T>, ? extends ForwardingPipeline<T>> nextFactory) {
    return new FunctionalPipelineBuilder<>(factory.compose(nextFactory));
  }

  public P end(EndPipeline<T> endPipeline) {
    return factory.apply(endPipeline);
  }
}