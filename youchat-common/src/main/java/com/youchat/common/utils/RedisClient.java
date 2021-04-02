package com.youchat.common.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

public class RedisClient {

    private static final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    private static JedisPool jedisPool;

    public RedisClient() {
        jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    /**
     * holder
     * create by lien6o
     */
    public static void executeWithHolder(RedisCaller caller) {
        try (Jedis jedis = jedisPool.getResource()) {
            caller.call(jedis);
        }
    }

    /**
     * holder
     * create by lien6o
     */
    public static <T> T execute(JedisExecutor<T> jedisExecutor) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedisExecutor.execute(jedis);
        }
    }

    /**
     * holder
     * create by lien6o
     */
    public static List<Object> pipeline(List<PipelineExecutor> pipelineExecutors) {
        try (Jedis jedis = jedisPool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            for (PipelineExecutor executor : pipelineExecutors)
                executor.load(pipeline);
            return pipeline.syncAndReturnAll();
        }
    }
}
