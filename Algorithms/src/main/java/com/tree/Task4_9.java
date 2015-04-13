package com.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given a binary tree in which each node contains a value. Design an algorithm
 * to print all paths which sum to a given value. The path does not need to start
 * or end at the root or a leaf
 */
public class Task4_9 {

	public List<List<IntNode>> listPaths(IntNode root, int expected) {
		List<List<IntNode>> res = new ArrayList<List<IntNode>>();
		if (root != null) {
			listPathsInt(root.left, expected, res);
			listPathsInt(root.right, expected, res);
		}		
		return res;
	}

	private void listPathsInt(IntNode n, final int expected, List<List<IntNode>> res) {
		if (n != null && !n.isLeaf()) {
			List<IntNode> path = new ArrayList<>();
			verifyPathSum(n, expected, 0, res, path);
		}
	}

	private void verifyPathSum(IntNode n, final int expected, int sum, List<List<IntNode>> res, List<IntNode> path) {
		if (n != null && !n.isLeaf()) {			
			path.add(n);			
			sum += n.val;
			
			if (sum == expected) {
				res.add(new ArrayList<>(path)); //found
			}
			
			this.verifyPathSum(n.left, expected, sum, res, path);
			this.verifyPathSum(n.right, expected, sum, res, path);		
			
			path.remove(path.size() - 1);
		}		
	}
	
	public static void main(String[] args) {
		IntNode root = new IntNode(6)		
			.setLeft(new IntNode(2)
				.setLeft(new IntNode(0)
					.setLeft(new IntNode(3)
						.setLeft(new IntNode(-2)
							.setLeft(new IntNode(2)
								.setLeft(new IntNode(0))
							)
							.setRight(new IntNode(1)
								.setRight(new IntNode(1)
									.setRight(new IntNode(6))
								)
							)
						)
					)
				)
				.setRight(new IntNode(1)
					.setRight(new IntNode(2)
						.setRight(new IntNode(6))
					)
				)
			)
			.setRight(new IntNode(5)
				.setRight(new IntNode(6))
			);
		
		Task4_9 task = new Task4_9();
		List<List<IntNode>> paths = task.listPaths(root, 5);
		
		//print paths
		for (int i = 0; i < paths.size(); i ++) {
			System.out.printf("%2d. Path: ", i + 1);
			List<IntNode> path = paths.get(i);
			for (IntNode n : path) {
				System.out.print(n.val + ", ");
			}
			System.out.println();
		}		
		
	}
}
