package com.youchat.creative.factory.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventConsumer {


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = Event.class)
    public void consumer(Event event) {
        System.out.println("consumer event=" + event);
    }


    @EventListener(classes = Event.class, condition = "#event.name.equals('lienbo')")
    public void consumer2(Event event) {
        System.out.println("consumer2 event=" + event);
    }
}
