package com.tree;

import java.util.List;

public class BNode<T extends Comparable<T>> {

	//TODO: handle that it can be null
	private List<T> keys;
	
	//TODO: handle that it can be null
	private List<BNode<T>> children;
	
	public boolean isLeaf = true;		
		
	public void addChild(int ind, BNode<T> child) {
		//TODO: implement		
	}	
	
	public void addKey(int ind, T key) {
		//TODO: implement
	}
	
	public int getKeysNumber() {
		//TODO look in keys
		return 0;
	}
	
	public List<T> getKeys() {
		return this.keys;
	}
	
	public List<BNode<T>> getChildren() {
		return this.children;
	}
	
	
}
