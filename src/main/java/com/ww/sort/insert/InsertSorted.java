package com.ww.sort.insert;

/**
 * @author xiaohua
 * @date 2020/8/16 15:32
 */
public class InsertSorted {

    private int[] array;

    private int length;

    public InsertSorted(int[] array) {
        this.array = array;
        this.length = array.length;
    }

    public void display() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * 插入排序
     */
    public void sorted() {
        for (int i = 1; i < length; i++) {
            int temp = array[i];
            int leftIndex = i - 1;
            while (leftIndex >= 0 && array[leftIndex] > temp) {
                array[leftIndex + 1] = array[leftIndex];
                leftIndex--;
            }
            array[leftIndex + 1] = temp; // 把temp放到空位上
        }
    }

    public static void main(String[] args) {
        int[] array = { 38, 65, 97, 76, 13, 27, 49 };
        InsertSorted insertSorted = new InsertSorted(array);
        System.out.println("排序前数据");
        insertSorted.display();
        insertSorted.sorted();
        System.out.println("排序后数据");
        insertSorted.display();
    }
}
