package com.tree;

import com.Utils;

public class BinarySearchTree<T extends Comparable<T>> {
	
	public TreeNode<T> root;
	
	public TreeNode<T> search(TreeNode<T> n, T key) {
		for (; n != null && !n.key.equals(key); n = key.compareTo(n.key) < 0 ? n.left : n.right);
		return n;
	}
	
	public TreeNode<T> minimum(TreeNode<T> n) {
		TreeNode<T> min = n;
		for (; min.left != null; min = min.left);
		return min;
	}
	
	public TreeNode<T> maximum(TreeNode<T> n) {
		TreeNode<T> max = n;
		for (; max.right != null; max = max.right);
		return max;
	}
	
	public TreeNode<T> predecessor(TreeNode<T> n) {
		if (n.left != null) {
			return maximum(n.left);
		} else {			
			for (; n.p != null && n.p.isLeftChild(n); n = n.p);
			return n.p;			
		}
	}
	
	public TreeNode<T> successor(TreeNode<T> n) {
		if (n.right != null) {
			return minimum(n.right);
		} else {			
			for (; n.p != null && n.p.isRightChild(n); n = n.p);
			return n.p;			
		}
	}
	
	public void insert(TreeNode<T> n) {		
		TreeNode<T> parent = root;
		while (parent != null) {
			TreeNode<T> tmp = parent;
			
			if (n.key.compareTo(parent.key) < 0) { //left														
				if ((parent = parent.left) == null) {
					tmp.setLeft(n);
				}						
			} else if ((parent = parent.right) == null) { //right
				tmp.setRight(n);					
			}
		}
		
		if (root == null) {
			root = n;
		}		
	}
	
	public void insert2(TreeNode<T> n) {
		TreeNode<T> cur = null;
		for (TreeNode<T> p = root; p != null; p = n.key.compareTo(p.key) < 0 ? p.left : p.right) {
			cur = p;	
		}
							
		n.p = cur;
		if (cur == null) {
			root = n;
		} else if (n.key.compareTo(cur.key) < 0) {
			cur.left = n;
		} else {
			cur.right = n;
		}					
	}	
	
	public void inOrderTreeWalk(TreeNode<T> n) {
		if (n == null) return;
		
		inOrderTreeWalk(n.left);
		
		visit(n);
		
		inOrderTreeWalk(n.right);
	}	
	
	public void preOrderTreeWalk(TreeNode<T> n) {
		if (n == null) return;
		
		visit(n);
		
		preOrderTreeWalk(n.left);				
		
		preOrderTreeWalk(n.right);
	}			

	public void postOrderTreeWalk(TreeNode<T> n) {
		if (n == null) return;
		
		postOrderTreeWalk(n.left);				
		
		postOrderTreeWalk(n.right);
		
		visit(n);
	}	
	
	protected void visit(TreeNode<T> n) {
		System.out.print(n + " ");		
	}
	
	public void setRoot(TreeNode<T> n) {
		root = n;
		if (root != null) {
			root.p = null;	
		}			
	}
	
	public void delete(TreeNode<T> n) {
		TreeNode<T> p = n.p;
		if (n.left == null && n.right == null) { //no children
			if (p != null) {
				p.replaceChild(n, null);
			} else {
				setRoot(null);
			}
		} else if (n.left == null || n.right == null) { //1 child
			TreeNode<T> child = n.left != null ? n.left : n.right;
			if (p != null) {
				p.replaceChild(n, child);
			} else {
				setRoot(child);
			}
		} else { //2 children
			TreeNode<T> min = minimum(n.right);
			TreeNode<T> minParent = min.p;
			TreeNode<T> minRight = min.right;
			
			if (p != null) {
				p.replaceChild(n, min);
			} else {
				setRoot(min);
			}
			
			min.setLeft(n.left);
			
			if (min != n.right) {
				min.setRight(n.right);
				minParent.setLeft(minRight);
			}
		}		
	}
	
	public void delete1(TreeNode<T> n) {
		TreeNode<T> p = n.p; // can be null
			
		if (n.left == null && n.right == null) { //no children			
			if (p != null) { //delete node from parent
				p.replaceChild(n, null);
			}
		} else if ((n.left != null) ^ (n.right != null)) { //node has only one child
			TreeNode<T> child = n.left != null ? n.left : n.right;
						
			if (p != null) { //delete node from parent
				p.replaceChild(n, child);
			} else {
				root = child;
				child.p = null;
			}
		} else { //both children
			TreeNode<T> min = minimum(n.right);
			TreeNode<T> minRight = min.right; //can be null
			TreeNode<T> minParent = min.p;

			if (p != null) {
				p.replaceChild(n, min);				
			} else {
				root = min;
				min.p = null;
			}
			
			min.setLeft(n.left);
			min.setRight(n.right);
			
			minParent.setLeft(minRight);
		}
	}	

	public static void main(String[] args) {				
//		TreeNode<Integer> root = new TreeNode<>(15);
//		root
//			.setLeft(new TreeNode<>(6)
//				.setLeft(new TreeNode<>(3)
//					.setLeft(new TreeNode<>(2))
//					.setRight(new TreeNode<>(4))
//				)
//				.setRight(new TreeNode<>(7)
//					.setRight(new TreeNode<>(13)
//						.setLeft(new TreeNode<>(9))
//					)
//				)
//			)
//			.setRight(new TreeNode<>(18)
//				.setLeft(new TreeNode<>(17))
//				.setRight(new TreeNode<>(20))
//			);
		
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		
		int[] input = new int[] {15, 6, 18, 3, 7, 17, 20, 2, 4, 13, 9};
		for (int i : input) {
			tree.insert(new TreeNode<Integer>(i));
		}
		
		tree.inOrderTreeWalk(tree.root);
		
		System.out.println("\n" + tree.search(tree.root, 13));
		
		System.out.println(tree.successor(tree.search(tree.root, 13)));
		
		//delete
		tree.delete(tree.search(tree.root, 15));
		tree.delete(tree.search(tree.root, 6));
		tree.delete(tree.search(tree.root, 17));
		tree.inOrderTreeWalk(tree.root);
	}
	
	public void printHierarchy(TreeNode<T> n) {
		printHierarchy(n, 0);
	}
	
	public void printHierarchy(TreeNode<T> n, int indent) {
		System.out.println(n);
		if (n.left != null) {
			System.out.print(Utils.ind(indent + 1) + "left: ");
			printHierarchy(n.left, indent + 1);
		}
		if (n.right != null) {
			System.out.print(Utils.ind(indent + 1) + "right: ");
			printHierarchy(n.right, indent + 1);
		}
	}

}
