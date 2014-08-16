package com.sort;

import static com.Utils.swap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class QuickSort2 {

	public static <T> void sort(T[] mas, Comparator<T> cmp) {
		sort(mas, 0, mas.length - 1, cmp);
	}
	
	//[start, end]
	private static <T> void sort(T[] mas, int start, int end, Comparator<T> cmp) {
		if (end - start > 0) {
			int pi = partition(mas, start, end, cmp);
			
			sort(mas, start, pi - 1, cmp);
			sort(mas, pi + 1, end, cmp);
		}
	}
	
	static Random rand = new Random(System.currentTimeMillis());
	private static int pickRandom(int start, int end) {
		return start + rand.nextInt(end - start + 1);
	}
	
	private static <T> int partition(T[] mas, int start, int end, Comparator<T> cmp) {
		int pi = pickRandom(start, end);
		T p = mas[pi];
		swap(mas, start, pi);
		
		int i = start + 1,  e = end;		
		while (i <= end) {
			if (cmp.compare(mas[i], p) <= 0) {
				swap(mas, i, i - 1);
				i ++;
			} else {
				swap(mas, i, e);
				e --;
			}
		}
		return i - 1;
	}
	
	public static void main(String[] args) {
		Integer[] mas = {3, 5, 2};
		Comparator<Integer> cmp = Integer::compare;
		
		QuickSort.sort(mas, cmp);
		
		System.out.println(Arrays.toString(mas));
		
		
	}
}
