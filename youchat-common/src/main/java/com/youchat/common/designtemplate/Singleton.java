package com.youchat.common.designtemplate;

import java.security.Signature;

public class Singleton {

    private Singleton() {
        System.out.println("init");
    }

    static class Holder {
        private final static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.instance;
    }

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        System.out.println(instance);

        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance1);

        System.out.println(Double.parseDouble("3.12"));
    }
}
