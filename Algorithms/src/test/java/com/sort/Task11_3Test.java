package com.sort;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

public class Task11_3Test {

	@Test
	public void testPartiotioned() {
		
		Integer[] mas = new Integer[] {4, 5, 6, 1, 2, 3};
		Integer val = 5;
		Comparator<Integer> cmp = Integer::compare;
		
		int res = Task11_3.search(mas, val, cmp);
		
		assertEquals(1, res);
	}
	
	@Test
	public void testSorted() {
		
		Integer[] mas = new Integer[] {1, 2, 3, 4, 5, 6};
		Integer val = 5;
		Comparator<Integer> cmp = Integer::compare;
		
		int res = Task11_3.search(mas, val, cmp);
		
		assertEquals(4, res);
	}
}
