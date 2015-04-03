package com.tree;


/**
 * Implement a function to check if a binary tree is a binary search tree.
 */
public class Task4_5 {

	public static boolean isBinarySearchTreeOnRanges(IntNode n) {
		return isBinarySearchTree(n, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	static boolean isBinarySearchTree(IntNode n, int left, int right) {
		if (n == null) {
			return true;
		}
		if (n.val < left || n.val > right) {
			return false;
		}
			
		if (!isBinarySearchTree(n.left, left, n.val) || !isBinarySearchTree(n.right, n.val, right)) {
			return false;
		}		
		return true;
	}
	
	 
	
	static class IntWrapper {
		int val = 0;	
	}
			
	public static boolean isBinarySearchTreeOnSorting1(IntNode n) {
		int[] mas = new int[n.size()];
		copyBST1(n, mas, new IntWrapper());
		
		for (int i = 1; i < mas.length; i ++) {
			if (mas[i - 1] > mas[i]) {
				return false;
			}
		} 		
		return true;
	}
	
	static void copyBST1(IntNode n, int[] mas, IntWrapper index) {
		if (n != null) {
			copyBST1(n.left, mas, index);		
			mas[index.val ++] = n.val;		
			copyBST1(n.right, mas, index);	
		}	
	}
	
	
	
	
	
	public static boolean isBinarySearchTreeOnSorting2(IntNode n) {
		IntWrapper prev = new IntWrapper();
		prev.val = Integer.MIN_VALUE;		
		return copyBST2(n, prev);
	}
	
	static boolean copyBST2(IntNode n, IntWrapper prev) {
		if (n != null) {
			copyBST2(n.left, prev);
			
			if (n.val < prev.val) {				
				return false;
			} else {
				prev.val = n.val;
			}
			
			copyBST2(n.right, prev);									
		}			
		return true;
	}	

	public static void main(String[] args) {
		IntNode n = new IntNode(5);
		n.left = new IntNode(1);
		n.right = new IntNode(8);
		
		n.left.left = new IntNode(-1);
		n.left.right = new IntNode(6);//error				
		
		System.out.println(isBinarySearchTreeOnSorting1(n));
		System.out.println(isBinarySearchTreeOnSorting2(n));
	}
}
