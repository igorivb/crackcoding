package com.string;

public class PatternFind {

	public static int findMatch(String t, String p) {		
		for (int i = 0, n = t.length() - p.length(); i <= n; i ++) {
			int j = 0;
			while (j < p.length() && t.charAt(i + j) == p.charAt(j)) {
				j ++;
			}
			if (j == p.length()) {
				return i;
			}				
		}
		return -1;
	}
	
	public static void main(String[] args) {
		
		String t = "ababba";
		String p = "abba";
		
		int res = findMatch(t, p);
		System.out.println(res);
	}
}
