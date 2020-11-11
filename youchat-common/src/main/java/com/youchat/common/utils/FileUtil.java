package com.youchat.common.utils;

import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.io.file.FileReader;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {
    /**
     * 按行读取文件
     */
    public static List<String> readeFileByLine(String name) {
        List<String> list = new LinkedList<>();
        try (BufferedReader bf =
                     new BufferedReader(new InputStreamReader(new FileInputStream(new File(name))))) {
            String str;
            while ((str = bf.readLine()) != null) {
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回数组
        return list;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String path = "/Users/enboli/lienbo/youchat/youchat-common/src/test/java/com/youchat/common/stream/test.txt";


        FileAppender appender = new FileAppender(new File(path), 2, true);
        appender.append("\n");
        appender.append("1");
        appender.append("2");
        appender.append("3");
        appender.append("4");
        appender.append("5");
        appender.flush();
        appender.toString();

        FileReader fileReader = new FileReader(path);
        String result = fileReader.readString();
        System.out.println(result);
    }
}
