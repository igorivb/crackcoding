package com.design;

import java.util.Random;

public class Employee {
	private final String name;
	
	private Random result = new Random(System.currentTimeMillis());
	private Random duration = new Random(System.currentTimeMillis());
	
	public Employee(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean handle(Call call) throws InterruptedException {
		call.setEmployee(this);						
		boolean handled = doHandle(call);		
		return handled;
	}
	
	private boolean doHandle(Call call) throws InterruptedException {
		long sleep = duration.nextInt(1);
		call.setStatus(CallStatus.IN_PROGRESS, "Duration: " + sleep + "secs");
		Thread.sleep(sleep * 1000);
		
		return result.nextBoolean();
	}

	public static class Resident extends Employee {
		public Resident(String name) {
			super(name);			
		} 		
	}
	
	public static class Manager extends Employee {
		public Manager(String name) {
			super(name);			
		} 
	}
	
	public static class Director extends Employee { 
		public Director(String name) {
			super(name);			
		} 
	}
}
