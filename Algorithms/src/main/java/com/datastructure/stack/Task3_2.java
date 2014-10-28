package com.datastructure.stack;

import java.util.Comparator;
import java.util.EmptyStackException;

import com.datastructure.StackDef;
import com.datastructure.StackOnArray;

/**
 * How would you design a stack which, in addition to push and pop, also has a
 * function min which returns the minimum element? Push, pop and min should all
 * operate in 0(1) time
 */
public class Task3_2 {

	static class Node<T> {
		T val;
		Node<T> prev; 
		Node<T> min;
		public Node(T val) {
			this.val = val;
		}
	} 
	
	/**
	 * Implement on top of linked list.
	 */
	static class StackMin<T> implements StackDef<T> {
		Node<T> head;
		Node<T> min;
		final Comparator<T> cmp;
		
		public StackMin(Comparator<T> cmp) {
			this.cmp = cmp;
		}

		//Value can't be null because of semantic of min
		public void push(T val) {
			Node<T> n = new Node<>(val);
			n.prev = head;
			head = n;
			
			if (min == null || cmp.compare(min.val, val) > 0) {
				n.min = min;
				min = n;
			}
		}
		
		public T pop() {
			if (isEmpty()) {
				throw new EmptyStackException();
			}
			
			if (min == head) { //update min
				min = head.min;
			}
			
			T res = head.val;			
			head = head.prev;			
			return res;
		}
		
		public T peek() {
			if (isEmpty()) {
				throw new EmptyStackException();
			}						
			T res = head.val;								
			return res;
		}
		
		public boolean isEmpty() {
			return head == null;
		}
		
		public T min() {
			return min != null ? min.val : null;
		}
	}
	
	//---------------------------
	
	/**
	 * Implement on top of array. Use separate stack to save mins. 
	 */
	static class StackMin2<T> extends StackOnArray<T> implements StackDef<T> {
		StackOnArray<T> mins = new StackOnArray<>();
		final Comparator<T> cmp;
		
		public StackMin2(Comparator<T> cmp) {
			this.cmp = cmp;
		}
		
		@Override
		public void push(T val) {
			super.push(val);
			
			T curMin = min();
			if (curMin == null || cmp.compare(curMin, val) > 0) {
				mins.push(val);
			}
		}
		
		@Override
		public T pop() {
			T res = super.pop();
			if (cmp.compare(res, min()) == 0) {
				mins.pop();
			}			
			return res;
		} 
		
		public T min() {
			return !mins.isEmpty() ? mins.peek() : null;
		}
	}
	
	public static void main(String[] args) {
		Comparator<Integer> cmp = Integer::compareTo;
		StackMin2<Integer> stack = new StackMin2<>(cmp);
		
		stack.push(3);
		System.out.println("min: " + stack.min());
		stack.push(2);
		System.out.println("min: " + stack.min());
		stack.push(0);
		System.out.println("min: " + stack.min());
		stack.pop();
		System.out.println("min: " + stack.min());
		stack.pop();
		System.out.println("min: " + stack.min());
		stack.push(-1);
		System.out.println("min: " + stack.min());
		stack.pop();
		System.out.println("min: " + stack.min());
		stack.pop();
		System.out.println("min: " + stack.min());		
	}
}
