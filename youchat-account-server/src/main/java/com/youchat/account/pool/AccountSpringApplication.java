package com.youchat.account.pool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.youchat.account")
public class AccountSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountSpringApplication.class, args);
        System.out.println("start success ");


    }
}
