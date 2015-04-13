package com.tree;


public class IntNode {
	public final int val;
	
	public IntNode left, right;
	
	public IntNode parent;
	
	public IntNode(int val) {
		this.val = val;
	}
	
	public IntNode setLeft(IntNode n) {
		this.left = n;
		n.parent = this;
		return this;
	}
	
	public IntNode setLeft(int val) {
		return this.setLeft(new IntNode(val));
	}
	
	public IntNode setRight(IntNode n) {
		this.right = n;
		n.parent = this;
		return this;
	}
	
	public IntNode setRight(int val) {
		return this.setRight(new IntNode(val));
	}
	
	public int size() {
		return 1 + 
			(this.left != null ? this.left.size() : 0) + 
			(this.right != null ? this.right.size() : 0);
	}

	public boolean isLeaf() {		
		return this.left == null && this.right == null;
	}
}
