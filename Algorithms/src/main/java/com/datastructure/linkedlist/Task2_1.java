package com.datastructure.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * Write code to remove duplicates from an unsorted linked list.
 * FOLLOW UP
 * How would you solve this problem if a temporary buffer is not allowed?
 */
public class Task2_1 {	
	
	public static void removeDuplicates(SingleLinkedNode n) {		
		Set<Object> map = new HashSet<>();
		for (SingleLinkedNode prev = null; n != null; n = n.next) {
			if (map.contains(n.value)) {
				prev.next = n.next;
				//n.next = null;
			} else {
				map.add(n.value);
				prev = n;
			}
		}
	}
	
	public static void removeDuplicatesNoBuffer(SingleLinkedNode node) {
		for (SingleLinkedNode i = node; i != null; i = i.next) {			
			for (SingleLinkedNode j = i; j.next != null;) {
				if (i.value.equals(j.next.value)) {
					j.next = j.next.next; //remove
				} else {
					j = j.next;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		SingleLinkedNode n1 = new SingleLinkedNode("a");
		SingleLinkedNode n2 = new SingleLinkedNode("b");
		SingleLinkedNode n3 = new SingleLinkedNode("a");
		SingleLinkedNode n4 = new SingleLinkedNode("a");
		SingleLinkedNode n5 = new SingleLinkedNode("c");
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		removeDuplicatesNoBuffer(n1);
		
		for (SingleLinkedNode n = n1; n != null; n = n.next) {
			System.out.println(n.value);
		}
	}
}
