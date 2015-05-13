package com.math;

public class Task7_4 {

	public static int add(int a, int b) {
		return a + b;
	}
	
	public static int negate(int a) {
		return -a;
	}
	
	public static int substract(int a, int b) {
		return a + negate(b);
	}
	
	public static int abs(int a) {
		return a < 0 ? negate(a) : a;
	}
	
	public static int multiply(int a, int b) {
		if (abs(a) < abs(b)) {
			return multiply(b, a); //faster
		}
		
		int res = 0;
		for (int i = 0; i < b; i ++) {
			res = add(res, a);
		}
		if (b < 0) {
			res = negate(res); 
		}
		return res;
	}
	
	public static int divide(int a, int b) {
		if (b == 0) throw new ArithmeticException("divide by zero");		
		int i = 0;
		int aMod = abs(a), bMod = abs(b);
		for (int mul = 0; (mul = add(mul, bMod)) <= aMod; i ++);
		i = a > 0 && b < 0 || a < 0 && b > 0 ? negate(i) : i;
		return i;
	}
	
	public static void main(String[] args) {
		System.out.println("add: " + add(3, -5));
		System.out.println("sub: " + substract(3, -5));
		System.out.println("mul: " + multiply(3, -5));
		System.out.println("div: " + divide(3, -5));		
	}
}
