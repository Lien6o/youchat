package com.youchat.creative.factory.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.youchat.creative.factory.spring")
@Slf4j
public class InjectConfig {

    @Bean
    public RemoteClient remoteClient() {
        return RemoteClient.builder().ipAddr("127.0.0.1").type("1").build();
    }

    @Bean
    public InjectService injectService(RemoteClient remoteClient) {
        return new InjectServiceImpl(remoteClient);
    }

    @Bean
    public RemoteClient remoteClientV2() {
        return RemoteClient.builder().ipAddr("127.0.0.2").type("2").build();
    }

    @Bean
    public InjectService injectServiceV2(RemoteClient remoteClientV2) {
        return new InjectServiceImpl(remoteClientV2);
    }
    // AutowiredAnnotationBeanPostProcessor implement MergedBeanDefinitionPostProcessor  findAutowiringMetadata

    // externallyManagedConfigMembers
}
