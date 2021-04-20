package com.youchat.creative.factory.rpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Lien6o
 * @description some desc
 * @email lienbo@meituan.com
 * @date 2021/4/13 11:51 上午
 */
public class Hessian {
//    public static void main(String[] args) {
//        Student student = new Student();
//        student.setNo(101);
//        student.setName("HESSIAN");
//
////把student对象转化为byte数组
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        Hessian2Output output = new Hessian2Output(bos);
//        output.writeObject(student);
//        output.flushBuffer();
//        byte[] data = bos.toByteArray();
//        bos.close();
//
////把刚才序列化出来的byte数组转化为student对象
//        ByteArrayInputStream bis = new ByteArrayInputStream(data);
//        Hessian2Input input = new Hessian2Input(bis);
//        Student deStudent = (Student) input.readObject();
//        input.close();
//
//        System.out.println(deStudent);
////        相对于JDK、JSON，由于Hessian更加高效，生成的字节数更小，有非常好的兼容性和稳定性，所以Hessian更加适合作为RPC框架远程通信的序列化协议。
////
////        但Hessian本身也有问题，官方版本对Java里面一些常见对象的类型不支持，比如：
////
////        Linked系列，LinkedHashMap、LinkedHashSet等，但是可以通过扩展CollectionDeserializer类修复；
////        Locale类，可以通过扩展ContextSerializerFactory类修复；
////        Byte/Short反序列化的时候变成Integer。
////        以上这些情况，你在实践时需要格外注意。
//    }
}
