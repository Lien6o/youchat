package com.youchat.creative.factory.spring;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Lien6o
 * @description some desc
 * @date 2021/4/6 11:39 上午
 */
public abstract class AbstractDemo {

    @Autowired
    private EatService eatService;

    protected abstract void needImpl();

    public final void execute() {
        needImpl();
        eatService.eat();
    }
}
