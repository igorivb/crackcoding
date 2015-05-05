package com.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Stick {

	private final int num;
	private Lock lock = new ReentrantLock();

	public Stick(int num) {
		this.num = num;
	}
	
	public boolean pickup() {
		return lock.tryLock();
	}
	
	public void putDown() {
		lock.unlock();
	}
}
