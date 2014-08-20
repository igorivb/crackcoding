package com.dynamicprog;

/**
 * See Cornel: 15.1 Rod cutting
 */
public class RodCutting3 {
	
	public static int cutRod(int[] prices, int len) {
		if (len > prices.length) {
			throw new IllegalArgumentException();
		}
		
		Integer[] memory = new Integer[prices.length];
		return cutRod(prices, len, memory);
	}
	
	static int cutRod(int[] prices, int len, Integer[] memory) {
		if (len == 0) {
			return 0;
		}
		
		int max = Integer.MIN_VALUE;
		
		for (int i = 0; i < len; i ++) {
			int val = prices[i];
			
			int subLen = len - i - 1;
			if (subLen > 0) {
				if (memory[subLen - 1] == null) {
					memory[subLen - 1] = cutRod(prices, subLen, memory);
				}				
				val += memory[subLen - 1];	
			}						 
			
			max = Math.max(max, val);
		}
		
		return max;
	}
	
	public static void main(String[] args) {
		//value for len = prices[len - 1]
		int[] prices = new int[] {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};	
		
		//int[] prices = new int[] {3, 5};
	
		
		for (int i = 0; i <= prices.length; i ++) {
			int revenue = cutRod(prices, i);
			System.out.printf("%2s: %3s\n", i, revenue);
		}		
	}
}
