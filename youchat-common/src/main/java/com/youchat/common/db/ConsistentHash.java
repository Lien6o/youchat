package com.youchat.common.db;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 1.首先求出memcached服务器（节点）的哈希值，并将其配置到0～232的圆（continuum）上。
 * 2.然后采用同样的方法求出存储数据的键的哈希值，并映射到相同的圆上。
 * 3.然后从数据映射到的位置开始顺时针查找，将数据保存到找到的第一个服务器上。如果超过232仍然找不到服务器，就会保存到第一台memcached服务器上。
 * <p>
 * <p>
 * <p>
 * 为了解决这种数据倾斜问题，一致性哈希算法引入了虚拟节点机制，即对每一个服务节点计算多个哈希，每个计算结果位置都放置一个此服务节点，称为虚拟节点。
 */
public class ConsistentHash<T> {
    /**
     * Hash算法对象，用于自定义hash算法
     */
    public interface HashFunc {
        Long hash(Object key);
    }

    /**
     * Hash计算对象，用于自定义hash算法
     */
    HashFunc hashFunc;
    /**
     * 复制的节点个数
     */
    private final int numberOfReplicas;
    /**
     * 一致性Hash环
     * Long 服务器hash ;
     * T: 服务器数据;
     */
    private final SortedMap<Long, T> circle = new TreeMap<>();

    /**
     * 构造，使用Java默认的Hash算法
     *
     * @param numberOfReplicas 复制的节点个数，增加每个节点的复制节点有利于负载均衡
     * @param nodes            节点对象
     */
    public ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
        this.numberOfReplicas = numberOfReplicas;
        //lamda表达式实现HashFunc接口，采用md5hash算法
        hashFunc = key -> md5HashingAlg(key.toString());
        //初始化节点
        for (T node : nodes) {
            add(node);
        }
    }

    private Long md5HashingAlg(String string) {
        return (long) UUID.fromString(string).hashCode();
    }

    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunc.hash(node.toString() + i), node);
        }
    }

    //获得离数据顺时针最近的节点
    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hashFunc.hash(key);
        if (!circle.containsKey(hash)) {
            // 返回此映射的部分视图，其键大于等于 hash
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        //正好命中
        return circle.get(hash);
    }
    
}
