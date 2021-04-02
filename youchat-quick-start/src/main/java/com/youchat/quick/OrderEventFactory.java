package com.youchat.quick;

import com.lmax.disruptor.EventFactory;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 21:17
 * @version: v1.0
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {

    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
