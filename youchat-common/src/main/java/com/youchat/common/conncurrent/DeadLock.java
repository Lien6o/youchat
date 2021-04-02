package com.youchat.common.conncurrent;

public class DeadLock {
    private static final Object o1 = new Object();
    private static final Object o2 = new Object();


    public static void main(String[] args) {
            new Thread(() -> {
                synchronized (o1) {
                    System.out.println("线程1锁o1");
                    try {
                        // 让当前线程睡眠，保证让另一线程得到o2，防止这个线程启动一下连续获得o1和o2两个对象的锁。
                         Thread.sleep(1000);
                        synchronized (o2) {
                            System.out.println("线程1锁o2");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(() -> {
                synchronized (o2) {
                    System.out.println("线程2锁o2");
                    synchronized (o1) {
                        System.out.println("线程2锁o1");
                    }
                }
            }).start();
    }
}
