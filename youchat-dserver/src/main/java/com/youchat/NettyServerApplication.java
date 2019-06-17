package com.youchat;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.youchat.common.disruptor.MessageConsumer;
import com.youchat.common.disruptor.RingBufferWorkerPoolFactory;
import com.youchat.server.MessageConsumerImpl4Server;
import com.youchat.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 20:40
 * @version: v1.0
 */
@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);

        MessageConsumer[] conusmers = new MessageConsumer[4];
        for(int i =0; i < conusmers.length; i++) {
            System.out.println("i = " + i);
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:serverId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024*1024,
                //new YieldingWaitStrategy(),
                new BlockingWaitStrategy(),
                conusmers);

        new NettyServer();
    }
}
