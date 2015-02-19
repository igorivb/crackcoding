package com.datastructure.stack;

import com.datastructure.StackDef;
import com.datastructure.StackOnArray;

/**
 * Implement a MyQueue class which implements a queue using two stacks.
 */
public class Task3_5<T> {

	private StackDef<T> newStack = new StackOnArray<T>();
	private StackDef<T> oldStack = new StackOnArray<T>();
	
	public void add(T elem) {
		newStack.push(elem);
	}
			
	public T remove() {
		shiftStacks();	
		return oldStack.pop();
	}

	private void shiftStacks() {
		if (oldStack.isEmpty()) {
			while (!newStack.isEmpty()) {
				oldStack.push(newStack.pop());
			}
		}			
	}	
}
