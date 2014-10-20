package com.datastructure.linkedlist;

/**
 * Implement an algorithm to find the kth to last element of a singly linked list.
 */
public class Task2_2 {

	static class Result {
		SingleLinkedNode node;
	}
	
	public static SingleLinkedNode findElem(SingleLinkedNode node, int k) {
		int size = 0;
		for (SingleLinkedNode n = node; n != null; n = n.next, size ++);
		if (k >= size) {
			return null;
		}
		SingleLinkedNode n = node;
		for (int i = 0; i < size - k - 1; i ++, n = n.next);
		return n;
	}
	
	public static SingleLinkedNode findElemRecursive(SingleLinkedNode node, int k) {
		Result res = new Result();
		findElemRecursive0(node, k, res);
		return res.node;
	}
	
	static int findElemRecursive0(SingleLinkedNode node, int k, Result res) {
		if (node == null) {
			return -1;
		}
		int i = findElemRecursive0(node.next, k, res) + 1;
		if (i == k) {
			res.node = node;
		}
		return i;
	}
	
	public static void main(String[] args) {
		SingleLinkedNode<String> n1 = new SingleLinkedNode<>("a");
		SingleLinkedNode<String> n2 = new SingleLinkedNode<>("b");
		SingleLinkedNode<String> n3 = new SingleLinkedNode<>("a1");
		SingleLinkedNode<String> n4 = new SingleLinkedNode<>("a2");
		SingleLinkedNode<String> n5 = new SingleLinkedNode<>("c");
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		for (int i = 0; i <= 5; i ++) {
			SingleLinkedNode<String> res1 = findElem(n1, i);
			SingleLinkedNode<String> res2 = findElemRecursive(n1, i);
			System.out.printf("%2s. %2s, %2s%n", i, res1, res2);
		}
		
	}
}
