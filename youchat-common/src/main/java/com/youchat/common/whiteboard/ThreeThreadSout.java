package com.youchat.common.whiteboard;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThreadSout {



    private static final Object OBJECT_LOCK = new Object();
    /**
     * 当前即将打印的数字
     */
    private static int current = 0;
    /**
     * 当前线程编号，从0开始
     */
    private int threadNo;
    /**
     * 线程数量
     */
    private int threadCount;
    /**
     * 打印的最大数值
     */
    private int maxInt;

    public void run() {
        while (true) {
            synchronized (OBJECT_LOCK) {
                // 判断是否轮到当前线程执行
                while (current % threadCount != threadNo) {
                    if (current > maxInt) {
                        break;
                    }
                    try {
                        // 如果不是，则当前线程进入wait
                        OBJECT_LOCK.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 最大值跳出循环
                if (current > maxInt) {
                    break;
                }
                System.out.println("thread" + threadNo + " : " + current);
                current++;
                // 唤醒其他wait线程
                OBJECT_LOCK.notifyAll();
            }
        }
    }

// ---------------------------------------------------------------------------------------------------------------------

    private static int count = 0;
    private static final Lock REENTRANT_LOCK = new ReentrantLock();


    static Condition c1 = REENTRANT_LOCK.newCondition();
    static Condition c2 = REENTRANT_LOCK.newCondition();
    static Condition c3 = REENTRANT_LOCK.newCondition();

    public static void main(String[] args) {


        new Thread(() -> {
            while (true) {
                REENTRANT_LOCK.lock();
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
                    REENTRANT_LOCK.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                REENTRANT_LOCK.lock();
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
                    REENTRANT_LOCK.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                REENTRANT_LOCK.lock();
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
                    REENTRANT_LOCK.unlock();
                }
            }
        }).start();
    }




}
