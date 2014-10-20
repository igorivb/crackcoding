package com.datastructure.linkedlist;

import java.util.Comparator;

/**
 * Write code to partition a linked list around a value x, such that all nodes less than x
 * come before all nodes greater than or equal to x
 */
public class Task2_4 {

	public static <T> SingleLinkedNode<T> partitionByX(SingleLinkedNode<T> node, T x, Comparator<T> cmp) {
		SingleLinkedNode<T> less = null, more = null, lastLess = null;
		for (SingleLinkedNode<T> n = node; n != null;) {
			SingleLinkedNode<T> next = n.next;			
			if (cmp.compare(n.value, x) < 0) {
				if (less == null) {
					lastLess = n;
				}
				n.next = less;
				less = n;				
			} else {				
				n.next = more;
				more = n;
			}			
			n = next;
		}
		
		//combine
		SingleLinkedNode<T> res = less;
		if (res == null) {
			res = more;
		} else {			
			lastLess.next = more; 
		}
		return res;
	}
	
	public static void main(String[] args) {
		SingleLinkedNode<String> n1 = new SingleLinkedNode<>("b");
		SingleLinkedNode<String> n2 = new SingleLinkedNode<>("c");
		SingleLinkedNode<String> n3 = new SingleLinkedNode<>("d");
		SingleLinkedNode<String> n4 = new SingleLinkedNode<>("e");
		SingleLinkedNode<String> n5 = new SingleLinkedNode<>("a");
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		SingleLinkedNode<String> res = partitionByX(n1, "f", String.CASE_INSENSITIVE_ORDER);
		for (SingleLinkedNode<String> n = res; n != null; n = n.next) {			
			System.out.printf("%s", n.value);
		}
	}
}
