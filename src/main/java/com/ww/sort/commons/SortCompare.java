package com.ww.sort.commons;

import com.ww.commons.StdRandom;
import com.ww.commons.Stopwatch;
import com.ww.sort.insert.InsertSort;
import com.ww.sort.select.Selection;

public class SortCompare {

	public static double time(String alg, Double[] a) {
		Stopwatch timer = new Stopwatch();
		
		switch(alg) {
			case "Insert":
				InsertSort.sort(a);
				break;
				
			case "Select":
				Selection.sort(a);
				break;
		}
		
		return timer.elapsedTime();
	}
	
	
	public static double timeRandomInput(String alg, int N, int T) {
		double total = 0.0;
		Double[] a = new Double[N];
		
		for (int t = 0; t < T; t++) {
			for (int i = 0; i < N; i++) {
				a[i] = StdRandom.uniform();
			}
			total += time(alg, a);
		}
		
		return total;
	}
	
	public static void main(String[] args) {
		int N = 2000;
		int T = 100;
		double t1 = timeRandomInput("Insert", N, T);
		double t2 = timeRandomInput("Select", N, T);
		
		System.out.println("选择排序：" + t2);
		System.out.println("插入排序：" + t1);
		System.out.println("选择排序时间/插入排序时间：" + (t2 / t1));
	}
}
