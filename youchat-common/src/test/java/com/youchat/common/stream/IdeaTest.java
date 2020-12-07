package com.youchat.common.stream;

import java.util.LinkedList;
import java.util.Queue;

public class IdeaTest {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        queue.offer(2);
        Integer remove = queue.remove();
        System.out.println(queue.poll());
        System.out.println(queue.poll());



    }
}
