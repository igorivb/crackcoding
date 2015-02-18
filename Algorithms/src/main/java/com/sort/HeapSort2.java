package com.sort;

import java.util.Arrays;
import java.util.Comparator;

public class HeapSort2 {

	private final int[] mas;
	private final Comparator<Integer> cmp;
	
	public HeapSort2(int[] mas, Comparator<Integer> cmp) {
		this.mas = mas;
		this.cmp = cmp;
	}

	public static int[] sort(int[] mas, Comparator<Integer> cmp) {
		return new HeapSort2(mas, cmp).doSort();
	}

	private int[] doSort() {		
		//1. create tree
		int i = 1 << ((int) Math.log(mas.length));
		for (; i >= 0; i --) {
			heapify(i, mas.length);
		}
		
		//2. do sorting 
		for (int last = mas.length; last > 0; last --) {
			swap(0, last - 1);			
			heapify(0, last - 1);
		}
		
		return mas;
	}

	private void swap(int i, int j) {
		int tmp = mas[i];
		mas[i] = mas[j];
		mas[j] = tmp;		
	}

	private void heapify(int i, int last) {		
		int leftChildInd = getLeftChildIndex(i);
		int rightChildInd = getRightChildIndex(i);
		
		if (leftChildInd < last || rightChildInd < last) { //not leaf	
			int minInd = (rightChildInd >= last || cmp.compare(mas[leftChildInd], mas[rightChildInd]) >= 0 ) ? leftChildInd : rightChildInd;
			if (cmp.compare(mas[minInd], mas[i]) > 0) {
				swap(i, minInd);
				heapify(minInd, last);
			}			
		}						
	}

	private int getLeftChildIndex(int i) {	
		return i * 2 + 1;
	}
	
	private int getRightChildIndex(int i) {
		return getLeftChildIndex(i) + 1;
	}
	
	public static void main(String[] args) {
		int[] mas = {1, 34, 67, 3, -5, 67, 45, 0, 120, 4};
		Comparator<Integer> cmp = Integer::compare;
		mas = sort(mas, cmp);
		System.out.println("Sorted mas: " + Arrays.toString(mas));
	}
}
