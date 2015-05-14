package com.math;

public class Line {

	public final Point p1, p2;

	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}	
	
	@Override
	public String toString() {		
		return String.format("line[%s, %s]", p1, p2);
	}
}
