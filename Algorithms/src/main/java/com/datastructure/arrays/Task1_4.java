package com.datastructure.arrays;

/**
 * Write a method to replace all spaces in a string with '%20'. You may assume that the
 * string has sufficient space at the end of the string to hold the additional characters,
 * and that you are given the "true" length of the string. (Note: if implementing in Java,
 * please use a character array so that you can perform this operation in place.)
 */
public class Task1_4 {

	//with Move
	public static void replaceSpaces1(char[] s, int initialLen) {
		int i = s.length - initialLen;		
		//move to right
		System.arraycopy(s, 0, s, i, initialLen);		
		for (int j = 0; i < s.length; i ++) {
			if (s[i] != ' ') {
				s[j ++] = s[i];
			} else {
				s[j ++] = '%'; s[j ++] = '2'; s[j ++] = '0';
			}
		}
	}
	
	//without Move
	public static void replaceSpaces(char[] s, int len) {
		int spacesCount = 0;
		for (int i = 0; i < len; i ++) {
			if (s[i] == ' ') {
				spacesCount ++;
			}
		}
		int newLen = len + spacesCount * 2;
		s[newLen] = '\0';
		for (int i = len - 1, j = newLen - 1; i >= 0; i --) {
			if (s[i] != ' ') {
				s[j --] = s[i];
			} else {
				s[j --] = '0'; s[j --] = '2'; s[j --] = '%';  
			}
		}
	}
	
	public static String replaceSpacesRegexp(char[] s) {
		return new String(s).replaceAll(" ", "%20");
	}
	
	public static void main(String[] args) {
		char[] s = " a b ________________".toCharArray();
		replaceSpaces(s, 5);
		System.out.println(new String(s));
		
		//System.out.println(replaceSpacesRegexp("a b c".toCharArray()));
	}
}
