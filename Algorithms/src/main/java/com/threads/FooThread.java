package com.threads;

import java.util.concurrent.CountDownLatch;


public class FooThread implements Runnable {

	final int id;
	Object[] locks;
	Foo foo;
	CountDownLatch latch;
	
	public FooThread(int id, Foo foo, Object[] locks, CountDownLatch latch) {
		this.id = id;
		this.locks = locks;
		this.foo = foo;
		this.latch = latch;
	}
	
	@Override
	public void run() {
		try {
			Object prevLock = getPrevLock();
			if (prevLock != null) {
				synchronized (prevLock) {
					prevLock.wait();
				}
			}
			
			this.doRun();
			
			Object lock = getLock();
			if (lock != null) {
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		} finally {
			latch.countDown();
		}				
	}
	
	private Object getPrevLock() {
		return id == 0 ? null : locks[id - 1];
	}
	
	private Object getLock() {
		return locks[id];
	}

	protected /*abstract*/ void doRun() {
		if (id == 0) {
			foo.first();
		} else if (id == 1) {
			foo.second();
		} else if (id == 2) {
			foo.third();
		} else {
			throw new IllegalStateException("Unpexpected thread id: " + id);
		}
	} 

}
