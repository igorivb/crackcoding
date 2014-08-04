package com.other;

import java.util.Arrays;
import java.util.Comparator;

public class Sorting {

	//Comparator or Comparable
	public static <T> int binarySearch(T[] mas, T value, Comparator<T> cmp) {
		//TODO: check preconditions
		
		int start = 0;
		int end = mas.length - 1;
		
		while (start <= end) {
			int i = start + (end - start) / 2;
			int c = cmp.compare(mas[i], value);
			if (c < 0) {
				start = i + 1;
			} else if (c > 0) {
				end = i - 1;
			} else {
				//found
				return i;
			}		
		}
		
		//(-(insertion point) - 1)
		
		return - (start + 1);
	}
	
	public static <T> void selectionSort(T[] mas, Comparator<? super T> cmp) {
		for (int i = 0; i < mas.length; i ++) {
			int min = i;
			for (int j = i + 1; j < mas.length; j ++) {
				if (cmp.compare(mas[min], mas[j]) > 0) {
					min = j;
				}
			}
			swap(mas, min, i);
		}
	}

	public static <T> void insertionSort(T[] mas, Comparator<? super T> cmp) {
		for (int i = 1, n = mas.length; i < n; i ++) {
			for (int j = i - 1; j >= 0 && cmp.compare(mas[j], mas[j + 1]) > 0; j --) {			
				swap(mas, j, j + 1);			
			}
		}
	}
	
	public static void swap(Object[] mas, int i, int j) {
		Object tmp = mas[i];
		mas[i] = mas[j];
		mas[j] = tmp;
	}
	
	public static <T> Comparator<T> reverseComparator(final Comparator<? super T> cmp) {
		return new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return cmp.compare(o2, o1);
			}
		};
	} 
	
	public static <T> void bubbleSort(T[] mas, Comparator<? super T> cmp) {
		for (int i = mas.length - 1; i >= 0; i --) {
			boolean swapped = false;
			for (int j = 0; j < i; j ++) {
				if (cmp.compare(mas[j], mas[j + 1]) > 0) {
					swapped = true;
					swap(mas, j, j + 1);
				}
			}
			if (!swapped) {
				break;
			} 
		}
	}
	
	public static void main(String[] args) {
		
		String[] mas = {"f", "d", "b", "g", "a"};
//		int res = binarySearch(mas, "h", String.CASE_INSENSITIVE_ORDER);
//		System.out.println(res);
		
		
		Comparator<String> cmp = String.CASE_INSENSITIVE_ORDER;
		//cmp = reverseComparator(cmp);		
				
		bubbleSort(mas, cmp);
		System.out.println(Arrays.toString(mas));
	}
}
