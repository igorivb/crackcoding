package com.sort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Task11_6Test {
	
	private int val;
	private Integer[] expected;
	
	public Task11_6Test(int val, Integer[] expected) {
		this.val = val;
		this.expected = expected;
	}
	
	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
	      { 4, new Integer[]{1, 0} },
	      { 17, new Integer[]{2, 2} },
	      { 2, new Integer[]{0, 1} },
	      { 25, null },
	      { -3, null },
	    });
	}
	
	@Test
	public void test1() {
		Integer[][] mas = new Integer[][] {
			{1, 2, 10},
			{4, 5, 15},
			{7, 9, 17}
		};
		
		Comparator<Integer> cmp = Integer::compare;
		
		Integer[] res = Task11_6.search(mas, val, cmp);
		
		assertArrayEquals(expected, res);
	}
}
