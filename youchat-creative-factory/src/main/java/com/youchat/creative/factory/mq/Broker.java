package com.youchat.creative.factory.mq;

/**
 * 实现一个生产者/消费者消息队列服务，主要有以下需求。
 *
 * 消息队列采用一个Ring-buffer的数据结构。
 * 可以有多个topic供生产者写入消息及消费者取出消息。
 * 需要支持多个生产者并发写。
 * 需要支持多个消费者消费消息（只要有一个消费者成功处理消息就可以删除消息）。
 * 消息队列要做到不丢数据（要把消息持久化下来）。
 * 能做到性能很高。
 *
 */
public class Broker {

    /**
     * 存储数据
     */
    private void flushData(String data) {

    }


}
