package com.ww.sort.quick;

import java.util.Arrays;

/**
 * @author xiaohua
 * @date 2020/9/19 9:24
 */
public class Quick2 {

    public static void main(String[] args) {
        Integer[] arr = { 49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        System.out.println("排序前:");
        Arrays.asList(arr).forEach(item -> System.out.print(item + ","));
        System.out.println();
        sort(arr, 0, arr.length - 1);
        System.out.println("排序后:");
        Arrays.asList(arr).forEach(item -> System.out.print(item + ","));
    }

    private static void sort(Integer[] arr, int low, int high) {
        if (low < high) {
            int index = getIndex(arr, low, high);

            sort(arr, low, index - 1);
            sort(arr, index + 1, high);
        }
    }

    /**
     * 返回排序一次后基准位置
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private static int getIndex(Integer[] arr, int low, int high) {
        // 选择基准数据
        int tmp = arr[low];
        while (low < high) {
            // 当队尾元素大于等于基准数据时，high--
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 交换
            arr[low] = arr[high];

            // 当队首元素小于等于tmp时，low++
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 交换
            arr[high] = arr[low];
        }

        arr[low] = tmp;
        return low;
    }
}
