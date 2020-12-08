package com.youchat.common.conncurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 用countDownLatch 三个线程对某个数进行累加
 */
public class Latch {
    private static int count = 0;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                if (countDownLatch.getCount()>0) {
                    count++;
                    countDownLatch.countDown();
                }
            }).start();
        }
        System.out.println("count = " + count);
    }
}
