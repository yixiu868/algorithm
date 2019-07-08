package com.ww.sort.select;

import com.ww.sort.commons.SortUtils;

/**
 * 选择排序
 * @author xiaohua
 *
 */
public class Selection {

	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a) {
		int N = a.length;
		
		for (int i = 0; i < N; i++) {
			int min = i;
			
			for (int j = i+1; j < N; j++) {
				if (SortUtils.less(a[j], a[min])) {
					min = j;
				}
			}
			
			SortUtils.exch(a, i, min);
		}
	}
}
