package com.youchat.common.whiteboard;

public class TwoThreadOutputV2 {

    private static    boolean loopForOdd = true;

    private static    boolean loopForEven = true;

    private static  int counter = 1;

    public static void main(String[] args) throws InterruptedException {

        // 奇数线程
        new Thread(() -> {
            while (true) {
                while (loopForOdd){

                }

                int counter = TwoThreadOutputV2.counter;
                if (counter > 100) {
                    break;
                }
                System.out.println("奇数线程：" + counter);

                TwoThreadOutputV2.counter++;

                // 修改volatile，通知偶数线程停止循环，同时，准备让自己陷入循环
                loopForEven = false;

                loopForOdd = true;

            }

        }).start();

        new Thread(() -> {
            while (true) {
                while (loopForEven) {

                }

                int counter = TwoThreadOutputV2.counter;
                if (counter > 100) {
                    break;
                }
                System.out.println("偶数线程：" + counter);

                TwoThreadOutputV2.counter++;

                // 修改volatile，通知奇数线程停止循环,同时，准备让自己陷入循环
                loopForOdd = false;

                loopForEven = true;
            }
        }).start();

        // 先启动奇数线程
        loopForOdd = false;

    }
}
