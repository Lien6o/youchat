package com.youchat.creative.factory.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lien6o
 */
@Component
public class DemoAService extends AbstractDemo{

    private final EatService eatService;

    @Autowired
    public DemoAService(EatService eatService) {
        this.eatService = eatService;
    }

    @Override
    protected void needImpl() {
        System.out.println("needImpl：");
        eatService.eat();
    }
}
