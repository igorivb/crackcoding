package com.dynamicprog;

/**
 * See Cornel: 15.1 Rod cutting
 */
public class RodCutting {

	public static int cutRod(int[] prices, int len) {
		if (len > prices.length) {
			throw new IllegalArgumentException("Invalid len");
		}
		
		Integer [] calcs = new Integer[prices.length + 1];
		calcs[0] = 0;
		
		return cutRod(prices, len, calcs);
	}
	
	static int cutRod(int[] prices, int len, Integer[] calcs) {
		if (len == 0) {
			return 0;
		}
		
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < len; i ++) {
			int val = prices[i];
			int subLen = len - i - 1;
			if (calcs[subLen] == null) {
				calcs[subLen] = cutRod(prices, subLen, calcs);
			}
			val += calcs[subLen];
						
			max = Math.max(max, val);
		}
		
		return max;
	}
	
	public static void main(String[] args) {
		int[] prices = new int[] {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
		//int[] prices = new int[] {1, 5, 8};
		for (int i = 0; i <= prices.length; i ++) {
			int revenue = cutRod(prices, i);
			System.out.printf("%2s: %3s\n", i, revenue);
		}
		
	}
}
