package com.datastructure.stack;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class Task3_5Test {

	List<Integer> list;
	
	public Task3_5Test(List<Integer> list, boolean ignore) {
		this.list = list;
	}
	
	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {	      
	      { Arrays.asList(1), true },
	      { Arrays.asList(1, 2), true },
	      { Arrays.asList(1, 2, 3), true },
	      { Arrays.asList(1, 2, 3, 4), true },
	      { Arrays.asList(1, 2, 3, 4, 5), true },
	    });
	}
	
	@Test
	public void test() {		
		Task3_5<Integer> queue = new Task3_5<>();
		for (Integer elem : list) {
			queue.add(elem);
		}
		
		for (Integer elem : list) {
			assertEquals(elem, queue.remove());
		}
	}
}
