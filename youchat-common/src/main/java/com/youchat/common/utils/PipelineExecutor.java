package com.youchat.common.utils;


import redis.clients.jedis.Pipeline;

@FunctionalInterface
public interface PipelineExecutor {
    void load(Pipeline pipeline);
}
