package com.youchat.creative.factory.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

public class TestApplicationEventMulticaster extends SimpleApplicationEventMulticaster {

    private static int counter = 0;

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        super.multicastEvent(event, eventType);
        counter++;
        System.out.println("counter = " + counter);
    }
}