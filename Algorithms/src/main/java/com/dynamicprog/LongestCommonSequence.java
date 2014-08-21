package com.dynamicprog;

import java.util.LinkedList;
import java.util.List;

public class LongestCommonSequence {

	enum Solution {
		X, Y, EQ 
	} 
	
	//top-down
	public static <T> Object[] lcs(T[] x, T[] y, int i, int j) {
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
	
	public static void main(String[] args) {
		String[] x = new String[] { "A", "B", "C", "B", "D", "A", "B" };
		String[] y = new String[] {"B", "D", "C", "A", "B", "A"};
		
//		String[] x = toStringArray("ACCGGTCGAGTGCGCGGAAGCCGGCCGAA");
//		String[] y = toStringArray("GTCGTTCGGAATGCCGTTGCTCTGTAAA");
		
		Object[] res = lcs(x, y, x.length, y.length);
		int len = (int) res[0];				
		
		List<String> solution = getSolutionAsString((Solution[][]) res[1], x, y);
				
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
