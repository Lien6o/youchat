package com.youchat.creative.factory.telnet;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 实现一个telnet版本的聊天服务器，主要有以下需求。
 * <p>
 * 每个客户端可以用使用telnet ip:port的方式连接到服务器上。
 * 新连接需要用用户名和密码登录，如果没有，则需要注册一个。
 * 然后可以选择一个聊天室加入聊天。
 * 管理员有权创建或删除聊天室，普通人员只有加入、退出、查询聊天室的权力。
 * 聊天室需要有人数限制，每个人发出来的话，其它所有的人都要能看得到。
 */
public class ChatRoom {

    private static final int PORT = 4000;


    @SneakyThrows
    public static void main(String[] args) {
        ServerSocket server = new ServerSocket(PORT);
        for (; ; ) {
            Socket socket = server.accept();
            try (InputStream inStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8));
                for (; ; ) {
                    System.out.println(reader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
