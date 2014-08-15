package com.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static com.Utils.swap;

public class QuickSort {

	public static <T> void sort(T[] mas, Comparator<T> cmp) {
		sort(mas, 0, mas.length - 1, cmp);
	}
	
	static <T> void sort(T[] mas, int start, int end, Comparator<T> cmp) {
		if (end - start > 0) {
			int pi = process(mas, start, end, cmp);
			
			if (pi != start) {
				sort(mas, start, pi - 1, cmp);	
			}
			
			if (pi != end) {
				sort(mas, pi + 1, end, cmp);	
			}			
		}
	}
		
	static Random random = new Random(System.currentTimeMillis());
	//[start, end]	
	static int pickRandom(int start, int end) {		
		int res = start + random.nextInt(end - start + 1);
		return res;
	}
	
	static <T> int process(T[] mas, int start, int end, Comparator<T> cmp) {		
		int pi = pickRandom(start, end);
		T p = mas[pi];		
		swap(mas, pi, start);
		
		int i = start + 1, e = end;
		while (i <= e) {
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
}
