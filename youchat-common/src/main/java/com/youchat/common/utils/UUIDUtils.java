package com.youchat.common.utils;
import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
/**
 * @author: Lien6o
 * @description:
 * @date: 2019-07-12 10:53
 * @version: v1.0
 */
public class UUIDUtils {
    public static String genUUID() {
        TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        System.out.println("timeBasedGenerator.generate() = " + timeBasedGenerator.generate());
        StringBuilder builder = new StringBuilder();

        return timeBasedGenerator.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1111; i++) {
            genUUID();
        }
    }
}
