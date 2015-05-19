package com.moderate;

/**
 * Write an algorithm which computes the number of trailing zeros in n factorial.		
 */
public class Task17_3 {

	public static int countFactZeros(int num) {
		if (num < 0) {
			throw new IllegalArgumentException("Factorial on negative is not allowed: " + num);
		}
		int count = 0;
		for (int i = 1; i <= num; i ++) {
			for (int n = i; n % 5 == 0 && (n = n / 5) > 0; count ++);			
		}
		return count;
	}
	
	public static int countFactZerosOptimized(int num) {
		if (num < 0) {
			throw new IllegalArgumentException("Factorial on negative is not allowed: " + num);
		}
		int count = 0, tmp;
		for (int div = 5; (tmp = num / div) > 0; div *= 5, count += tmp);
		return count;
	}
	
	public static int countFactZerosFromBook(int num) {
		int count = 0;
		if (num < 0) {
			System.out.println("Factorial is not defined for negative numbers");
			return 0;
		}
		
		for (int i = 5; num / i > 0; i *= 5) {		
			count += num / i;						
		}
		return count;
	}
	
	public static void main(String[] args) {		
		for (int i = 0; i < 10000; i++) {
			int r1 = countFactZerosFromBook(i);
			int r2 = countFactZeros(i);			
			int r3 = countFactZerosOptimized(i);		
			System.out.printf("%3d! %10d| %10d | %10d%n", i, r1, r2, r3);
			if (r1 != r2 || r1 != r3) {
				System.err.println("Error for " + i);
				break;
			}
		}
	}
}
