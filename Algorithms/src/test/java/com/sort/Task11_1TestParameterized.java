package com.sort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Task11_1TestParameterized {

	int stage;
	
	public Task11_1TestParameterized(Integer stage, boolean some) {
		this.stage = stage;
		System.out.println("stage: " + stage);
	}
	
	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
	      { 1, true },
	      { 2, true },
	    });
	}
	
	@Test
	public void test1() {
		System.out.println("  test1: " + stage);
		
		Comparator<Integer> cmp = Integer::compare;
		int aSize = 3;
		Integer[] a = new Integer[8];
		a[0] = 3;
		a[1] = 20;
		a[2] = 34;
		//Arrays.sort(a, 0, aSize, cmp);
		
		Integer[] b = new Integer[] {-3, 12, 25, 50, 100};
		//Arrays.sort(b, cmp);
		
		//make a copy for verifying results
		Integer[] expected = Arrays.copyOf(a, a.length);
		System.arraycopy(b, 0, expected, aSize, b.length);
		Arrays.sort(expected, cmp);
		
		//insert
		Task11_1.insert(a, aSize, b, cmp);			
		
		assertArrayEquals(expected, a);		
	}
	
	@Test
	public void testRandom() {
		System.out.println("  testRandom: " + stage);
		
		Random rand = new Random(System.currentTimeMillis());
		
		Comparator<Integer> cmp = Integer::compare;
		
		//[5, 105)
		int aSize = 5 + rand.nextInt(100);
		//[10, 110)
		int bSize = 10 + rand.nextInt(100);
		
		Integer[] a = new Integer[aSize + bSize];
		for (int i = 0; i < aSize; i ++) {
			a[i] = rand.nextInt(100);
		}
		Arrays.sort(a, 0, aSize, cmp);
		
		Integer[] b = new Integer[bSize];
		for (int i = 0; i < bSize; i ++) {
			b[i] = rand.nextInt(100);
		}
		Arrays.sort(b, cmp);
		
		//make a copy for verifying results
		Integer[] expected = Arrays.copyOf(a, a.length);
		System.arraycopy(b, 0, expected, aSize, b.length);
		Arrays.sort(expected, cmp);
		
		//insert
		Task11_1.insert(a, aSize, b, cmp);			
		
		assertArrayEquals(expected, a);		
	}
}
