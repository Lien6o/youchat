package com.youchat.common.cache;

/**
 * lien6o
 * 2019年04月15日15:03:04
 */
public class RedisHolder<T>  {
    private T value;

    public RedisHolder() {
    }

    public RedisHolder(T value) {
        this.value = value;
    }

    public void value(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }
}
