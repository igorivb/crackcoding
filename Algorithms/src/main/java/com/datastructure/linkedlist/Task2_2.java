package com.datastructure.linkedlist;

/**
 * Implement an algorithm to find the kth to last element of a singly linked list.
 */
public class Task2_2 {

	static class Node {
		Object value;
		Node next;
		public Node(Object value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return this.value.toString();
		}
	}
	
	static class Result {
		Node node;
	}
	
	public static Node findElem(Node node, int k) {
		int size = 0;
		for (Node n = node; n != null; n = n.next, size ++);
		if (k >= size) {
			return null;
		}
		Node n = node;
		for (int i = 0; i < size - k - 1; i ++, n = n.next);
		return n;
	}
	
	public static Node findElemRecursive(Node node, int k) {
		Result res = new Result();
		findElemRecursive0(node, k, res);
		return res.node;
	}
	
	static int findElemRecursive0(Node node, int k, Result res) {
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
		Node n1 = new Node("a");
		Node n2 = new Node("b");
		Node n3 = new Node("a1");
		Node n4 = new Node("a2");
		Node n5 = new Node("c");
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		for (int i = 0; i <= 5; i ++) {
			Node res1 = findElem(n1, i);
			Node res2 = findElemRecursive(n1, i);
			System.out.printf("%2s. %2s, %2s%n", i, res1, res2);
		}
		
	}
}
