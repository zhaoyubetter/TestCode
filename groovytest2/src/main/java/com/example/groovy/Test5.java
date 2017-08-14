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

        int a[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
        insert_search(a, 22, a.length);
    }

    static int insert_search(int a[], int key, int n) {
        int low = 0;
        int high = n-1;
        int index = -1;
        int time = 0;
        int mid = 0;


        while(low <= high) {
            // 因计算机浮点数运算问题，在分母上需加入0.5
            mid = (int)(low + (key-a[low])/((a[high]-a[low]) + 0.5f)*(high-low));
            // mid2 = low + (key-a[low])/((a[high]-a[low]))*(high-low);
            System.out.format("查找次数：%d, 当前mid：%d\n", ++time, mid);

            if(a[mid] == key) {
                index = mid;
                break;
            } else if(a[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return index;
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
