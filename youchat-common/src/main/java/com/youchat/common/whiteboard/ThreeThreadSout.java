package com.youchat.common.whiteboard;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThreadSout {


    private static int count = 0;
    private static Lock lock = new ReentrantLock();


    public static void main(String[] args) {

        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();

        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (count % 3 != 0) {
                        //刚开始count为0  0%3=0 所以此线程执行  执行完之后 唤醒现成2，由于此时count已经进行了++，所有while成立，c1进入等待状态，其他两个也一样
                        c1.await();
                    }
                    System.out.print( "A");
                    count++;
                    //唤醒线程2
                    c2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (count % 3 != 1) {
                        c2.await();
                    }
                    System.out.print("B");
                    count++;
                    //唤醒线程3
                    c3.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (count % 3 != 2) {
                        c3.await();
                    }
                    System.out.print("C");
                    count++;
                    //唤醒线程1
                    c1.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }




}
