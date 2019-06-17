package com.youchat.client;

import com.youchat.common.disruptor.MessageConsumer;
import com.youchat.common.entity.TranslatorData;
import com.youchat.common.entity.TranslatorDataWrapper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 20:44
 * @version: v1.0
 */
public class MessageConsumerImpl4Client extends MessageConsumer {

    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    public void onEvent(TranslatorDataWrapper event) throws Exception {
        TranslatorData response = event.getData();
        ChannelHandlerContext ctx = event.getCtx();
        //业务逻辑处理:
        long start = System.currentTimeMillis();
        try {
            System.err.println("Client端: id= " + response.getId()
                    + ", name= " + response.getName()
                    + ", message= " + response.getMessage());
        } finally {
            ReferenceCountUtil.release(response);
        }
        System.out.println("System.currentTimeMillis()-start = " + (System.currentTimeMillis() - start));

    }

}
