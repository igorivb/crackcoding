package com.datastructure;

import java.util.Arrays;
import java.util.EmptyStackException;

public class StackOnArray<T> implements StackDef<T> {

	Object[] mas;
	int top = 0;
	
	public StackOnArray() {
		mas = new Object[8];
	}
	
	public void push(T obj) {
		if (top == mas.length) {			
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
}
