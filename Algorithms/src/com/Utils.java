package com;

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
}
