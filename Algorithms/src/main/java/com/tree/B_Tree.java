package com.tree;

import com.Tuple2;
import com.Utils;

public class B_Tree<T extends Comparable<T>> {
		
	private final int t; 
	
	public BNode<T> root;		
	
	public B_Tree(int t) {
		if (t < 2) {
			throw new IllegalArgumentException("t must be >= 2");
		}
		this.t = t;
	}
	
	//read node from disk
	private BNode<T> diskRead(BNode<T> n, int childIndex) {		
		return n.getPrivateChild(childIndex);
	}
	
	//write node to disk
	private void diskWrite(BNode<T> n) { }
	
	//create page on disk	
	private BNode<T> allocateNode() {
		BNode<T> node = new BNode<>(this.t);
		node.isLeaf = true;
		return node;
	} 
	
	//TODO: do we need freeNode(node) ?
	
	/**
	 * Print keys in alphabetical order.
	 */
	public void traverseSorted(BNode<T> n) {
		int i = 0;
		for (; i < n.getKeysNumber(); i ++) {
			if (!n.isLeaf) {
				traverseSorted(this.diskRead(n, i));
			}
			System.out.print(n.getKey(i) + " ");
		} 
		if (!n.isLeaf) {
			traverseSorted(diskRead(n, i));
		}			
	}
			
	public Tuple2<BNode<T>, Integer> search(BNode<T> n, T key) {
		while (true) {
			int i = n.getKeyIndex(key);
			if (i >= 0) { //found
				return new Tuple2<BNode<T>, Integer>(n, i);
			} else if (!n.isLeaf) {		
				int ind = -(i + 1);
				n = diskRead(n, ind);
			} else {
				return null;
			}
		}
	}	
	
	public Tuple2<BNode<T>, Integer> search2(BNode<T> n, T key) {
		int i;
		for (i = 0; i < n.getKeysNumber() && key.compareTo(n.getKey(i)) > 0; i ++);		
		if (i < n.getKeysNumber() && key.equals(n.getKey(i))) { //found
			return new Tuple2<BNode<T>, Integer>(n, i);
		} else if (!n.isLeaf) {
			return search2(this.diskRead(n, i), key);
		} else {
			return null;
		}
	}
	
	public T min(BNode<T> n) {
		for (; !n.isLeaf; n = this.diskRead(n, 0));
		return n.getKey(0);
	}
	
	public T max(BNode<T> n) {
		for (;!n.isLeaf; n =  diskRead(n, n.getKeysNumber()));
		return n.getKey(n.getKeysNumber() - 1);
	}
	
	public void insert(T key) {
		if (root == null) {
			root = this.allocateNode();
		}
		
		if (this.isNodeFull(root)) {
			BNode<T> tmp = root;
			
			root = this.allocateNode();
			root.isLeaf = false;
			root.addChild(0, tmp);

			this.splitNode(root, 0);
		}
		
		insertNotFull(root, key);
	}

	private void insertNotFull(BNode<T> node, T key) {
		int keyIndex = this.getPossibleKeyIndex(node, key);		
		if (node.isLeaf) {
			node.addKey(keyIndex, key);
			diskWrite(node);
		} else {
			int childIndex = keyIndex;
			BNode<T> child = this.diskRead(node, childIndex);
			if (this.isNodeFull(child)) {
				this.splitNode(node, childIndex);
			}
			node = child;
			
			insertNotFull(node, key);
		}		
	}

	private void splitNode(BNode<T> parent, int childIndex) {		
		BNode<T> n = diskRead(parent, childIndex);		
		int medianIndex = this.getMedianIndex();	
		
		BNode<T> newNode = this.allocateNode();
		int newKeyIndex = this.getPossibleKeyIndex(parent, n.getKey(medianIndex));
		parent.addKey(newKeyIndex, n.getKey(medianIndex)); //move median key to parent
		parent.addChild(childIndex + 1, newNode);
		
		for (int i = medianIndex + 1, j = 0; i < n.getKeysNumber(); i ++) { //mode keys from node to newNode
			newNode.addKey(j ++, n.getKey(i));
		}
		for (int i = n.getKeysNumber() - 1; i >= medianIndex; i --) { //delete keys from node
			n.removeKey(i);
		}
		
		diskWrite(parent);
		diskWrite(n);
		diskWrite(newNode);
	}
	
	private int getMedianIndex() {
		return t - 1;
	}

	//ind >= 0
	private int getPossibleKeyIndex(BNode<T> node, T key) {
		int i = node.getKeyIndex(key);
		return i >= 0 ? i : -(i + 1);
	}

	private boolean isNodeFull(BNode<T> node) {
		return node.getKeysNumber() == 2 * t - 1;
	}
	
	public void print(BNode<T> node, int indent) {		
		System.out.println(Utils.ind(indent) + node);
		if (!node.isLeaf) {
			for (int i = 0; i < node.getKeysNumber() + 1; i ++) { 
				print(diskRead(node, i), indent + 1);
			}	
		}		
	}
	
	public static void main(String[] args) {
		B_Tree<Integer> tree = new B_Tree<>(2);
		
		int[] input = new int[] {15, 6, 18, 3, 7, 17, 20, 2, 4, 13, 9/*, 8, 22*/};
		for (int i : input) {
			tree.insert(i);						
		}
		
		tree.print(tree.root, 0);
		
		System.out.println();
		
		//search
		for (int i : input) {
			Tuple2<BNode<Integer>, Integer> res = tree.search2(tree.root, i);
			System.out.printf("Search: %d, found: %s\n", i, 
				(res != null ? (String.format("[%d, %s]", res._2, res._1)) : "<not found>"));
		}
		
		tree.traverseSorted(tree.root);
		
		System.out.printf("%nMin: %s%n", tree.min(tree.root));
		System.out.printf("Max: %s%n", tree.max(tree.root));
	}
	
}
