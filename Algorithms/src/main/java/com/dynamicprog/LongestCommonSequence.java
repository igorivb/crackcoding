package com.dynamicprog;

import java.util.LinkedList;
import java.util.List;

public class LongestCommonSequence {

	enum Solution {
		X, Y, EQ 
	} 
	
	//top-down
	public static <T> Object[] lcsTopDown(T[] x, T[] y, int i, int j) {
		Integer[][] m = new Integer[i + 1][j + 1];
		m[0][0] = 0;
				
		Solution[][] s = new Solution[i][j];
				
		int len = lcs0(x, y, i, j, m, s);
		
		return new Object[] {len, s};
	}
	
	/*
	 * i - length for x
	 * j - length for y
	 */
	static <T> int lcs0(T[] x, T[] y, int i, int j, Integer[][] m, Solution[][] s) {			
		if (m[i][j] != null) {
			return m[i][j];
		}		
		
		int len;		
		if (i == 0 || j == 0) {
			len = 0;			
		} else if (x[i - 1].equals(y[j - 1])) {
			len = lcs0(x, y, i - 1, j - 1, m, s) + 1;
			s[i - 1][j - 1] = Solution.EQ; 
		} else {
			int l1 = lcs0(x, y, i - 1, j, m, s);
			int l2 = lcs0(x, y, i, j - 1, m, s);
			if (l1 <= l2) {
				len = l2;
				s[i - 1][j - 1] = Solution.Y;
			} else {
				len = l1;
				s[i - 1][j - 1] = Solution.X;
			}				
		}
				
		m[i][j] = len;		 				
		
		return len;
	}	
	
	/*
	 * i - length for x
	 * j - length for y
	 */
	public static <T> Object[] lcsBottomUp(T[] x, T[] y, int xLen, int yLen) {
		int[][] m = new int[xLen + 1][yLen + 1];
				
		Solution[][] s = new Solution[xLen][yLen];
				
		for (int i = 1; i <= xLen; i ++) {
			for (int j = 1; j <= yLen; j ++) {				
				int len;		
				if (i == 0 || j == 0) {
					len = 0;			
				} else if (x[i - 1].equals(y[j - 1])) {
					len = m[i - 1][j - 1] + 1;
					s[i - 1][j - 1] = Solution.EQ; 
				} else {
					int l1 = m[i - 1][j];
					int l2 = m[i][j - 1];
					if (l1 <= l2) {
						len = l2;
						s[i - 1][j - 1] = Solution.Y;
					} else {
						len = l1;
						s[i - 1][j - 1] = Solution.X;
					}				
				}						
				m[i][j] = len;		 												
			}
		}
		
		return new Object[] {m[xLen][yLen], s, m};
	}
	
	static <T> List<String> getSolutionAsString(Solution[][] solution, T[] x, T[] y) {
		List<String> solList = new LinkedList<>();
		
		int i = x.length - 1;
		int j = y.length - 1;
		while (i >= 0 && j >= 0) {
			switch (solution[i][j]) {
				case EQ:
					solList.add(0, String.valueOf(x[i]));
					i --;
					j --;
					break;
				case X:
					i --;
					break;
				case Y:
					j --;
					break;
				default:
					//should not happen
					throw new RuntimeException("Illegal solution");					
			}						
		}		
		return solList;
	}
	
	static <T> List<String> getSolution2AsString(int[][] m, T[] x, T[] y) {
		LinkedList<String> list = new LinkedList<>();
		for (int i = x.length, j = y.length; i > 0 && j > 0;) {
			if (x[i - 1] == y[j - 1]) {
				list.addFirst(String.valueOf(x[i - 1]));
				i --;
				j --;
			} else {
				if (m[i - 1][j] > m[i][j - 1]) {
					i --;
				} else {
					j --;
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		//Len:  4, solution: [B, D, A, B]		  
		String[] x = new String[] { "A", "B", "C", "B", "D", "A", "B" };
		String[] y = new String[] {"B", "D", "C", "A", "B", "A"};
		
		//Len: 20, solution: [G, T, C, G, T, C, G, G, A, A, G, C, C, G, G, C, C, G, A, A]
//		String[] x = toStringArray("ACCGGTCGAGTGCGCGGAAGCCGGCCGAA");
//		String[] y = toStringArray("GTCGTTCGGAATGCCGTTGCTCTGTAAA");
		
		Object[] res = lcsBottomUp(x, y, x.length, y.length);
		int len = (int) res[0];				
		
		List<String> solution;
		
		//solution = getSolutionAsString((Solution[][]) res[1], x, y);
		
		solution = getSolution2AsString((int[][]) res[2], x, y);
				
		System.out.printf("Len: %2s, solution: %s\n", len, solution);					
	}
	
	static String[] toStringArray(String str) {
		String[] mas = new String[str.length()];
		for (int i = 0; i < mas.length; i ++) {
			mas[i] = String.valueOf(str.charAt(i));
		}
		return mas;
	}
}
