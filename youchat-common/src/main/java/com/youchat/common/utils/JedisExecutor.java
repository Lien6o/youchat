package com.youchat.common.utils;


import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisExecutor<T> {
    T execute(Jedis jedis);
}
