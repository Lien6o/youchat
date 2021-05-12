package com.youchat.creative.factory.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventService {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void sendEvent(String name) {

        NamedEvent namedEvent = new NamedEvent(name);
        publisher.publishEvent(namedEvent);
//        publisher.publishEvent(new ApplicationEvent(name) {
//            @Override
//            public Object getSource() {
//                return name;
//            }
//        });
        log.info("EventService over");

    }
}
