package com.math;

import static com.Utils.doubleEquals;

public class SlopLine {
	
	public final double a; //slop
	public final double b; //intercept
	
	//store x value in slop field
	public final boolean isVertical;
	
	public SlopLine(double a, double b) {
		this.a = a;
		this.b = b;
		isVertical = false;
	}
	
	//vertical
	public SlopLine(double xVal) {
		a = xVal;
		b = Double.NaN; //not defined
		isVertical = true;
	}

	public static SlopLine create(Point m1, Point m2) {
		if (doubleEquals(m1.x, m2.x)) { //vertical: special case
			return new SlopLine(m1.x);
		} else {
			double a = (m2.y - m1.y) / (m2.x - m1.x);
			double b = m1.y - a * m1.x;
			return new SlopLine(a, b);
		}
	}	
	
	public boolean isHorizontal() {
		return !isVertical && doubleEquals(a, 0d);
	}
	
	@Override
	public String toString() {
		return String.format("Vertical: %b, horizontal: %b, a: %.2f, b: %.2f", isVertical, isHorizontal(), a, b);
	}

	public boolean containsPoint(Point p) {
		return isVertical ? p.x == this.a : (p.y == this.a * p.x + this.b);		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof SlopLine) {
			SlopLine line = (SlopLine) obj;			
			if (this.isVertical) {
				return doubleEquals(this.a, line.a);
			} else {
				return doubleEquals(this.a, line.a) && doubleEquals(this.b, line.b); 
			}
		}		
		return false;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;						
		result = prime * result + Double.hashCode(this.a);
		result = prime * result + (isVertical ? 1231 : 1237);
		if (!isVertical) {
			result = prime * result + Double.hashCode(this.b);	
		}
		return result;
	}
}
