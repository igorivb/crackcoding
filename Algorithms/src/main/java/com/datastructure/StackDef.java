package com.datastructure;

public interface StackDef<T> {
	
	void push(T obj);
	
	/** 
	 * @throws EmptyStackException
	 */
	T pop();
	
	/** 
	 * @throws EmptyStackException
	 */
	T peek();
	
	boolean isEmpty();
}
