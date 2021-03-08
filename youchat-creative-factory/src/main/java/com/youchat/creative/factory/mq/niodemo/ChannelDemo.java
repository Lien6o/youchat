package com.youchat.creative.factory.mq.niodemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author enboli
 *
 * Java NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。
 *
 * 在使用FileChannel之前，必须先打开它。但是，我们只能通过使用一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例
 */
public class ChannelDemo {

    private static final int SIZE = 1024;

    public static void main(String[] args) throws Exception {
   //     // 获取通道，该通道允许写操作
   //     FileChannel fc = new FileOutputStream("/Users/enboli/doc/test").getChannel();
   //     // 将字节数组包装到缓冲区中
   //     fc.write(ByteBuffer.wrap("Some text".getBytes()));
   //     // 关闭通道
   //     fc.close();
// ---------------------------------------------------------------------------------------------------------------------
    //   // 随机读写文件流创建的管道
    //   fc = new RandomAccessFile("/Users/enboli/doc/test", "rw").getChannel();
    //   // fc.position()计算从文件的开始到当前位置之间的字节数
    //   System.out.println("此通道的文件位置：" + fc.position());
    //   // 设置此通道的文件位置,fc.size()此通道的文件的当前大小,该条语句执行后，通道位置处于文件的末尾
    //   fc.position(fc.size());
    //   // 在文件末尾写入字节
    //   fc.write(ByteBuffer.wrap("Some more".getBytes()));
    //   fc.close();
// -//-------------------------------------------------------------------------------------------------------------------
        // 用通道读取文件
        FileChannel fc = new FileInputStream("/Users/enboli/doc/test").getChannel();
        // buffer 分配缓冲区大小
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        // 将文件内容读到指定的缓冲区中
        fc.read(buffer);
        /*
         *
         * 0 <= mark <= position <= limit <= capacity
         *
         * buffer中的flip方法涉及到buffer中的Capacity,Position和Limit三个概念。
         * 其中Capacity在读写模式下都是固定的，就是我们分配的缓冲大小,
         * Position类似于读写指针，表示当前读(写)到什么位置,Limit在写模式下表示最多能写入多少数据，
         * 此时和Capacity相同，在读模式下表示最多能读多少数据，此时和缓存中的实际数据大小相同。
         *
         * 在写模式下调用flip方法，那么limit就设置为了position当前的值(即当前写了多少数据),position会被置为0，以表示读操作从缓存的头开始读。
         *
         * 也就是说调用flip之后，读写指针指到缓存头部，并且设置了最多只能读出之前写入的数据长度(而不是整个缓存的容量大小)。
         */
        // flip() 翻动
        //       this.limit = this.position;
        //       this.position = 0;
        buffer.flip();
        // while position < limit
        while (buffer.hasRemaining()) {
            System.out.println("hasRemaining= " + (char) buffer.get());
        }
        fc.close();
    }

}
