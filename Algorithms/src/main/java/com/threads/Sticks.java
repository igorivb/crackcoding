package com.threads;

public class Sticks {

	private Stick[] sticks;
	
	public Sticks(int size) {
		sticks = new Stick[size];
		for (int i = 0; i < size; i ++) {
			sticks[i] = new Stick(i);
		}
	}
	
	/**
	 * Implementation with deadlock.
	 */
	public Stick getStick(Philosopher p, boolean left) throws InterruptedException {
		int stickNum = left ? p.number : (p.number == 0 ? sticks.length - 1 : p.number - 1);				
		Stick stick = sticks[stickNum];
		synchronized (stick) {
			while (true) {
				if (stick.isFree()) {
					stick.setOwner(p);
					return stick;					
				} else {
					stick.wait();	
				}
			} 									
		}
	}
	
	public void releaseStick(Stick stick) {
		synchronized (stick) {
			stick.intern_release();
			stick.notifyAll();
		}
	}
}
