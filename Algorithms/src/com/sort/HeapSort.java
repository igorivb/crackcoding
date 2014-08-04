package com.sort;

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
		
		public int getParent(int i) {			
			return i == 0 ? -1 : (i - 1) / 2;
		}
		
		public int getLeftChild(int i) {
			return i * 2 + 1;
		}
		
		public void bubbleUp(int i) {			
			while (i > 0) {
				int parentIndex = this.getParent(i);
				//check violation
				if (cmp.compare(this.mas[parentIndex], this.mas[i]) > 0) {
					Utils.swap(this.mas, parentIndex, i);
					i = parentIndex;
				} else {
					break;
				}							
			}		
		}
		
		public void bubbleDown() {
			for (int i = 0; i < mas.length; i ++) {
				
			}
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
	}
	
	public static <T> void heapSort(T[] mas, Comparator<? super T> cmp) {
		
		//init
		Heap<T> heap = new Heap<>(mas, cmp);	
		for (int i = 0; i < mas.length; i ++) {			
			heap.bubbleUp(i);
		}
		
		heap.print();
		
		heap.bubbleDown();
	}
	
	public static void main(String[] args) {
		Integer[] mas = {3, 12, 15, 6, 1};
		Comparator<Integer> cmp = new Comparator<Integer>() {
			public int compare(Integer ob1, Integer ob2) {
				return ob1.compareTo(ob2);
			}
		};
		heapSort(mas, cmp);
		
		
	}
}
