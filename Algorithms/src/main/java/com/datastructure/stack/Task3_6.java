package com.datastructure.stack;

import java.util.Comparator;

import com.datastructure.StackDef;
import com.datastructure.StackOnArray;

/**
 * Write a program to sort a stack in ascending order (with biggest items on top).
 * You may use at most one additional stack to hold items, but you may not copy the
 * elements into any other data structure (such as an array). The stack supports the
 * following operations: push, pop, peek, and isEmpty
 */
public class Task3_6 {

	public static <T> void sort(StackDef<T> input, Comparator<T> cmp) {
		StackDef<T> buf = new StackOnArray<T>();
		
		while (!input.isEmpty()) {
			T item = input.pop();
			while (!buf.isEmpty() && cmp.compare(buf.peek(), item) < 0) {
				input.push(buf.pop());
			}
			buf.push(item);
		}
		
		while (!buf.isEmpty()) { //copy back
			input.push(buf.pop());
		}
	}
	
	public static void main(String[] args) {
		Integer[] mas = {1, 34, 67, 3, -5, 67, 45, 0, 120, 4};
		
		Comparator<Integer> cmp = (Integer::compare);
		//cmp = cmp.reversed();
		
		StackDef<Integer> input = new StackOnArray<>();
		for (Integer elem : mas) input.push(elem);		
		
		sort(input, cmp);
		
		while (!input.isEmpty()) System.out.print(input.pop() + " ");
		
	}
}
