package com.youchat.common.apt;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/5/19 9:14 下午
 * @version: v1.0
 */
public class Demo {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {
        System.out.println(new Demo());
    }
}
