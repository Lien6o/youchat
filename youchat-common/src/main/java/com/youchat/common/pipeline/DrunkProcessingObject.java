package com.youchat.common.pipeline;

import java.util.function.UnaryOperator;

public class DrunkProcessingObject implements UnaryOperator<String> {

    @Override
    public String apply(String s) {
        System.out.println("drunk");
        return "drunk";
    }
}
