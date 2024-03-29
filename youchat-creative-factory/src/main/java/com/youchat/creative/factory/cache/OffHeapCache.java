package com.youchat.creative.factory.cache;



import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

public class OffHeapCache {

    public static void main(String[] args) {
            OHCache<String, String> ohCache = OHCacheBuilder.<String, String>newBuilder()
                    .keySerializer(new StringSerializer())
                    .valueSerializer(new StringSerializer())
                    .eviction(Eviction.LRU)
                    .build();

            ohCache.put("hello", "world");
            System.out.println(ohCache.get("hello"));
    }
}
