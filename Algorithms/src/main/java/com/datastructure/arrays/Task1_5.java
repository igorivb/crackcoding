package com.datastructure.arrays;

/**
 * Implement a method to perform basic string compression using the counts of
 * repeated characters. For example, the string aabcccccaaa would become a2b1c5a3. 
 * If the "compressed" string would not become smaller than the original
 * string, your method should return the original string.
 */
public class Task1_5 {

	public static String compress1(String s) {
		char[] result = new char[s.length()];
		int resInd = 0;
		
		char prev = s.charAt(0);
		int count = 1;
		for (int i = 1; i <= s.length(); i ++) {
			if (i == s.length() || s.charAt(i) != prev) {
				//add to result								
				String strCount = String.valueOf(count);
				int num = 1 + strCount.length();				
				if (resInd + num < result.length) {
					result[resInd ++] = prev;
					for (int j = 0; j < strCount.length(); j ++) {
						result[resInd ++] = strCount.charAt(j);
					}								
					
					if (i < s.length()) {
						prev = s.charAt(i);
						count = 1;								
					}	
				} else {
					//compressed string is more than original. Return original
					return s;
				}								
			} else {
				count ++;
			}						
		}
		
		return new String(result, 0, resInd);
	}
	
	//use StringBuilder instead of char[]
	public static String compress(String s) {
		StringBuilder result = new StringBuilder(s.length());		
		char prev = s.charAt(0);
		int count = 1;
		for (int i = 1; i <= s.length(); i ++) {
			if (i == s.length() || s.charAt(i) != prev) {														
				result.append(prev).append(count); //add to result
				if (result.length() > s.length()) {
					return s; //compressed string is more than original. Return original
				} else if (i < s.length()) {
					prev = s.charAt(i);
					count = 1;
				}										
			} else {
				count ++;
			}						
		}		
		return result.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(compress("aabcccccaaa"));
		System.out.println(compress("abc"));
		System.out.println(compress("a"));
		System.out.println(compress("aaaaaaaaaaaabbb"));
	}
}
