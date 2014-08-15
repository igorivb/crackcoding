package com.sort;

import static org.junit.Assert.assertArrayEquals;
import java.util.Comparator;

import org.junit.Test;

public class QuickSortTest {

	@Test
	public void testSimple1() {		
		Integer[] mas = {3, 5, 2};
		Comparator<Integer> cmp = Integer::compare;
		
		Integer[] expected = {2, 3, 5};
		
		QuickSort.sort(mas, cmp);
		
		assertArrayEquals(expected, mas);		
	}
	
	@Test
	public void testSimple2() {		
		Integer[] mas = {5, 3};
		Comparator<Integer> cmp = Integer::compare;
		
		Integer[] expected = {3, 5};
		
		QuickSort.sort(mas, cmp);
		
		assertArrayEquals(expected, mas);		
	}
	
	@Test
	public void testSimple3() {		
		Integer[] mas = {5};
		Comparator<Integer> cmp = Integer::compare;
		
		Integer[] expected = {5};
		
		QuickSort.sort(mas, cmp);
		
		assertArrayEquals(expected, mas);		
	}
}
