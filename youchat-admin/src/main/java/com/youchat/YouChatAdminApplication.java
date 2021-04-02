package com.youchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @program: youchat-common
 * @description: main
 * @author: lien6o
 * @create: 2018-08-15 16:01
 **/

@ServletComponentScan
@SpringBootApplication
@EnableCaching
public class YouChatAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(YouChatAdminApplication.class, args);
    }
}
