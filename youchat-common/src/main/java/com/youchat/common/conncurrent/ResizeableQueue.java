package com.youchat.common.conncurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Lien6o
 * @date 2021/5/7 10:55 上午
 */
public interface ResizeableQueue {

    void setSize();

    int getSize();


    default void get() {
        ArrayBlockingQueue<String> strings = new ArrayBlockingQueue<>(10);
        strings.size();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

    }
   //public E take() throws InterruptedException {
   //    final ReentrantLock lock = this.lock;
   //    lock.lockInterruptibly();
   //    try {
   //        while (count == 0)
   //            notEmpty.await();
   //        return dequeue();
   //    } finally {
   //        lock.unlock();
   //    }
   //}
   //private E dequeue() {
   //    // assert lock.getHoldCount() == 1;
   //    // assert items[takeIndex] != null;
   //    final Object[] items = this.items;
   //    @SuppressWarnings("unchecked")
   //    E x = (E) items[takeIndex];
   //    items[takeIndex] = null;
   //    if (++takeIndex == items.length)
   //        takeIndex = 0;
   //    count--;
   //    if (itrs != null)
   //        itrs.elementDequeued();
   //    notFull.signal();
   //    return x;
   //}

   //private E dequeue() {
   //    ResizableCapacityLinkedBlockingQueue.Node<E> h = this.head;
   //    ResizableCapacityLinkedBlockingQueue.Node<E> first = h.next;
   //    h.next = h;
   //    this.head = first;
   //    E x = first.item;
   //    first.item = null;
   //    return x;
   //}
   //public E take() throws InterruptedException {
   //    int c = true;
   //    AtomicInteger count = this.count;
   //    ReentrantLock takeLock = this.takeLock;
   //    takeLock.lockInterruptibly();

   //    Object x;
   //    int c;
   //    try {
   //        while(count.get() == 0) {
   //            this.notEmpty.await();
   //        }

   //        x = this.dequeue();
   //        c = count.getAndDecrement();
   //        if (c > 1) {
   //            this.notEmpty.signal();
   //        }
   //    } finally {
   //        takeLock.unlock();
   //    }

   //    if (c == this.capacity) {
   //        this.signalNotFull();
   //    }

   //    return x;
   //}
}
