package com.sort;

import java.util.Arrays;
import java.util.Comparator;

public class HeapSort3<T> {
	
	final T[] mas;
	final Comparator<T> cmp;
	
	public HeapSort3(T[] mas, Comparator<T> cmp) {
		this.mas = mas;
		this.cmp = cmp; 
	}
	
	public static <T> T[] sort(T[] mas, Comparator<T> cmp) {
		HeapSort3<T> heap = new HeapSort3<>(mas, cmp);
		heap.sort();
		return mas;
	}	
	
	public void sort() {			
		//init
		for (int i = getParent(mas.length - 1); i >= 0; i --) {	
			heapify(i, mas.length - 1);
		}
				
		//sort
		for (int last = mas.length - 1; last > 0; last --) {
			swap(0, last);
			heapify(0, last - 1);
		}
	}

	void heapify(int n, int last) {
		int left = this.getLeftChild(n);
		int right = left + 1;
		if (left <= last) {
			int max = right > last || cmp.compare(mas[left], mas[right]) >= 0 ? left : right;
			if (cmp.compare(mas[max], mas[n]) > 0) {
				swap(n, max);
				heapify(max, last);
			}
		}		
	}
	
	int getParent(int n) {
		return (n - 1) / 2;
	}
	
	int getLeftChild(int n) {
		return 2 * n + 1;
	}

	void swap(int i, int j) {
		T tmp = mas[i];
		mas[i] = mas[j];
		mas[j] = tmp;
	}
	
	public static void main(String[] args) {
		Integer[] mas = {1, 34, 67, 3, -5, 67, 45, 0, 120, 4};
		Comparator<Integer> cmp = Integer::compare;
		mas = sort(mas, cmp);
		System.out.println("Sorted mas: " + Arrays.toString(mas));
	}
}
