package com.youchat.common.stream;

import javax.jws.soap.SOAPBinding;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: youchat-common
 * @description:
 * @author: lien6o
 * @create: 2018-08-20 17:39
 **/
public class TestSreamApi {

    public static void main(String[] args) {
        ArrayList<User> userList = getUserList();
        // 多条件分组
        Map<String, Map<String, List<User>>> map = userList.stream().collect(Collectors.groupingBy(User::getGender, Collectors.groupingBy(User::getHomeAddr)));
        map.forEach((k, v) -> {
            System.out.println(k + " " + v);
        });
        System.out.println("----");
        // 多条件分组 并求和各个分组
        Map<String, Integer> collect = userList.stream().collect(Collectors.groupingBy(User::getName, Collectors.summingInt(User::getAge)));
        collect.forEach((k, v) ->
                System.out.println(k + " " + v)
        );
        System.out.println("----");
        // 条件分组并且返回排序后结果
        Map<String, Optional<User>> user = userList.stream().collect(Collectors.groupingBy(User::getName, Collectors.maxBy(Comparator.comparingInt(User::getAge))));
        user.forEach((k, v) ->
                System.out.println(k + " " + v.get().getAge())
        );
        Map<Boolean, Map<String, List<User>>> paraMap = userList.stream().collect(Collectors.partitioningBy(User::isStudent, Collectors.groupingBy(User::getName)));
        System.out.println(paraMap.toString());
        Map<Boolean, List<User>> collect1 = userList.stream().collect(Collectors.groupingBy(User::isStudent));
        System.out.println(collect1.toString());
    }

    public static ArrayList<User> getUserList() {
        User user = new User("a", "m", "bj", false, 14);
        User user1 = new User("a", "m", "bj", false, 16);
        User user2 = new User("a", "m", "sh", true, 19);
        User user3 = new User("b", "w", "sh", true, 14);
        User user4 = new User("b", "w", "sh", false, 13);
        ArrayList<User> objects = new ArrayList<>();
        objects.add(user);
        objects.add(user1);
        objects.add(user2);
        objects.add(user3);
        objects.add(user4);
        return objects;
    }


}
