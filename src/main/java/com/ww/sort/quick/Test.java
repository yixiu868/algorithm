package com.ww.sort.quick;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        Integer[] a = { 8, 9, 1, 2, -8, 9, 10, 3, -1, 7, 6, 2, 10 };

        System.out.print("排序前：");
        System.out.println(Arrays.asList(a).toString());

        Quick.sort(a);

        System.out.print("排序后：");
        System.out.println(Arrays.asList(a).toString());
    }
}
