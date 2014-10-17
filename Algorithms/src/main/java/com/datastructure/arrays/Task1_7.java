package com.datastructure.arrays;

import static com.Utils.printMatrixSimple;

import java.util.BitSet;

/**
 * Write an algorithm such that if an element in an MxN matrix is 0, its entire row and
 * column are set to 0.
 */
public class Task1_7 {

	public static void transform(int[][] mas) {
		BitSet rowSet = new BitSet(mas.length);
		BitSet colSet = new BitSet(mas[0].length);
		
		//mark rows and columns which have elem with 0
		for (int i = 0; i < mas.length; i ++) {
			for (int j = 0; j < mas[i].length; j ++) {
				if (mas[i][j] == 0) {
					rowSet.set(i);
					colSet.set(j);
				}
			}
		}
		
		for (int i = 0; i < mas.length; i ++) {
			for (int j = 0; j < mas[i].length; j ++) {
				if (rowSet.get(i) || colSet.get(j)) {
					mas[i][j] = 0;
				}
			}
		}
		
//		//iterate rows
//		for (int i = 0; i < mas.length; i ++) {
//			if (rowSet.get(i)) {
//				for (int j = 0; j < mas[i].length; j ++) {
//					mas[i][j] = 0;
//				}
//			}
//		}
//		
//		//iterate cols
//		for (int j = 0; j < mas[0].length; j ++) {
//			if (colSet.get(j)) {
//				for (int i = 0; i < mas.length; i ++) {
//					mas[i][j] = 0;
//				}
//			}
//		}
	}		
	
	public static void main(String[] args) {		
		int[][] mas = new int[][] {
			{1,1,1,1},
			{1,0,1,1},
			{0,1,1,0},
		};
		
		//int[][] mas = AssortedMethods.randomMatrix(10, 15, 0, 99);		
		
		System.out.println("Initial");
		printMatrixSimple(mas, 1);
		
		transform(mas);
		
		System.out.println("\nTransformed");
		printMatrixSimple(mas, 1);
	}
}
