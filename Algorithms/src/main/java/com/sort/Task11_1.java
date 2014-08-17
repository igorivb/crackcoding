package com.sort;

import java.util.Comparator;

public class Task11_1 {

	public static <T> void insert(T[] a, int aSize, T[] b, Comparator<T> cmp) {
		if (aSize + b.length != a.length) {
			throw new IllegalArgumentException("Incorrect a.length");
		}
				
		int ai = aSize - 1;
		int bi = b.length - 1;
		int i = a.length - 1;
		
		for (;bi >= 0; i --) {
			if (ai >= 0 && cmp.compare(a[ai], b[bi]) >= 0) {
				a[i] = a[ai];
				ai --;
			} else {
				a[i] = b[bi];
				bi --;
			}
		}
	}
}
