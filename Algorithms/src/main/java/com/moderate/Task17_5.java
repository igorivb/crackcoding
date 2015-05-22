package com.moderate;

import java.util.Arrays;
import java.util.Random;

import com.Tuple2;

public class Task17_5 {

	enum Slot { 
		R, Y, G, B;
		
		public static Slot get(int num) {
			for (Slot s : Slot.values()) {
				if (s.ordinal() == num) {
					return s;
				}
			}
			throw new IllegalArgumentException("Unpexpected slot: " + num);
		}
	}
	
	public static Tuple2<Integer, Integer> measure1(Slot[] sol, Slot[] prop) {
		final int size = sol.length;
		int hits = 0, pseudoHists = 0;
		boolean[] marks = new boolean[size];
		
		//hits
		for (int i = 0; i < size; i ++) {
			if (sol[i] == prop[i]) {
				hits ++;
				marks[i] = true;
			}
		}
		
		//pseudo hits
		for (int i = 0; i < size; i ++) {
			if (sol[i] != prop[i]) {
				for (int j = 0; j < size; j ++) {
					if (!marks[j] && prop[i] == sol[j]) {
						pseudoHists ++;
						marks[j] = true;
						break;
					}
				}	
			}			
		}
		
		return new Tuple2<Integer, Integer>(hits, pseudoHists);
	}
	
	//Simpler and better
	public static Tuple2<Integer, Integer> measure2(Slot[] sol, Slot[] prop) {
		final int size = sol.length;
		int hits = 0, pseudoHists = 0;
		int[] marks = new int[size];
		
		//hits
		for (int i = 0; i < size; i ++) {
			if (sol[i] == prop[i]) {
				hits ++;				
			} else {
				marks[sol[i].ordinal()] ++;
			}
		}
		
		//pseudo hits
		for (int i = 0; i < size; i ++) {
			if (prop[i] != sol[i] && marks[prop[i].ordinal()] > 0) {
				pseudoHists ++;
				marks[prop[i].ordinal()] --;
			}
		}
		
		return new Tuple2<Integer, Integer>(hits, pseudoHists);
	}
	
	static Random r = new Random(System.currentTimeMillis());
	static Slot[] randomSlots(int size) {
		Slot[] slot = new Slot[size];
		
		for (int i = 0; i < size; i ++) {
			slot[i] = Slot.get(r.nextInt(Slot.values().length));				
		}
		
		return slot;
	}
	
	static boolean check(Slot[] sol, Slot[] prop) {
		Tuple2<Integer, Integer> res1 = measure1(sol, prop);
		Tuple2<Integer, Integer> res2 = measure2(sol, prop);
		
		System.out.println(Arrays.toString(sol));
		System.out.println(Arrays.toString(prop));
		
		System.out.printf("Hits: %d, pseudo hits: %d%n", res1._1, res1._2);
		System.out.printf("Hits: %d, pseudo hits: %d%n", res2._1, res2._2);
		
		if (res1._1 != res2._1 || res1._2 != res2._2) {
			System.out.println("Failed");			
			return false;
		} else {
			return true;
		}
	}	
	
	public static void main(String[] args) {				
//		Slot[] sol =  {Y, Y, G, B};
//		Slot[] prop = {B, B, Y, G};
//		check(sol, prop);
		
		final int size = 4;
		int failed = 0;
		for (int i = 0; i < 1000; i ++) {
			Slot[] sol = randomSlots(size);
			Slot[] prop = randomSlots(size);
			
			if (!check(sol, prop)) {
				failed ++;
			}						
			System.out.println();
		}
		System.out.println("Failed: " + failed);		
	}	
}
