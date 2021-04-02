package com.youchat.common.utils;

import java.util.Random;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/8/13 4:58 下午
 * @version: v1.0
 */
public class RandomTest {

    public static void main(String[] args) {


         int t = 0;
         int d = 0;
         for (int i = 0; i < 1000000; i++) {

             if (aa()) {
                 t++;
             } else {
                 d++;
             }
         }
         System.out.println(t);
         System.out.println(d);
    }

    private static int a() {
        Random rand = new Random();
        int x = rand.nextInt(5) + 1;
        return x;
    }


    private static boolean aa() {
        boolean a = a() % 2 == 1 && (a() + 5) % 2 == 1;
        boolean b = a() % 2 == 0 && (a() + 5) % 2 == 0;
        return a == b;
    }
}
