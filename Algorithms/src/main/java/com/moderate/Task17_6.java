package com.moderate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import com.Tuple2;
import com.Utils;

/**
 * Given an array of integers, write a method to find indices m and n such that if you
 * sorted elements m through n, the entire array would be sorted. Minimize n - m (that
 * is, find the smallest such sequence).
 */
public class Task17_6 {

	static class Diff {
		final int m;
		final int n;
		final boolean asc;
		
		public Diff(int m, int n, boolean asc) {			
			this.m = m;
			this.n = n;
			this.asc = asc;
		}
		
	}
	
	static Tuple2<Integer, Integer> doFindDiff(Integer[] mas, Comparator<Integer> cmp) {
		int m = -1, n = -1;		
		final int len = mas.length;
		Integer[] sorted = Arrays.copyOf(mas, len);
		Arrays.sort(sorted, cmp);
		
		for (int i = 0; i < len; i ++) {
			if (mas[i] != sorted[i]) {
				m = i;
				break;
			}
		}
		
		if (m < len) {
			for (int i = len - 1; i >= 0; i --) {
				if (mas[i] != sorted[i]) {
					n = i + 1;
					break;
				}
			}
		}
		
		return new Tuple2<Integer, Integer>(m, n);
	}
	
	/**
	 * @return [m, n)
	 * 	m = len if array is already sorted
	 */
	public static Diff findDiff(Integer[] mas, Comparator<Integer> cmp) {
		Tuple2<Integer, Integer> res1 = doFindDiff(mas, cmp);
		Tuple2<Integer, Integer> res2 = doFindDiff(mas, cmp.reversed());	
		if (res1._2 - res1._1 <= res2._2 - res2._1) {
			return new Diff(res1._1, res1._2, true);
		} else {
			return new Diff(res2._1, res2._2, false);
		}
	}
	
	static <T> String showRangArray(T[] mas, int s, int e) {
		StringBuilder res = new StringBuilder("[");
		
		for (int i = s, len = e; i < len; i ++) {
			res.append(mas[i]);
			if (i < len - 1) {
				res.append(", ");
			}
		}
		
		res.append("]");
		return res.toString();
	}

	static boolean check(Integer[] mas) {
		System.out.println(Arrays.toString(mas));
		
		Comparator<Integer> cmp = Integer::compare;
		Diff res = findDiff(mas, cmp);
		System.out.printf("Result: (%2d, %2d), %s, %s%n", res.m, res.n, res.asc ? "asc" : "desc", res.m == mas.length ? "Not required" : ("Sort: " + showRangArray(mas, res.m, res.n)));
		
		Integer[] sorted = Arrays.copyOf(mas, mas.length);
		Comparator<Integer> sortedCmp = res.asc ? cmp : cmp.reversed();
		if (res.m != mas.length) {
			Arrays.sort(sorted, sortedCmp);
		}		
		
		System.out.println(Arrays.toString(sorted));
		
		//compare results from Book
		if (res.asc) { //book has correct results only for ascending
			int[] book = new int[mas.length];
			for (int i = 0; i < mas.length; i ++) {
				book[i] = mas[i];
			}
			Tuple2<Integer, Integer> bookRes = Question_17_6.findUnsortedSequence(book);
			if (res.m != bookRes._1 || res.n != bookRes._2) {
				System.err.printf("Book res is different: %2d, %2d%n", bookRes._1, bookRes._2);				
			}
		}
		
						
		if (Utils.isSortedArray(sorted, sortedCmp)) {
			System.out.println("Success");
			return true;
		} else {
			System.err.println("Failure");
			return false;
		}
	}
	
	static Integer[] randomIntegerMas(int size, int max) {
		Integer[] mas = new Integer[size];
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < size; i ++) {
			mas[i] = r.nextInt(max);
		}
		return mas;
	}
	
	public static void main(String[] args) {
//		//Integer[] mas = {2, 5, 8, 10, 3, 6, 20};
//		Integer[] mas = {1, 2, 4, 7, 10, 11, 8, 12, 5, 7, 16, 18, 19};
//		check(mas);
		
		int failed = 0;
		for (int i = 0; i < 10; i ++) {
			System.out.printf("---------- %2d%n", i + 1);
			Integer[] mas = randomIntegerMas(10, 100);
			if (!check(mas)) {
				failed ++;
			}		
			System.out.println();
		}				
		
		System.out.println("Failed: " + failed);
	}
		
}