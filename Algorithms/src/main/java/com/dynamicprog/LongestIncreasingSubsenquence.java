package com.dynamicprog;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class LongestIncreasingSubsenquence {

	/**
	 * res[0] - len
	 * res[1] - int[] solution
	 */
	public static <T> Object[] lis(T[] mas, Comparator<T> cmp) {
		int maxLen = 0;
		int[] c = new int[mas.length];
		for (int i = 0; i < mas.length; i ++) {			
			int max = 0;
			for (int j = 0; j < i; j ++) {
				if (cmp.compare(mas[j], mas[i]) < 0 && c[j] > max) {
					max = c[j];
				}
			}
			c[i] = max + 1;
			maxLen = Math.max(maxLen, c[i]);
		}
		return new Object[]{maxLen, c};
	}
	
	private static <T> List<T> getSolutionPath(T[] mas, int len, int[] sol, Comparator<T> cmp) {
		LinkedList<T> list = new LinkedList<>();
		int lenInd = -1;
		for (int i = sol.length - 1; i >= 0; i --) {
			if (sol[i] == len && (lenInd == -1 || cmp.compare(mas[i], mas[lenInd]) < 0)) {
				list.addFirst(mas[i]);
				//list.set(len - 1, mas[i]);
				len --;
				lenInd = i;
			}
		} 		
		return list;
	}
	
	public static void main(String[] args) {
		//Integer[] mas = new Integer[] {1, 4, 3, 5, 7, 10, 6, 8, 2};		
		//Integer[] mas = new Integer[] {10, 22, 9, 33, 21, 50, 41, 60, 80, 4, 89, 12};
		Integer[] mas = new Integer[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
		
		Comparator<Integer> cmp = Integer::compare;
		
		Object[] res = lis(mas, cmp);		
		int len = (int) res[0];
		
		int[] solution = (int[]) res[1];
		List<Integer> objList = getSolutionPath(mas, len, solution, cmp) ;
		//Integer[] list = (Integer[]) objList;
		
		System.out.printf("Len : %2s, solution: %s\n", len, objList);
	}


}
