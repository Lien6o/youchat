package com.youchat.creative.factory.http;

import com.google.common.base.Splitter;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//      GET /lienbo HTTP/1.1
//      Host: 127.0.0.1:8080
//      Connection: keep-alive
//      Cache-Control: max-age=0
//      Upgrade-Insecure-Requests: 1
//      User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36
//      Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
//      Sec-Fetch-Site: none
//      Sec-Fetch-Mode: navigate
//      Sec-Fetch-User: ?1
//      Sec-Fetch-Dest: document
//      Accept-Encoding: gzip, deflate, br
//      Accept-Language: zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7
@Getter
public class HttpHeader {

    @SneakyThrows
    public HttpHeader(BufferedReader reader) {
        String first = reader.readLine();
        List<String> strings = Splitter.on(' ').splitToList(first);
        String s = strings.get(0);
        switch (s){
            case "GET":
                this.requestMethod = HttpEnum.GET;
                break;
            case "POST":
                this.requestMethod = HttpEnum.POST;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + s);
        }
        this.path = strings.get(1);
        this.httpVersion = strings.get(2);
        this.paramMap = getParameter(this.path);

        for (; ; ) {
            String header = reader.readLine();
            // 读取到空行时, HTTP Header读取完毕
            if (header.isEmpty()) {
                break;
            }
            System.out.println(header);
        }
    }

    private Map<String,String> paramMap;

    private HttpEnum requestMethod;

    private String path;

    private String httpVersion;

    private String host;

    private String connection;

    private String cacheControl;

    private String upgradeInsecureRequests;

    private String userAgent;

    private String acceptEncoding;

    private String acceptLanguage;

    private static Map<String, String> getParameter(String url) {
        Map<String, String> map = new HashMap<>();
        try {
            final String charset = "utf-8";
            url = URLDecoder.decode(url, charset);
            if (url.indexOf('?') != -1) {
                final String contents = url.substring(url.indexOf('?') + 1);
                String[] keyValues = contents.split("&");
                for (int i = 0; i < keyValues.length; i++) {
                    String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
                    String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
