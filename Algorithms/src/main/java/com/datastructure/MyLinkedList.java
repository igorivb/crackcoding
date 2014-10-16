package com.datastructure;

public class MyLinkedList<T> {
	
	private final Node<T> root;
	private int size;
	
	static class Node<T> {
		T elem;
		Node<T> next;
		Node<T> prev;
		public Node(T elem) {
			this.elem = elem;
		}
	}
	
	public MyLinkedList() {
		root = new Node<>(null);
		root.next = root.prev = root; 
	}
	
	public void insert(T elem) {
		Node<T> n = new Node<>(elem);
		n.next = root.next;
		root.next.prev = n;		
		n.prev = root;		
		root.next = n;	
		size ++;
	}
	
	public boolean delete(T elem) {
		Node<T> n = search(elem);
		if (n != null) {
			n.next.prev = n.prev;
			n.prev.next = n.next;
			n.next = n.prev = null;
			size --;
			return true;
		}
		return false;
	}
			
	private Node<T> search(T elem) {
		for (Node<T> n = root.next; n != root; n = n.next) {
			if (n.elem.equals(elem)) {
				return n;
			}			
		}		
		return null;
	}

	public boolean contains(T elem) {
		return search(elem) != null;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public static void main(String[] args) {
		MyLinkedList<String> list = new MyLinkedList<>();
		list.insert("a");
		System.out.println("insert a: " + list.size());
		
		list.insert("b");
		System.out.println("insert b: " + list.size());		
		
		System.out.println("delete c: " + list.delete("c"));
		System.out.println("delete b: " + list.delete("b"));
		System.out.println("delete a: " + list.delete("a"));
		
		System.out.println("size: " + list.size());
	}
}
