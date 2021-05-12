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
public class EventConsumer {


    @SuppressWarnings("unused")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = NamedEvent.class)
    public void consumer(NamedEvent namedEvent) {
        log.info("consumer TransactionalEventListener=" + namedEvent);
    }


    @Async
    @EventListener(classes = NamedEvent.class, condition = "#namedEvent.name.equals('lienbo')")
    public void consumer2(NamedEvent namedEvent) {
        log.info("consumer2 namedEvent=" + namedEvent);
    }

    @SneakyThrows
    @EventListener(classes = NamedEvent.class, condition = "#namedEvent.name.equals('lienbo')")
    public void consumer3(NamedEvent namedEvent) {
        Thread.sleep(5000L);
        log.info("consumer3 namedEvent=" + namedEvent);
    }

    @SneakyThrows
    @EventListener(classes = NamedEvent.class, condition = "#namedEvent.name.equals('lienbo')")
    public void consumer4(NamedEvent namedEvent) {

        log.info("consumer4 namedEvent=" + namedEvent);
    }

    @EventListener(classes = NamedEvent.class, condition = "#namedEvent.name.equals('lienbo')")
    public void consumer5(NamedEvent namedEvent) {
        log.info("consumer5 namedEvent=" + namedEvent);
    }
}
