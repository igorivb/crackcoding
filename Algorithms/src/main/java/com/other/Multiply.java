package com.other;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Multiply {

//	static BigInteger multiply(int x, int y) {		
//		if (y == 1) {
//			return BigInteger.valueOf(x);
//		}		
//		if (y == 0) {
//			return BigInteger.valueOf(1);
//		}
//		
//		BigInteger res = BigInteger.valueOf(x).multiply(BigInteger.valueOf(x)).multiply(multiply(x, y / 2));		
//		if (y % 2 != 0) {
//			res = res.multiply(BigInteger.valueOf(x));
//		} 		
//		return res;
//	}
	
	static int multiply(int base, int exp) {
		if (exp == 0) {
			return 1;
		}
		if (exp == 1) {
			return base;
		}
		
		int res = multiply(base * base, exp / 2);
		if (exp % 2 != 0) {
			res *= base;
		}
		return res;
	}
	
	static int multiply2(int x, int y) {		
		int res = x;
		for (int i = 1; i < y; i ++) {
			res *= x;
		}		
		return res;
	}
	
	
	//pow(x, y) = pow(x * x, y / 2)	
	public static BigInteger pow(BigInteger x, int y) {
		if (y == 0) {
			return BigInteger.valueOf(1);
		}
		if (y == 1) {
			return x;
		}
		BigInteger result = pow(x.multiply(x), y / 2);
		if (y % 2 != 0) {
			result = result.multiply(x);
		}
		return result;
	}
	

	//see http://stackoverflow.com/a/101613
	public static BigDecimal ipow(int base, int exp) {
		
		BigInteger result = BigInteger.ONE;
		BigInteger myBase = BigInteger.valueOf(base);
		
		int myExp = exp < 0 ? -exp : exp;;
		
		while (myExp > 0) {
									
			if (myExp % 2 != 0) {
				result = result.multiply(myBase);
			}
			
			myBase = myBase.multiply(myBase);			
			myExp = myExp / 2;
		}
		
		//transform to BigDecimal
		BigDecimal res = new BigDecimal(result);		
		if (exp < 0) {
			res = BigDecimal.ONE.divide(res);			
		}
		
		return res;
	}
	
	public static BigDecimal ipowWithBigDecimal(int base, int exp) {
		
		BigDecimal result = BigDecimal.ONE;
		BigDecimal myBase = new BigDecimal(base);
		
		int myExp = exp < 0 ? -exp : exp;
		
		while (myExp > 0) {
									
			if (myExp % 2 != 0) {
				result = result.multiply(myBase);
			}
			
			myBase = myBase.multiply(myBase);			
			myExp = myExp / 2;
		}
			
		if (exp < 0) {
			result = BigDecimal.ONE.divide(result);			
		}
		
		return result;
	}
	
	
	public static BigDecimal powAgain(int num, int exp) {
		
		BigDecimal result = BigDecimal.ONE;
		BigDecimal base = new BigDecimal(num);
		
		int myExp = exp < 0 ? -exp : exp;
		
		while (myExp > 0) {
			
			//if (myExp % 2 != 0) {
			if ((myExp & 1) == 1) {
				result = result.multiply(base);
			}
			
			base = base.multiply(base);
			
			//myExp = myExp / 2;
			myExp >>= 1;
		}
		
		if (exp < 0) {
			result = BigDecimal.ONE.divide(result);
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		for (int i = -4; i <= 10; i ++) {
			System.out.println(i + ": "  + powAgain(-2, i));			
		}			
	}

}
