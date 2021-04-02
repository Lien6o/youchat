package com.youchat.common.pipeline;

import java.util.Collections;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 这是使用了一元表达式
 */
public class ProcessEntry {

    public static void main(String[] args) {

        DrunkProcessingObject drunk2 = new DrunkProcessingObject();
        DrunkProcessingObject drunk3 = new DrunkProcessingObject();
        EatProcessingObject eat1 = new EatProcessingObject();

        UnaryOperator<String> p1 = text -> "From Raoul,Mario and Alan:" + text;
        Function<String, String> pa = input -> {
            String a = input + " I am a function;";
            System.out.println("-----");
            return a.hashCode() + "";
        };

        Function<String, String> pipeline = eat1.andThen(drunk2).andThen(drunk3);

        pipeline.apply("22");
        pa.apply("00000");


        // 头部管节
        Function<Integer, String> start = String::valueOf;

        // 继续增加管节
        Function<Integer, String> pipelineV2 = start
                .andThen(input -> Integer.parseInt(input) + 1)
                .andThen(input -> Collections.singletonList(String.valueOf(input + 1)))
                .andThen(input -> input.get(0));
        // 真实执行
        String execute = pipelineV2.apply(1);
        System.out.println("execute = " + execute);

    }
}
