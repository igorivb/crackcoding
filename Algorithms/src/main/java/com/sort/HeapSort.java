package com.sort;

import java.util.Arrays;
import java.util.Comparator;

import com.Utils;

public class HeapSort {

	private static class Heap<T> {
		
		private T[] mas;
		private Comparator<? super T> cmp;
					
		public Heap(T[] mas, Comparator<? super T> cmp) {
			this.mas = mas;		
			this.cmp = cmp;
		}		
		
		public void print() {
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < mas.length; i ++) {
				str.append(mas[i]);
				
				if (i < mas.length - 1) {
					int tmp = i + 2;
					if ((tmp & (tmp - 1)) == 0) {
						str.append("\n");
					} else {
						str.append(", ");
					}
				}
			}
			System.out.println(str);
		}
		
		public void heapify(int i, int last) {
			for (;;) {
				int left = getLeft(i);
				int right = getRight(i);
				
				int max = -1;
				if (left <= last) {
					max = left;
					
					if (right <= last && cmp.compare(this.mas[left], this.mas[right]) < 0) {
						max = right;
					}
				}									
								
				if (max != -1 && cmp.compare(this.mas[i], this.mas[max]) < 0) {
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
	
	public static <T> void heapSort(T[] mas, Comparator<? super T> cmp) {
		
		//create heap
		Heap<T> heap = new Heap<>(mas, cmp);
		int last = heap.mas.length - 1;
		for (int i = last >> 1; i >= 0; i --) {
			heap.heapify(i, last);
		}
		
		//System.out.println("Created heap:");
		//heap.print();
		
		//extract min
		while (last > 0) {
			Utils.swap(heap.mas, 0, last);
			last --;
			heap.heapify(0, last);
		}
		
		//System.out.println("Extracted min:");
		//heap.print();
	}
	
	public static void main(String[] args) {
		Integer[] mas = {1, 34, 67, 3, -5, 67, 45, 0, 120, 4};		
		heapSort(mas, Integer::compare);
		System.out.println("Sorted mas: " + Arrays.toString(mas));
	}
}
