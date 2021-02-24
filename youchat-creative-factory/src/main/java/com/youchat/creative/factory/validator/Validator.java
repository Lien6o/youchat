package com.youchat.creative.factory.validator;

@FunctionalInterface
public interface Validator <T>{


    boolean accept(T t);

}
