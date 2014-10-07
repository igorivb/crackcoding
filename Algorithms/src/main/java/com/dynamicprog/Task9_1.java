package com.dynamicprog;

import java.math.BigInteger;

public class Task9_1 {

	public static BigInteger[] findCount(int len, int steps) {
		BigInteger[] c = new BigInteger[len + 1];
		c[0] = BigInteger.ONE;				
		for (int i = 1; i <= len; i ++) {
			c[i] = BigInteger.ZERO;
			for (int j = i - 1; j >=0 && j >= i - steps; j --) {				
				c[i] = c[i].add(c[j]);				
			}
		}				
		return c;
	}
	
	//from book
	public static int countWaysDP(int n, int[] map) {
		if (n < 0) {
			return 0;
		} else if (n == 0) {
			return 1;
		} else if (map[n] > -1) {
			return map[n];
		} else {
			map[n] = countWaysDP(n - 1, map) + countWaysDP(n - 2, map)
					+ countWaysDP(n - 3, map);
			return map[n];
		}
	}
	
	public static void main(String[] args) {
		int len = 40;
		int steps = 3;
		BigInteger[] c = findCount(len, steps);
		for (int i = 1; i <= len; i ++) {
			System.out.printf("%2s: %s\n", i, c[i]);
		}
		
//		int[] map = new int[len + 1];		
//		for (int i = 0; i < map.length; i ++) {
//			map[i] = -1;
//		}
//		countWaysDP(30, map);
//		for (int i = 1; i <= len; i ++) {
//			System.out.printf("%2s: %s\n", i, map[i]);
//		}
		
	}
}
