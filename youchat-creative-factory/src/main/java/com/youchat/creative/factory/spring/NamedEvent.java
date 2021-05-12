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
public class NamedEvent extends ApplicationEvent {

    private String name;

    public NamedEvent(String name) {
        super(name);
        this.name = name;
    }
}
