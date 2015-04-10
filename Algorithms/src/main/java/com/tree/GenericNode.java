package com.tree;


public class GenericNode<T> {
	public final T val;
	
	public GenericNode<T> left, right;
	
	public GenericNode(T val) {
		this.val = val;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof GenericNode) {
			return this.val.equals(((GenericNode<T>) obj).val);	
		}
		return false;
	}
	
	@Override
	public String toString() {	
		return this.val.toString();
	}
}
