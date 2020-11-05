package com.youchat.common.leetcode;

public class RandomFunctionCommon {
    /**
     * 概率原函数
     *
     * 生成 [min,max] 的随机数
     * p   概率 0
     * 1-p 概率 1
     */
    private static class RandomOriginal {

        private final double p;

        public RandomOriginal(double p) {
          this.p = p;
        }

        public int getNext() {
            return Math.random() < p ? 0 : 1;
        }
    }


    private static int genZeroOrOne(double p) {
        int r1 ;
        int r2 ;
        do {
            r1 = new RandomOriginal(p).getNext();
            r2 = new RandomOriginal(p).getNext();
        } while (r1 == r2);

        return r1 > r2 ? 0 : 1;
    }

    public static void main(String[] args) {
        testBinaryGen();
    }

    private static void testBinaryGen() {
        int set1 = 0;
        int set0 =0;
        for (int i = 0; i < 1000; i++) {
            int binary = genZeroOrOne(0.7);
            if (binary == 1) {
                set1++;
            }else {
                set0 ++;
            }
        }
        System.out.println(set0);
        System.out.println(set1);
    }
}
