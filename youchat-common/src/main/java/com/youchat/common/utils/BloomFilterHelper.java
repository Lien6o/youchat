package com.youchat.common.utils;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

public class BloomFilterHelper<T> {

    private int numHashFunctions;

    private int bitSize;

    private Funnel<T> funnel;

    /**
     *
     * @param funnel
     * @param expectedInsertions
     * @param fpp
     */
    private BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }


    public static <T> BloomFilterHelper<T> create(int expectedInsertions, double fpp) {
        Preconditions.checkArgument(expectedInsertions >= 0L, "Expected insertions (%s) must be >= 0", expectedInsertions);
        Preconditions.checkArgument(fpp > 0.0D, "False positive probability (%s) must be > 0.0", fpp);
        Preconditions.checkArgument(fpp < 1.0D, "False positive probability (%s) must be < 1.0", fpp);
        if (expectedInsertions == 0) {
            expectedInsertions = 1;
        }
        try {
            return new BloomFilterHelper(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);
        } catch (IllegalArgumentException var10) {
            throw new IllegalArgumentException("Could not create BloomFilter of  bits", var10);
        }
    }




    int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}
