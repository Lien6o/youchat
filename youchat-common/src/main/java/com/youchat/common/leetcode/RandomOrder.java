package com.youchat.common.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 乱序字符串
 * <p>
 * 452
 */
public class RandomOrder {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        bySwap(array);
        byHash(array);
    }

    /**
     * 随机交换
     * 优
     */
    private static void bySwap(int[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int hash = UUID.randomUUID().toString().hashCode();
            int r = (hash < 0 ? (hash * -1) : hash) % length;
            int temp = array[i];
            array[i] = array[r];
            array[r] = temp;
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 哈希散列
     * 简
     */
    private static void byHash(int[] array) {
        Map<String, Integer> map = new HashMap<>(array.length);
        for (int i : array) {
            map.put(UUID.randomUUID().toString(), i);
        }
        System.out.println(map.values());
    }
}
