package com.tree;

public class TreeNode<T extends Comparable<T>> {

	enum Color { BLACK, RED }
	
	public final T key;
	public Object data;
	
	public TreeNode<T> left;
	public TreeNode<T> right;
	public TreeNode<T> p;
	
	public Color color = Color.RED;
	
	public TreeNode(T key, Color color) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		this.key = key;
		this.color = color;
	}
	
	public TreeNode(T key) {
		this(key, Color.RED);
	}
	
	public TreeNode<T> setLeft(TreeNode<T> n) {
		if (this.left != null) {
			this.left.p = null;
		}
		this.left = n;		
		if (n != null) {
			if (n.p != null) { //clear n from its parent
				if (n.p.isLeftChild(n)) {
					n.p.left = null;
				} else {
					n.p.right = null;
				}				
			}
			n.p = this;	
		}
		
		return this;
	}
	
	public TreeNode<T> setRight(TreeNode<T> n) {
		if (this.right != null) {
			this.right.p = null;
		}
		this.right = n;
		if (n != null) {
			if (n.p != null) { //clear n from its parent
				if (n.p.isLeftChild(n)) {
					n.p.left = null;
				} else {
					n.p.right = null;
				}				
			}
			n.p = this;
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
		return "{" + key.toString() + " " + color + "}";
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
