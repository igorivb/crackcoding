package com.math;

import java.util.HashMap;
import java.util.Map;

import com.Tuple2;

/**
 * Given a two-dimensional graph with points on it, find a line which passes the most number of points.
 */
public class Task7_6 {
	
	//Complexity: O(n^3)
	public static Tuple2<SlopLine, Integer> findMaxLineBruteForce(Point[] points) {
		if (points.length < 2) {
			return null;
		}
		
		SlopLine maxLine = null;
		int maxCount = Integer.MIN_VALUE;		
		for (int i = 0; i < points.length; i ++) {
			for (int j = i + 1; j < points.length; j ++) {
				int count = 2;				
				SlopLine line = SlopLine.create(points[i], points[j]);
				
				for (int z = j + 1; z < points.length; z ++) {
					if (line.containsPoint(points[z])) {
						count ++;
					}
				}
				
				if (count > maxCount) {
					maxCount = count;
					maxLine = line;
				}
			}			
		}		
		return new Tuple2<SlopLine, Integer>(maxLine, maxCount);
	}
		
	/**
	 * @return tuple where Integer means number of times where line occurred, it doesn't equal to number of points
	 * 
	 * Complexity: O(n^2)
	 */
	public static Tuple2<SlopLine, Integer> findMaxLine(Point[] points) {
		if (points.length < 2) {
			return null;
		}
		
		Map<SlopLine, Integer> lines = new HashMap<>();		
		for (int i = 0; i < points.length; i ++) {
			for (int j = i + 1; j < points.length; j ++) {
				SlopLine line = SlopLine.create(points[i], points[j]);
				Integer count = lines.get(line);
				count = count == null ? 1 : count.intValue() + 1;
				lines.put(line, count);
			}			
		}		
		
		SlopLine maxLine = null;
		for (Map.Entry<SlopLine, Integer> entry : lines.entrySet()) {
			if (maxLine == null || entry.getValue() > lines.get(maxLine)) {
				maxLine = entry.getKey();
			}			
		}
		
		return new Tuple2<SlopLine, Integer>(maxLine, lines.get(maxLine));
	}	
	
	public static void main(String[] args) {
		Point[] points = new Point[] {
			new Point(2, 2),
			new Point(3, 3),
			new Point(4, 2),
			new Point(5, 3),
			new Point(5, 5),			
			new Point(6, 2),
			new Point(6, 6),
			new Point(7, 3)
		};
		
		//Tuple2<SlopLine, Integer> res = findMaxLineBruteForce(points);
		Tuple2<SlopLine, Integer> res = findMaxLine(points);
		System.out.printf("Count: %d, Line: %s%n", res._2, res._1);
	}

}
