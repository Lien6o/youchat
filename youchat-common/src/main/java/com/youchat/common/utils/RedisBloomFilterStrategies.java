package com.youchat.common.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;

public enum  RedisBloomFilterStrategies implements RedisBloomFilter.Strategy {

    MURMUR128_MITZ_64  {
        @Override
        public boolean put(String string, int numHashFunctions, RedisBitmaps bits) {
            long l = System.currentTimeMillis();
            long bitSize = bits.bitSize();
            byte[] bytes = Hashing.murmur3_128().hashString(string, Charsets.UTF_8).asBytes();

            long hash1 = lowerEight(bytes);
            long hash2 = upperEight(bytes);

            boolean bitsChanged = false;
            long combinedHash = hash1;
            long[] offsets = new long[numHashFunctions];

            for (int i = 0; i < numHashFunctions; i++) {
                offsets[i] = (combinedHash & Long.MAX_VALUE) % bitSize;
                combinedHash += hash2;
            }
            bitsChanged = bits.set(offsets);

            bits.ensureCapacityInternal();
            //自动扩容
            return bitsChanged;
        }

        @Override
        public boolean mightContain(String object, int numHashFunctions, RedisBitmaps bits) {
            long bitSize = bits.bitSize();
            byte[] bytes = Hashing.murmur3_128().hashString(object, Charsets.UTF_8).asBytes();
            long hash1 = lowerEight(bytes);
            long hash2 = upperEight(bytes);
            long combinedHash = hash1;
            long[] offsets = new long[numHashFunctions];
            for (int i = 0; i < numHashFunctions; i++) {
                offsets[i] = (combinedHash & Long.MAX_VALUE) % bitSize;
                combinedHash += hash2;
            }
            return bits.get(offsets);
        }

        private long lowerEight(byte[] bytes) {
            return Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
        }

        private long upperEight(byte[] bytes) {
            return Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
        }
    };

    private RedisBloomFilterStrategies() {
    }

}
