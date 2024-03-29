package com.youchat.client;

import com.youchat.common.disruptor.MessageProducer;
import com.youchat.common.disruptor.RingBufferWorkerPoolFactory;
import com.youchat.common.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 20:45
 * @version: v1.0
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         try {
         TranslatorData response = (TranslatorData)msg;
         System.err.println("Client端: id= " + response.getId()
         + ", name= " + response.getName()
         + ", message= " + response.getMessage());
         } finally {
         //一定要注意 用完了缓存 要进行释放
         ReferenceCountUtil.release(msg);
         }
         */
        TranslatorData response = (TranslatorData)msg;
        String producerId = "code:seesionId:002";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(response, ctx);


    }
}
