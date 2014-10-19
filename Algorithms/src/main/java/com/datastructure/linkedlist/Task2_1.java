package com.datastructure.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * Write code to remove duplicates from an unsorted linked list.
 * FOLLOW UP
 * How would you solve this problem if a temporary buffer is not allowed?
 */
public class Task2_1 {

	static class Node {
		Object value;
		Node next;
		public Node(Object value) {
			this.value = value;
		}
	}
	
	public static void removeDuplicates(Node n) {		
		Set<Object> map = new HashSet<>();
		for (Node prev = null; n != null; n = n.next) {
			if (map.contains(n.value)) {
				prev.next = n.next;
				//n.next = null;
			} else {
				map.add(n.value);
				prev = n;
			}
		}
	}
	
	public static void removeDuplicatesNoBuffer(Node node) {
		for (Node i = node; i != null; i = i.next) {			
			for (Node j = i; j.next != null;) {
				if (i.value.equals(j.next.value)) {
					j.next = j.next.next; //remove
				} else {
					j = j.next;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Node n1 = new Node("a");
		Node n2 = new Node("b");
		Node n3 = new Node("a");
		Node n4 = new Node("a");
		Node n5 = new Node("c");
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		removeDuplicatesNoBuffer(n1);
		
		for (Node n = n1; n != null; n = n.next) {
			System.out.println(n.value);
		}
	}
}
