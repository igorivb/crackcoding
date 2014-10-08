package com.dynamicprog;

public class Task9_8 {		
	
	public static int findWaysNumber(int num) {
		int[] options = {1, 5, 10, 25};
		return findWaysNumber(num, num, options);	
	}
	
	public static int findWaysNumber(int num, int max, int[] options) {
		int count = 0;
		if (num > 0) {
			for (int i = 0; i < options.length && options[i] <= num && options[i] <= max; i ++) {										
				count += findWaysNumber(num - options[i], options[i], options); //apply option			
			}		
		} else {			
			count = 1; //base
		}					
		return count;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			int result = findWaysNumber(i);
			System.out.printf("%3s. %s%n", i, result);
		}				
	}
}
