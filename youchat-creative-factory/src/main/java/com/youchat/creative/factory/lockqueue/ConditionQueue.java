package com.youchat.creative.factory.lockqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Lien6o
 * @date 2021/4/20 4:47 下午
 *
 * 非公平锁在调用 lock 后，首先就会调用 CAS 进行一次抢锁，如果这个时候恰巧锁没有被占用，那么直接就获取到锁返回了。
 * 非公平锁在 CAS 失败后，和公平锁一样都会进入到 tryAcquire 方法，在 tryAcquire 方法中，如果发现锁这个时候被释放了（state == 0），非公平锁会直接 CAS 抢锁，但是公平锁会判断等待队列是否有线程处于等待状态，如果有则不去抢锁，乖乖排到后面。
 *
 */
public class ConditionQueue {
    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantLock reentrantLock = new ReentrantLock();

        Condition condition = reentrantLock.newCondition();

        try {
            // 非公平锁在此刻会先进行一次CAS
            reentrantLock.lock();
            // tryAcquire state == 0 时：非公平锁会少一次判读队列中是否存在前节点

            // 一多一少
        } finally {
            reentrantLock.unlock();
        }
    }
}
