package com.dynamicprog;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class LongestIncreasingSubsenquence {

	public static <T> int[] lis(T[] mas, Comparator<T> cmp) {
		int[] c = new int[mas.length];
		for (int i = 0; i < mas.length; i ++) {			
			int max = 0;
			for (int j = 0; j < i; j ++) {
				if (cmp.compare(mas[j], mas[i]) < 0 && c[j] > max) {
					max = c[j];
				}
			}
			c[i] = max + 1;
		}
		return c;
	}
	
	private static <T> List<T> getSolutionPath(T[] mas, int[] sol, Comparator<T> cmp) {
		LinkedList<T> list = new LinkedList<>();
		
		T val = mas[mas.length - 1];
		int c = sol[mas.length - 1];
		list.add(val);
		
		for (int i = mas.length - 2; i >= 0; i --) {
			if (sol[i] == c - 1 && cmp.compare(mas[i], val) < 0) {
				list.addFirst(mas[i]);
				c --;
				val = mas[i];
			}
		} 		
		return list;
	}
	
	public static void main(String[] args) {
		//Integer[] mas = new Integer[] {1, 4, 3, 5, 7, 10, 6, 8};
		
		Integer[] mas = new Integer[] {10, 22, 9, 33, 21, 50, 41, 60, 80};
		
		Comparator<Integer> cmp = Integer::compare;
		
		int[] solution = lis(mas, cmp);
		int len = solution[solution.length - 1];
		
		List<Integer> list = getSolutionPath(mas, solution, cmp) ;
		
		System.out.printf("Len : %2s, solution: %s\n", len, list);
	}


}
