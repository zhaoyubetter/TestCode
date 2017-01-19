package com.example.groovy;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/12/8.
 */
public class Main {
    public static void main(String[] args) {
        printCount(30);
    }

    /**
     * 30个数的三种不同组合
     * time1:1866
     * time2:8878
     * time3:1779
     * <p/>
     * time1:1870
     * time2:8947
     * time3:1787
     *
     * @param count
     */
    private static void printCount(int count) {
        int flag = 1;
        int[] flagItems = new int[count];
        for (int i = 0; i < count; flagItems[i] = flag, flag *= 2, i++) ;
        System.out.println(Arrays.toString(flagItems) + " 组合个数:" + flag);
        StringBuilder builder = new StringBuilder();

        long st = System.currentTimeMillis();
        //比较加入了一处判断后的效率
        for (int start = 1; start <= flag; start++) {
//            builder.delete(0,builder.length());
            for (int i = 0; i < flagItems.length; i++) {
                if (0 != (start & flagItems[i])) {
//                    builder.append(i+1);
                }
            }
//            System.out.println(builder);
        }
        System.out.println("time1:" + (System.currentTimeMillis() - st));
        for (int start = 1; start <= flag; start++) {
//            builder.delete(0,builder.length());
            for (int i = 0; i < flagItems.length && start >= flagItems[i]; i++) {
                if (0 != (start & flagItems[i])) {
//                    builder.append(i+1);
                }
            }
//            System.out.println(builder);
        }
        System.out.println("time2:" + (System.currentTimeMillis() - st));
        st = System.currentTimeMillis();
        for (int start = 1; start <= flag; start++) {
//            builder.delete(0,builder.length());
            for (int i = 0; i < flagItems.length; i++) {
                if (start >= flagItems[i] && 0 != (start & flagItems[i])) {
//                    builder.append(i+1);
                }
            }
//            System.out.println(builder);
        }
        System.out.println("time3:" + (System.currentTimeMillis() - st));
    }
}
