package com.other;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Calculations {

	public static void main(String[] args) {
	
//		BigDecimal bd = new BigDecimal(0.5);
//		System.out.println(bd.doubleValue());

		/*
		 * 0.3
		 * 0.8
		 * 0.9
		 */
//		double d = 0;
//		for (int i = 0; i < 10; i ++) {
//			System.out.println( d+=0.1 );
//		}
//		
//		double a = 0.7 + 0.1;
//		System.out.println("a: " + a);
//		
//		
//		BigInteger[] i = BigInteger.ONE.divideAndRemainder(BigInteger.valueOf(5));
//		
//		System.out.println(i[0] + "   " + i[1]);

		
//		 BigDecimal d = new BigDecimal("1115.32");
//	      BigDecimal taxRate = new BigDecimal("0.0049");
//	      BigDecimal d2 = d.multiply(taxRate);
//	      System.out.println("Unformatted: " + d2.toString());
//	      
//	      d2 = d2.setScale(2, BigDecimal.ROUND_HALF_UP);	      
//	      System.out.println("Unformatted: " + d2.toString());
//	      
	      
	      BigDecimal val = new BigDecimal("12.235");
	      
	      
	      val = val.movePointRight(1);
	      	      
	      System.out.println(val);

	      
		 
	      
	}

}
