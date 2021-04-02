package com.youchat.creative.factory.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventService {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void sendEvent(String name) {

        Event event = new Event(name);
        publisher.publishEvent(event);
//        publisher.publishEvent(new ApplicationEvent(name) {
//            @Override
//            public Object getSource() {
//                return name;
//            }
//        });
        log.info("EventService over");

    }
}
