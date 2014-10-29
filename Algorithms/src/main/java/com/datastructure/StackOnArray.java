package com.datastructure;

import java.util.Arrays;
import java.util.EmptyStackException;

public class StackOnArray<T> implements StackDef<T> {

	private Object[] mas;
	private int top = 0;
	private boolean canExpand;
	
	public StackOnArray() {
		this(8, true);
	}
	
	public StackOnArray(int defaultSize, boolean canExpand) {
		mas = new Object[defaultSize];
		this.canExpand = canExpand;
	}
	
	public void push(T obj) {
		if (top == mas.length) {
			if (!canExpand) {
				throw new IllegalStateException("Stack is full. Size: " + this.size());
			}
			mas = Arrays.copyOf(mas, mas.length << 1); //extend array
		}
		mas[top ++] = obj;
	}
	
	public T pop() {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}
		
		top --;
		@SuppressWarnings("unchecked")
		T obj = (T) mas[top];
		mas[top] = null;		
		return obj;
	}
	
	public T peek() {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}		
		@SuppressWarnings("unchecked")
		T obj = (T) mas[top - 1];		
		return obj;
	}
	
	public boolean isEmpty() {
		return top == 0;
	}
	
	public static void main(String[] args) {
		StackOnArray<String> stack = new StackOnArray<>();
		stack.push("a");
		stack.push("b");
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		stack.push("c");
		System.out.println(stack.pop());		
		System.out.println("empty: " + stack.isEmpty());
	}

	@Override
	public int size() {		
		return top;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("[");
		for (int i = top - 1; i >= 0; i --) {
			res.append(mas[i]).append(",");
		}
		res.append("]");
		return res.toString();
	}
}
