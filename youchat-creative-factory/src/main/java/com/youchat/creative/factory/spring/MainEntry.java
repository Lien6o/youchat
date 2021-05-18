package com.youchat.creative.factory.spring;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class MainEntry {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(InjectConfig.class);

//        Entry entry = applicationContext.getBean(Entry.class);
//
//        entry.execute();
      /*
       * No qualifying bean of type 'com.youchat.creative.factory.spring.RemoteClient' available: expected single matching bean but found 2: remoteClientV2,remoteClient
       * RemoteClient bean = applicationContext.getBean(RemoteClient.class);
       * System.out.println(bean);
       */
      // System.out.println(applicationContext.getBean("remoteClientV2"));
      // System.out.println(applicationContext.getBean("remoteClient"));

        /* spring event test */
//       EventService eventService = applicationContext.getBean(EventService.class);
//       eventService.sendEvent("lienbo");
//       DemoAService demoAService = applicationContext.getBean(DemoAService.class);
//       demoAService.self();
//       demoAService.execute();

        /* spring inner bean test */
//        InnerBean bean1 = applicationContext.getBean(InnerBean.class);
//        System.out.println("bean1 = " + bean1);
//        InnerBean.CheckA bean = applicationContext.getBean(InnerBean.CheckA.class);
//        bean.check();
//        InnerBean innerBean = (InnerBean) applicationContext.getBean("innerBean");
//        System.out.println("innerBean = " + innerBean);
//        InnerBean.CheckB bean2 = (InnerBean.CheckB) applicationContext.getBean("com.youchat.creative.factory.spring.InnerBean$CheckB");
//        bean2.check();
//        InnerBean.CheckA bean3 = (InnerBean.CheckA) applicationContext.getBean("innerBean.CheckA");
//        bean3.check();
    }
}
