package com.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Task16_3 {

	static final int size = 5;
	static final int max = 3;
	
	private static Stick getLeft(int i, Stick[] sticks) {		
		return sticks[i];
	}
	
	private static Stick getRight(int i, Stick[] sticks) {
		int num = i == 0 ? sticks.length - 1 : i - 1;
		return sticks[num];
	}
	
	public static void main(String[] args) throws Exception {	
		CountDownLatch latch = new CountDownLatch(size * max);
		
		Stick[] sticks = new Stick[size];
		for (int i = 0; i < size; i ++) {
			sticks[i] = new Stick(i);
		}
		
		Philosopher ps[] = new Philosopher[size];
		for (int i = 0; i < size; i ++) {
			ps[i] = new Philosopher(i, getLeft(i, sticks), getRight(i, sticks), max, latch);
		}
		
		ExecutorService executorService = Executors.newFixedThreadPool(size); 
		for (int i = 0; i < size; i ++) {
			executorService.submit(ps[i]);
		}
		
		//wait some time
		//Thread.sleep(60 * 1000);
		if (!latch.await(60, TimeUnit.SECONDS)) {
			System.out.println("Stop on time out");
		}	
		System.out.println("Terminating threads");
		executorService.shutdownNow();	
	}	
	
}
