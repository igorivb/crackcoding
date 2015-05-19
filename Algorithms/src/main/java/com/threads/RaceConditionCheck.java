package com.threads;

public class RaceConditionCheck {

	static int val = 0; 
	
	static class Exec implements Runnable {

		@Override
		public void run() {	
			for (int i = 0; i < 100000000; i ++) {
				val ++;
			}
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new Exec());
		Thread t2 = new Thread(new Exec());
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		//val doesn't equal to loop * 2
		System.out.println("Va: " + val);
	}
}
