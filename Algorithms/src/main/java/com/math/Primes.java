package com.math;

public class Primes {

	public static boolean isPrimeNaive(int n) {
		if (n < 2) {
			return false;
		}
		for (int i = 2; i < n; i ++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isPrimeNaiveBetter(int n) {
		if (n < 2) {
			return false;
		}
		for (int i = 2, max = (int) Math.sqrt(n); i <= max; i ++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * The Sieve of Eratosthenes.
	 */
	public static boolean[] generatePrimes(int max) {
		boolean mas[] = new boolean[max + 1];
		mas[0] = mas[1] = true;
		for (int i = 2; i <= Math.sqrt(max);) {			
			for (int j = i * 2; j < mas.length; j += i) { //cross remaining multiple of prime
				mas[j] = true;
			}   			
			for (i = i + 1; i < mas.length && mas[i] == true; i ++); //find next prime							
		}
		return mas;
	}
	
	static void showPrimes(int max) {
		boolean[] primes = generatePrimes(max);
		int j = 0;
		for (int i = 0; i < primes.length; i ++) { 
			if (!primes[i]) {
				j ++;
				System.out.print(i + " ");
				if (j % 100 == 0) {
					System.out.println();
				}
			}
		}
		System.out.println("\ncount: " + j);
	}
	
	/**
	 * Greatest common divisor.
	 */
	public static int gcd(int n1, int n2) {
		int div;
		return (div = n1 % n2) == 0 ? n2 : gcd(n2, div);
	}
	
	/**
	 * Least common multiple.
	 */
	public static int lcm(int n1, int n2) {
		return (n1 * n2) / gcd(n1, n2);
	}
			
	public static void main(String[] args) {
		//showPrimes(100);
		
		//System.out.println(gcd(24, 18));
		
		System.out.println(lcm(4, 10));
		
	}
}
