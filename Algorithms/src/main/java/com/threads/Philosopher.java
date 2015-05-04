package com.threads;

import java.util.Random;

public class Philosopher implements Runnable {

	public final int number;
	final Sticks sticks;
	
	Stick left, right;
	boolean up;
	
	Random r = new Random(System.currentTimeMillis());
	
	public Philosopher(int number, Sticks sticks) {
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
			Thread.currentThread().interrupt(); //propogate exception to Thread
		}			
	}

	private void releaseLeft() {
		sticks.releaseStick(left);
		left = null;
		print("Release left");
	}
	
	private void releaseRight() {
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
		left = sticks.getStick(this, true);		
		up = true;
		print("Take left: " + left);
	}

	private void think() throws InterruptedException {
		int sleep = r.nextInt(1000);
		//print("Think for " + sleep + " milisecs");
		Thread.sleep(sleep);				
	}
	
	private void print(String msg) {
		System.out.println(toString() + ": " + msg);		
	}

	@Override
	public String toString() {	
		return "Phil_" + number;
	}
}
