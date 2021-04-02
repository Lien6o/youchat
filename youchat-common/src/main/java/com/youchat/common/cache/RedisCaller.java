package com.youchat.common.cache;


import redis.clients.jedis.Jedis;

/**
 * redis 回调
 * create by lien6o
 * 2019年04月15日15:03:18
 */
@FunctionalInterface
public interface RedisCaller {
        void call(Jedis jedis);
        }
