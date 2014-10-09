package com.dynamicprog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Write an algorithm to print all ways of arranging eight queens on an 8x8 chess
 * board so that none of them share the same row, column or diagonal. In this case,
 * "diagonal" means all diagonals, not just the two that bisect the board
 */
public class Task9_9 {

	static final int SIZE = 8;
	
	static class Point {
		final int x;  final int y;
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	
	static class Result {
		List<Point[]> solutions = new ArrayList<>();
		public int getCount() {
			return this.solutions.size();
		}
	}
	
	public static Result countSolutions() {
		Point[] positions = new Point[SIZE]; //solutions
		for (int i = 0; i < positions.length; i ++) {
			positions[i] = new Point(-1, -1);
		}
		
		int[][] mas = new int[SIZE][SIZE]; //matrix
		
		int pos = 0; //index in positions to work with
		Point n = new Point(0, 0); //index in mas
		
		Result result = new Result();
		process(pos, n, positions, mas, result);
		return result;
	}

	static boolean process(int pos, Point n, Point[] positions, int[][] mas, Result result) {
		if (pos == SIZE) {
			//add to solution			
			result.solutions.add(Arrays.copyOf(positions, SIZE));
			return true;
		}
		
		if (n.x == SIZE || n.y == SIZE) {
			//no solution: stop processing this path as there're no more steps
			return false;
		}
		
		for (int i = n.x; i < SIZE; i ++) {
			for (int j = n.y; j < SIZE; j ++) {
				positions[pos] = new Point(i, j);
				if (isValid(positions, pos)) {
					//recurse down
					if (!process(pos + 1, new Point(i + 1, 0) /*go to next row*/, positions, mas, result)) {
						break;
					}
				}
			}
		}
		
		return true;
	}

	//check only last position
	static boolean isValid(Point[] positions, int pos) {
		for (int i = 0; i < pos; i ++) {
			if (!isValid(positions[i], positions[pos])) {
				return false;
			}
		}
		return true;
	}

	static boolean isValid(Point p1, Point p2) {
		//rows and columns
		if (p1.x == p2.x || p1.y == p2.y) {
			return false;
		}
		
		//diagonals
		if (-p2.x - p2.y + p1.x + p1.y == 0 || 
			-p2.x + p2.y + p1.x - p1.y == 0) {
			return false;
		}
		
		return true;
	}	

	static boolean validateSolution(Point[] sol) {
		//validate
		for (int i = 0; i < sol.length; i ++) {
			for (int j = i + 1; j < sol.length; j ++) {
				if (!isValid(sol[i], sol[j])) {
					System.out.printf("Invalid %s. pos1: %s and pos2: %s%n", Arrays.toString(sol), i, j);
					return false;
				}		
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Result result = countSolutions();
		for (int i = 0; i < result.solutions.size(); i ++) {
			Point[] sol = result.solutions.get(i);
			String str = Arrays.toString(sol);  
			System.out.printf("%2s. %s%n", (i + 1), str);
			
			//validate
			validateSolution(sol);			
		}
	}
}
