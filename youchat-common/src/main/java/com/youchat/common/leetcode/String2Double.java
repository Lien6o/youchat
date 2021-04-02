package com.youchat.common.leetcode;

public class String2Double {

    static String string = "3.123";

    public static void main(String[] args) {
        int i = string.indexOf('.');
        int j = string.length() - i;
        String[] split = string.split("\\.");
        String s = split[0] + split[1];

        int integer = Integer.parseInt(s);
        int kk = 1;
        for (int k = 0; k < j-1; k++) {
            kk = kk * 10;
        }
        double v = integer / (double) (kk);
        System.out.println("v = " + v);
    }


}
