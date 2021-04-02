package com.youchat.common.designtemplate;

import java.util.Arrays;

public class Strategy {


    private interface Who {
        void who();
    }

    public static class Apple implements Who {
        @Override
        public void who() {
            System.out.println("apple");
        }
    }

    public static class Banana implements Who {
        @Override
        public void who() {
            System.out.println("banana");
        }
    }

    enum StrategyEnum implements Who {
        Apple {
            @Override
            public void who() {
                System.out.println("Apple");
            }
        },

        Banana {
            @Override
            public void who() {
                System.out.println("Banana");
            }
        },
        Orange {
            @Override
            public void who() {
                System.out.println("Orange");
            }
        },;

        @Override
        public void who() {
            System.out.println("StrategyEnum");
        }
    }

    public static void main(String[] args) {
        StrategyEnum[] values = StrategyEnum.values();
        for (StrategyEnum value : values) {
            value.who();;
        }
    }
}
