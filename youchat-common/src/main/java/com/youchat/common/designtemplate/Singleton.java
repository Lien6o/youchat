package com.youchat.common.designtemplate;

public class Singleton {

    private Singleton() {
    }

    private String a ;

    static class Holder {
        private final static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.instance;
    }

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        System.out.println(Double.parseDouble("3.12"));
    }
}
