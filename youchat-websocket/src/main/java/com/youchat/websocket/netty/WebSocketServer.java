package com.youchat.websocket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @program: youchat-common
 * @description: http://127.0.0.1:8080/index.html
 * @author: lien6o
 * @create: 2018-08-27 16:36
 **/
public class WebSocketServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGrop = new NioEventLoopGroup(1);
        EventLoopGroup workGrop = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGrop, workGrop)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer(null));
            Channel ch = b.bind(8080).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGrop.shutdownGracefully();
            workGrop.shutdownGracefully();
        }
    }
}
