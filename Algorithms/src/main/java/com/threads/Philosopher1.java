package com.threads;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher1 implements Runnable {

	public final int number;
	private final Sticks1 sticks;
	
	private Stick1 left, right;
	private boolean up;
	
	private Random r = new Random(System.currentTimeMillis());
	
	private static AtomicInteger opCounter = new AtomicInteger(0);
	
	public Philosopher1(int number, Sticks1 sticks) {
		this.number = number;
		this.sticks = sticks;
	}
	
	@Override
	public void run() {
		try {
			while (true) {						
				think();
				
				if (left == null && right == null) {
					takeLeft();
				} else if (left != null && right == null) {
					if (up) {
						takeRight();	
					} else {
						releaseLeft();
					}
				} else {
					releaseRight();
				}				
			}	
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt(); //propagate exception to Thread
		}			
	}

	private void releaseLeft() throws InterruptedException {
		sticks.releaseStick(left);
		left = null;
		print("Release left");
	}
	
	private void releaseRight() throws InterruptedException {
		sticks.releaseStick(right);
		right = null;
		up = false;
		print("Release right");
	}

	private void takeRight() throws InterruptedException {		
		right = sticks.getStick(this, false);		
		print("Take right: " + right); 
	}

	private void takeLeft() throws InterruptedException {		
		if ((left = sticks.getStick(this, true)) != null) {
			up = true;	
		}
		print("Take left: " + left);
	}

	private void think() throws InterruptedException {
		int sleep = r.nextInt(1000);
		//print("Think for " + sleep + " milisecs");
		Thread.sleep(sleep);				
	}
	
	private void print(String msg) {
		System.out.printf("%3d. %s: %s%n", opCounter.incrementAndGet(), this.toString(), msg);		
	}

	@Override
	public String toString() {	
		return "Phil_" + number;
	}
}
