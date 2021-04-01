package com.youchat.creative.factory.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PeopleEnum implements Say {
    /**
     * 普通人
     */
    common(1, "普通人") {
        @Override
        public void speak() {
            System.out.println("普通人");
        }
    },
    /**
     * 好人
     */
    good_man(2, "好人"),
    /**
     * 坏人
     */

    bad_man(3, "坏人"),
    /**
     * 男人
     */
    man(4, "男人") {
        @Override
        public void speak() {
            System.out.println("男人");
        }
    },
    ;

    /**
     * code
     */
    private final int code;
    /**
     * 描述
     */
    private final String desc;


    @Override
    public void speak() {
        System.out.println("common");
    }

    public static void main(String[] args) {
        for (PeopleEnum value : PeopleEnum.values()) {
            System.out.println("value.getCode() = " + value.getCode());
            value.speak();
        }
    }
}
