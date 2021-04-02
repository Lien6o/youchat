package com.youchat.common.stream;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

public class ConcurrencyTest {



    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, () -> {
            list.add("0");
        });

        // 获取总的执行时间，单位毫秒
        Console.log(tester.getInterval());
        System.out.println(list.size());
    }
}
