package com.youchat.creative.factory.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainEntry {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(InjectConfig.class);

        Entry entry = applicationContext.getBean(Entry.class);

        entry.execute();

    }
}
