package com.youchat.common.vavr;

import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.control.Try;


/**
 * @author: Lien6o
 * @description:
 * @date: 2020/5/18 11:50 上午
 * @version: v1.0
 */
public class HelloWord {

    public static void main(String[] args) {
        whenCreatesTuple_thenCorrect1();
        whenCreatesTuple_thenCorrect2();
        tryExceptions();
    }

    /**
     * 元组Tuple
     * Java中没有与元组(Tuple)相对应的结构。Tuple是函数式编程中一种常见的概念。
     * Tuple是一个不可变，并且能够以类型安全的形式保存多个不同类型的对象。Tuple中最多只能有8个元素。
     */
    public static void whenCreatesTuple_thenCorrect1() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        String element1 = java8._1;
        int element2 = java8._2();

        System.out.println(element1);
        System.out.println(element2);

    }

    /**
     *  引用元素时从1开始，而不是0。
     *
     *     Tuple中的元素必须是所声明的类型。
     *     当需要返回多个对象时可以考虑使用Tuple。
     */
    public static void whenCreatesTuple_thenCorrect2() {
        Tuple3<String, Integer, Double> java8 = Tuple.of("Java", 8, 1.8);
        String element1 = java8._1;
        int element2 = java8._2();
        double element3 = java8._3();
        System.out.println(element1);
        System.out.println(element2);
        System.out.println(element3);

    }

    public static void tryExceptions() {
        Try<Integer> result = Try.of(() -> {
            int c = 1 / 0;
            throw new RuntimeException("");
        });

        result.andThen(() -> {
            System.out.println("andThen");
        });
        System.out.println(result.getCause());
        System.out.println(result.isSuccess());
        result.andFinally(() -> System.out.println("andFinally"));
        // result.getCause().
        java.util.List<Integer> lists = List.of(1, 2, 3).asJava();
        Lazy<Double> lazy = Lazy.of(Math::random);
        Lazy<String> string = Lazy.of(() -> "2222");

    }

}
