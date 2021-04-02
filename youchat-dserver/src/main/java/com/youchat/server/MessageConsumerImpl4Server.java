package com.youchat.server;

import com.youchat.common.disruptor.MessageConsumer;
import com.youchat.common.entity.TranslatorData;
import com.youchat.common.entity.TranslatorDataWrapper;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 20:41
 * @version: v1.0
 */
public class MessageConsumerImpl4Server extends MessageConsumer {

    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) throws Exception {
        TranslatorData request = event.getData();
        ChannelHandlerContext ctx = event.getCtx();
        //1.业务处理逻辑:
        System.err.println("Sever端: id= " + request.getId()
                + ", name= " + request.getName()
                + ", message= " + request.getMessage());

        //2.回送响应信息:
        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());
        //写出response响应信息:
        ctx.writeAndFlush(response);
    }

}
