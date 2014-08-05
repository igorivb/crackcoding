package com.sort;

import java.util.Comparator;

public class MergeSort implements Sorting {

	@Override
	public <T> void sort(T[] mas, Comparator<? super T> cmp) {
		sort(mas, 0, mas.length, cmp);		
	}
	
	//[start, end)
	private <T> void sort(T[] mas, int start, int end, Comparator<? super T> cmp) {
		if (end - start > 1) {
			int mid = (start + end) >> 1;
			
			sort(mas, start, mid, cmp);
			sort(mas, mid, end, cmp);
			
			merge(mas, start, mid, end, cmp);
		}
	}
	
	/*
	 * mas1 - [start, mid)
	 * mas2 - [mid, end)
	 */
	private <T> void merge(T[] mas, int start, int mid, int end, Comparator<? super T> cmp) {		
		int totalSize = end - start;
		@SuppressWarnings("unchecked")
		T[] tmp = (T[]) new Object[totalSize];
		
		for (int i = 0, i1 = start, i2 = mid; i < totalSize; i ++) {
			boolean has1 = i1 < mid;
			boolean has2 = i2 < end;
			
			if (has1 && has2 ? cmp.compare(mas[i1], mas[i2]) < 0 : has1) {
				tmp[i] = mas[i1 ++];
			} else {
				tmp[i] = mas[i2 ++];
			}		
		}
		
		//copy tmp to mas
		System.arraycopy(tmp, 0, mas, start, totalSize);
	}

}
