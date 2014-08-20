package com.dynamicprog;


/**
 * See Cornel: 15.1 Rod cutting
 */
public class RodCutting3 {
	
	public static int cutRodTopDown(int[] prices, int len) {
		if (len > prices.length) {
			throw new IllegalArgumentException("len");
		}
		
		Integer[] memory = new Integer[prices.length];
		return cutRodTopDown(prices, len, memory);
	}
	
	private static int cutRodTopDown(int[] prices, int len, Integer[] memory) {
		if (len == 0) {
			return 0;
		}
		if (memory[len - 1] != null) {
			return memory[len - 1];
		}
		
		int max = Integer.MIN_VALUE;		
		for (int i = 0; i < len; i ++) {			
			int val = prices[i] + cutRodTopDown(prices, len - i - 1, memory);
			max = Math.max(max, val);
		}
		memory[len - 1] = max;
		
		return max;
	}
	
	/*
	 * Contains solution.
	 */
	public static Object[] cutRodBottomUp(int[] prices, int len) {
		int[] memory = new int[prices.length + 1];	 //memory[0] = 0
		Integer[] solution = new Integer[prices.length + 1];
		solution[0] = 0;
		
		for (int intLen = 1; intLen <= len; intLen ++) { 
			int q = Integer.MIN_VALUE;
			for (int i = 0; i < intLen; i ++) {
				int val =  prices[i] + memory[intLen - i - 1];
				if (q < val) {
					q = val;
					//remember best solution at current length
					solution[intLen] = i + 1;
				}
				
			}
			memory[intLen] = q;
		}
				
		return new Object[] {memory[len], solution};
	}	
	
	
	public static String printCutRodBottomUp(int[] prices, int len) {		
		Object[] result = cutRodBottomUp(prices, len);
		
		int revenue = (int) result[0];
		Integer[] solutions = (Integer[]) result[1];
		
		StringBuilder strSolution = new StringBuilder();		
		while (len > 0) {
			if (strSolution.length() > 0) {
				strSolution.append(", ");
			}
			strSolution.append(solutions[len]);
			len = len - solutions[len];
		}
		
		String str = String.format("Revenue: %2s, solution: [%s]", revenue, strSolution);
		return str;		
	}
	

	public static void main(String[] args) {

		int[] prices = new int[] {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
		
		for (int i = 1; i <= prices.length; i ++) {
		
			System.out.printf("%2s: %s\n", i, printCutRodBottomUp(prices, i));
			
//			Object[] result = cutRodBottomUp(prices, i);
//			int revenue = (int) result[0];
//			
//			Integer[] solutionIndexes = (Integer[]) result[1];
//			List<Integer> solution = new ArrayList<>();
//			for (int j = 0; j < solutionIndexes.length; j ++) {
//				if (solutionIndexes[j] != null) {
//					solution.add(prices[solutionIndexes[j]]);	
//				}				
//			}
//			
//			System.out.printf("%2s: %3s | %s\n", i, revenue, solution);
		}
		
		
			

	}
}
