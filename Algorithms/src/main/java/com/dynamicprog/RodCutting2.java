package com.dynamicprog;

/**
 * See Cornel: 15.1 Rod cutting
 */
public class RodCutting2 {
	
	public static int cutRod(int[] prices, int len) {
		if (len >= prices.length) {
			throw new IllegalArgumentException("Illegal len");
		}
		
		Integer[] memory = new Integer[prices.length];
		return cutRod(prices, len, memory);
	}
	
	private static int cutRod(int[] prices, int len, Integer[] memory) {
		if (len == 0) {
			return 0;
		}
		
		int max = Integer.MIN_VALUE;
		
		for (int i = 1; i <= len; i ++) {					
			int subLen = len - i;
			if (memory[subLen] == null) {
				memory[subLen] = cutRod(prices, subLen, memory);
			}
			
			int value = prices[i] + memory[subLen];
			
			max = Math.max(max, value);
		}
				
		return max;
	}
	
	public static void main(String[] args) {
		int[] prices = new int[] {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};	
		//int[] prices = new int[] {0, 1, 5};
		
		//int[] prices = new int[] {0, 3, 5};
		
		for (int i = 0; i < prices.length; i ++) {
			int revenue = cutRod(prices, i);
			System.out.printf("%2s: %3s\n", i, revenue);
		}
		
	}
}
