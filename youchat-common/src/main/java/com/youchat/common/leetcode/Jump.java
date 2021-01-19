package com.youchat.common.leetcode;

public class Jump {

    public static void main(String[] args) {
        System.out.println("jump(100) = " + jump(6));
    }

    private static int jump(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return jump(n - 1) + jump(n - 2);
    }
}
