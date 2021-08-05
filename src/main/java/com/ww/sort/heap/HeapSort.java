package com.ww.sort.heap;

/**
 * 堆排序
 */
public class HeapSort {

    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a) {
        int N = a.length - 1;
        for (int k = N/2; k >= 1; k--) {
            sink(a, k, N);
        }
        while (N > 1) {
            exch(a, 1, N--);
            sink(a, 1, N);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparable[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    @SuppressWarnings("rawtypes")
    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    @SuppressWarnings("rawtypes")
    private static void sink(Comparable[] a, int k, int size) {
        while (2*k <= size) {
            int j = 2*k;
            if (j < size && less(a, j, j+1)) {
                j++;
            }
            if (!less(a, k, j)) {
                break;
            }
            exch(a, k, j);
            k = j;
        }
    }
}
