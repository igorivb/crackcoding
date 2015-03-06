package com.tree;

import static com.tree.TreeNode.Color.BLACK;
import static com.tree.TreeNode.Color.RED;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

	private void rightRotate(TreeNode<T> x) {
		TreeNode<T> y = x.left;
		if (x.p == null) {
			setRoot(y);
		} else {
			x.p.replaceChild(x, y);			
		}
		
		x.setLeft(y.right);
		
		y.setRight(x);
	}
	
	private void leftRotate(TreeNode<T> x) {
		TreeNode<T> y = x.right;
		if (x.p == null) {
			setRoot(y);
		} else {
			x.p.replaceChild(x, y);			
		}
		
		x.setRight(y.left);
		
		y.setLeft(x);
	}
	
	@Override
	public void insert(TreeNode<T> n) {
		super.insert(n);		
		n.color = RED;
		
		fixInsert(n);
	}
	
	private void fixInsert(TreeNode<T> z) {
		while (z.p != null && z.p.color == RED) {
			if (z.p == z.p.p.left) {
				TreeNode<T> y = z.p.p.right; //TODO: can be null ?
				if (y.color == RED) {
					y.color = BLACK;
					z.p.color = BLACK;
					z.p.p.color = RED;
					z = z.p.p;							
				} else {
					if (z == z.p.right) {
						z = z.p;						
						leftRotate(z);
					}
					z.p.color = BLACK;
					z.p.p.color = RED;
					rightRotate(z.p.p);
				}
			} else { //similar as above
				TreeNode<T> y = z.p.p.left;
				if (y.color == RED) {
					y.color = BLACK;
					z.p.color = BLACK;
					z.p.p.color = RED;
					z = z.p.p;							
				} else {
					if (z == z.p.left) {
						z = z.p;
						rightRotate(z);
					}
					z.p.color = BLACK;
					z.p.p.color = RED;
					leftRotate(z.p.p);
				}
			}			
		}
		root.color = BLACK;
	}

	@Override
	public void delete(TreeNode<T> n) {
		// TODO implement
		super.delete(n);
	}
	
	public static void main(String[] args) {				
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		
		TreeNode<Integer> root = new TreeNode<>(11, BLACK);
		root
			.setLeft(new TreeNode<>(2)
				.setLeft(new TreeNode<>(1, BLACK))
				.setRight(new TreeNode<>(7, BLACK)
					.setLeft(new TreeNode<>(5))					
					.setRight(new TreeNode<>(8))										
				)
			)
			.setRight(new TreeNode<>(14, BLACK)
				.setRight(new TreeNode<>(15))
			);
		
		tree.setRoot(root);		
		
		tree.insert(new TreeNode<Integer>(4));
		
		tree.printHierarchy(tree.root);
	}
}
