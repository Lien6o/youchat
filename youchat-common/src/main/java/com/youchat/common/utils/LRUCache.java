package com.youchat.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/1/30 4:02 下午
 * @version: v1.0
 */
public class LRUCache extends LinkedHashMap {

    public static void main(String[] args) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("", "");
    }

    /**
     * 删除最大条目 重写该方法
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {

        return super.removeEldestEntry(eldest);
    }
}
