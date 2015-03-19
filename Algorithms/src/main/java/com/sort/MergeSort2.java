package com.sort;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort2 {

	public static <T> T[] sort(T[] mas, Comparator<T> cmp) {
		sort(mas, 0, mas.length, cmp);
		return mas;
	}
	
	//[s, e)
	static <T> void sort(T[] mas, int s, int e, Comparator<T> cmp) {
		if (e - s > 1) {
			int m = (s + e) >> 1;
			sort(mas, s, m, cmp);
			sort(mas, m, e, cmp);
			merge(mas, s, m, e, cmp);
		}		
	}
	
	static <T> void merge1(T[] mas, int s, int m, int e, Comparator<T> cmp) {
		@SuppressWarnings("unchecked")
		T[] res = (T[]) new Object[e - s];
		
		int i = s, j = m, k = 0;
		while (i < m && j < e) {
			if (cmp.compare(mas[i], mas[j]) <= 0) {
				res[k ++] = mas[i];
				i ++;
			} else {
				res[k ++] = mas[j];
				j ++;
			}
		}
		
		if (i < m) {
			System.arraycopy(mas, i, res, k, m - i);
		} else if (j < e) {
			System.arraycopy(mas, j, res, k, e - j);
		}
		
		System.arraycopy(res, 0, mas, s, res.length);
	}
	
	static <T> void merge(T[] mas, int s, int m, int e, Comparator<T> cmp) {
		@SuppressWarnings("unchecked")
		T[] res = (T[]) new Object[e - s];
		for (int i = s, j = m, k = 0; i < m || j < e; k ++) {
			if (i >= m) {
				res[k] = mas[j ++];
			} else if (j >= e) {
				res[k] = mas[i ++];
			} else if (cmp.compare(mas[i], mas[j]) <= 0) {
				res[k] = mas[i ++];
			} else {
				res[k] = mas[j ++];
			}
		}
		System.arraycopy(res, 0, mas, s, res.length);
	}
	
	public static void main(String[] args) {
		Integer[] mas = {1, 34, 67, 3, -5, 67, 45, 0, 120, 4};
		Comparator<Integer> cmp = Integer::compare;
		mas = sort(mas, cmp);
		System.out.println("Sorted mas: " + Arrays.toString(mas));
	}
}
