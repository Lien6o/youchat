package com.youchat.quick;


import com.lmax.disruptor.EventHandler;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 21:17
 * @version: v1.0
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
       // Thread.sleep(Integer.MAX_VALUE);
        System.err.println("消费者: " + event.getValue());

    }
}
