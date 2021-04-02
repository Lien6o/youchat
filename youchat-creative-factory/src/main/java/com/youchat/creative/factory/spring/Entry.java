package com.youchat.creative.factory.spring;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Entry {

    @Resource(name = "injectService")
    InjectService injectService;

    @Resource(name = "injectServiceV2")
    InjectService injectServiceV2;


    public void execute() {
        injectService.doSomething();
        injectServiceV2.doSomething();
    }
}
