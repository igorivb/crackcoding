package com.sort;

import java.util.Arrays;

import static com.Utils.toStringArrayWithIndexes; 

public class BinarySearch {
	
	public static int binarySearch(int[] mas, int elem) {
		return binarySearch0(mas, elem, 0, mas.length);
	}
	
	public static int binarySearch(int[] mas, int elem, int fromIndex, int toIndex) {
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
		}
		if (fromIndex < 0) {
			throw new IllegalArgumentException("fromIndex < 0: " + fromIndex);
		}
		if (toIndex > mas.length) {
			throw new IllegalArgumentException("toIndex > mas.length: " + toIndex);
		}
		
		return binarySearch0(mas, elem, fromIndex, toIndex);
	}

	private static int binarySearch0(int[] mas, int elem, int fromIndex, int toIndex) {
		
		//[start, end]
		int start = fromIndex;
		int end = toIndex - 1;
		
		while (start <= end) {
			int middle = start + (end - start) / 2;
			if (mas[middle] == elem) {
				//found
				return middle;
			} else if (elem < mas[middle]) {
				end = middle - 1;
			} else {
				start = middle + 1;
			}
		}
		//not found
		return - (start + 1);
	}
	
	public static void main(String[] args) {
		int[] mas = new int[] {3, 5, 87, -1, 45, 5, 67, 12, 34};
		Arrays.sort(mas);
		System.out.println(toStringArrayWithIndexes(mas));
		
		int elem = -5;
		
		int ind1 = binarySearch(mas, elem);
		
		int ind2 = Arrays.binarySearch(mas, elem);
		
		System.out.println(ind1 + " ? " + ind2);
	}
}
