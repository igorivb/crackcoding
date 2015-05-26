package com.moderate;

/**
 * Given any integer, print an English phrase that describes the integer (e.g., "One
 * Thousand, Two Hundred Thirty Four").
 */
public class Task17_7 {
	
	static final String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
	static final String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
	static final String[] decades = {"twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};
	static final String[] divTable = { "", "thousand", "million", "billion" };
	
	static String showHundreds(int[] mas, int div) {
		StringBuilder res = new StringBuilder();
		
		int n = mas[div * 3 + 2];
		if (n != 0) { //hundreds
			res.append(digits[n]).append(" hundred");
			if (n != 1) { //plural
				res.append("s");
			}
		}
		
		boolean hasTeens = false;
		n = mas[div * 3 + 1];		
		if (n != 0) {
			if (res.length() > 0) {
				res.append(" ");
			}
			if (n != 1) { //decades but not teens
				res.append(decades[n - 2]);								
			} else { //teens
				res.append(teens[mas[3 * div]]);
				hasTeens = true;
			}
		}
		
		n = mas[div * 3];
		if (n != 0 && !hasTeens) {
			if (res.length() > 0) {
				res.append(" ");
			}
			res.append(digits[n]);
		}
		
		return res.toString();
	}
	
	static void process(int[] mas, int div, StringBuilder res) {
		if (div >= 0) {			
			if (mas[3 * div] != 0 || mas[3 * div + 1] != 0 || mas[3 * div + 2] != 0) { //check that div has some value
				if (res.length() > 0) {
					res.append(" ");
				}
				
				String strHund = showHundreds(mas, div); 
				res.append(strHund);
				if (divTable[div].length() > 0) {
					res.append(" ").append(divTable[div]);
					if (mas[div * 3 + 2] != 0 || mas[div * 3 + 1] != 0 || mas[div * 3] != 1) {
						res.append("s");  //plural
					}
				}	
			}						
					
			process(mas, div - 1, res);		
		}				
	}		

	public static String numToString(int num) {
		if (num < 0) {
			return "Negative " + numToString(num * -1);
		}
		//split num
		int[] mas = new int[12]; //billion
		int size = 1;		
		for (int t = num; (t = t / 10) > 0; size ++);		
		for (int i = 0; i < size; i ++, num /= 10) {
			mas[i] = num % 10;
		}				
		
		StringBuilder res = new StringBuilder();
		int div = (size - 1) / 3;
		process(mas, div, res);
		return res.toString();
	}	
	
	public static void main(String[] args) {
		int num = 1577601498;
		System.out.println(num + " " + numToString(num));
	}
}
