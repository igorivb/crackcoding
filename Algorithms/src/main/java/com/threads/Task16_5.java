package com.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Task16_5 {

	public static void main(String[] args) throws Exception {
		final int threads = 3;
		Foo foo = new Foo();
		Object[] locks = new Object[threads];
		CountDownLatch latch = new CountDownLatch(threads);
		
		
		for (int i = 0; i < threads; i ++) {
			locks[i] = new Object();			
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		for (int i = 0; i < threads; i ++) {
			executor.submit(new FooThread(threads - i - 1, foo, locks, latch));
		}
		
		if (!latch.await(3, TimeUnit.SECONDS)) {
			System.err.println("timeout on threadds");
		}		
		executor.shutdownNow();
	}
}
