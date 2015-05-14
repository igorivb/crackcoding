package com.math;

import static com.Utils.doubleEquals;

public class Point {

	public final double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}		
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Point) {
			Point p = (Point) obj;
			return doubleEquals(this.x, p.x) && doubleEquals(this.y, p.y);
		}
		return false;
	}
	
	@Override
	public String toString() {			
		return String.format("(%.2f, %.2f)", x, y);
	}
}
