package com.youchat.creative.factory.cache;

import org.caffinitas.ohc.CacheSerializer;

import java.nio.ByteBuffer;

public class StringSerializer implements CacheSerializer<String> {

    @Override
    public void serialize(String s, ByteBuffer byteBuffer) {

    }

    @Override
    public String deserialize(ByteBuffer byteBuffer) {
        return "null";
    }

    @Override
    public int serializedSize(String s) {
        return 0;
    }
}
