package com.youchat.common.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;
import com.youchat.common.entity.TranslatorDataWrapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 19:19
 * @version: v1.0
 */
public class RingBufferWorkerPoolFactory {

    private static class SingletonHolder {
        static final RingBufferWorkerPoolFactory INSTANCE = new RingBufferWorkerPoolFactory();
    }

    private RingBufferWorkerPoolFactory(){

    }

    public static RingBufferWorkerPoolFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static Map<String, MessageProducer> producers = new ConcurrentHashMap<>();

    private static Map<String, MessageConsumer> consumers = new ConcurrentHashMap<>();

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    private SequenceBarrier sequenceBarrier;

    private WorkerPool<TranslatorDataWrapper> workerPool;

    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers) {
        //1. 构建ringBuffer对象
        this.ringBuffer = RingBuffer.create(type,
                TranslatorDataWrapper::new,
                bufferSize,
                waitStrategy);
        //2.设置序号栅栏
        this.sequenceBarrier = this.ringBuffer.newBarrier();
        //3.设置工作池
        this.workerPool = new WorkerPool<>(this.ringBuffer,
                this.sequenceBarrier,
                new EventExceptionHandler(), messageConsumers);
        //4 把所构建的消费者置入池中
        for(MessageConsumer mc : messageConsumers){
            consumers.put(mc.getConsumerId(), mc);
        }
        //5 添加我们的sequences
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());
        //6 启动我们的工作池
        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/2));
    }

    public MessageProducer getMessageProducer(String producerId){
        MessageProducer messageProducer = producers.get(producerId);
        if(null == messageProducer) {
            messageProducer = new MessageProducer(producerId, this.ringBuffer);
            producers.put(producerId, messageProducer);
        }
        return messageProducer;
    }

    /**
     * 异常静态类
     * @author Alienware
     *
     */
    static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWrapper> {
        public void handleEventException(Throwable ex, long sequence, TranslatorDataWrapper event) {
        }

        public void handleOnStartException(Throwable ex) {
        }

        public void handleOnShutdownException(Throwable ex) {
        }
    }

}
