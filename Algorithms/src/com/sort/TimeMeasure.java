package com.sort;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Utility class to show how much time was spent for operation.
 */
public class TimeMeasure {
	long start;
	long end;
	String msg;
	String indent = "";
	
	public TimeMeasure(String msg) {
		this(msg, 0);
	}
	
	public TimeMeasure(String msg, int ind) {
		this.msg = msg;
		
		if (ind > 0) {
			for (int i = 0; i < ind; i ++) {
				indent = "  " + indent;
			}
		}
		
		start();
		
		System.out.println(indent + "Start - " + msg);
	}				
	
	public void start() {
		start = System.currentTimeMillis();
	}
	
	public void end() {
		end = System.currentTimeMillis();
	}		
	
	public String diff() {
		double val = (end - start) / 1000.0;
		BigDecimal bd = new BigDecimal(val, new MathContext(4, RoundingMode.CEILING));			
		return bd.toString();
	}
	
	public void endAndPrint() {
		end();
		System.out.println(indent + "End - " + msg + ": " + diff());
	}
}
