package com.youchat.creative.factory.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lien6o
 */
@Component
public class DemoAService extends AbstractDemo {


    // com.youchat.creative.factory.spring.DemoAService@a5b0b86

    protected String needImpl() {
        return eatService.eat();
    }

    @DummyTransactional
    public void self() {
        System.out.println("this = " + this);

        System.out.println("self eatService = " + eatService + " super " + super.eatService);
        System.out.println();

    }
}
