package com.datastructure.arrays;

import java.util.BitSet;
import static com.Utils.printMatrixSimple;

/**
 * Given an image represented by an NxN matrix, where each pixel in the image is 4
 * bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
 */
public class Task1_6 {

	public static void rotate(int[][] mas) {
		BitSet state = new BitSet(mas.length << 1);
		for (int i = 0; i < mas.length; i ++) { //x
			for (int j = 0; j < mas.length; j ++) { //y
				process(mas, i, j, mas[i][j], state);
			}			
		}
	}

	static void process(int[][] mas, int i, int j, int val, BitSet state) {
		int x = i, y = j, curVal = val;
		
		//iterate until find already processed element	
		while (state.get(x * mas.length + y) == false) {
			//calculate next element
			int nextX = y;
			int nextY = mas.length - x - 1;
			int nextVal= mas[nextX][nextY];
					
			state.set(x * mas.length + y); //mark as processed			
			mas[nextX][nextY] = curVal;
			
			x = nextX; y = nextY; curVal = nextVal;					
		}
	} 
	
	public static void main(String[] args) {
//		int[][] mas = new int[][] {
//			{1,0,0},
//			{0,0,0},
//			{1,1,1},
//		};
//		
//		int[][] mas = new int[][] {
//			{0,1,1},
//			{0,1,0},   
//			{1,0,1},
//		};
		
		int[][] mas = AssortedMethods.randomMatrix(5, 5, 0, 9);
		
		System.out.println("Initial");
		printMatrixSimple(mas);
		
		System.out.println("\nRotate");
		rotate(mas);
		printMatrixSimple(mas);
	}
}
