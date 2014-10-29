package com.datastructure.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

import com.datastructure.StackDef;
import com.datastructure.StackOnArray;

/**
 * Imagine a (literal) stack of plates. If the stack gets too high, it might topple. Therefore,
 * in real life, we would likely start a new stack when the previous stack exceeds some
 * threshold. Implement a data structure SetOfStacks that mimics this. 
 * SetOfStacks should be composed of several stacks and should create a new stack once
 * the previous one exceeds capacity. SetOfStacks.push() and SetOfStacks.pop () 
 * should behave identically to a single stack (that is, pop () should return the
 * same values as it would if there were just a single stack).
 * 
 * FOLLOW UP
 * Implement a function popAt(int index) which performs a pop operation on a
 * specific sub-stack.
 */
public class Task3_3 {

	static class SetOfStacks<T> implements StackDef<T> {
		private final int stackSize;
		private StackDef<T>[] stacks;
		private int curStack;
		private int size;
		
		@SuppressWarnings("unchecked")
		public SetOfStacks(int stackSize) {
			this.stackSize = stackSize;
			
			stacks = new StackOnArray[1];
			curStack = 0;
			stacks[0] = new StackOnArray<T>(this.stackSize, true);			
		}

		@Override
		public void push(T val) {
			if (stacks[curStack].size() == stackSize) { //go to next array
				curStack ++;
				
				if (curStack == stacks.length) { //extend array
					stacks = Arrays.copyOf(stacks, stacks.length + 1);
					stacks[curStack] = new StackOnArray<T>(this.stackSize, true);
				}
			}
			
			stacks[curStack].push(val);						
			size ++;
		}

		@Override
		public T pop() {
			if (isEmpty()) {
				throw new EmptyStackException();
			}
			
			if (stacks[curStack].isEmpty()) {
				curStack --;				
			}			
			size --;
			return stacks[curStack].pop();
		}

		@Override
		public T peek() {
			if (isEmpty()) {
				throw new EmptyStackException();
			}
			
			int i = curStack;
			if (stacks[i].isEmpty()) {
				i --;			
			}			
			return stacks[i].peek();
		}
		
		public T popAt(int index) {
			//TD: check index in range
			T res = stacks[index].pop();
			size --;
			return res;
		}
		
		public T popAtWithRollover(int i) {
			T res = stacks[i].pop();
			size --;
			
			for (; i + 1 < stacks.length && !stacks[i + 1].isEmpty(); i ++) {				
				List<T> tmpList = new ArrayList<>();										
				while (true) {
					T elem = stacks[i + 1].pop();	
					if (!stacks[i + 1].isEmpty()) {
						tmpList.add(0, elem);
					} else {
						stacks[i].push(elem); //last element, add it to prev stack
						break;
					}
				}
								
				for (T elem : tmpList) { //add elements back to stack
					stacks[i + 1].push(elem);
				}
			}			
			
			return res;
		}
		
		@Override
		public boolean isEmpty() {
			return size == 0;
		}

		@Override
		public int size() {			
			return size;
		}
		
	}
	
	static void checkPopAtWithRollover() {
		SetOfStacks<Integer> stack = new SetOfStacks<>(3);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		stack.push(4);
		stack.push(5);
		stack.push(6);
		
		stack.push(7);
		stack.push(8);	
		
		System.out.println("popAtWithRollover: " + stack.popAtWithRollover(0));
		
	}
	
	public static void main(String[] args) {
		SetOfStacks<Integer> stack = new SetOfStacks<>(2);
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		
		System.out.println("Size: " + stack.size());
		
		System.out.println("PopAt 1: " + stack.popAt(1) + ", size: " + stack.size());
		
 		stack.pop();//5 
		Integer val = stack.pop();//3
		System.out.println(val);
		
		stack.pop(); //2
		stack.pop(); //1
		
		try {
			stack.pop();	
		} catch (EmptyStackException es) {
			System.out.println("EmptyStackException");
		}
		
		checkPopAtWithRollover();		
	}	
}
