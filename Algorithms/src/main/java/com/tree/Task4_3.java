package com.tree;

import com.Utils;

/**
 * Given a sorted (increasing order) array with unique integer elements, write an algorithm
 * to create a binary search tree with minimal height.
 */
public class Task4_3 {
	
	public static IntNode createTree(int[] mas) {
		return createTree(mas, 0, mas.length);
	}
	
	static IntNode createTree(int[] mas, int s, int e) {
		if (e - s >= 1) {
			int mid = (s + e) >> 1;
			IntNode node = new IntNode(mas[mid]);			
			node.left = createTree(mas, s, mid);
			node.right = createTree(mas, mid + 1, e);
			return node;
		} else {
			return null;
		}
	}
	
	static void print(IntNode n, int ind) {
		if (n != null) {
			System.out.println(Utils.ind(ind) + n.val);
			print(n.left, ind + 1);
			print(n.right, ind + 1);	
		}
	}
	
	public static void main(String[] args) {
		//int[] mas = {1, 2, 4, 6};
		int[] mas = {0, 1, 3, 5, 7, 10, 12};
		
		IntNode n = createTree(mas);
		print(n, 0);
	}
}
