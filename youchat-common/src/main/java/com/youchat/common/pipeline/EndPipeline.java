package com.youchat.common.pipeline;

/**
 * @author fishzhao
 * @since 2020-01-21
 */
public abstract class EndPipeline<T> implements Pipeline<T> {

  private final String name;

  public EndPipeline(String name) {
    this.name = name;
  }

  @Override
  public abstract void process(PipelineContext ctx, T t);

  @Override
  public final void forward(PipelineContext ctx, T t) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return name;
  }
}