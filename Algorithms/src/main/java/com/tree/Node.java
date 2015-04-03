package com.tree;


public class Node<T> {
	public final T val;
	
	public Node<T> left, right;
	
	public Node(T val) {
		this.val = val;
	}
}
