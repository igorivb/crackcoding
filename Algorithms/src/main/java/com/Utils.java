package com;

import java.util.Comparator;


public final class Utils {

	private Utils() { }
	
	public static <T> void swap(T[] mas, int i, int j) {
		T tmp = mas[i];
		mas[i] = mas[j];
		mas[j] = tmp;
	}
	
	public static void printMatrix(int[][] mas) {
		for (int i = 0; i < mas.length; i ++) {
			System.out.print("{ ");
			for (int j = 0; j < mas[i].length; j ++) {
				System.out.print(mas[i][j]);
				if (j < mas[i].length - 1) {
					System.out.print(", ");
				}
			}
			System.out.println(" }");
		}
	}
	
	public static void printMatrixSimple(int[][] mas) {
		printMatrixSimple(mas, 2);
	}
	
	public static void printMatrixSimple(int[][] mas, int format) {
		String strFormat = "%"+format+"s| ";
		for (int i = 0; i < mas.length; i ++) {
			for (int j = 0; j < mas[i].length; j ++) {
				System.out.printf(strFormat, mas[i][j]);
			}
			System.out.println();
		}
	}
	
	public static String toStringArrayWithIndexes(int[] mas) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < mas.length; i ++) {
			str.append("[" + i + "] = " + mas[i]);
			if (i < mas.length - 1) {
				str.append(", ");
			}
		}
		return str.toString();
	}
	
	public static String byteToBinaryString(byte b) {
		String str = Integer.toBinaryString((b & 0xFF));
		str = String.format("%8s", str).replaceAll(" ", "0");
		return str;
	}		
	
	public static String ind(int indent) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < indent; i ++) {
			str.append("  ");
		}
		return str.toString();
	}
	
	
	public static <T> boolean isSortedArray(T[] mas, Comparator<T> cmp) {		
		for (int i = 0; i < mas.length - 2; i ++) {
			if (cmp.compare(mas[i], mas[i + 1]) > 0) {
				return false;
			}
		}		
		return true;
	}
	
	final static double doubleEpsilon = 1e-5;
	
	/**
	 * Note: it doesn't take into account special floating values, e.g. NaN, infinities
	 */
	public static boolean doubleEquals(double d1, double d2) {
		return doubleCompare(d1, d2) == 0;
	}

	/**
	 * Note: it doesn't take into account special floating values, e.g. NaN, infinities
	 */
	public static int doubleCompare(double d1, double d2) {
		return Math.abs(d1 - d2) < doubleEpsilon ? 0 : (d1 < d2) ? -1 : 1;
	}
}
