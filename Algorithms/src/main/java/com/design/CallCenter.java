package com.design;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.design.Employee.Director;
import com.design.Employee.Manager;
import com.design.Employee.Resident;

public class CallCenter {

	public final static int rankLevels = 3;			
	
	//unbounded
	private List<BlockingQueue<Employee>> employees;
	
	//bounded
	private List<BlockingQueue<Call>> calls;
	
	//threads for handling calls for each rank
	private ExecutorService executorService;
	
	//in secs
	private final int callWaitTimeout;
	
	public CallCenter(int residentsSize, int managersSize, int directorsSize,
		int callsMaximum, int callWaitTimeout) {
		
		this.callWaitTimeout = callWaitTimeout;
		
		//calls
		calls = new ArrayList<>(rankLevels);
		for (int i = 0; i < rankLevels; i ++) {
			calls.add(new ArrayBlockingQueue<>(callsMaximum));	
		}
		
		//employees
		employees = new ArrayList<>(rankLevels);
		for (int i = 0; i < rankLevels; i ++) {
			employees.add(new LinkedBlockingQueue<>());
		}		
		for (int i = 0; i < residentsSize; i ++) { //residents
			employees.get(Rank.RESIDENT.val()).add(new Resident("resident_" + i));
		}	
		for (int i = 0; i < managersSize; i ++) { //managers
			employees.get(Rank.MANAGER.val()).add(new Manager("manager_" + i));
		}
		for (int i = 0; i < directorsSize; i ++) { //directors
			employees.get(Rank.DIRECTOR.val()).add(new Director("director_" + i));
		}
		
		//executors
		executorService = Executors.newFixedThreadPool(rankLevels);
		for (int i = 0; i < rankLevels; i ++) {
			final int rank = i;
			executorService.execute(new Runnable() {				
				@Override
				public void run() {
					handle(rank);
				}
			});
		}
	}
		
	//process calls for specific rank
	private void handle(int rank) {
		BlockingQueue<Call> rankCalls = calls.get(rank);
		BlockingQueue<Employee> rankEmployees = employees.get(rank);
		
		while (true) { //infinite loop
			try {
				Call call = rankCalls.take();
				Employee emp;
				
				if (call.getRank().val() != rank) { //unexpected: should not happen
					System.err.printf("Call has unexpected rank. Call rank: %d, processed rank: %d%n", call.getRank().val(), rank);
					continue; //skip this call
				}
				
				if ((emp = rankEmployees.poll(callWaitTimeout, TimeUnit.SECONDS)) != null) {
					//call.setStatus(CallStatus.IN_PROGRESS);
					
					if (!emp.handle(call)) {
						if (call.getRank().val() == rankLevels - 1) { //reached maximum rank ?
							call.setStatus(CallStatus.REJECTED, "Call has maximum rank");
						} else { //increase rank and add to queue			
							call.increaseRank();
							handle(call);
						}
					} else {
						call.setStatus(CallStatus.COMPLETED); //completed call
					}
					
					rankEmployees.add(emp); //return employee back										
				} else {
					call.setStatus(CallStatus.REJECTED, "Waited too long for available employee");
				}									
			} catch (InterruptedException ie) {
				break; //stop processing
			}
		} 
	}
	
	public void close() {
		executorService.shutdownNow();
	} 
	
	public void handle(Call call) throws InterruptedException {						
		if (!calls.get(call.getRank().val()).offer(call, callWaitTimeout, TimeUnit.SECONDS)) {
			call.setStatus(CallStatus.REJECTED, "Call queue is overflow");
		} else {
			call.setStatus(CallStatus.PENDING);
		}					
	}
}
