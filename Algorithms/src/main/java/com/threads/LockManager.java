package com.threads;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class LockManager {

	enum VisitStatus { OPEN, IN_PROGRESS, VISITED }

	private Map<Integer, LinkedList<MyLock>> threadsLocks = new HashMap<>();
	
	public synchronized boolean lock(MyLock lock, int threadId, boolean checkDeadlock) {
		LinkedList<MyLock> locks = threadsLocks.get(threadId);
		if (locks == null) {
			locks = new LinkedList<>();
			threadsLocks.put(threadId, locks);						
		}
		
		if (!locks.isEmpty()) {
			locks.getLast().children.add(lock);
		}		
		locks.add(lock);
								
		if (checkDeadlock && this.hasDeadlock()) {
			unlock(threadId);
			return false;
		}
		
		return true;
	} 
	
	public synchronized void unlock(int threadId) {
		LinkedList<MyLock> locks = threadsLocks.get(threadId);
		if (locks == null || locks.isEmpty()) {
			throw new IllegalStateException("Thread doesn't have locks. Id: " + threadId);
		}
		if (locks.size() == 1) {
			locks.clear();
		} else {
			MyLock prev = locks.get(locks.size() - 2);
			prev.children.remove(prev.children.size() - 1);			
			locks.removeLast();
		}
	}
	
	//	A = {1, 2, 3, 4}
	//	B = {1, 3, 5}
	//	C = {7, 5, 9, 2}
	public boolean hasDeadlock() {		
		for (LinkedList<MyLock> locks : threadsLocks.values()) {
			if (!locks.isEmpty() && this.hasDeadlock(locks.getFirst())) {
				return true;
			}
		}		
		return false;
	}

	private boolean hasDeadlock(MyLock lock) {
		Map<Integer, VisitStatus> locksVisited = new HashMap<>();		
		
		Queue<MyLock> q = new LinkedList<>();
		q.add(lock);
		
		while (!q.isEmpty()) {
			MyLock l = q.remove();
			locksVisited.put(l.id, VisitStatus.IN_PROGRESS);
			
			for (MyLock child : l.children) {
				if (locksVisited.get(child.id) == VisitStatus.VISITED) {
					System.out.println("Lock causes deadlock: " + child.id);
					return true; //cycle: deadlock
				}
				q.add(child);				
			}
			
			locksVisited.put(l.id, VisitStatus.VISITED);
		}
		
		return false;
	}
}
