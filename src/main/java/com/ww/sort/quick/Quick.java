package com.ww.sort.quick;

import com.ww.commons.StdRandom;
import com.ww.sort.commons.SortUtils;

/**
 * 快速排序
 */
public class Quick {

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];

        while (true) {
            while (SortUtils.less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }

            while (SortUtils.less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            SortUtils.exch(a, i, j);
        }

        SortUtils.exch(a, lo, j);

        return j;
    }
}
