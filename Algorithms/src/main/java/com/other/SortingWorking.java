package com.other;

import java.util.Comparator;

import com.Utils;

/** 
 * 
 * quickSort
 * heapSort
 */
public class SortingWorking {

	public static <T> int binarySearch(T[] mas, T val, Comparator<T> cmp) {
		int s = 0, e = mas.length - 1;
		while (s <= e) {
			int mid = (s + e) >>> 1;
			int c = cmp.compare(val, mas[mid]);
			if (c < 0) {
				e = mid - 1;
			} else if (c > 0) {
				s = mid + 1;
			} else {
				//found
				return mid;
			}
		}
		return -(s + 1);
	} 
	
	public static <T> void insertionSort(T[] mas, Comparator<T> cmp) {
		for (int i = 1; i < mas.length; i ++) {
			for (int j = i - 1; j >= 0 && cmp.compare(mas[j], mas[j + 1]) > 0; j --) {				
				Utils.swap(mas, j, j + 1);				
			}
		}
	}
	
	public static <T> void selectionSort(T[] mas, Comparator<T> cmp) {
		for (int i = 0; i < mas.length; i ++) {
			int min = i;
			for (int j = i + 1; j < mas.length; j ++) {
				if (cmp.compare(mas[min], mas[j]) > 0) {
					min = j;					
				}
			}
			Utils.swap(mas, i, min);
		}
	}
	
	public static <T> void bubbleSort(T[] mas, Comparator<T> cmp) {
		for (int i = mas.length - 1; i > 0; i --) {
			boolean swapped = false;
			for (int j = 0; j < i; j ++) {
				if (cmp.compare(mas[j], mas[j + 1]) > 0) {
					swapped = true;
					Utils.swap(mas, j, j + 1);
				}
			}
			if (!swapped) {
				break;
			}
		}
	}

	public static <T> void mergeSort(T[] mas, Comparator<T> cmp) {
		mergeSort(mas, 0, mas.length, cmp);
	}
	
	//[s, e)
	static <T> void mergeSort(T[] mas, int s, int e, Comparator<T> cmp) {
		if (e - s > 1) {
			int m = (s + e) >>> 1;
			mergeSort(mas, s, m, cmp);
			mergeSort(mas, m, e, cmp);
			join(mas, s, m, e, cmp);
		}
	}
	
	static <T> void join(T[] mas, int s, int m, int e, Comparator<T> cmp) {
		int len = e - s;		
		@SuppressWarnings("unchecked")
		T[] tmp = (T[]) new Object[len];
		for (int i = 0, i1 = s, i2 = m; i < len; i ++) {
			if (i2 >= e || cmp.compare(mas[i1], mas[i2]) <= 0) {
				tmp[i] = mas[i1 ++];				
			} else {
				tmp[i] = mas[i2 ++];
			}
		}		
		
		//copy tmp to mas
		System.arraycopy(tmp, 0, mas, s, len);
	}

//	//[s, e)
//	public static <T> void quickSort(T[] mas, int s, int e, Comparator<T> cmp) {
//		quickSort(mas, 0, mas.length - 1, cmp);
//		int p = getPrivot(mas, s, e);
//		int i = process(mas, s, e, p, cmp);
//		
//		quickSort(mas, s, i - 1, cmp);
//		quickSort(mas, i + 1, e, cmp);
//	}
//	
//	static <T> void quickSort(T[] mas, int s, int e, Comparator<T> cmp) {
//		
//	}
	
	
	public static void main(String[] args) {
		
	}
}
