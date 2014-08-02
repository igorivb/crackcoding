package com.other;

import static com.Utils.printMatrix;

public class MatrixMultiplication {

	public static int[][] matrixMultiply(int[][] a, int[][] b) {
		if (a == null || a.length == 0 || b == null || b.length == 0) {
			throw new IllegalArgumentException("empty arrays");
		}		
		if (b.length != a[0].length) {
			throw new IllegalArgumentException("not compatible arrays");
		}
		
		int m = a.length;
		int n = b.length;
		int q = b[0].length;
		
		int[][] c = new int[m][q];
		
		for (int i = 0; i < m; i ++) {
			for (int j = 0; j < q; j ++) {
				for (int r = 0; r < n; r ++) {
					c[i][j] += a[i][r] * b[r][j];
				}
			}
		}
		
		return c;
	}
	
	public static void main(String[] args) {
		int[][] a = new int[][] { 
			{2, -2},
			{4, 3},
			{5, 0}
		};
		int[][] b = new int[][] {
			{4, 5, -3},
			{-2, 3, 2}
		};
		
		int[][] c = matrixMultiply(a, b);
		printMatrix(c);
	}
}
