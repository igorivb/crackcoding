package com.sort;

import java.util.Comparator;

import com.Utils;

/**
 * Heap sort again.
 */
public class HeapSortWorking {

	private static class Heap<T> {
		T[] mas;
		Comparator<? super T> cmp;
		
		public Heap(T[] mas, Comparator<? super T> cmp) {
			this.mas = mas;
			this.cmp = cmp;
		}

		public void heapify(int i, int last) {
			for (;;) {
				int left = this.getLeft(i);
				int right = this.getRight(i);
				
				int max = -1;				
				if (left <= last) {
					max = left;
					
					if (right <= last && cmp.compare(mas[left], mas[right]) < 0) {
						max = right;
					}
				}
				
				if (max != -1 && cmp.compare(mas[i], mas[max]) < 0) {
					Utils.swap(mas, i, max);
					i = max;
				} else {
					break;
				}
			}			
		}

		public int getLeft(int i) {
			return 2 * i + 1;			
		}
		
		public int getRight(int i) {
			return this.getLeft(i) + 1;
		}		
	} 
		
	public static <T> void sort(T[] mas, Comparator<? super T> cmp) {
		//create heap
		Heap<T> heap = new Heap<>(mas, cmp);		
		int last = mas.length - 1;
		
		for (int i = last >> 1; i >= 0; i --) {
			heap.heapify(i, last);
		}
		
		//extract min
		while (last > 0) {
			Utils.swap(mas, 0, last);
			last --;
			heap.heapify(0, last);
		}
	}
}
