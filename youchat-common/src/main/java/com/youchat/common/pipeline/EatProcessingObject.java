package com.youchat.common.pipeline;

import java.util.function.UnaryOperator;

public class EatProcessingObject implements UnaryOperator<String> {


    @Override
    public String apply(String s) {
        System.out.println("eat");
        return "eat";
    }
}
