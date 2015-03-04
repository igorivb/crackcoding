package com.other;

/*
 * see http://younglinux.info/algorithm/euclidean
 */
public class GreatestCommonDivisor {

	public static int nod1Recursion(int a, int b) {
		int dv; return (dv= a % b) == 0 ? b : nod1Recursion(b, dv);
	}
	
	public static int nod1(int a, int b) {
		while (a % b != 0) {
			int tmp = a % b;
			a = b;
			b = tmp;
		}
		return b;
	}
	
	public static void main(String[] args) {
		System.out.println(nod1Recursion(30, 18));
	}
}
