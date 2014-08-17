package com.sort;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Task11_5Test {

	private String val;
	private int expected;
	
	public Task11_5Test(String val, int expected) {
		this.val = val;
		this.expected = expected;
	}
	
	@Test
	public void test() {
		String[] mas = new String[] {"", "", "a", "", "", "b", "", "", "c", "", "", "d", "", "", "e", "", ""};
		
		Comparator<String> cmp = String.CASE_INSENSITIVE_ORDER;
		
		int res = Task11_5.search(mas, val, cmp);
		
		assertEquals(expected, res);
	}
	
	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
	      { "a", 2 },
	      { "b", 5 },
	      { "c", 8 },
	      { "d", 11 },
	      { "e", 14 },
	      { "n", -1 }
	    });
	}
}
