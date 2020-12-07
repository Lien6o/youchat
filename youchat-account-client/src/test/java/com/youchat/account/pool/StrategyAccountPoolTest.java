package com.youchat.account.pool;

import junit.framework.TestCase;

public class StrategyAccountPoolTest extends TestCase {

    public void testGetAccount() {
        ConfigProperty configProperty = new ConfigProperty();
        configProperty.setInitAccountPoolSize(10);

        StrategyAccountPool strategyAccountPool = new StrategyAccountPool(configProperty,()-> new AbstractAccount() {
            @Override
            public String toString() {

                return "super.hashCode()";
            }
        });


        AbstractAccount account = strategyAccountPool.getAccount();
        System.out.println("account = " + account);
    }
}