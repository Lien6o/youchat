package com.youchat.server;

import com.youchat.common.disruptor.MessageProducer;
import com.youchat.common.disruptor.RingBufferWorkerPoolFactory;
import com.youchat.common.entity.TranslatorData;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 20:42
 * @version: v1.0
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         TranslatorData request = (TranslatorData)msg;
         System.err.println("Sever端: id= " + request.getId()
         + ", name= " + request.getName()
         + ", message= " + request.getMessage());
         //数据库持久化操作 IO读写 ---> 交给一个线程池 去异步的调用执行
         TranslatorData response = new TranslatorData();
         response.setId("resp: " + request.getId());
         response.setName("resp: " + request.getName());
         response.setMessage("resp: " + request.getMessage());
         //写出response响应信息:
         ctx.writeAndFlush(response);
         */
        TranslatorData request = (TranslatorData)msg;
        //自已的应用服务应该有一个ID生成规则
        String producerId = "code:sessionId:001";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(request, ctx);


    }

}
