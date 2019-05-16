package com.youchat.common.utils;

import com.youchat.common.retry.RetryException;
import com.youchat.common.retry.Retryer;
import com.youchat.common.retry.RetryerBuilder;
import com.youchat.common.retry.StopStrategies;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RetryTest {
    public static void main(String[] args) {
        Callable<Boolean> callable = () -> {
             // do something useful here
             System.out.println(" useful in here ");
             throw new RuntimeException();
         };
         Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Objects::isNull)
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(8))
                .build();
        try {
            retryer.call(callable);
        } catch (RetryException | ExecutionException e) {
            e.printStackTrace();
        }
        // SimpleTimeLimiter.create()
    }


}
