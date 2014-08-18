package com.sort;

import java.util.Comparator;

public class Task11_6 {

	private static final Integer[] noResult = null;
	
	//FIXME: incorrect
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
	
	
//	public static boolean findflement(int[][] matrix, int elem) {
//		int row = 0;
//		int col = matrix[0].length - 1;
//		while (row < matrix.length && col >= 0) {
//			if (matrix[row][col] == elem) {
//				return true;
//			} else if (matrix[row][col] > elem) {
//				col--;
//			} else {
//				row++;
//			}
//		}
//		return false;
//	}
	
	/*
	 * 1. if start of the column > val, then search in left columns
	 * 2. if end of the row < val, then search down rows
	 */
	
	public static <T> boolean findElement(T[][] mas, T val, Comparator<T> cmp) {
		int row = 0;
		int col = mas[0].length - 1;
		
		while (col >= 0 && row <= mas.length - 1) {
			int c = cmp.compare(mas[row][col], val);
			if (c == 0) {
				return true;
			} else if (c > 0) {
				col --;
			} else {
				row ++;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		
//		int[][] mas = new int[][] {
//			{15, 20, 40, 85},
//			{20, 35, 80, 95},
//			{30, 55, 95, 105},
//			{40, 80, 100, 120},
//		};
		
		Integer[][] mas = new Integer[][] {
				{15, 40, 40, 85},
				{20, 42, 80, 95},
				{30, 55, 95, 105},
				{40, 80, 100, 120},
			};
		
		Comparator<Integer> cmp = Integer::compare;
		
		Integer val = 42;
		
		boolean res = findElement(mas, val, cmp);
		System.out.println(res);
	}
	
	
}
