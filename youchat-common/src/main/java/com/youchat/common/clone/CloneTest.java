package com.youchat.common.clone;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/25 11:33 上午
 * @version: v1.0
 */

//定义学生类
    @Data
class Student implements Cloneable{
    private String name; //学生姓名
    private Teacher teacher; //定义老师类

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student clone() {
        Student student = null;
        try {
            student = (Student) super.clone();
//            Teacher teacher = this.teacher.clone();//克隆teacher对象
          //  student.setTeacher(teacher);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return student;
    }

}

//定义老师类
@Data
class Teacher implements Cloneable{
    private String name;  //老师姓名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    //重写克隆方法，堆老师类进行克隆
    public Teacher clone() {
        Teacher teacher= null;
        try {
            teacher= (Teacher) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return teacher;
    }
}

  class Test {

    public static void main(String args[]) {
        Teacher teacher = new Teacher (); //定义老师1
        teacher.setName("刘老师");
        Student stu1 = new Student();  //定义学生1
        stu1.setName("test1");
        stu1.setTeacher(teacher);

        Student stu2 = stu1.clone(); //定义学生2
        stu2.setName("test2");
        stu2.getTeacher().setName("王老师");//修改老师
        System.out.println("学生" + stu1.getName() + "的老师是:" + stu1.getTeacher().getName());
        System.out.println("学生" + stu1.getName() + "的老师是:" + stu2.getTeacher().getName());
        System.out.println(LocalDateTime.now());
        for (int i = 0; i < 10000000; i++) {
            Student student = new Student();
        }
        System.out.println(LocalDateTime.now());
        Student student = new Student();
        for (int i = 0; i < 10000000; i++) {
            Student clone = student.clone();
        }
        System.out.println(LocalDateTime.now());
    }
}
