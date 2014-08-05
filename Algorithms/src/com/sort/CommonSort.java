package com.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.Utils;

public class CommonSort {

	interface Sorting {
		<T> void sort(T[] mas, Comparator<? super T> cmp);
	}
		
	static class SelectionSort implements Sorting {
		
		public <T> void sort(T[] mas, Comparator<? super T> cmp) {
			for (int i = 0, size = mas.length; i < size; i ++) {
				int min = i;
				for (int j = i + 1; j < size; j ++) {
					if (cmp.compare(mas[min], mas[j]) > 0) {
						min = j;
					}
				}
				Utils.swap(mas, min, i);
			}
		}	
	}	
	
	static class InsertionSort implements Sorting {
		public <T> void sort(T[] mas, Comparator<? super T> cmp) {
			for (int i = 1; i < mas.length; i ++) {
				for (int j = i - 1; j >= 0 && cmp.compare(mas[j], mas[j + 1]) > 0; j --) {
					Utils.swap(mas, j, j + 1);
				}
			}
		}
	}
	
	static class BubbleSort implements Sorting {
		public <T> void sort(T[] mas, Comparator<? super T> cmp) {
			for (int i = mas.length - 1; i > 0; i--) {
				for (int j = 0; j < i; j ++) {
					if (cmp.compare(mas[j], mas[j + 1]) > 0) {
						Utils.swap(mas, j, j + 1);
					}
				}
			}
		}
	}
	
	static Sorting insertionSort = new InsertionSort();
	static Sorting bubbleSort = new BubbleSort();
	static Sorting selectionSort = new SelectionSort();
	
	
	static <T> int binarySearch(T[] mas, T val, Comparator<? super T> cmp) {
		int start = 0, end = mas.length - 1;
		while (start <= end) {
			int i = (start + end) >>> 1;
			int c = cmp.compare(val, mas[i]);
			if (c < 0) {
				end = i -1;
			} else if (c > 0) {
				start = i + 1;
			} else {
				//found
				return i;
			}			
		}
		return -1;
	}
	
	public static void main(String[] args) {		
		
		Integer[] mas = {1, 34, 67, 3, 67, 45, 120, 4};
		Comparator<Integer> cmp = Integer::compare;
		
		mas = sort(mas, cmp);
		
		int val = 120;
		int res = binarySearch(mas, val, cmp);
		System.out.printf("Search for %s. Result: %s\n", val, res);
	}
	
	static <T> T[] sort(T[] mas, Comparator<? super T> cmp) {
		
		List<Sorting> sorts = Arrays.asList(selectionSort, insertionSort, bubbleSort);
		 
		T[] result = null;
		
		for (int i = 0; i < sorts.size(); i ++) {
			
			System.out.printf("Sorting: %s\n", i);
			
			//make a copy because array is modified during sorting
			T[] copy = Arrays.copyOf(mas, mas.length);
			
			Sorting sort = sorts.get(i);			
			sort.sort(copy, cmp);
			
			if (result == null) {
				result = copy;
			} else if (!Arrays.equals(result, copy)) {
				System.err.printf("Not equal results for %s and %s. Arrays:\n%s\n%s", i - 1, i, Arrays.toString(result), Arrays.toString(copy));
				throw new RuntimeException(String.format("Not equal results for %s and %s.", i - 1, i));				
			}
		}
		
		System.out.printf("Result: %s\n", Arrays.toString(result));
		return result;
	} 
}
