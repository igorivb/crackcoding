package com.tree;


public class StringNode {
	public final String id;		
	public StringNode left, right;
	
	public StringNode(String id) {
		this.id = id;
	}
	public StringNode setLeft(StringNode n) {
		this.left = n;
		return this;
	}
	public StringNode setRight(StringNode n) {
		this.right = n;
		return this;
	}
	
	@Override
	public String toString() {		
		return this.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof StringNode) {
			return this.id.equals(((StringNode) obj).id);	
		}
		return false;
	}
}
