package com.dynamicprog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task9_5 {

	public static void permutations(int[] mas) {		
		List<List<Integer>> levelPermutations = new ArrayList<>();
		//base
		levelPermutations.add(Arrays.asList(mas[0]));
		
		for (int i = 1; i < mas.length; i ++) {
			List<List<Integer>> curPermutations = new ArrayList<>();
			
			for (List<Integer> list : levelPermutations) {
				//insert current in list
				for (int j = 0; j <= list.size(); j ++) {
					List<Integer> newList = new ArrayList<>(list);
					newList.add(j, mas[i]);
					curPermutations.add(newList);
				}				
			}			
			levelPermutations = curPermutations;
		}
		
		for (int i = 0; i < levelPermutations.size(); i ++) {
			System.out.printf("%3s. %s%n", (i + 1), levelPermutations.get(i));			
		}
	}
	
	public static void main(String[] args) {
		int[] mas = new int[] {1, 2, 3, 4, 5};
		permutations(mas);
	}
}
