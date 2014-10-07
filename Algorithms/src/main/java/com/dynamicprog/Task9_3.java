package com.dynamicprog;

public class Task9_3 {
	
	public static int findMagicIndexDistinct(int[] mas) {
		int iter = 0;
		
		int s = 0, e = mas.length - 1;		
		while (s <= e) {
			iter ++;
			
			int mid = (s + e) >>> 1;
			if (mas[mid] < mid) {
				s = mid + 1;
			} else if (mas[mid] > mid) {
				e = mid - 1;
			} else {
				//found
				System.out.println("Number of iterations: " + iter);
				
				return mid;
			}
		}				
		
		return -1;
	}	
	
	
	public static int findMagic(int[] mas) {
		return findMagic(mas, 0, mas.length - 1);
	}
	
	public static int findMagic(int[] mas, int s, int e) {
		if (s < 0 || e >= mas.length || e < s) {
			return -1;
		}		
		int m = (s + e) >>> 1;
		
		if (m == mas[m]) {
			return m;
		}
		int left = findMagic(mas, s, Math.min(m - 1, mas[m]));
		if (left == -1) {
			int right = findMagic(mas, Math.max(m + 1, mas[m]), e);
			return right;
		} else {
			return left;
		}
	}
	
	public static void main(String[] args) {
		//int[] mas = new int[] {-40, -20, -1, 1, 2, 3, 5, 7, 9, 12, 13};
		int[] mas = new int[] {-10,-5,2,2,2,3,4,8,9,12,13};
		int res = findMagic(mas);
		if (res != -1) {
			System.out.printf("Index: %2s, value: %2s\n", res, mas[res]);	
		} else {
			System.out.println("not found");
		}
	}
	
}
