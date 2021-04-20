package com.youchat.creative.factory.closeable;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Lien6o
 * @date 2021/4/12 8:44 下午
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Service service = new Service();
        try (AutoCloseable ignored = service.stop()) {
            // do
            System.out.println("\"do some thing\" = " + "do some thing");
        }
        new ReentrantLock();
    }
}
