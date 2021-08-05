package com.ww.sort.shell;

import com.ww.sort.commons.SortUtils;

/**
 * 希尔排序
 * @author xiaohua
 *
 */
public class Shell {

    /**
     * 升序排列
     *
     * @param a
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;

        while (h < N / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            // 将数组变为h有序
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && SortUtils.less(a[j], a[j-h]); j -= h) {
                    SortUtils.exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}
