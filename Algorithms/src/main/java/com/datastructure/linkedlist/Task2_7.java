package com.datastructure.linkedlist;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Implement a function to check if a linked list is a palindrome
 */
public class Task2_7 {

	static class MyNodeStack<T> {
		Object[] mas = new Object[8];
		int i = 0;
		public void push(T val) {
			if (i == mas.length) {
				mas = Arrays.copyOf(mas, mas.length << 1);
			}
			mas[i ++] = val;
		}
		public T pop() {
			if (i == 0) {
				throw new EmptyStackException();
			}
			i --;
			@SuppressWarnings("unchecked")
			T res = (T) mas[i];
			mas[i] = null;
			return res;
		}
	}
	
	public static <T> boolean isPalindrome(SingleLinkedNode<T> node) {
		MyNodeStack<T> stack = new MyNodeStack<>();
		SingleLinkedNode<T> slow = node, fast = node;
		
		//fill stack
		while (fast != null && fast.next != null) {
			stack.push(slow.value);
			slow = slow.next;
			fast = fast.next.next;
			
//			if (fast == null) {
//				break;
//			}
//			if (fast.next == null) {
//				slow = slow.next;
//				break;
//			}
		}
		
		if (fast != null) { //Has odd number of elements, so skip the middle element
			slow = slow.next;
		}
		
		//compare
		for (SingleLinkedNode<T> n = slow; n != null; n = n.next) {
			if (!n.value.equals(stack.pop())) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		SingleLinkedNode<Integer> n1 = new SingleLinkedNode<>(1);
		System.out.println(isPalindrome(n1));
		
		SingleLinkedNode<Integer> n2 = new SingleLinkedNode<>(2);
		n1.next = n2;
		System.out.println(isPalindrome(n1));
		
		SingleLinkedNode<Integer> n3 = new SingleLinkedNode<>(3);
		n2.next = n3;
		System.out.println(isPalindrome(n1));
		
		SingleLinkedNode<Integer> n4 = new SingleLinkedNode<>(2);
		n3.next = n4;
		System.out.println(isPalindrome(n1));
		
		SingleLinkedNode<Integer> n5 = new SingleLinkedNode<>(1);
		n4.next = n5;
		System.out.println(isPalindrome(n1));
		
	}
}
