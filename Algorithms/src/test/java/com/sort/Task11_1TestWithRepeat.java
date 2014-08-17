package com.sort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.RepeatRule;
import com.RepeatRule.Repeat;

public class Task11_1TestWithRepeat {
	
	@Rule
	public TestName name = new TestName();
	
	@Rule
	public RepeatRule repeatRule = new RepeatRule();
	
	@Test
	public void test1() {
		System.out.println("test: " + name.getMethodName() + ", " + this);
		
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

	int g = 0;
	@Before
	public void prepare() {
		g ++;
		System.out.println("prepare: " + g + ",  " + name.getMethodName());
	}
	
	@Repeat(times = 100)
	@Test
	public void testRandom() {
		//'this' shows us that the same instance of Test is used for all repeats. 
		System.out.println("test: " + name.getMethodName() + ", " + this);
		
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
