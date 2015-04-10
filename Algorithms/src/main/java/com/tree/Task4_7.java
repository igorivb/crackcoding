package com.tree;


/**
 * Design an algorithm and write code to find the first common ancestor of two nodes
 * in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not
 * necessarily a binary search tree
 */
public class Task4_7 {

	static class Node {
		final String id;		
		Node left, right;
		public Node(String id) {
			this.id = id;
		}
		public Node setLeft(Node n) {
			this.left = n;
			return this;
		}
		public Node setRight(Node n) {
			this.right = n;
			return this;
		}	
		@Override
		public String toString() {		
			return this.id;
		}
	} 
	
	static class SearchResult {
		boolean n1, n2;
	}
	
	/**
	 * Simple version. Has bug when nodes are nested.
	 */
	public Node findFirstCommonAncestor1(Node root, Node n1, Node n2) {
		SearchResult res = new SearchResult();
		
		if (root == null) {
			return null;
		}
				
		find(root.left, n1, n2, res); //search in left side
		
		if (res.n1 && res.n2) {
			return findFirstCommonAncestor1(root.left, n1, n2);
		} else if (res.n1 == false && res.n2 == false) {
			return findFirstCommonAncestor1(root.right, n1, n2); //search in right side
		} else {
			return root; //result
		}
	}
	
	void find(Node root, Node n1, Node n2, SearchResult res) {
		if (root != null) {
			if (root == n1) {
				res.n1 = true;
			} 
			if (root == n2) {
				res.n2 = true;
			}
			
			if (!res.n1 || !res.n2) {			
				find(root.left, n1, n2, res);
				find(root.right, n1, n2, res);
			}	
		}						
	}
	
	
	/**
	 * Optimized.
	 * Assumes that n1 and n2 are in tree. 
	 * TODO: handle that nodes may be not present in tree.
	 */
	public Node findFirstCommonAncestor2(Node root, Node n1, Node n2) {
		if (root == null) {
			return null;
		}
			
		Node currentRes = null;		
		if (root == n1) {
			currentRes = n1;
		} else if (root == n2) {
			currentRes = n2;
		}
		
		Node resLeft = findFirstCommonAncestor2(root.left, n1, n2);
		if (resLeft != null) {
			if (currentRes != null) {
				return null; //no result because nodes contain each other	
			}
			if (resLeft != n1 && resLeft != n2) {
				return resLeft; //bubble up result
			}			
		}
		
		Node resRight = findFirstCommonAncestor2(root.right, n1, n2);
		if (resRight != null) {
			if (currentRes != null) {
				return null; //no result because nodes contain each other	
			}
			if (resRight != n1 && resRight != n2) {
				return resRight; //bubble up result
			}
		}
				
		if (resLeft != null && resRight != null) {
			return root; //common ancestor
		}
		if (currentRes != null) {
			return currentRes;
		}
		
		return resLeft != null ? resLeft : resRight;	
	}
	
	/**
	 * From crack coding book. 
	 */
	public Node commonAncestorBad(Node root, Node p, Node q) {
		if (root == null) {
			return null;
		}
		if (root == p && root == q) {
			return root;
		}

		Node x = commonAncestorBad(root.left, p, q);
		if (x != null && x != p && x != q) { // Already found ancestor
			return x;
		}

		Node y = commonAncestorBad(root.right, p, q);
		if (y != null && y != p && y != q) { // Already found ancestor
			return y;
		}
		if (x != null && y != null) { // p and q found in diff. subtrees
			return root; // This is the common ancestor
		} else if (root == p || root == q) {
			return root;
		} else {
			/* If either x or y is non-null, return the non-null value */
			return x == null ? y : x;
		}

	}
	
	
	
	/**
	 * Optimized.
	 * Assumes that n1 and n2 are in tree. 
	 * Handle that nodes may be not present in tree.
	 */
	public Node findFirstCommonAncestor3(Node root, Node n1, Node n2) {
		Node res = findFirstCommonAncestor3Internal(root, n1, n2);
		return res == n1 || res == n2 ? null : res;
	}
	private Node findFirstCommonAncestor3Internal(Node root, Node n1, Node n2) {
		if (root == null) {
			return null;
		}		
		
		Node currentRes = root == n1 ? n1 : (root == n2 ? n2 : null);
		
		//search in left side
		Node resLeft = findFirstCommonAncestor3Internal(root.left, n1, n2);
		if (resLeft != null) {
			if (currentRes != null) {
				return null; //no result because nodes contain each other	
			}
			if (resLeft != n1 && resLeft != n2) {
				return resLeft; //bubble up result
			}			
		}
		
		//search in right side
		Node resRight = findFirstCommonAncestor3Internal(root.right, n1, n2);
		if (resRight != null) {
			if (currentRes != null) {
				return null; //no result because nodes contain each other	
			}
			if (resRight != n1 && resRight != n2) {
				return resRight; //bubble up result
			}
		}
				
		if (resLeft != null && resRight != null) {
			return root; //common ancestor
		}
		
		//TODO: possible issue
		if (currentRes != null) {
			return currentRes;
		}
		
		return resLeft != null ? resLeft : resRight;	
	}	
	
	
	
	public static void main(String[] args) {
		Node root = new Node("3");
		Node n1 = new Node("5");
		Node n2 = new Node("15");
		
		//case-1: nodes are nested, no result
//		root.setRight(n1.setLeft(n2));
		
		//case-2: normal
		root.setLeft(n1).setRight(n2);
		
		//case-3: normal with a little bit complicated structure of tree
//		root
//			.setLeft(
//				new Node("1")
//					.setLeft(
//						n1.setLeft(new Node("8"))
//					)
//			)
//			.setRight(n2);
//		
//		root = new Node("22").setRight(root);
		
		
		//case-4: search where node is not present in tree		
//		root.setLeft(n1).setRight(n2);
//		n2 = new Node("unknown");
		
				
		Task4_7 task = new Task4_7();			
		Node successor = task.findFirstCommonAncestor3(root, n1, n2);
		
		
		
		System.out.println(successor);
	}
	
}
