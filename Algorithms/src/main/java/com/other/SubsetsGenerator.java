package com.other;

import java.util.ArrayList;
import java.util.List;

public class SubsetsGenerator {

	public static <T> List<List<T>> generateSubsets(T[] mas) {
		List<List<T>> bag = new ArrayList<List<T>>();
		
		int iterCount = (int) Math.pow(2, mas.length);
		
		String maskStr = "%" + mas.length + "s";
		
		for (int i = 0; i < iterCount; i ++) {
			List<T> tuple = new ArrayList<>();			
			
			int tmp = i;
			for (int j = 0; j < mas.length; j ++) {
				if ((tmp & 1) == 1) {
					tuple.add(mas[j]);
				}		
				tmp >>>= 1;
			}
			
			String strBinary = String.format(maskStr, Integer.toBinaryString(i)).replaceAll(" ", "0");
			
			System.out.printf("%2s. %s, %s\n", bag.size() + 1, strBinary, tuple);
			
			bag.add(tuple);
		}
		
		return bag;
	}
	
	public static void main(String[] args) {
		//Integer[] mas = {5, 10};
		Integer[] mas = {4, 3, 2, 1};
		
		List<List<Integer>> subSets = generateSubsets(mas);
		System.out.println("Count: " + subSets.size());
	}
}
