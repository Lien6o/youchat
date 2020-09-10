package com.youchat.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/8/15 10:25 上午
 * @version: v1.0
 */
public class SocketBIO {
    /**
     * 链接 -> 线程 BIO
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(9090, 20);

        while (true) {
            Socket accept = serverSocket.accept();

            new Thread(()->{
                InputStream inputStream = null;
                try {
                    inputStream = accept.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    while (true) {
                        String readLine = reader.readLine();
                        if (readLine != null) {
                            System.out.println(readLine);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
