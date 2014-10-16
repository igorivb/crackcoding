package com.datastructure.arrays;

/**
 * Given two strings, write a method to decide if one is a permutation of the other
 */
public class Task1_3 {

	public static boolean permutation(String s1, String s2) {
		if (s1.length() != s2.length()) {
			return false;
		}
		
		char[] index = new char[1 << 16];
		for (int i = 0; i < s1.length(); i ++) {
			index[s1.charAt(i)] ++;			
		}
		
		for (int i = 0; i < s2.length(); i ++) {
			char c = s2.charAt(i);
			if (index[c] == 0) {
				return false;
			} else {
				index[c] --;
			}			
		}
		
		return true;
	} 
	
	public static void main(String[] args) {
		System.out.println(permutation("abc", "cba"));
		System.out.println(permutation("abc", "cbd"));
	}
}
