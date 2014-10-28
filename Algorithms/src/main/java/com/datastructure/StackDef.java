package com.datastructure;

public interface StackDef<T> {
	
	void push(T val);
	
	/** 
	 * @throws EmptyStackException
	 */
	T pop();
	
	/** 
	 * @throws EmptyStackException
	 */
	T peek();
	
	boolean isEmpty();
	
	int size();
}
