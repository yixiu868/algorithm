package com.ww.sort.bubble.exercise;

/**
 * @author xiaohua
 * @date 2020/8/16 14:33
 * 冒泡排序使用练习
 */
public class BubbleTest {

    public static void sort(int[] array) {
        // 外层循环，比较趟数
        for (int i = 1; i < array.length; i++) {
            // 内层循环，每趟比较次数
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
    }

    public static String display(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int[] array = { 6, 6, 3, 7, 2, 3, 8, 9, 10, -1 };
        System.out.println("排序前:" + display(array));
        sort(array);
        System.out.println("排序后:" + display((array)));
    }
}
