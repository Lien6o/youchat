package com.youchat.creative.factory.lockqueue;

import lombok.SneakyThrows;

import java.util.concurrent.Phaser;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Lien6o
 * @description some desc
 * @email lienbo@meituan.com
 * @date 2021/4/15 3:03 下午
 */
public class LockSurce {


    private static final ReentrantLock reentrantLock = new ReentrantLock(true);
    Phaser phaser = new Phaser();
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(LockSurce::lock);
            thread.setName("test-" + i);
            thread.start();
        }
    }

    @SneakyThrows
    private static void lock() {

    }
}
