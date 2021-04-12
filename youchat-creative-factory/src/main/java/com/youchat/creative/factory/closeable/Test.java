package com.youchat.creative.factory.closeable;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Lien6o
 * @date 2021/4/12 8:44 下午
 */
public class Test {
    public static void main(String[] args) {

        try (Service service = new Service();
             AutoCloseable ignored = service.stop()) {

        } catch (Exception e) {

        }
    }
}
