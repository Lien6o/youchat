package com.youchat.creative.factory.validator;

public class CommonResult<T> {
    private Integer code;
    private String errorMessage;
    private T data;

    public CommonResult(Integer code, String errorMessage, T data) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static <T> CommonResult<T> error(T data) {
        return new CommonResult<>(-1, "", data);

    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
