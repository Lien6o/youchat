package com.youchat.creative.factory.http;

import lombok.SneakyThrows;

import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private static final int PORT = 8080;

    @SneakyThrows
    public static void main(String[] args) {
        // 监听指定端口
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("server is running...");
        for (; ; ) {
            Socket sock = ss.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            HttpExecutor.execute(sock);
        }
    }
}
