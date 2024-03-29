package com.youchat.common.jdk;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * https://zhuanlan.zhihu.com/p/24877041?refer=dreawer
 */
public class PriorityQueueEat {

    public static void main(String[] args) {

        Queue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        priorityQueue.add(4);
        priorityQueue.add(6);
        priorityQueue.add(9);
        priorityQueue.add(0);
        priorityQueue.add(1);
        priorityQueue.add(5);
        priorityQueue.add(2);
        priorityQueue.add(3);

        /*
         *[0, 1, 2, 3, 4, 6, 9, 5]
         *
         * 小顶堆 任意一个非叶子节点的权值，都"不大于"其左右子节点的权值
         *          0
         *      1       2
         *    3   4   6   9
         *  5
         * priorityQueue.remove()
         *          1
         *      3       2
         *    5   4   6   9
         *
         */
        System.out.println("priorityQueue = " + priorityQueue);

        System.out.println("priorityQueue.toArray() = " + Arrays.toString(priorityQueue.toArray()));
    }
}
