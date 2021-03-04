package com.youchat.creative.factory.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 *
 * 自定义异常 为了在长业务流程 和层级中，使用 异常 的中断退出属性。直接返回到 方法调用栈低。
 *
 * 这类异常，是由开发人员自己定义 抛出，所以有很强的明确性,所以可以不打印堆栈 减少对性能的消耗。
 *
 * 对外层处理 仅需要将异常当做 java bean 处理 获取错误码和错误信息即可。
 * @author enboli
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyException extends RuntimeException {

    private int code;
    private String errorMessage;

    public MyException(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    /**
     * 自定义业务异常不打印堆栈
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
