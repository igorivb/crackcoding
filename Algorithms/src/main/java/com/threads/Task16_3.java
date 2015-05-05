package com.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * In the famous dining philosophers problem, a bunch of philosophers are sitting
 * around a circular table with one chopstick between each of them. A philosopher
 * needs both chopsticks to eat, and always picks up the left chopstick before the right
 * one. A deadlock could potentially occur if all the philosophers reached for the left
 * chopstick at the same time. Using threads and locks, implement a simulation of the
 * dining philosophers problem that prevents deadlocks
 */
public class Task16_3 {

	public static void main(String[] args) throws Exception {
		final int size = 3;
		
		Sticks sticks = new Sticks(size);
		
		ExecutorService executorService = Executors.newFixedThreadPool(size, new ThreadFactory() { //change thread name			
			@Override
			public Thread newThread(Runnable r) {
				String name = "Philosopher";
				Thread t = new Thread(r, name);				
				return t;
			}
		});
		for (int i = 0; i < size; i ++) {
			executorService.submit(new Philosopher(i, sticks));	
		}
		
		Thread.sleep(60 * 1000);
		executorService.shutdownNow();
	}
}
