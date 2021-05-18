package com.youchat.creative.factory.spring;

import org.springframework.stereotype.Component;

/**
 * @author Lien6o
 * @date 2021/5/14 3:41 下午
 */
@Component
public class InnerBean {


    interface check {
        void check();
    }


    @Component
    public static class CheckA implements check {
        @Override
        public void check() {

            System.out.println("\"CheckA\" = " + "CheckA");
        }
    }

    @Component
    public class CheckB implements check {
        @Override
        public void check() {
            System.out.println("\"CheckB\" = " + "CheckB");
        }
    }
}

