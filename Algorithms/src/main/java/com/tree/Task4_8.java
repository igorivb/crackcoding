package com.tree;

/**
 * You have two very large binary trees: Tl, with millions of nodes, and T2, with
 * hundreds of nodes. Create an algorithm to decide ifT2 is a subtree ofTl.
 * A tree T2 is a subtree of Tl if there exists a node n in Tl such that the subtree ofn
 * is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical.
 */
public class Task4_8 {

	public static boolean hasSubtree(StringNode n1, StringNode n2) {
		if (n1 == null) {
			return false;
		}
		if (n1.equals(n2) && isEqual(n1, n2)) {
			return true;
		}				
		
		return hasSubtree(n1.left, n2) || hasSubtree(n1.right, n2);		
	}

	private static boolean isEqual(StringNode n1, StringNode n2) {
		if (n1 == null && n2 == null) {
			return true;
		}
		if (n1 != null ? !n1.equals(n2) : n2 != null) {
			return false;
		}
		return isEqual(n1.left, n2.left) && isEqual(n1.right, n2.right);
	}
	
	public static void main(String[] args) {
		StringNode n1 = new StringNode("1")
			.setLeft(new StringNode("1").setRight(new StringNode("2")))
			.setRight(new StringNode("5"));
		
		StringNode n2 = new StringNode("1")
			.setRight(new StringNode("2")/*.setRight(new StringNode("3"))*/);
			
		
		System.out.println(hasSubtree(n1, n2));
	}
}
