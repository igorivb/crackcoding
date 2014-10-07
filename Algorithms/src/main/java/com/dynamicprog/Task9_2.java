package com.dynamicprog;

import java.math.BigInteger;

public class Task9_2 {

	public static Object[] findNumberOfPaths(int x, int y) {
		BigInteger[][] numberOfPaths = new BigInteger[x + 1][y + 1];
		int[][] d = new int[x + 1][y + 1];
		
		for (int i = 0; i <= x; i ++) {
			numberOfPaths[i][0] = BigInteger.ONE;
			d[i][0] = i;
		}
		for (int j = 0; j <= y; j ++) {
			numberOfPaths[0][j] = BigInteger.ONE;
			d[0][j] = j;
		}
		
		for (int i = 1; i <= x; i ++) {
			for (int j = 1; j <= y; j ++) {
				numberOfPaths[i][j] = numberOfPaths[i - 1][j].add(numberOfPaths[i][j - 1]);
				
				d[i][j] = Math.min(d[i - 1][j], d[i][j - 1]) + 1;
			}
		}
		return new Object[] {numberOfPaths, d};
	}
	
	public static void main(String[] args) {
		int x = 4, y = 5;
		Object[] res = findNumberOfPaths(x, y);
		
		BigInteger[][] numberOfPaths = (BigInteger[][]) res[0];
		int[][] d = (int[][]) res[1];
		for (int i = 1; i <= x; i ++) {
			for (int j = 1; j <= y; j ++) {
				System.out.printf("(%2s,%2s) shortest path: %5s, numberOfPaths: %d%n", i, j, d[i][j], numberOfPaths[i][j]);
			}
		}				
	}
}
