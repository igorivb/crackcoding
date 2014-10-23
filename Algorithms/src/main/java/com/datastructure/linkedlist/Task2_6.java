package com.datastructure.linkedlist;

/**
 * Given a circular linked list, implement an algorithm which returns the node at the
 * beginning of the loop
 */
public class Task2_6 {
	
	static <T> SingleLinkedNode<Integer> getLoopCollisionPoint(SingleLinkedNode<Integer> node) {
		SingleLinkedNode<Integer> slow = node;
		SingleLinkedNode<Integer> fast = node;
		
		int count = 0;
		while (slow != null && fast != null) {
			count ++;
			
			slow = slow.next;
			fast = fast.next != null ? fast.next.next : null;
			
			if (slow.value == 6) {
				System.out.println("Slow = " + slow + ", fast = " + fast);
			}
			
			if (slow != null && fast != null && slow == fast) {
				System.out.printf("Collision point: %s%n", slow);
				return slow;
			}									
		}
		return null;
	}
	
	public static <T> boolean hasLoop(SingleLinkedNode<Integer> node) {
		SingleLinkedNode<Integer> collisionPoint = getLoopCollisionPoint(node);
		return collisionPoint != null;
	}
	
	public static <T> SingleLinkedNode<Integer> getLoopStart(SingleLinkedNode<Integer> node) {
		SingleLinkedNode<Integer> collisionPoint = getLoopCollisionPoint(node);		
		if (collisionPoint != null) {			
			for (SingleLinkedNode<Integer> start = node; start != collisionPoint;) {
				start = start.next;
				collisionPoint = collisionPoint.next;
			}
			return collisionPoint;
		}		
		return null;
	}
	
	public static void main(String[] args) {
		SingleLinkedNode<Integer> n0 = new SingleLinkedNode<>(0);
		SingleLinkedNode<Integer> n1 = new SingleLinkedNode<>(1);
		SingleLinkedNode<Integer> n2 = new SingleLinkedNode<>(2);
		SingleLinkedNode<Integer> n3 = new SingleLinkedNode<>(3);
		SingleLinkedNode<Integer> n4 = new SingleLinkedNode<>(4);
		SingleLinkedNode<Integer> n5 = new SingleLinkedNode<>(5); 
		SingleLinkedNode<Integer> n6 = new SingleLinkedNode<>(6); //loop start
		SingleLinkedNode<Integer> n7 = new SingleLinkedNode<>(7);
		SingleLinkedNode<Integer> n8 = new SingleLinkedNode<>(8);
		SingleLinkedNode<Integer> n9 = new SingleLinkedNode<>(9);
		SingleLinkedNode<Integer> n10 = new SingleLinkedNode<>(10);
		
		n0.next = n1;
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		n8.next = n9;
		n9.next = n10;		
		n10.next = n6;
		
//		//2nd
//		SingleLinkedNode<Integer> n = new SingleLinkedNode<>(1);
//		n.next = n;
		
		SingleLinkedNode<Integer> loopStart = getLoopStart(n0);
		System.out.println("Has loop: " + loopStart != null);
		System.out.println("Loop start point: " + loopStart);
	}
}
