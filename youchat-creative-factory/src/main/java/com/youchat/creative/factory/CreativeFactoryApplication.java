package com.youchat.creative.factory;

import com.youchat.creative.factory.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CreativeFactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreativeFactoryApplication.class, args);
        testLog();
    }

    private static void testLog() {
        log.info("xxxxxxxxxxxxxxx- xxxxxxxxxxxxxxxx");
        if (1 == 1) {
            throw new MyException(1, "MyException_ERROR_MESSAGE");
        }
    }
}
