package com.youchat.creative.factory.mq.niodemo;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * https://wiki.jikexueyuan.com/project/java-nio-zh/java-nio-buffer.html
 *
 * @author enboli
 */
public class BufferDemo {
    /**
     * 容量（Capacity）
     * 作为一块内存，buffer有一个固定的大小，叫做capacity容量。也就是最多只能写入容量值得字节，整形等数据。一旦buffer写满了就需要清空已读数据以便下次继续写入新的数据。
     *
     * 位置（Position）
     * 当写入数据到Buffer的时候需要中一个确定的位置开始，默认初始化时这个位置position为0，一旦写入了数据比如一个字节，整形数据，那么position的值就会指向数据之后的一个单元，position最大可以到capacity-1.
     *
     * 当从Buffer读取数据时，也需要从一个确定的位置开始。buffer从写入模式变为读取模式时，position会归零，每次读取后，position向后移动。
     *
     * 上限（Limit）
     * 在写模式，limit的含义是我们所能写入的最大数据量。它等同于buffer的容量。
     *
     * 一旦切换到读模式，limit则代表我们所能读取的最大数据量，他的值等同于写模式下position的位置。
     *
     * 数据读取的上限时buffer中已有的数据，也就是limit的位置（原position所指的位置）。
     *
     */
    @SneakyThrows
    public static void main(String[] args) {
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        // create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);
        // read into buffer.
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            // make buffer ready for read
            // 翻转（flip()）
            // flip()方法可以吧Buffer从 {写模式} 切换到 {读模式}。
            // 调用flip方法会设置limit为之前的position的值，并把position归零。
            // 也就是说，现在position代表的是读取位置，limit标示的是已写入的数据位置。
            buf.flip();
            while (buf.hasRemaining()) {
                // read 1 byte at a time
                System.out.print((char) buf.get());
            }
            // make buffer ready for writing
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
