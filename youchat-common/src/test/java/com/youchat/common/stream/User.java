package com.youchat.common.stream;

/**
 * @program: youchat-common
 * @description:
 * @author: lien6o
 * @create: 2018-08-20 17:43
 **/
public class User {
    private String name;
    private String gender;
    private String homeAddr;
    private  boolean isStudent;
    private int age;


    public User(String name, String gender, String homeAddr, boolean isStudent, int age) {
        this.name = name;
        this.gender = gender;
        this.homeAddr = homeAddr;
        this.isStudent = isStudent;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
