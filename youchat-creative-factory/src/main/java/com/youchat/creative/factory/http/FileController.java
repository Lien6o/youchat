package com.youchat.creative.factory.http;

import org.springframework.util.StringUtils;

import java.io.File;

public class FileController implements HttpController{

    @Override
    public String execute(HttpHeader httpHeader) {
        String href = "http://127.0.0.1:8080/file?path=";
        String head = "<h1>path: " + httpHeader.getPath() + "</h1>";
        String url = httpHeader.getParamMap().get("path");
        if (StringUtils.isEmpty(url)) {
            url = "/";
        }
        FileList files = getFiles(url);

        for (String f : files.getFile()) {
            head += "<h2>" + f + "</h2>";
        }
        for (String d : files.getDirectory()) {
            String dd = d.startsWith("/") ? d.substring(1) : d;
            head += "<h2><a href=" + href + dd + ">" + d + "</a></h2>";
        }
        return "<html><body>" + head + "</body></html>";
    }

    public static void main(String[] args) {
        FileList files = getFiles("/Users");
        System.out.println(files);
    }

    public static FileList getFiles(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        FileList fileList = new FileList();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("文 件：" + tempList[i]);
                fileList.getFile().add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                System.out.println("文件夹：" + tempList[i]);
                fileList.getDirectory().add(tempList[i].toString());
            }
        }
        return fileList;
    }
}
