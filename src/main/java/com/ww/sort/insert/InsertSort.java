package com.ww.sort.insert;

import com.ww.sort.commons.SortUtils;

/**
 * 插入排序
 * @author xiaohua
 *
 */
public class InsertSort {

	@SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a) {
		// 升序排序
		int N = a.length;

		/**
		 * 描述：
		 * 用外层索引位置元素，与前一个元素比较，依次比到开始处第一个元素
		 */
		for (int i = 1; i < N; i++) {
			for (int j = i; j > 0 && SortUtils.less(a[j], a[j-1]); j--) {
				SortUtils.exch(a, j, j-1);
			}
		}
	}
}
