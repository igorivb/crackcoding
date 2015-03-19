package com.tree;

/**
 * Implement a function to check if a binary tree is balanced. For the purposes of this
 * question, a balanced tree is defined to be a tree such that the heights of the two
 * subtrees of any node never differ by more than one
 */
public class Task4_1 {
	
	private static class Node {
		final String key;
		Node left;
		Node right;
		public Node(String key) {
			this.key = key;
		}				
		public Node setLeft(Node left) {
			this.left = left;
			return this;
		}		
		public Node setRight(Node right) {
			this.right = right;
			return this;
		}
		@Override
		public String toString() {		
			return this.key;
		}
	} 
			
	public boolean isTreeBalanced(Node root) {
		return maxHeight(root) >= 0;
	} 
	
	int maxHeight(Node n) {
		if (n == null) { 
			return 0;
		}	
		
		int leftHeight = maxHeight(n.left);
		if (leftHeight == -1) {
			return -1;
		}
		int rightHeight = maxHeight(n.right);
		if (rightHeight == -1) {
			return -1;
		}
		
		int diff = Math.abs(leftHeight - rightHeight);
		if (diff > 1) {
			System.out.printf("Not balanced: %s, left: %d, right: %d\n", n, leftHeight, rightHeight);
			return -1;
		}
		
		return Math.max(leftHeight, rightHeight) + 1;
	}
	
	public static void main(String[] args) {
		Node root = new Node("1");
		root
			.setLeft(
				new Node("2")
					.setLeft(
						new Node("3")
							.setLeft(new Node("5"))
					)
			)
			.setRight(
				new Node("22")
					.setLeft(
						new Node("4")
							.setLeft(new Node("6"))
					)
			);
		
		System.out.println(new Task4_1().isTreeBalanced(root));
	}

}
