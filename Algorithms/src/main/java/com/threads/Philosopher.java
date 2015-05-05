package com.threads;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {

	private final int num;	
	private final Stick left;
	private final Stick right;
	
	private final int max;	
	private int eatCount;
	
	private static AtomicInteger opCounter = new AtomicInteger(0);
	private Random r = new Random(System.currentTimeMillis()); 
	
	CountDownLatch latch;
	
	public Philosopher(int num, Stick left, Stick right, int max, CountDownLatch latch) {
		this.num = num;
		this.left = left;
		this.right = right;
		
		this.max = max;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			while(eatCount < max) {
				eat();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void eat() throws InterruptedException {
		if (pickUp()) {
			chew();
			putDown();
		}		
	}
	
	private boolean pickUp() throws InterruptedException {
		pause();
		print("Left picking up");
		if (left.pickup()) {
			pause();
			print("Right picking up");
			if (right.pickup()) {
				return true;
			} else {
				print("Left put down");
				left.putDown();
			}
		}
		return false;
	}

	private void chew() throws InterruptedException {
		print("Eat " + eatCount ++);	
		latch.countDown();
		pause();		
	}

	private void putDown() {
		print("Right put down");
		right.putDown();
		print("Left put down");
		left.putDown();		
	}

	private void pause() throws InterruptedException {
		int sec = r.nextInt(1000);
		Thread.sleep(sec);
	}
	
	@Override
	public String toString() {
		return "Phil_" + num;
	}
	
	private void print(String msg) {
		System.out.printf("%3d. %s: %s%n", opCounter.incrementAndGet(), this.toString(), msg);
	}
}
