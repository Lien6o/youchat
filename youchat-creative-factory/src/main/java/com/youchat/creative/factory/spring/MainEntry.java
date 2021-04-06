package com.youchat.creative.factory.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class MainEntry {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(InjectConfig.class);

//        Entry entry = applicationContext.getBean(Entry.class);
//
//        entry.execute();
        /*
         * No qualifying bean of type 'com.youchat.creative.factory.spring.RemoteClient' available: expected single matching bean but found 2: remoteClientV2,remoteClient
         * RemoteClient bean = applicationContext.getBean(RemoteClient.class);
         * System.out.println(bean);
         */

       // System.out.println(applicationContext.getBean("remoteClientV2"));

       // System.out.println(applicationContext.getBean("remoteClient"));

        // event test
//        EventService eventService = applicationContext.getBean(EventService.class);
//        eventService.sendEvent("lienbo");


        Map<String, AbstractDemo> beansOfType = applicationContext.getBeansOfType(AbstractDemo.class);
        AbstractDemo bean = applicationContext.getBean(AbstractDemo.class);
        bean.execute();

        System.out.println(beansOfType);

    }
}
