package com.tree;

/**
 * Write an algorithm to find the 'next'node (i.e., in-order successor) of a given node in
 * a binary search tree. You may assume that each node has a link to its parent.
 */
public class Task4_6 {

	static class Node {
		final String id;
		Node parent;
		Node left, right;
		public Node(String id) {
			this.id = id;
		}
		public Node setLeft(Node n) {
			this.left = n;
			n.parent = this;
			return this;
		}
		public Node setRight(Node n) {
			this.right = n;
			n.parent = this;
			return this;
		}		
	} 
	
	public Node successor(Node n) {
		if (n.right != null) {
			//find min in right sub tree
			for (n = n.right; n.left != null; n = n.left);
			return n;
			
		} else {
			//find first parent where n is left child
			for (; n.parent != null && n.parent.left != n; n = n.parent);
			return n.parent;
		}
	}
	
	public static void main(String[] args) {
		Node root = new Node("3");
		Node n = new Node("5");
		root.setRight(
			new Node("10")
				.setLeft(n)
		);
		
		Task4_6 task = new Task4_6();		
		Node successor = task.successor(root.right.left);
		System.out.println(successor.id);
				
	}
}
