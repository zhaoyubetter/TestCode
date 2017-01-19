package com.example.groovy.generic;

/**
 * Created by zhaoyu1 on 2016/11/8.
 */
public class GenericMemoryCell<T> {

    private T storeValue;

    public T read() {
        return storeValue;
    }

    public void write(T value) {
        this.storeValue = value;
    }
}
