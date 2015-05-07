package com.threads;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of deadlock detection.
 * When it finds deadlock it doesn't provide info about contending threads and which locks they held.
 */
public class Task16_4 {

	public static void main(String[] args) {
		int maxLock = 10;
		
		int[][] intLocks = {
			{1, 2, 3, 4}, //thread-0
			{1, 3, 5},    //thread-1
			{7, 5, 9, 2}  //thread-2
		};
				 		
		LockManager lockManager = new LockManager();
		
		Map<Integer, MyLock> locks = new HashMap<>();		
		for (int i = 0; i < maxLock; i ++) {
			locks.put(i, new MyLock(i));
		}
		
		for (int i = 0; i < intLocks.length; i ++) {
			for (int j = 0; j < intLocks[i].length; j ++) {
				if (!lockManager.lock(locks.get(intLocks[i][j]), i, false)) {
					//System.err.printf("Failed to lock. Thread id: %d, lock id: %d%n", i, intLocks[i][j]);
					//break;
				}
			}
		}		
		System.out.println(lockManager.hasDeadlock());
		
		
//		lockManager.lock(locks.get(1), 0, false);
//		lockManager.lock(locks.get(2), 0, false);
//		lockManager.lock(locks.get(3), 0, false);
//		lockManager.lock(locks.get(4), 0, false);
//		
//		lockManager.lock(locks.get(1), 1, false);
//		lockManager.lock(locks.get(3), 1, false);
//		lockManager.lock(locks.get(5), 1, false);
//		
//		lockManager.lock(locks.get(7), 2, false);
//		lockManager.lock(locks.get(5), 2, false);
//		lockManager.lock(locks.get(9), 2, false);
//		//lockManager.lock(locks.get(2), 2, false);
//		
//		lockManager.unlock(1);
//		lockManager.lock(locks.get(2), 2, false);
//		
//		System.out.println(lockManager.hasDeadlock());
	}
}
