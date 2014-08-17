package com.sort;

import java.util.Comparator;

public class Task11_3 {

	public static <T> int search(T[] mas, T val, Comparator<T> cmp) {				
		int start = 0;
		int end = mas.length - 1;
		
		while (start <= end) {
			boolean isNormal = cmp.compare(mas[start], mas[end]) <= 0;
			int mid = (start + end) >> 1;
			
			int c = cmp.compare(val, mas[mid]);
			if (c == 0) {
				//found
				return mid;
			} 
			
			if (c > 0) {
				if (!isNormal && cmp.compare(val, mas[start]) >= 0) {
					end = mid - 1;
				} else {
					//forward
					start = mid + 1;
				}
			} else {
				if (!isNormal && cmp.compare(val, mas[end]) <= 0) {
					start = mid + 1;
				} else {
					//back
					end = mid - 1;
				}
			}
		}
		
		//not found
		return -1;
	}
}
