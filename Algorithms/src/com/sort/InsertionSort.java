package com.sort;

import java.util.Arrays;
import java.util.Comparator;

import static com.Utils.swap;

public class InsertionSort {

	public static <T> void sort(T[] mas, Comparator<T> cmp) {
		for (int i = 0, n = mas.length; i <n; i ++) {
			for (int j = i - 1; j >= 0 && cmp.compare(mas[j], mas[j + 1]) > 0; j --) {				
				swap(mas, j, j + 1);				
			}
		}
	}
	
	public static <T> void sortDesc(T[] mas, Comparator<T> cmp) {
		for (int i = 0, n = mas.length; i <n; i ++) {
			for (int j = i - 1; j >= 0 && cmp.compare(mas[j], mas[j + 1]) < 0; j --) {				
				swap(mas, j, j + 1);				
			}
		}
	}
	
	public static void main(String[] args) {
		
		String str = "S E L E C T I O N S O R T";
		String[] input = str.split(" ");
		//Character[] input = {'S', 'E', 'L', 'E', 'C', 'T', 'I', 'O', 'N', 'S', 'O', 'R', 'T'};
		sort(input, String.CASE_INSENSITIVE_ORDER);
		
		
		System.out.println(Arrays.toString(input));
	}
}
