package com.youchat.quick;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 21:15
 * @version: v1.0
 */
public class FirstEntry {

    public static void main(String[] args) {
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 4;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);

        new Disruptor<OrderEvent>(orderEventFactory, ringBufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

    }
}
