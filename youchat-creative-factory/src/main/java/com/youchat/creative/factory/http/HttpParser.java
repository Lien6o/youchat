package com.youchat.creative.factory.http;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    /**
     * 实现一个简单的HTTP服务器，主要有以下需求。
     * 解释浏览器传来的HTTP协议，只需要处理URL path。
     * 然后把所代理的目录列出来。
     * 在浏览器上可以浏览目录里的文件和下级目录。
     * 如果点击文件，则把文件打开传给浏览器（浏览器能够自动显示图片、PDF，或HTML、CSS、JavaScript以及文本文件）。
     * 如果点击子目录，则进入到子目录中，并把子目录中的文件列出来。
     *
     * @param input
     * @param output
     * @throws IOException
     */
    public static void parse(InputStream input, OutputStream output) throws IOException {
        System.out.println("Process new http request...");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        HttpHeader httpHeader = new HttpHeader(reader);
        String path = httpHeader.getPath();
        System.out.println(path);
        HttpController controller = router(path);

        String data = controller.execute(httpHeader);

        // 发送成功响应:
        //    String data = "<html><body><h1>欢迎来到德莱联盟!</h1></body></html>";
        int length = data.getBytes(StandardCharsets.UTF_8).length;
        writer.write("HTTP/1.0 200 OK\r\n");
        writer.write("Connection: close\r\n");
        writer.write("Content-Type: text/html\r\n");
        writer.write("Content-Length: " + length + "\r\n");
        // 空行标识Header和Body的分隔
        writer.write("\r\n");
        writer.write(data);
        writer.flush();
    }

    private static HttpController router(String path) {
        return new FileController();
    }
}
