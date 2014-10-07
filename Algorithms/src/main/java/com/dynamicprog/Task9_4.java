package com.dynamicprog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task9_4 {
	
	public static List<List<Integer>>[] showSubsets(int[] mas) {
		int len = mas.length;
		List<List<Integer>>[] allSubsets = new ArrayList[len + 1];			
		for (int i = 0; i <= len; i ++) {
			allSubsets[i] = new ArrayList<List<Integer>>();
		}
		allSubsets[len].add(new ArrayList<>());
				
		for (int i = 0; i < len; i ++) {
			List<List<Integer>> current = allSubsets[i];			
			current.add(Arrays.asList(mas[i]));
			
			for (int j = 1; j <= i; j ++) {
				List<List<Integer>> sub = allSubsets[i - j];
				for (List<Integer> list: sub) {
					List<Integer> el = new ArrayList<>(list);
					el.add(mas[i]);
					current.add(el);
				}
			}						
		}
		return allSubsets;
	}
	
	public static void combinatoric(int[] mas) {
		int num = 1 << mas.length; //exponent !!!
		for (int i = 0; i < num; i ++) {			
			List<Integer> list = new ArrayList<>();			
			for (int tmp = i, c = 0; tmp != 0; c ++, tmp >>>= 1) {
				if ((tmp & 1) == 1) {
					list.add(mas[c]);
				}								
			}			
			System.out.printf("%2s. %s%n", (i + 1), list);
		}
	}
	
	public static void main(String[] args) {
		int[] mas = new int[] {0, 1, 2, 3, 4, 5};
		int counter = 0;
		List<List<Integer>>[] allSubsets = showSubsets(mas);		
		for (int i = 0; i < allSubsets.length; i ++) {
			List<List<Integer>> levelSubset = allSubsets[i];
			System.out.printf("--- %2s%n", i);						
			for (List<Integer> list : levelSubset) {	
				counter ++;	
				System.out.printf("\t%3s. %s%n", counter, list);
			}
		}		
		System.out.println("Counter: " + counter);
		
		System.out.println("=============================");
		System.out.println("=============================");
		
		combinatoric(mas);
	}
}
