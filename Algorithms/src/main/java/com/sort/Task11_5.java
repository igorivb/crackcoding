package com.sort;

import java.util.Comparator;

public class Task11_5 {

	public static int search(String[] mas, String val, Comparator<String> cmp) {		
		int start = 0;
		int end = mas.length - 1;
		
		while (start <= end) {
			int mid = (start + end) >> 1;
					
			//find first non-empty string
			if (mas[mid].length() == 0) {
				boolean found = false;
				for (int i = 0; mid + i <= end || mid - i >= start; i ++) {
					if (mid + i <= end && mas[mid + i].length() > 0) {
						found = true;
						mid = mid + i;
						break;
					}
					if (mid - i >= start && mas[mid - i].length() > 0) {
						found = true;
						mid = mid - i;
						break;
					}
				}
				//all strings are empty, end processing
				if (!found) {
					return -1;
				}
			}
			
			int c = cmp.compare(val, mas[mid]);
			if (c < 0) {
				end = mid - 1;
			} else if (c > 0) {
				start = mid + 1;
			} else {
				//found
				return mid;
			}
		}
		
		return -1;
	}
}
