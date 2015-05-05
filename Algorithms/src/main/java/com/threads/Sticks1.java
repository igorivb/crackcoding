package com.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sticks1 {

	private Stick1[] sticks;
	
	private Lock lock = new ReentrantLock();
	
	public Sticks1(int size) {
		sticks = new Stick1[size];
		for (int i = 0; i < size; i ++) {
			sticks[i] = new Stick1(i);
		}
	}
	
	/**
	 * Implementation with deadlock. 
	 * Blocks till it gets stick. 
	 */
	public Stick1 getStick_Deadlock(Philosopher1 p, boolean left) throws InterruptedException {
		int stickNum = left ? p.number : (p.number == 0 ? sticks.length - 1 : p.number - 1);				
		Stick1 stick = sticks[stickNum];
		synchronized (stick) {
			while (!stick.isFree()) {
				stick.wait();							
			} 									
			stick.setOwner(p);
			return stick;		
		}
	}
	
	public void releaseStick_Deadlock(Stick1 stick) {
		synchronized (stick) {
			stick.intern_release();
			stick.notifyAll();
		}
	}
	
	/**
	 * Return null if stick is not available.
	 */
	public Stick1 getStick(Philosopher1 p, boolean left) throws InterruptedException {
		int stickNum = left ? p.number : (p.number == 0 ? sticks.length - 1 : p.number - 1);
		int rightNum = stickNum == 0 ? sticks.length - 1 : stickNum - 1;
		
		lock.lockInterruptibly();
		try {
			if (sticks[stickNum].isFree()) {
				if (!left || sticks[rightNum].isFree()) {
					sticks[stickNum].setOwner(p);
					return sticks[stickNum]; 
				}
			}
			return null;
		} finally {
			lock.unlock();
		}															
	}
	
	public void releaseStick(Stick1 stick) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			stick.intern_release();
		} finally {
			lock.unlock();
		}			
	}	

}
