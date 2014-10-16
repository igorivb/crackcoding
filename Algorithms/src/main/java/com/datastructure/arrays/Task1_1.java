package com.datastructure.arrays;

import java.util.BitSet;

/**
 * Implement an algorithm to determine if a string has all unique characters. 
 * What if you cannot use additional data structures?
 */
public class Task1_1 {

	//Brute Force: O(n^2)
	static boolean unique(String str) {
		for (int i = 0; i < str.length(); i ++) {
			for (int j = i + 1; j < str.length(); j ++) {
				if (str.charAt(i) == str.charAt(j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//for ASCI
	static boolean uniqueAscii(String str) {
		final int SIZE = 256;
		//optimization
		if (str.length() > SIZE) {
			return false;
		}
		
		boolean[] chars = new boolean[SIZE];
		for (int i = 0; i < str.length(); i ++) {
			if (chars[str.charAt(i)]) {
				return false;
			} else {
				chars[str.charAt(i)] = true;
			}
		}
		return true;
	}
	
	//for ASCI with BitSet
	static boolean uniqueAsciiWithBitSet(String str) {
		final int SIZE = 256;
		if (str.length() > SIZE) {
			return false;
		}
		
		BitSet bits = new BitSet(SIZE);
		for (int i = 0; i < str.length(); i ++) {
			if (bits.get(str.charAt(i))) {
				return false;
			} else {
				bits.set(str.charAt(i));
			}
		}
		return true;
	}
	
	//for ASCI with Bits: a-z
	static boolean uniqueAsciiWithBits(String str) {
		final int SIZE = 26;
		if (str.length() > SIZE) {
			return false;
		}
		
		int bits = 0;
		for (int i = 0; i < str.length(); i ++) {
			char c = str.charAt(i);
			if (c < 'a' || c > 'z') {
				throw new RuntimeException("Unexpected char: " + c);
			}
			
			int ind = c - 'a';
			if (((bits >> ind) & 1) == 1) {
				return false;
			} else {
				bits |= 1 << ind;
			}
		}
		return true;
	}
	
	//for Unicode: Bits (max(c) = 2^16)
	static boolean uniqueWithBits(String str) {
		final int SIZE = 1 << 16;
		if (str.length() > SIZE) {
			return false;
		}
		
		int bits = 0;
		for (int i = 0; i < str.length(); i ++) {
			char c = str.charAt(i);
			
			int ind = c;
			if (((bits >> ind) & 1) == 1) {
				return false;
			} else {
				bits |= 1 << ind;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(uniqueWithBits("abc"));
		System.out.println(uniqueWithBits("abca"));
		System.out.println(uniqueWithBits("abc_"));
	}
}
