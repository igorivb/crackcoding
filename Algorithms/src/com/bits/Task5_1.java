package com.bits;

public class Task5_1 {

	public static int insert(int n, int m, int i, int j) {
		//TODO: check preconditions
		
		//where to start in m: index
		int startM = 31;
		int val = 1 << 31;
		while ((val & m) == 0) {
			startM --;
			val >>>= 1;
		}
		if (startM < 0) {
			throw new RuntimeException("M doesn't contain 1: " + m);
		}
		
		//iterate in M and set bits
		for (int k = 0; k <= startM; k ++) {
			//get bit from M
			int bit = getBit(m, startM - k);
			//update in N
			n = updateBit(n, j - k, bit);
		}
		
		return n;
	}

	static int getBit(int num, int pos) {
		return  (num >>> pos) & 1;
	}
	
	public static int updateBit(int num, int pos, int val) {
		num &= (~(1 << pos));
		num |= (val << pos);
		return num;
	}
	
	
	public static int insert2(int n, int m, int i, int j) {
		//find start index in M (first 1)
		final int thr = 1 << 31;
		int tmp = m;
		int startM;
		
		for (startM = 31; startM >= 0 && (thr & tmp) == 0; startM --) {
			tmp <<= 1;
		}
		if (startM < 0) {
			throw new RuntimeException("Not valid M: " + m);
		}
		
		//iterate M bit by bit and set in N
		for (int k = 0; k <= startM; k ++) {
			int bit = getBit(m, startM - k);
			n = updateBit(n, j - k, bit);
		}
		
		return n;
	}
	
	/*
	 * 1. Clear the bits j through i in N
	 * 2. Shift M so that it lines up with bits j through i
	 * 3. Merge M and N.
	 */
	public static int insert3(int n, int m, int i, int j) {
		
		//1. Clear the bits j through i in N
		int tmp = 0;
		for (int k = 0; k <= (j - i); k ++) {
			tmp |= (1 << (j - k));
		}
		n &= ~tmp;
		
		//2. Shift M so that it lines up with bits j through i
		m <<= i;
		
		//3. Merge M and N
		n |= m;
		
		return n;
	}
	
	
	public static int insert4(int n, int m, int i, int j) {
		
		//1. Clear the bits j through i in N
		int tmp = (~0) << (j + 1);
		tmp |= 1 << (i - 1);
		
		n &= tmp;
		
		//2. Shift M so that it lines up with bits j through i
		m <<= i;
		
		//3. Merge M and N
		n |= m;
		
		return n;
	}


	
	private static String toBinaryString(int num) {
		return Integer.toBinaryString(num);
	}
	
	public static void main(String[] args) {
		int n = 0b10000000000;
		int m = 0b0010011;
		int i = 2, j = 6;
		int expected = 0b10001001100;
		
//		int n = 0b10001000000;
//		int m = 0b0010011;
//		int i = 2, j = 7;		
//		int expected = 0b10001001100;
		
		
		int res = insert3(n, m, i, j);
		System.out.println((expected == res) + " " + toBinaryString(res));		 				
	}
}
