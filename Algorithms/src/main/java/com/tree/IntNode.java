package com.tree;


public class IntNode {
	public final int val;
	
	public IntNode left, right;
	
	public IntNode(int val) {
		this.val = val;
	}
	
	public int size() {
		return 1 + 
			(this.left != null ? this.left.size() : 0) + 
			(this.right != null ? this.right.size() : 0);
	}
}
