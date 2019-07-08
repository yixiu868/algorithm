package com.ww.sort.insert;

import com.ww.sort.commons.SortUtils;

/**
 * 插入排序
 * @author xiaohua
 *
 */
public class InsertSort {

	public static void sort(Comparable[] a) {
		// 升序排序
		int N = a.length;
		
		for (int i = 1; i < N; i++) {
			for (int j = i; j > 0 && SortUtils.less(a[j], a[j-1]); j--) {
				SortUtils.exch(a, j, j-1);
			}
		}
	}
}
