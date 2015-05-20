package com.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Use Lock, Condition
 */
public class ProducerConcumer2<T> {

	private List<T> list;
	private final int maxSize;
	private int size;
	
	private final Lock lock = new ReentrantLock();
	private final Condition putCond = lock.newCondition();
	private final Condition getCond = lock.newCondition();
	
	public ProducerConcumer2(int bound) {
		list = new ArrayList<>(bound);
		this.maxSize = bound;
	}
	
	public void put(T elem) throws InterruptedException {
		lock.lock();
		try {
			if (isFull()) {
				putCond.await();
			}
			list.add(elem);
			size ++;
			
			getCond.signal();	
		} finally {
			lock.unlock();
		}		
	}		

	public T get() throws InterruptedException {
		lock.lock();
		try {
			while (isEmpty()) {
				getCond.await();
			}
			T res = list.remove(list.size() - 1);
			size --;
			
			putCond.signal();
			
			return res;
		} finally {
			lock.unlock();
		}
	}
	
	private boolean isEmpty() {
		return size == 0;
	}

	private boolean isFull() {		
		return this.size == maxSize;
	}
	
	public static void main(String[] args) throws Exception{
		ProducerConcumer2<Integer> list = new ProducerConcumer2<>(3);
		
		list.put(1);
		list.put(2);
		list.put(3);
		System.out.println(list.get());
		System.out.println(list.get());
		System.out.println(list.get());
		System.out.println(list.get());
	}
}
