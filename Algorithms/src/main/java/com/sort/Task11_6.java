package com.sort;

import java.util.Comparator;

public class Task11_6 {

	private static final Integer[] noResult = null;
	
	public static <T> Integer[] search(T[][] mas, T val, Comparator<T> cmp) {
		int rowIndex = searchInRow(mas, val, cmp);
		
		if (rowIndex >= 0) {
			//found
			return new Integer[] {rowIndex, 0};
		}
		
		//transform from negative next to positive current row 
		rowIndex = (-rowIndex - 1) - 1;
		//can be nagative
		if (rowIndex < 0) {
			return noResult;
		}
		
		//search in row
		int ind = search(mas[rowIndex], val, cmp);
		if (ind < 0) {
			return noResult;
		} else {
			return new Integer[]{rowIndex, ind};
		}
	}
	
	static <T> int searchInRow(T[][] mas, T val, Comparator<T> cmp) {
		int s = 0;
		int e = mas.length - 1;
		
		while (s <= e) {
			int mid = (s + e) >> 1;
			int c = cmp.compare(val, mas[mid][0]);
			if (c == 0) {
				//found
				return mid;
			} else if (c < 0) {
				e = mid - 1;
			} else {
				s = mid + 1;
			}
		}
		return -(s + 1);
	}
	
	static <T> int search(T[] mas, T val, Comparator<T>cmp) {
		int s = 0;
		int e = mas.length - 1;
		
		while (s <= e) {
			int mid = (s + e) >> 1;
			int c = cmp.compare(val, mas[mid]);
			if (c == 0) {
				//found
				return mid;
			} else if (c < 0) {
				e = mid - 1;
			} else {
				s = mid + 1;
			}
		}
		return -(s + 1);
	}
}
