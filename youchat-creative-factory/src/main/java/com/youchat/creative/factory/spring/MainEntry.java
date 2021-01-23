package com.youchat.creative.factory.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainEntry {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(InjectConfig.class);

        Entry entry = applicationContext.getBean(Entry.class);

        entry.execute();
        /*
         * No qualifying bean of type 'com.youchat.creative.factory.spring.RemoteClient' available: expected single matching bean but found 2: remoteClientV2,remoteClient
         * RemoteClient bean = applicationContext.getBean(RemoteClient.class);
         * System.out.println(bean);
         */

        System.out.println(applicationContext.getBean("remoteClientV2"));

        System.out.println(applicationContext.getBean("remoteClient"));
    }
}
