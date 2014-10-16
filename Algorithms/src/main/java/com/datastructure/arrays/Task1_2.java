package com.datastructure.arrays;

/**
 * Implement a function void reverse(char* str) in C or C++ which reverses a null-terminated string.
 */
public class Task1_2 {

	public static void reverse(char[] str) {
		for (int i = 0, n = str.length / 2; i < n; i ++) {
			char t = str[i];
			str[i] = str[str.length - i - 1];
			str[str.length - i - 1] = t;
		}
	}
	
	public static void main(String[] args) {
		char[] str = "1234".toCharArray();
		reverse(str);
		System.out.println(str);
		
		str = "12345".toCharArray();
		reverse(str);
		System.out.println(str);
		
		str = "12".toCharArray();
		reverse(str);
		System.out.println(str);
		
		str = "1".toCharArray();
		reverse(str);
		System.out.println(str);
	}
}
