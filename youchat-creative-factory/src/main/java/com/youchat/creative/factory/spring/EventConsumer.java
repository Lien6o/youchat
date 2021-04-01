package com.youchat.creative.factory.spring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@Async("new_task2")
public class EventConsumer {


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = Event.class)
    public void consumer(Event event) {
        log.info("consumer event=" + event);
    }


    @EventListener(classes = Event.class, condition = "#event.name.equals('lienbo')")
    public void consumer2(Event event) {
        log.info("consumer2 event=" + event);
    }

    @SneakyThrows
    @EventListener(classes = Event.class, condition = "#event.name.equals('lienbo')")
    public void consumer3(Event event) {
        Thread.sleep(5000L);
        log.info("consumer3 event=" + event);
    }

    @SneakyThrows
    @EventListener(classes = Event.class, condition = "#event.name.equals('lienbo')")
    public void consumer4(Event event) {

        log.info("consumer4 event=" + event);
    }

    @EventListener(classes = Event.class, condition = "#event.name.equals('lienbo')")
    public void consumer5(Event event) {
        log.info("consumer5 event=" + event);
    }
}
