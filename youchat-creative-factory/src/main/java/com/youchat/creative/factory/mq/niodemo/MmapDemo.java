package com.youchat.creative.factory.mq.niodemo;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * mmap函数会在内存中找一段空白内存，然后将这部分内存与文件的内容对应起来。
 * 我们对内存的所有操作都会直接反应到文件中去。
 * mmap的主要功能就是建立内存与文件这种对应关系。
 * 所以才被命名为memory map
 */
public class MmapDemo {

    public static void main(String args[]){
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile("/Users/enboli/doc/test", "rw");
            RandomAccessFile world = new RandomAccessFile("C:/hinusDocs/world.txt", "rw");
            FileChannel fc = f.getChannel();
            // 在sun.nio.ch.FileChannelImpl里有map的具体实现：
            MappedByteBuffer buf = fc.map(FileChannel.MapMode.READ_WRITE, 0, 20);

            FileChannel worldChannel = world.getChannel();
            MappedByteBuffer worldBuf = worldChannel.map(FileChannel.MapMode.READ_WRITE, 0, 20);
            worldBuf.put(buf);

            fc.close();
            f.close();
            world.close();
            worldChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
