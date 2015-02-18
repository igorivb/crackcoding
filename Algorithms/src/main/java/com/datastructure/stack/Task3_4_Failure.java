package com.datastructure.stack;

import com.datastructure.StackOnArray;

/**
 * In the classic problem of the Towers of Hanoi, you have 3 towers and N disks of
 * different sizes which can slide onto any tower. The puzzle starts with disks sorted
 * in ascending order of size from top to bottom (i.e., each disk sits on top of an even
 * larger one). You have the following constraints:
 * (T) Only one disk can be moved at a time.
 * (2) A disk is slid off the top of one tower onto the next rod.
 * (3) A disk can only be placed on top of a larger disk.
 * Write a program to move the disks from the first tower to the last using Stacks. 
 */
public class Task3_4_Failure {		
	
	static class Tower extends StackOnArray<Integer> {		
		public Tower(int disksNum) {
			super(disksNum, false);
		}
		@Override
		public void push(Integer val) {
			if (!canAdd(val)) {
				throw new IllegalStateException(String.format("Can't add elem more that top. Elem: %s, top: %s", val, this.peek()));
			}
			super.push(val);
		}
		
		public boolean canAdd(Integer val) {
			return this.isEmpty() || val < this.peek();
		}
	}
	
	final int disksNum;	
	final Tower[] towers;
	
	public Task3_4_Failure(int disksNum) {
		this.disksNum = disksNum;	
		
		towers = new Tower[3];
		for (int i = 0; i < towers.length; i ++) {
			towers[i] = new Tower(disksNum);
		}
		
		//initial state
		for (int i = disksNum; i > 0; i --) {
			towers[0].push(i);
		}
	}		
	
	public void process() {
		printTowers();
		process(0);
	}
	
	//TODO: rework for iterative approach
	
	int count = 0;
	
	private void process(int cur) {
		System.out.println("\n\n" + (++count) + ". Process: " + cur);
		//printTowers();
		
		if (towers[towers.length - 1].size() == disksNum) {	
			System.out.println("Found solution");
			return; //found solution
		}
		
		int stopPoint = moveForward(cur);
		
		if (stopPoint == towers.length - 1) { //reached end									
			System.out.println("Reached end");
						
			int next = cur;
			
			if (towers[cur].isEmpty()) { //take either before or next
				int beforeInd = -1;
				for (int i = cur - 1; i >= 0; i--) {
					if (!towers[i].isEmpty()) {
						beforeInd = i;
						break;
					}
				}
				
				if (beforeInd >= 0) { //has before
					next = beforeInd;
					System.out.println("Take before");
				} else if (cur + 1 < towers.length) {
					next = cur + 1; //next
					System.out.println("Take next");
				}
			}
			
			this.process(next);
			
		} else { //stopped somewhere before end
			System.out.println("No movement: stopped somewhere before end");
												
			int next;
			int backInd = moveBack(stopPoint + 1); //take next element and move it back
			if (backInd < stopPoint) {
				System.out.println("Try again the same");
				next = stopPoint; //try again the same
			} else {
				System.out.println("Try previous");
				
				
				if (stopPoint == 0) {
					//TODO: is it ok? 
					next = stopPoint; 
				} else {
					next = stopPoint - 1; 	
				} 
				
			}
			
			this.process(next);
		}
	}
	
	private int moveBack(int cur) {				
		Integer elem = towers[cur].pop();
		
		int i = cur - 1;
		for (; i >= 0 && towers[i].canAdd(elem); i --);
		
		int res = i + 1;
		towers[res].push(elem);	
		
		System.out.printf("Move back. Cur: %s, end: %s%n", cur, res);
		printTowers();
		
		return res;
	}

	private int moveForward(int cur) {				
		Integer elem = towers[cur].pop();
		
		int i = cur + 1;
		for (; i < towers.length && towers[i].canAdd(elem); i ++);
		
		int res = i - 1;
		towers[res].push(elem);
		
		System.out.printf("Move forward. Cur: %s, end: %s%n", cur, res);
		printTowers();
		
		return res;
	}
	
	void printTowers() {
		//System.out.printf("----Tow_1: %10s ----tow_2: %10s ----tow_3: %10s%n", towers[0], towers[1], towers[2]);
		
		System.out.printf("-------| -------| -------|%n");
		System.out.printf("%7s| %7s| %7s|%n", towers[0], towers[1], towers[2]);
	}

	public static void main(String[] args) {
		int disksNum = 3;
		Task3_4_Failure task = new Task3_4_Failure(disksNum);
		task.process();
	}
}
