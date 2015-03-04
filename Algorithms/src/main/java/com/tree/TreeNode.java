package com.tree;

public class TreeNode<T extends Comparable<T>> {

	public final T key;
	public Object data;
	
	public TreeNode<T> left;
	public TreeNode<T> right;
	public TreeNode<T> parent;
	
	public TreeNode(T key) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		this.key = key;
	}
	
	public TreeNode<T> setLeft(TreeNode<T> n) {
		if (this.left != null) {
			this.left.parent = null;
		}
		this.left = n;		
		if (n != null) {
			n.parent = this;	
		}
		
		return this;
	}
	
	public TreeNode<T> setRight(TreeNode<T> n) {
		if (this.right != null) {
			this.right.parent = null;
		}
		this.right = n;
		if (n != null) {
			n.parent = this;
		}
		
		return this;
	}
	
	public void replaceChild(TreeNode<T> oldChild, TreeNode<T> newChild) {
		if (isLeftChild(oldChild)) {
			this.setLeft(newChild);
		} else if (isRightChild(oldChild)) {
			this.setRight(newChild);
		}
	}
	
	public boolean isLeftChild(TreeNode<T> n) {
		return this.left != null && this.left.equals(n);
	}
	
	public boolean isRightChild(TreeNode<T> n) {
		return this.right != null && this.right.equals(n);
	}

	@Override
	public String toString() {
		return key.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof TreeNode) {
			return this.key.equals(((TreeNode<T>) obj).key);
		}
		return false;		
	}	
	
}
