package com.dynamicprog;

import java.util.Arrays;


public class Task9_10 {

	static class Result {
		Box[] path;
		int height;
		int len;
		public void update(Box[] path, int len, int height) {
			this.path = Arrays.copyOf(path, len);
			this.height = height;
			this.len = len;
		}					
	}
	
	static class Box implements Comparable<Box> {
		final int width;
		final int height;
		final int depth;
		public Box(int width, int height, int depth) {
			this.width = width;
			this.height = height;
			this.depth = depth;
		}
		@Override
		public boolean equals(Object obj) {
			return this.compareTo((Box) obj) == 0;
		}
		@Override
		public int compareTo(Box b) {
			if (this.width == b.width && this.height == b.height && this.depth == b.depth) {
				return 0;
			}
			return this.width < b.width && this.height < b.height && this.depth < b.depth ? -1 : 1;
		}
		@Override
		public String toString() {		
			return "Box("+width+","+height+","+depth+")";
		}
	} 
			
	public static Result findBestPath(Box[] input) {
		Result result = new Result();		
		Box[] path = new Box[input.length];
		process(path, 0, input, result);
		return result;
	}

	static void process(Box[] path, int pathInd, Box[] input, Result result) {						
		int curHeight = getTotalHeight(path, pathInd);
		if (curHeight > result.height) {
			result.update(path, pathInd, curHeight);			
			System.out.println("Intermediate solution. Height: " + result.height);
			printPath(result);
		}

		int len = input.length - pathInd;
		if (len > 0) {
			//iterate remaining elements
			Box[] remaining = new Box[len];
			int remainingInd = 0;
			for (int i = 0; i < input.length; i ++) {
				boolean contains = false;
				for (int j = 0; j < pathInd; j ++) {
					if (input[i].equals(path[j])) {
						contains = true;
						break;
					}
				}
				if (!contains) {
					remaining[remainingInd ++] = input[i];
				}
			}
			
			for (int i = 0; i < len; i ++) {
				path[pathInd] = remaining[i];
				if (isValid(path, pathInd)) {
					//go down
					process(path, pathInd + 1, input, result);
				}
			}
		}		
	}

	static int getTotalHeight(Box[] path, int len) {
		int h = 0;
		for (int i = 0; i < len; i ++) {
			Box b = path[i];
			h += b.height;
		}
		return h;
	}

	static boolean isValid(Box[] path, int pathInd) {
		return pathInd == 0 || path[pathInd] == path[pathInd - 1] || path[pathInd].compareTo(path[pathInd - 1]) < 0;
	}
	
	static void printPath(Result result) {
		for (int i = result.len - 1; i >= 0 ; i --) {
			System.out.printf("%s %n", result.path[i]);
		}
	}
	
	public static void main(String[] args) {
		Box[] boxes = { new Box(3, 4, 1), new Box(8, 9, 4), new Box(7, 8, 3)};
		Result result = findBestPath(boxes);
		
		System.out.println("\nFinal solution. Height: " + result.height);
		printPath(result);
	}
}
