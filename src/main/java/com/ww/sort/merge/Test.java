package com.ww.sort.merge;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        Character[] a = { 'M', 'E', 'R', 'G', 'E', 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E' };

        System.out.println("排序前");
        System.out.println(Arrays.toString(a));

        System.out.println("排序后");
        MergeBU.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
