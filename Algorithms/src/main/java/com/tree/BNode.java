package com.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BNode<T extends Comparable<T>> {

	//sorted
	private List<T> keys;
	
	//sorted according to keys
	private List<BNode<T>> children;
	
	public boolean isLeaf = true;		
		
	public BNode(int t) {
		keys = new ArrayList<>(2 * t - 1);
		children = new ArrayList<>(2 * t);
	}
	
	public void addChild(int index, BNode<T> child) {
		children.add(index, child);	
	}	
	
	public void addKey(int index, T key) {
		this.keys.add(index, key);
	}
	
	public int getKeysNumber() {
		return this.keys.size();
	}
	
	public T getKey(int index) {
		return this.keys.get(index);
	}
	
	//can be called by B_Tree now
	BNode<T> getPrivateChild(int index) {
		return this.children.get(index);
	}
	
	public void removeKey(int index) {
		this.keys.remove(index);
	}
	
	/**
	 * Can be negative.
	 */
	public int getKeyIndex(T key) {
		return Collections.binarySearch(this.keys, key);
	}
	
	@Override
	public String toString() {
		return String.format("Leaf: %b, keys number: %d, keys: %s", isLeaf, this.getKeysNumber(), this.keys);
	}
	
//	public List<T> getKeys() {
//		return this.keys;
//	}
//	
//	public List<BNode<T>> getChildren() {
//		return this.children;
//	}
	
	
}
