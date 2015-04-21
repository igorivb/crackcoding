package com.scalability;

import java.util.HashMap;
import java.util.Map;

public class Task10_7 {

	static class Result { 
		final String id;
		public Result(String id) {
		this.id = id;
		}
	} 
	
	
	static class ListNode {
		ListNode prev;
		final String val;
		public ListNode(String val) {
			this.val = val;
		}
		@Override
		public String toString() {		
			return val;
		}
	}
	
	
	static class LRUList {
		
		final int sizeThreshold;
		
		ListNode head, tail;
		int size;
		
		public LRUList(int sizeThreshold) {
			this.sizeThreshold = sizeThreshold;
		}
		
		public void insert(String val) {
			if (size == sizeThreshold) {
				removeLast();				
			}
			remove(val); 										 
			addFirst(new ListNode(val));				
		}
		
		private void addFirst(ListNode node) {
			if (head == null) { //empty
				head = tail = node;
			} else {
				head = head.prev = node;
			}	
			this.size ++;
		}
		
		private void addFirst(String val) {
			addFirst(new ListNode(val));
		}
		
		public String removeLast() {
			if (tail == null) {
				throw new IllegalStateException("List is empty");
			}
			String res = tail.val;
			remove(res);
			return res;
		}
		
		public boolean remove(String val) {
			ListNode cur, prev = null;
			for (cur = tail; cur != null && !cur.val.equals(val); prev = cur, cur = cur.prev);
						
			if (cur != null) {
				if (prev == null) { //delete tail
					tail = cur.prev;
				} else {
					prev.prev = cur.prev;
				}
				
				if (cur == head) { //delete tail
					head = prev;
				}
				size --;
				return true;
			}
			return false;
		}
		
		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			for (ListNode n = tail; n != null; n = n.prev) {
				res.insert(0, res.length() != 0 ? (n.val + ", ") : n.val);
			}
			res.insert(0, "[");
			res.append("]");
			
			return res.toString();
		}
	}
			
	
	Map<String, Result> map = new HashMap<>();
	//LinkedList<String> list = new LinkedList<>();
	LRUList list = new LRUList(3);
	
	
	
	public void insert(String query, Result res) {
		map.put(query, res);
		list.insert(query);						
	}

	public Result get(String query) {
		if (map.containsKey(query)) {
			list.insert(query);
			return map.get(query);
		}
		return null;
	}
	
	public void remove(String query) {
		map.remove(query);
		list.remove(query);		
	}		
		
	
	public static void main(String[] args) {
		Task10_7 task = new Task10_7();
		
		task.insert("a", new Result("a"));
		task.insert("b", new Result("b"));
		task.insert("c", new Result("c"));
		
		
		task.insert("a", new Result("aa"));
		task.get("b");
		
		task.insert("d", new Result("d"));

		
		System.out.println(task.list);
	}
	
}
