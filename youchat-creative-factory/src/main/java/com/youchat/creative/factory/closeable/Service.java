package com.youchat.creative.factory.closeable;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Lien6o
 */
public class Service     {
//    @Override
//    public void close() {
//        System.out.println("close()");
//    }


    public AutoCloseable stop() {
        System.out.println("stop()");
        return () -> System.out.println("close stop()");
    }
}
