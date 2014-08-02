package com.bits;

public class Task5_2 {

	public static String toBinary(double num) {
		
		//[0, 1)
		if (num >= 1 || num < 0) {
			throw new IllegalArgumentException("Incorrect num: " + num);
		}
		
		StringBuilder str = new StringBuilder(".");
		double cmp = 1.0 / 2;
		
		while (num > 0) {
			if (str.length() == 32) {
				String msg = "More than 32 chars";
				System.err.println(msg);
				break;
			}
			
			if (num >= cmp) {
				str.append(1);
				num -= cmp;
			} else {
				str.append(0);
			}
			cmp = cmp / 2;
		}
		
		return str.toString();
	}
	
	public static void main(String[] args) {
		double num = 5.0 / 8;
		String str = toBinary(num);
		System.out.println(str);
	}
}
