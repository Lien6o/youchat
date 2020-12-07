package com.youchat.common.utils;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {
    /**
     * 按行读取文件
     */
    public static List<String> readeFileByLine(String path) {
        List<String> list = new LinkedList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String str;
            while ((str = bf.readLine()) != null) {
                list.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回数组
        return list;
    }

    public static void writeFile(String text, String path) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
            out.newLine();
            out.append(text);
            out.newLine();
            out.append("你好");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final static String path = "/Users/enboli/lienbo/youchat/youchat-common/src/test/java/com/youchat/common/stream/test.txt";


    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("FileUtil.readeFileByLine(path) = " + FileUtil.readeFileByLine(path));
        writeFile("-------", path);
        System.out.println("FileUtil.readeFileByLine(path) = " + FileUtil.readeFileByLine(path));
    }
}
