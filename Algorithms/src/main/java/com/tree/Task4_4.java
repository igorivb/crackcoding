package com.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree, design an algorithm which creates a linked list of all the nodes at
 * each depth (e.g., if you have a tree with depth D, you'll have D linked lists
 */
public class Task4_4 {
	
	public static void createListWithDFS(IntNode n, int d, List<List<IntNode>> list) {
		if (n != null) {
			List<IntNode> elems;
			if (list.size() > d) {
				elems = list.get(d);
			} else {
				elems = new ArrayList<>();
				list.add(elems);	
			}					
			elems.add(n);
			
			createListWithDFS(n.left, d + 1, list);
			createListWithDFS(n.right, d + 1, list);
		}
	}
	
	public static List<List<IntNode>> createListWithBFS(IntNode n) {
		List<List<IntNode>> result = new ArrayList<>();
		
		List<IntNode> parents = new ArrayList<>();
		parents.add(n);		
		while (!parents.isEmpty()) {
			result.add(parents);
			List<IntNode> current = parents;
			parents = new ArrayList<>();
			
			for (IntNode node : current) {
				if (node.left != null) {
					parents.add(node.left);
				}
				if (node.right != null) {
					parents.add(node.right);
				}
			}							
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		int[] mas = {0, 1, 3, 5, 7, 10, 12};
		IntNode n = Task4_3.createTree(mas);
				
		//DFS
		//List<List<IntNode>> list = new ArrayList<>();		
		//createListWithDFS(n, 0, list);
		
		//BFS
		List<List<IntNode>> list = createListWithBFS(n);
		
		for (int i = 0; i < list.size(); i ++) {
			System.out.print("Depth " + i + ": ");
			for (IntNode node : list.get(i)) {
				System.out.print(node.val + " ");
			}
			System.out.println();
		}
	}
}
