package com.bits;

public class Task5_5 {

	public static byte numberOfBits(int a, int b) {
		byte count = 0;
		for (int n = a ^ b; n > 0; n >>>= 1) {
			count += n & 1;
		}
		return count;
	}
	
	public static byte numberOfBits2(int a, int b) {
		byte count = 0;
		for (int n = a ^ b; n > 0; n &= n - 1) {
			count ++;
		}
		return count;
	}
	
	public static void main(String[] args) {
		int a = 0b0101;
		int b = 0b1111;
		
		byte count = numberOfBits2(a, b);
		System.out.println(count);
	}
}
