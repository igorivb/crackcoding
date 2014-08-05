package com.sort;

import java.util.Comparator;

public interface Sorting {
	
	<T> void sort(T[] mas, Comparator<? super T> cmp);
}
