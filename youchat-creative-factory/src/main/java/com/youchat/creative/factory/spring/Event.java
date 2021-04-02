package com.youchat.creative.factory.spring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * TODO get set 方法必须有！！！！！！！！！
 */
@ToString
@Getter
@Setter
public class Event extends ApplicationEvent {

    private String name;

    public Event(String name) {
        super(name);
        this.name = name;
    }
}
