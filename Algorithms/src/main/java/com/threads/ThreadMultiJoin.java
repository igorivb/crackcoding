package com.threads;

import java.util.Random;

/**
 * See threads/thread_multijoin.c from "The Linux Programming Interface" book
 */
public class ThreadMultiJoin {

	enum ThreadStatus { ALIVE, TERMINATED, JOINED }		
	
	
	//Structure to track thread status
	static class ThreadTracking {
		final Thread thread;
		ThreadStatus status;
		
		public ThreadTracking(Thread thread) {
			this.status = ThreadStatus.ALIVE;	
			this.thread = thread;
		}				
	}
	
	//Worker thread
	class Worker implements Runnable {
		final long sleep;
		final int pos;
		
		public Worker(long sleep, int pos) {
			this.sleep = sleep;
			this.pos = pos;
		}

		@Override
		public void run() {
			try {
				System.out.printf("Worker %2d sleeping millis %d%n", pos, sleep);
				Thread.sleep(sleep);										
				
				synchronized (lock) { //mark terminated and notify
					trackings[pos].status = ThreadStatus.TERMINATED;
					numUnjoined ++;
					lock.notify();	
				}				
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}			
		}
	}
	
	
	final int totalThreads;		
	
	final long[] sleeps;
	
	final ThreadTracking[] trackings; //access with lock
	int numLive; //alive or terminated
	int numUnjoined = 0; //terminated but not joined. Access with lock
	
	final Object lock = new Object();
	
	public ThreadMultiJoin(long[] sleeps) {
		this.sleeps = sleeps;
		totalThreads = sleeps.length;		
		trackings = new ThreadTracking[totalThreads];
		numLive = totalThreads;
	}
	
	public void run() throws InterruptedException {				
		//create threads
		System.out.printf("Creating threads: %d%n", totalThreads);
		for (int i = 0; i < totalThreads; i ++) {							
			Thread t = new Thread(new Worker(sleeps[i], i));
			trackings[i] = new ThreadTracking(t);						
			t.start();						
		}
				
		while (numLive > 0) { //iterate till we join all threads
			synchronized (lock) {
				while (numUnjoined == 0) { //wait till notification that some thread died
					lock.wait();
				}
				
				System.out.printf("Main notified on thread termination. NumUnjoined: %2d, numLive: %2d%n", numUnjoined, numLive);
				
				for (int i = 0; i < trackings.length; i ++) {
					ThreadTracking track = trackings[i];
					if (track.status == ThreadStatus.TERMINATED) {
						System.out.printf("Joining thread: %2d%n", i);
						track.thread.join(); //join thread
						
						track.status = ThreadStatus.JOINED;
						numLive --;
						numUnjoined --;															
					}
				}
			}
		}
		
		System.out.println("Done!");
	}

	public static void main(String[] args) throws Exception {
		System.out.printf("Available processors: %d%n", Runtime.getRuntime().availableProcessors());
		
		final int size = 1000;
		long[] sleeps = new long[size];
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < size; i ++) {
			sleeps[i] = (r.nextInt(5) + 1) * 1000;
		}				
				
		ThreadMultiJoin run = new ThreadMultiJoin(sleeps);
		run.run();
	}

}
