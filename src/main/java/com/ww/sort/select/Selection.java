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

		/**
		 * 从头遍历所有元素
		 */
		for (int i = 0; i < N; i++) {
			int min = i;

			/**
			 * 每个元素与其后所有元素比较，选出最大或最小值，填充在外层遍历索引位置
			 */
			for (int j = i+1; j < N; j++) {
				if (SortUtils.less(a[j], a[min])) {
					min = j;
				}
			}
			
			SortUtils.exch(a, i, min);
		}
	}
}
