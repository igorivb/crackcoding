package com.math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Task7_7 {

	final static int[] primes = {3, 5, 7};
	
	public static int findNumV1(int pos) {		
		if (pos <= 0) {
			throw new RuntimeException("Invalid pos: " + pos);
		}
		
		List<Integer> results = new ArrayList<>();
		results.add(0);
		results.add(1);
				
		for (int i = 2; i <= pos; i ++) {
			int min = Integer.MAX_VALUE;			
			for (int r : results) {
				for (int prime : primes) {
					int tmp;
					if ((tmp = prime * r) > results.get(i - 1)) {						
						min = Math.min(min, tmp);
					}	
				}				
			}
			results.add(min);
		}
		
		return results.get(pos);
	}
	
	public static int findNumV2(int pos) {				
		if (pos <= 0) {
			throw new RuntimeException("Invalid pos: " + pos);
		}
		
		Set<Integer> results = new HashSet<>();
		results.add(1);
				
		int min = 1;
		for (int i = 1; i <= pos; i ++) {			
			min = Integer.MAX_VALUE; //find min
			for (int r : results) {
				min = Math.min(min, r);
			}
			results.remove(new Integer(min));
			
			for (int prime : primes) { //add next numbers
				results.add(prime * min);
			}
		}
		return min;
	}
	
	public static int findNumV3(int pos) {				
		if (pos <= 0) {
			throw new RuntimeException("Invalid pos: " + pos);
		}
		
		//init queues
		List<Queue<Integer>> queues = new ArrayList<>(primes.length);
		for (int i = 0; i < primes.length; i ++) {
			queues.add(new LinkedList<>());
		}
		queues.get(0).add(1);
		
		int min = 1;
		for (int i = 1; i <= pos; i ++) {			
			//find min in queues
			min = Integer.MAX_VALUE;
			int qi = -1;			
			for (int qPos = 0; qPos < queues.size(); qPos ++) {
				int elem = !queues.get(qPos).isEmpty() ? queues.get(qPos).peek() : Integer.MAX_VALUE;
				if (elem < min) {
					min = elem;
					qi = qPos;
				}
			}
			queues.get(qi).remove();
			
			//add next numbers to queues
			for (int j = qi; j < queues.size(); j ++) {
				queues.get(j).add(min * primes[j]);
			}
		}
		return min;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i <= 100; i ++) {			
			int r1 = findNumV1(i);
			int r2 = findNumV2(i);
			int r3 = findNumV3(i);
			System.out.printf("%3d | %7d | %7d | %7d%n", i, r1, r2, r3);
			if (r1 != r2 || r2 != r3) {
				System.err.println("Not equal for i: " + i);
				break;
			}
		}
	}
}
