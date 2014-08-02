package com.other;

import java.math.BigInteger;

public class Logarithm {

	//TODO: implement through iteration
	public static BigInteger powerWithBigInteger(int a, int n) {
		if (n == 1) {
			return BigInteger.valueOf(a);
		} else if (n == 2) {
			return BigInteger.valueOf(a * a);
		} else {
			BigInteger res = powerWithBigInteger(a, n / 2);
			res = res.multiply(res);
			if (n % 2 != 0) {
				res = res.multiply(BigInteger.valueOf(a));
			}
			return res;
		}		
	}
	
	public static long power(int a, int n) {
		if (n == 0) {
			return 1;
		} else {
			long res = power(a, n / 2);
			res = res * res;
			if (n % 2 != 0) {
				res = res * a;
			}
			return res;
		}		
	}
	
	public static void main(String[] args) {
		
		for (int i = 2; i < 100; i ++) {
			int a = 2, n = i;
			
			BigInteger n1 = powerWithBigInteger(a, n); 
			
			System.out.println(n + ". " + n1);
			
//			double n2 = Math.pow(a, n);
//			BigInteger expected = BigInteger.valueOf((long) n2);
			
			//double n2 = Math.pow(a, n);
			//long expected = (long) n2;
			
			//System.out.println(n + ". " + n1 + " --- " + expected);
			
			//if (n1 != expected) {
			//	throw new RuntimeException("not equal for " + n);
			//}
		}
		
	}
}
