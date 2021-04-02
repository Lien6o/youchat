package com.youchat.common.utils;

import com.google.common.math.LongMath;
import com.google.common.primitives.Longs;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.LongStream;

public class RedisBitmaps {

    private static final String BASE_KEY = "bloomfilter";
    private static final String CURSOR = "cursor";
    private long bitSize;

    RedisBitmaps(long bits) {
        // 位数组的长度，相当于n个long的长度
        this.bitSize = LongMath.divide(bits, Long.SIZE, RoundingMode.CEILING) * Long.SIZE;
        if (bitCount() == 0) {
            RedisClient.execute((jedis -> jedis.setbit(currentKey(), bitSize - 1, false)));
        }
    }

    boolean get(long[] offsets) {
        for (long i = 0; i < cursor() + 1; i++) {
            final long cursor = i;
            // 只要有一个cursor对应的bitmap中，offsets全部命中，则表示可能存在
            boolean match = Arrays.stream(offsets).boxed()
                    .allMatch(offset -> RedisClient.execute(jedis -> jedis.getbit(genkey(cursor), offset)));
            if (match) {
                return true;
            }
        }
        return false;
    }

    boolean get(final long offset) {
        return RedisClient.execute(jedis -> jedis.getbit(currentKey(), offset));
    }

    boolean set(long[] offsets) {
        if (cursor() > 0 && get(offsets)) {
            return false;
        }
        boolean bitsChanged = false;
        for (long offset : offsets) {
            bitsChanged |= set(offset);
        }
        return bitsChanged;
    }

    boolean set(long offset) {
        long l = System.currentTimeMillis();

        if (!get(offset)) {
            RedisClient.execute(jedis -> jedis.setbit(currentKey(), offset, true));
            return true;
        }
        return false;
    }

    long bitCount() {
        return RedisClient.execute(jedis -> jedis.bitcount(currentKey()));
    }

    long bitSize() {
        return this.bitSize;
    }

    private String currentKey() {
        String genkey = genkey(cursor());
        return genkey;
    }

    private String genkey(long cursor) {
        return BASE_KEY + "-" + cursor;
    }

    private Long cursor() {
        String cursor = RedisClient.execute(jedis -> jedis.get(CURSOR));
        return cursor == null ? 0 : Longs.tryParse(cursor);
    }

    /**
     * 扩容
     */
    void ensureCapacityInternal() {
        if (bitCount() * 2 > bitSize()) {
            grow();
        }
    }


    void grow() {
        Long cursor = RedisClient.execute(jedis -> jedis.incr(CURSOR));
        RedisClient.execute((jedis -> jedis.setbit(genkey(cursor), bitSize - 1, false)));
    }

    void reset() {
        String[] keys = LongStream.range(0, cursor() + 1).boxed().map(this::genkey).toArray(String[]::new);
        RedisClient.execute(jedis -> jedis.del(keys));
        RedisClient.execute(jedis -> jedis.set(CURSOR, "0"));
        RedisClient.execute(jedis -> jedis.setbit(currentKey(), bitSize - 1, false));
    }

    private PipelineExecutor apply(PipelineExecutor executor) {
        return executor;
    }
}

