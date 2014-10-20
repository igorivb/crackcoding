package com.datastructure.linkedlist;

public class SingleLinkedNode<T> {
	public T value;
	public SingleLinkedNode<T> next;
	public SingleLinkedNode(T value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return this.value.toString();
	}
	
	public static <T> SingleLinkedNode<T> insertBefore(SingleLinkedNode<T> list, SingleLinkedNode<T> newNode) {
		newNode.next = list;
		return newNode;
	} 
	
	public static <T> String toString(SingleLinkedNode<T> node) {
		StringBuilder str = new StringBuilder("{");
		for (SingleLinkedNode<T> n = node; n != null; n = n.next) {
			str.append(n.value);
			if (n.next != null) {
				str.append(", ");				
			}
		}
		str.append("}");
		//System.out.println(str);
		return str.toString();
	}
}