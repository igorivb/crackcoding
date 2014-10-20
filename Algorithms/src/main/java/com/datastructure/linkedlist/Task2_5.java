package com.datastructure.linkedlist;

import static com.datastructure.linkedlist.SingleLinkedNode.insertBefore;

/**
 * You have two numbers represented by a linked list, where each node contains a
 * single digit. The digits are stored in reverse order, such that the 1 's digit is at the head
 * of the list. Write a function that adds the two numbers and returns the sum as a
 * linked list.
 * FOLLOW UP
 * Suppose the digits are stored in forward order. Repeat the above problem
 */
public class Task2_5 {

	static class NumNode extends SingleLinkedNode<Integer> {
		//public NumNode next; 								//TODO: Don't use it because of collision with SingleLinkedNode.next 	!!!
		public NumNode(Integer value) {
			super(value);
		}
	}
	
	public static NumNode sumForward(NumNode a, NumNode b) {
		int n1 = readForward(a);
		int n2 = readForward(b);
		int sum = n1 + n2;
		return storeForward(sum);
	}

	//12: 1->2
	static NumNode storeForward(int num) {
		NumNode node = null;
		for (int i = num; i > 0; i = i / 10) {
			int val = i % 10;
			NumNode newNode = new NumNode(val);
			newNode.next = node;
			node = newNode;
		}	
		return node;
	}

	//1->2: 12
	static int readForward(NumNode node) {
		int count = 0;
		for (NumNode n = node; n != null; n = (NumNode) n.next, count ++);
			
		int num = 0;
		for (NumNode n = node; n != null; n = (NumNode) n.next, count --) {
			num += n.value * Math.pow(10, count - 1);
		}		
		return num;
	}
	
	static void forward() {
		NumNode a1 = new NumNode(1);
		NumNode a2 = new NumNode(2);
		a1.next = a2;
		
		NumNode b1 = new NumNode(9);
		NumNode b2 = new NumNode(6);
		b1.next = b2;
		
		NumNode res = sumForward(a1, b1);
		System.out.println(SingleLinkedNode.toString(res));
	}
	
	
	//-------------------- Reverse
	
	public static NumNode sumReverse(NumNode n1, NumNode n2) {
		return sumReverse(n1, n2, 0);
	}
	
	static NumNode sumReverse(NumNode n1, NumNode n2, int carry) {
		if (n1 == null && n2 == null && carry == 0) {
			return null;
		}
		
		//sum
		int sum = carry;
		if (n1 != null) {
			sum += n1.value;
		}
		if (n2 != null) {
			sum += n2.value;
		}
		if (sum >= 10) {
			carry = 1;
			sum -= 10;
		} else {
			carry = 0;
		}
		
		NumNode curNode = new NumNode(sum);
		//recurse
		NumNode next = sumReverse(n1 != null ? (NumNode) n1.next : null, n2 != null ? (NumNode) n2.next : null, carry);
		curNode.next = next;
		
		return curNode;
	}
	
	static void reverse() {
		//12
		NumNode a1 = new NumNode(1);
		NumNode a2 = new NumNode(2);
		a2.next = a1;
		
		//96
		NumNode b1 = new NumNode(9);
		NumNode b2 = new NumNode(6);
		b2.next = b1;
		
		NumNode res = sumReverse(a2, b2);
		System.out.println("12+96");
		System.out.println(SingleLinkedNode.toString(res));
		
		//--- 2nd
		
		//198
		a1 = new NumNode(1);
		a2 = new NumNode(9);
		NumNode a3 = new NumNode(8);
		a3.next = a2;
		a2.next = a1;
		
		//78
		b1 = new NumNode(7);
		b2 = new NumNode(8);
		b2.next = b1;
		
		res = sumReverse(a3, b2);
		System.out.println("198+78");
		System.out.println(SingleLinkedNode.toString(res));
	}	
	
	
	
	//-------------------- Forward recursive
	
	static int len(NumNode node) {
		int len = 0;
		for (NumNode n = node; n != null; n = (NumNode) n.next, len ++);
		return len;		
	}
	
	static NumNode addZeros(NumNode node, int len) {
		for (int i = 0; i < len; i ++) { 			
			node = (NumNode) insertBefore(node, new NumNode(0));
		}	
		return node;
	}
	
	public static NumNode sumForwardRecursive(NumNode n1, NumNode n2) {
		//add zeros, so lists have the same length
		int l1 = len(n1);
		int l2 = len(n2);
		if (l1 < l2) {
			n1 = addZeros(n1, l2 - l1);
		} else if (l2 < l1) {
			n2 = addZeros(n2, l1 - l2);
		}
				
		Object[] res = sumForwardRecursive0(n1, n2);
		//handle carry
		NumNode resNode = (NumNode) res[0];
		if ((Integer) res[1] == 1) {
			resNode = (NumNode) insertBefore(resNode, new NumNode(1));
		}
		
		return resNode;
	}
	
	//[NumNode,carry]
	static Object[] sumForwardRecursive0(NumNode n1, NumNode n2) {
		if (n1 == null || n2 == null) {
			return new Object[]{ null, 0 };
		}	
				
		//go down
		Object[] next = sumForwardRecursive0((NumNode) n1.next, (NumNode) n2.next);
		
		int sum = n1.value + n2.value + (Integer) next[1];
		int carry = 0;					
		if (sum >= 10) {
			carry = 1;
			sum -= 10;
		}
		
		NumNode curNode = new NumNode(sum);
		curNode.next = (NumNode) next[0];
		
		return new Object[] {curNode, carry};
	}		
	
	static void forwardRecursive() {
		//12
		NumNode a1 = new NumNode(1);
		NumNode a2 = new NumNode(2);
		a1.next = a2;
		
		//96
		NumNode b1 = new NumNode(9);
		NumNode b2 = new NumNode(6);
		b1.next = b2;
		
		NumNode res = sumForwardRecursive(a1, b1);
		System.out.println("12+96 = " + SingleLinkedNode.toString(res));
		
		
		//--- 2nd
		//987
		a1 = new NumNode(9);
		a2 = new NumNode(8);
		NumNode a3 = new NumNode(7);
		a1.next = a2;
		a2.next = a3;
		
		//78
		b1 = new NumNode(7);
		b2 = new NumNode(8);
		b1.next = b2;
		
		res = sumForwardRecursive(a1, b1);
		System.out.println("987+78 = " + SingleLinkedNode.toString(res));
	}	

	public static void main(String[] args) {		
		//forward();		
		//reverse();
		forwardRecursive();
	}
}
