package com.youchat.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: youchat-common
 * @description: liek name
 * @author: lien6o
 * @create: 2018-08-15 16:46
 **/
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

   private String s = "";

}
