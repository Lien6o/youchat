package com.youchat.creative.factory.mq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用 hashmap 的一个简易队列。理解题意
 */
public class DefaultQueueStoreImpl2 extends QueueStore {

    public static final Collection<byte[]> EMPTY = new ArrayList<>();

    Map<String, List<byte[]>> queueMap = new ConcurrentHashMap<>();

    @Override
    public synchronized void put(String queueName, byte[] message) {
        if (!queueMap.containsKey(queueName)) {
            queueMap.put(queueName, new ArrayList<>());
        }
        queueMap.get(queueName).add(message);
    }

    @Override
    public synchronized Collection<byte[]> get(String queueName, long offset, long num) {
        if (!queueMap.containsKey(queueName)) {
            return EMPTY;
        }
        List<byte[]> msgs = queueMap.get(queueName);
        return msgs.subList((int) offset, offset + num > msgs.size() ? msgs.size() : (int) (offset + num));
    }
}