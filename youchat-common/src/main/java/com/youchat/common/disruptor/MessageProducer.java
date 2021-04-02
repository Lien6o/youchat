package com.youchat.common.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.youchat.common.entity.TranslatorData;
import com.youchat.common.entity.TranslatorDataWrapper;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 19:18
 * @version: v1.0
 */
public class MessageProducer {

    private String producerId;

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    public MessageProducer(String producerId, RingBuffer<TranslatorDataWrapper> ringBuffer) {
        this.producerId = producerId;
        this.ringBuffer = ringBuffer;
    }

    public void onData(TranslatorData data, ChannelHandlerContext ctx) {
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWrapper wrapper = ringBuffer.get(sequence);
            wrapper.setData(data);
            wrapper.setCtx(ctx);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
