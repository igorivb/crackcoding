package com.sort;

import java.util.Arrays;
import java.util.Comparator;
import static com.Utils.swap;

public class SelectionSort {

	public static <T> void sort(T[] mas, Comparator<T> cmp) {		
		
		int n = mas.length;
		for (int i = 0; i < n; i ++) {
			int min = i;
			for (int j = i + 1; j < n; j ++) 
				if (cmp.compare(mas[j], mas[min]) < 0) min = j;	
			
			swap(mas, i, min);
		}
	}
	
	public static <T> void sortDesc(T[] mas, Comparator<T> cmp) {
		int n = mas.length;
		for (int i = 0; i < n; i ++) {
			int max = i;
			for (int j = i + 1; j < n; j ++) {
				if (cmp.compare(mas[max], mas[j]) < 0) {
					max = j;
				}
			}
			swap(mas, i, max);
		}
	}
	
	public static void main(String[] args) {
		
		String str = "S E L E C T I O N S O R T";
		String[] input = str.split(" ");
		//Character[] input = {'S', 'E', 'L', 'E', 'C', 'T', 'I', 'O', 'N', 'S', 'O', 'R', 'T'};
		sortDesc(input, String.CASE_INSENSITIVE_ORDER);
		
		
		System.out.println(Arrays.toString(input));
	}
}
