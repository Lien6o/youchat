package com.youchat.creative.factory.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.*;

public class HttpExecutor {

    private static ExecutorService service =
            new ThreadPoolExecutor(10, 20,
            0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10));

    public static void execute(Socket sock) {

        service.submit(() -> {
            try (InputStream input = sock.getInputStream();
                 OutputStream output = sock.getOutputStream()) {
                HttpParser.parse(input, output);
            } catch (Exception e) {
                try {
                    sock.close();
                } catch (IOException ioe) {
                }
                System.out.println("client disconnected.");
            }

        });
    }

}
