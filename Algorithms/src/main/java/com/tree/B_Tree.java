package com.tree;

import java.util.Collections;

import com.Tuple2;

/**
 * TODO:
 *   1. handle read and write
 *   2. implement to the end
 *   3. re-implement all again
 */
public class B_Tree<T extends Comparable<T>> {
		
	private final int t; 
	
	private BNode<T> root;		
	
	public B_Tree(int t) {
		if (t < 2) {
			throw new IllegalArgumentException("t must be >= 2");
		}
		this.t = t;
	}
	
	//read node from disk
	private BNode<T> diskRead(BNode<T> n) {		
		return n;
	}
	
	//write node to disk
	private void diskWrite(BNode<T> n) { }
	
	//create page on disk
	private BNode<T> allocateNode() {
		BNode<T> node = new BNode<>();
		node.isLeaf = true;
		return node;
	} 
	
	public Tuple2<BNode<T>, Integer> search(BNode<T> n, T key) {
		do {
			int i = Collections.binarySearch(n.getKeys(), key);
			if (i >= 0) { //found
				return new Tuple2<BNode<T>, Integer>(n, i);
			} else if (!n.isLeaf) {		
				int ind = -(i + 1);
				n = diskRead(n.getChildren().get(ind));
			}
		} while (!n.isLeaf);
		
		return null;
	}	
	
	public void insert(T key) {
		if (root == null) {
			root = this.allocateNode();
		}
				
		if (isNodeFull(root)) {
			BNode<T> tmp = root;
			
			//create new root as parent of current root
			root = this.allocateNode();
			root.isLeaf = false;
			
			root.addChild(0, tmp);
			
			splitNode(root, 0);
		}
			
		insertNotFullNode(root, key);
	}
	
	private void insertNotFullNode(BNode<T> node, T key) {
		int childInd = findPossibleChildIndex(node, key);
		
		if (!node.isLeaf) {						
			BNode<T> child = node.getChildren().get(childInd);
			if (isNodeFull(child)) {
				splitNode(node, childInd);
			} 
			
			insertNotFullNode(child, key);		
		} else {			
			node.addKey(childInd, key);
		}
	}

	private void splitNode(BNode<T> parent, int childIndex) {
		BNode<T> node = parent.getChildren().get(childIndex);						
		BNode<T> newNode = this.allocateNode();
		
		int median = getNodeMedianIndex();
		parent.addKey(childIndex, node.getKeys().get(median)); //move median key to parent
		parent.addChild(childIndex + 1, newNode);
		
		//move keys to new node
		for (int i = median + 1, j = 0; i < node.getKeysNumber(); i ++) {
			newNode.addKey(j ++, node.getKeys().get(i));
		}
		for (int i = node.getKeysNumber() - 1; i >= median; i --) { //delete keys from node
			node.getKeys().remove(i);
		}		
	}
	
	private int getNodeMedianIndex() {
		return t - 1;
	}

	private boolean isNodeFull(BNode<T> node) {		
		return node.getKeysNumber() == 2 * t - 1;
	}
	
	private int findPossibleChildIndex(BNode<T> node, T key) {
		int ind = Collections.binarySearch(node.getKeys(), key);
		if (ind < 0) {
			ind = -(ind + 1);
		}
		return ind;
	}
}
