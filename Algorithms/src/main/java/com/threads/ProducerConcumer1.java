package com.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Use wait, notify
 */
public class ProducerConcumer1<T> {

	private List<T> list;
	private final int maxSize;
	private int size;
	
	private final Object lock = new Object(); 
	
	public ProducerConcumer1(int bound) {
		list = new ArrayList<>(bound);
		this.maxSize = bound;
	}
	
	public void put(T elem) throws InterruptedException {
		synchronized (lock) {
			if (isFull()) {
				lock.wait();
			}
			list.add(elem);
			size ++;
			
			lock.notifyAll();
		}
	}		

	public T get() throws InterruptedException {
		synchronized (lock) {
			while (isEmpty()) {
				lock.wait();
			}
			T res = list.remove(list.size() - 1);
			size --;
			
			lock.notifyAll();
			
			return res;
		}
	}
	
	private boolean isEmpty() {
		return size == 0;
	}

	private boolean isFull() {		
		return this.size == maxSize;
	}
	
	public static void main(String[] args) throws Exception{
		ProducerConcumer1<Integer> list = new ProducerConcumer1<>(3);
		
		list.put(1);
		list.put(2);
		list.put(3);
		System.out.println(list.get());
		System.out.println(list.get());
		System.out.println(list.get());
		System.out.println(list.get());
	}
}
