package com.datastructure.stack;

import com.datastructure.StackDef;
import com.datastructure.StackOnArray;

/**
 * Implement a MyQueue class which implements a queue using two stacks.
 */
public class Task3_5<T> {

	private StackDef<T> stack1 = new StackOnArray<T>();
	private StackDef<T> stack2 = new StackOnArray<T>();
	
	public void add(T elem) {
		stack1.push(elem);
	}
	
	public T remove() {
		while (!stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}
		T res = stack2.pop();
		while (!stack2.isEmpty()) {
			stack1.push(stack2.pop());
		}		
		return res;
	}	
}
