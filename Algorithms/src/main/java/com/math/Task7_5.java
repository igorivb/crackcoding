package com.math;

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
				return Double.compare(this.x, p.x) == 0 && Double.compare(this.y, p.y) == 0; //TODO: check that is ok to compare doubles in this way
			}
			return false;
		}
		
		@Override
		public String toString() {
			// TODO 
			return super.toString();
		}
	}
	
	//slop and y-intercept
	static class SlopLine {
		final double slop;
		final double intercept;
		
		public SlopLine(double slop, double intercept) {
			this.slop = slop;
			this.intercept = intercept;
		}

		public static SlopLine create(Point m1, Point m2) {
			//TODO
			return null;
		}				
	}
	
	//2 points
	static class Line {
		final Point p1, p2;

		public Line(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}		
	}
	
	static class Square {
		final Point top, bottom;
						
		public Square(Point top, Point bottom) {		
			this.top = top;
			this.bottom = bottom;
		}

		Point getMiddle() {
			//TODO
			return null;
		}
		
		@Override
		public String toString() {
			// TODO
			return super.toString();
		}
	}
	
	static Point findLeftEdgeInterselection(Square s, SlopLine slopLine) {
		// TODO
		return null;
	}
	
	static Point findBottomEdgeInterselection(Square s, SlopLine slopLine) {
		// TODO
		return null;
	}
	
	public static Line findMiddleLine(Square s1, Square s2) {
		Point m1 = s1.getMiddle();
		Point m2 = s2.getMiddle();
		
		if (!m1.equals(m2)) {			
			SlopLine slopLine = SlopLine.create(m1, m2);
			
			//find edge point in square 1
			Point edgePoint1 = findLeftEdgeInterselection(s1, slopLine);
			if (edgePoint1 == null) {
				edgePoint1 = findBottomEdgeInterselection(s1, slopLine);
			}	
			
			//find edge point in square 2
			Point edgePoint2 = findLeftEdgeInterselection(s2, slopLine);
			if (edgePoint2 == null) {
				edgePoint2 = findBottomEdgeInterselection(s2, slopLine);
			}
			
			return new Line(edgePoint1, edgePoint2); 
		} else {
			return new Line(m1, m2);
		}				
	}

	public static void main(String[] args) {
		//TODO: init
		Square s1 = new Square(new Point(0, 0),new Point(0, 0)); 
		Square s2 = new Square(new Point(0, 0),new Point(0, 0));
		
		Line res = findMiddleLine(s1, s2);
		System.out.println("Middle line: " + res);
	}
}
