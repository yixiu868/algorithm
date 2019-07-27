package com.ww.sort.heap;

import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        Integer[] a = new Integer[101];
        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < 101; i++) {
            a[i] = random.nextInt(10000);
        }

        System.out.println("堆排序：");
        System.out.print("排序前：");
        System.out.println(Arrays.asList(a).toString());

        HeapSort.sort(a);

        System.out.print("排序后：");
        System.out.println(Arrays.asList(a).toString());
    }
}
