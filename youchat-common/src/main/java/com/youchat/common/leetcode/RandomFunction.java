package com.youchat.common.leetcode;

import java.util.*;

/**
 *
 * 给定函数 f 等概率生成 [a,b] 区间的任意数字，
 *
 * 仅使用此唯一随机方式。生成 函数 g  , 使得等概率生成[c,d]区间的任意数字。
 *
 *
 * step1.生成 等概率 0 1 发生器。
 * step2.用二进制描述目标函数区间。超出区间则重试，直至符合区间预期。
 *
 */
public class RandomFunction {
    /**
     * 概率原函数
     *
     * 生成 [min,max] 的随机数
     *
     */
    private static class RandomOriginal {

        private final int min;

        private final int max;

        public RandomOriginal(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getNext() {
            return new Random().nextInt(max - min + 1) + min;
        }

        public int min() {
            return this.min;
        }

        public int max() {
            return this.max;
        }
    }

    public static void main(String[] args) {
        testG();
    }

    private static void testG() {
        List<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            int g = g(1, 4, 3, 6);
            list.add(g);
        }
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer key : list) {
            Integer c = map.get(key);
            if (c != null) {
                c++;
            } else {
                c = 1;
            }
            map.put(key, c);
        }
        System.out.println(map);
    }

    /**
     * 目标函数 等概率生成 [c,d]
     */
    private static int g(int a, int b, int c, int d) {
        // 0 - h
        int h = d - c;
        // 需要左移多少位才能产生大于h的数字
        int i = 1;
        while ((1 << i) - 1 < h) {
            i++;
        }

        int r = 0;
        do {
            for (int j = 0; j < i; j++) {
                r = r + (binaryGen(a, b) << j);
            }
        } while (r > h);
        return r + h;
    }

    /**
     * 该函数只能返回 0 1  并且概率都是 50%
     */
    private static int binaryGen(int min, int max) {
        RandomOriginal f = new RandomOriginal(min, max);
        int size = max - min + 1;
        boolean isOdd = (size & 1) == 1;
        int mid = min + (max - min) / 2;
        int next;
        int r = 0;
        do {
            next = f.getNext();
            if (isOdd) {
                if (next < mid) {
                    r = 1;
                }
            } else {
                if (next <= mid) {
                    r = 1;
                }
            }
        } while (isOdd && next == mid);
        return r;
    }

    private static void testBinaryGen() {
        int set1 = 0;
        int set0 =0;
        for (int i = 0; i < 1000; i++) {
             int binary = binaryGen(1, 4);
             if (binary == 1) {
                 set1++;
             }else {
                 set0 ++;
             }
             System.out.println(binary);
         }
        System.out.println(set0);
        System.out.println(set1);
    }
}
