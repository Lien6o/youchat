package com.youchat.creative.factory.exception;

public class MyException extends RuntimeException {

    private int code;
    private String errorMessage;

    public MyException( int code, String errorMessage) {
        super();
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
