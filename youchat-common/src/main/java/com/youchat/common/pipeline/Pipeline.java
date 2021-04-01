package com.youchat.common.pipeline;

/**
 * @author fishzhao
 * @since 2020-01-21
 */
public interface Pipeline<T> {

  void process(PipelineContext ctx, T t);

  void forward(PipelineContext ctx, T t);
}