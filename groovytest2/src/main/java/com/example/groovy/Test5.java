package com.example.groovy;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by zhaoyu1 on 2016/12/8.
 */
public class Test5 {
    public static void main(String[] args) {
        testArrayList();
    }

    public static void testArrayList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(10 + i);
        }

        ListIterator<Integer> iterator = list.listIterator();
        iterator.next();
        System.out.println( iterator.previousIndex() + " " +  iterator.hasPrevious());
        System.out.println( iterator.next() + " " +  iterator.previousIndex());
    }
}
