package com.youchat;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.youchat.client.MessageConsumerImpl4Client;
import com.youchat.client.NettyClient;
import com.youchat.common.disruptor.MessageConsumer;
import com.youchat.common.disruptor.RingBufferWorkerPoolFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 20:44
 * @version: v1.0
 */
@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(NettyClientApplication.class, args);

        MessageConsumer[] conusmers = new MessageConsumer[4];
        for(int i =0; i < conusmers.length; i++) {
            System.out.println("i = " + i);
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024*1024,
                 new YieldingWaitStrategy(),
                //new BlockingWaitStrategy(),
                conusmers);

        //建立连接 并发送消息
        new NettyClient().sendData();
    }
}
