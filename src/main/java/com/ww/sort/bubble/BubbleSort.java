package com.ww.sort.bubble;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void sort(int[] arr) {
        /** 外层为冒泡排序遍历趟数 */
        for (int i = 1; i < arr.length; i++) {
            /** 内层循环为每趟排序比较的次数 */
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static void display(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = { -8, 9, 2, -10, 3, 2, 8, 6, 2, 9, 11, 28, 36, 28, -8, 9, 2, -10, 3, 2, 8, 6, 2, 9, 11, 28, 36, 28 };

        System.out.println("排序前数组:");
        display(arr);

        sort(arr);
        System.out.println("排序后数组");
        display(arr);
    }
}
