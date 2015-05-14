package com.math;

import static com.Utils.doubleEquals;
import static com.Utils.doubleCompare;

/**
 * Given two squares on a two-dimensional plane, find a line that would cut these two
 * squares in half. Assume that the top and the bottom sides of the square run parallel
 * to the x-axis.
 */
public class Task7_5 {

	//point
	static class Point {		
		final double x, y;
		
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
	
	//slop and y-intercept
	static class SlopLine {
		final double a; //slop
		final double b; //intercept
		
		//store x value in slop field
		final boolean isVertical;
		
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
	}
	
	//2 points
	static class Line {
		final Point p1, p2;

		public Line(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}	
		
		@Override
		public String toString() {		
			return String.format("line[%s, %s]", p1, p2);
		}
	}
	
	enum EdgeType {
		LEFT, RIGHT, TOP, BOTTOM
	}
	
	static class Square {
		final Point top, bottom;							
		
		public Square(Point top, Point bottom) {		
			this.top = top;
			this.bottom = bottom;
		}

		public Point getMiddle() {			
			return new Point((top.x + bottom.x) / 2, (top.y + bottom.y) / 2);
		}
		
		public Point findEdgeInterselection(SlopLine line, EdgeType edgeType) {			
			if (edgeType == EdgeType.LEFT || edgeType == EdgeType.RIGHT) {
				double x = edgeType == EdgeType.LEFT ? top.x : bottom.x;
				if (!line.isVertical) { //not parallel					
					double y = line.a * x + line.b;
					if (doubleCompare(y, top.y) <= 0 && doubleCompare(y, bottom.y) >= 0) { // in range ?
						return new Point(x, y);
					}
				}
			} else if (edgeType == EdgeType.BOTTOM || edgeType == EdgeType.TOP) {
				if (!line.isHorizontal()) { //not parallel
					double y = edgeType == EdgeType.BOTTOM ? bottom.y : top.y;
					double x = line.isVertical ? line.a : (y - line.b) / line.a;
					if (doubleCompare(x, top.x) >= 0 && doubleCompare(x, bottom.x) <= 0) { // in range ?
						return new Point(x, y);
					}
				} 
			} 			
			return null;
		}
		
		@Override
		public String toString() {			
			return String.format("square[top: %s, bottom: %s]", top, bottom);
		}
	}		
	
	public static Line findMiddleLine(Square s1, Square s2) {
		Point m1 = s1.getMiddle();
		Point m2 = s2.getMiddle();
		
		System.out.printf("Mid point 1: %s%n", m1);
		System.out.printf("Mid point 2: %s%n", m2);
		
		if (!m1.equals(m2)) {			
			SlopLine slopLine = SlopLine.create(m1, m2);
			System.out.printf("Slop line: %s%n", slopLine);
			
			//find edge point in square 1
			Point edgePoint1 = s1.findEdgeInterselection(slopLine, EdgeType.LEFT);
			if (edgePoint1 == null) {
				edgePoint1 = s1.findEdgeInterselection(slopLine, EdgeType.BOTTOM);
			}
			
			//find edge point in square 2
			Point edgePoint2 = s2.findEdgeInterselection(slopLine, EdgeType.RIGHT);
			if (edgePoint2 == null) {
				edgePoint2 = s2.findEdgeInterselection(slopLine, EdgeType.TOP);
			}
			
			return new Line(edgePoint1, edgePoint2); 
		} else { //squares have the same middle point
			return new Line(m1, m2);
		}				
	}	
	
	public static void main(String[] args) {		
//		Square s1 = new Square(new Point(2, 3),new Point(4, 1)); 
//		Square s2 = new Square(new Point(2, 7),new Point(4, 5));
		
//		Square s1 = new Square(new Point(2, 3),new Point(4, 1)); 
//		Square s2 = new Square(new Point(6, 3),new Point(8, 1));
		
//		Square s1 = new Square(new Point(2, 3),new Point(4, 1)); 
//		Square s2 = new Square(new Point(5, 6),new Point(7, 4));
		
		Square s1 = new Square(new Point(2, 3),new Point(4, 1)); 
		Square s2 = new Square(new Point(3, -2),new Point(5, -4));
		
		Line res = findMiddleLine(s1, s2);
		System.out.println("Middle line: " + res);				
	}
}
