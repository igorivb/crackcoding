package com.moderate;

/**
 * Write a method which finds the maximum of two numbers. You should not use
 * if-else or any other comparison operator.
 */
public class Task17_4 {

	static int sign(int num) {
		return flip(num >>> 31);
	}
	
	static int flip(int num) {
		return num ^ 1;
	}

	/**
	 * Doesn't handle possible overflow.
	 */
	public static int max1(int a, int b) {
		int k = sign(a - b);
		int q = flip(k);		
		return a * k + b * q; 
	}
	
	/**
	 * Handle possible overflow.
	 */
	public static int max(int a, int b) {
		int theSameSign = flip(sign(a) ^ sign(b));
		int k = sign(a) * flip(theSameSign) + sign(a - b) * theSameSign;
		int q = flip(k);
		return a * k + b * q; 
	}
	
	static void check(int a, int b) {
		int r1 = max1(a, b);
		int r2 = max(a, b);
		System.out.printf("max(%d, %d). r1: %d, r2: %d, real: %d, summary: %s%n", a, b, r1, r2, Math.max(a, b),r1 == r2 ? "ok" : "fail");		
	}
	
	public static void main(String[] args) {
		int[] mas = { 1, 5, 10, -20, Integer.MAX_VALUE - 2, -5 };
			
		for (int i = 0; i < mas.length; i += 2) {
			check(mas[i], mas[i + 1]);
			check(mas[i + 1], mas[i]);
			System.out.println();
		}
	}
}
