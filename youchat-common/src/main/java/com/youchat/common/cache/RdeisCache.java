package com.youchat.common.cache;

import com.youchat.common.utils.JedisExecutor;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-05-16 10:36
 * @version: v1.0
 */
public class RdeisCache {

    private static Jedis getJedis(){
        return new Jedis();
    }
    /**
     * holder
     * create by lien6o
     *
     * miniapplogic_ 不加这个前缀
     */
    public static void executeWithHolder(RedisCaller caller) {
        try (Jedis jedis = getJedis()) {
            caller.call(jedis);
        }
    }

    /**
     * holder
     * create by lien6o
     * miniapplogic_ 不加这个前缀 不和其他 本类方法兼容
     */
    public static <T> T execute(JedisExecutor<T> jedisExecutor) {
        try (Jedis jedis = getJedis()) {
            return jedisExecutor.execute(jedis);
        }
    }


    /**
     * holder
     * create by lien6o
     */
    public static List<Object> pipeline(List<PipelineExecutor> pipelineExecutors) {
        try (Jedis jedis = getJedis()) {
            Pipeline pipeline = jedis.pipelined();
            for (PipelineExecutor executor : pipelineExecutors)
                executor.load(pipeline);
            return pipeline.syncAndReturnAll();
        }
    }
}
