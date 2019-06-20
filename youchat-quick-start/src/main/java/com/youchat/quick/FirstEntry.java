package com.youchat.quick;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
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

        /**
         * 1 eventFactory: 消息(event)工厂对象
         * 2 ringBufferSize: 容器的长度
         * 3 executor: 线程池(建议使用自定义线程池) RejectedExecutionHandler
         * 4 ProducerType: 单生产者 还是 多生产者
         * 5 waitStrategy: 等待策略
         */
        //1. 实例化disruptor对象
        Disruptor<OrderEvent> disruptor =
                new Disruptor<>(orderEventFactory, ringBufferSize, executor, ProducerType.SINGLE, new BlockingWaitStrategy());

        //2. 添加消费者的监听 (构建disruptor 与 消费者的一个关联关系)
        disruptor.handleEventsWith(new OrderEventHandler());

        //3. 启动disruptor
        disruptor.start();

        //4. 获取实际存储数据的容器: RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        long l = System.currentTimeMillis();
        for(long i = 0 ; i < 500000; i ++){
            bb.putLong(0, i);
            producer.sendData(bb);
        }
        System.out.println(System.currentTimeMillis() - l);
        disruptor.shutdown();
        executor.shutdown();

        long ll = System.currentTimeMillis();
        for(long i = 0 ; i < 500000; i ++){
            System.out.print("消费者: 499994消费者: 499995zzf");
        }
        System.out.println(System.currentTimeMillis() - ll);

    }
}
