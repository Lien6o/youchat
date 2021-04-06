package com.youchat.creative.factory.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


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
@Accessors(chain = true)
public class MyException extends RuntimeException {

    private int code;
    private String errorMessage;

    public MyException(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    /**
     * 自定义业务异常不打印堆栈
     * https://stackoverflow.com/questions/9788993/why-is-throwable-fillinstacktrace-method-public-why-would-someone-use-it
     * https://www.atlassian.com/blog/archives/if_you_use_exceptions_for_path_control_dont_fill_in_the_stac
     *
     *
     * One reason is for performance.
     * Throwing and catching an exception is cheap;
     * the expensive part is filling in the stack trace.
     * If you override fillInStackTrace() to do nothing, creating an exception also becomes cheap.
     *
     * With cheap exceptions, you can use exceptions for flow control,
     * which can make the code more readable in certain situations;
     * you can use them when when implementing JVM languages where you need more advanced flow control,
     * and they are useful if you are writing an actors library.
     */
     @Override
     public Throwable fillInStackTrace() {
         return this;
     }

    public static void main(String[] args) {
        MyException myException = new MyException(1, "myException");

        MyException myException1 = new MyException(1, "");
        MyException myException2 = myException1.setCode(1).setErrorMessage("");
        throw myException;
    }
}
