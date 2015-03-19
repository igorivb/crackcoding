package com.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import com.Utils;

public class QuickSort3 {

	public static <T> T[] sort(T[] mas, Comparator<T> cmp) {
		sort(mas, 0, mas.length, cmp);
		return mas;
	}
	
	//[s, e]
	static <T> void sort(T[] mas, int s, int e, Comparator<T> cmp) {
		if (e - s > 1) {			
			int pi = rotateElements(mas, s, e, cmp);
			sort(mas, s, pi, cmp);
			sort(mas, pi + 1, e, cmp);
		}
	}
	
	static <T> int rotateElements(T[] mas, int s, int e, Comparator<T> cmp) {                      
		int pi = getRandom(s, e);
		System.out.println("random: " + pi);
		T p = mas[pi];
		
		Utils.swap(mas, s, pi);
		int i = s + 1;
		while (i < e) {
			if (cmp.compare(mas[i], p) <= 0) {
				Utils.swap(mas, i, i - 1);
				i ++;
			} else {
				Utils.swap(mas, i, --e);
			}
		}	
		
		return i - 1; 
	}

	static Random rand = new Random(System.currentTimeMillis());
	static int getRandom(int s, int e) {
		return s + rand.nextInt(e - s);
		//return 2;
	}
	
	public static void main(String[] args) {
		//Integer[] mas = {3, 5, 2};
		Integer[] mas = {1, 34, 67, 3, -5, 67, 45, 0, 120, 4};
		//Integer[] mas = {1, 3, -5};
		
		Comparator<Integer> cmp = Integer::compare;
		
		for (int i = 0; i < 100; i ++) {
			sort(mas, cmp);
			
			System.out.println(Arrays.toString(mas));
			
			//check
			for (int j = 0; j < mas.length - 1; j ++) {
				if (cmp.compare(mas[j], mas[j+ 1]) > 0) {
					System.err.println("error");
				}
			}
		}										
	}
}
