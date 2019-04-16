package com.youchat.common.utils;

import com.google.common.base.Preconditions;

public class SimpleRedisBloomFilter {

        /**
         * 根据给定的布隆过滤器添加值
         */
        public static  <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
            Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
            int[] offset = bloomFilterHelper.murmurHashOffset(value);
            for (int i : offset) {
                RedisClient.execute(jedis -> jedis.setbit(key, i, true));
            }
        }

        /**
         * 根据给定的布隆过滤器判断值是否存在
         */
        public static  <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
            Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
            int[] offset = bloomFilterHelper.murmurHashOffset(value);
            for (int i : offset) {
                if (!RedisClient.execute(jedis -> jedis.getbit(key, i))) {
                    return false;
                }
            }
            return true;
        }
}
