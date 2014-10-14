package com.dynamicprog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: use depth first search because it is more optimal
 * 
 * see
 * 	http://logika.vobrazovanie.ru/index.php?link=resh_per_01.html&&a=pereliv.html
 */
public class WaterTask {

	enum Action {
		FILL_B1, CLEAR_B1, FROM_B1_TO_B2,
		FILL_B2, CLEAR_B2, FROM_B2_TO_B1		
	}
	
	private static class Volumes {
		int waterB1; int waterB2;
		Volumes(int waterB1, int waterB2) {
			this.waterB1 = waterB1;
			this.waterB2 = waterB2;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}			
			if (obj instanceof Volumes) {
				Volumes v = (Volumes) obj;
				return this.waterB1 == v.waterB1 && this.waterB2 == v.waterB2;
			}
			return false;
		}
		@Override
		public int hashCode() {
			int hash = 1;
	        hash = hash * 17 + this.waterB1;
	        hash = hash * 31 + this.waterB2;
	        return hash;
		}
		@Override
		public String toString() {
			return "Volumes [" + waterB1 + ", " + waterB2 + "]";
		}
		
	}
	
	static List<Action> findPath(int b1, int b2, int expected) {
		int waterB1 = 0; int waterB2 = 0;
		List<Action> actionPath = new ArrayList<>();
		List<Action> solution = new ArrayList<>();
		Map<Volumes, Integer> used = new HashMap<>();
		findPath(b1, b2, expected, waterB1, waterB2, actionPath, solution, used);
		return solution;
	}
	
	static int counter = 0;
	static void findPath(int b1, int b2, int expected, int waterB1, int waterB2, List<Action> actionPath, List<Action> solution, Map<Volumes, Integer> used) {
		
		if (waterB1 < 0 || waterB2 < 0) {
			throw new RuntimeException();
		}
		
		//check duplicates
		Volumes key = new Volumes(waterB1, waterB2);
		if (!used.containsKey(key)) {
			used.put(key, actionPath.size());
		} else if (used.get(key) > actionPath.size()) {
			used.put(key, actionPath.size());
		} else {
			return;
		}
		
		//check solution
		if (expected == waterB1 || expected == waterB2) {
			if (solution.isEmpty() || actionPath.size() < solution.size()) {
				//found better solution
				solution.clear();
				solution.addAll(actionPath);
			}
			return;
		}
		
		if (!solution.isEmpty() && actionPath.size() == solution.size()) {
			//no sense to process as it is already worse than solution
			return;
		}
		
		//apply actions
		
		//FILL_B1
		if (waterB1 < b1) {
			actionPath.add(Action.FILL_B1);
			System.out.printf("%2s. %15s, b1: %2s, b2: %2s%n", (++counter), Action.FILL_B1, b1, waterB2);
			findPath(b1, b2, expected, /*waterB1 = b1*/b1, waterB2, actionPath, solution, used);
			actionPath.remove(actionPath.size() - 1);
		}
		
		//CLEAR_B1
		if (waterB1 > 0) {
			actionPath.add(Action.CLEAR_B1);
			System.out.printf("%2s. %15s, b1: %2s, b2: %2s%n", (++counter), Action.CLEAR_B1, 0, waterB2);
			findPath(b1, b2, expected, /*clear*/0, waterB2, actionPath, solution, used);
			actionPath.remove(actionPath.size() - 1);
		}
		
		//FROM_B1_TO_B2
		if (waterB1 > 0 && waterB2 < b2) {
			actionPath.add(Action.FROM_B1_TO_B2);
			int diff = Math.min(b2 - waterB2, waterB1);
			System.out.printf("%2s. %15s, b1: %2s, b2: %2s%n", (++counter), Action.FROM_B1_TO_B2, waterB1 - diff, waterB2 + diff);
			findPath(b1, b2, expected, waterB1 - diff, waterB2 + diff, actionPath, solution, used);
			actionPath.remove(actionPath.size() - 1);
		}				
		
		//FILL_B2
		if (waterB2 < b2) {
			actionPath.add(Action.FILL_B2);
			System.out.printf("%2s. %15s, b1: %2s, b2: %2s%n", (++counter), Action.FILL_B2, waterB1, b2);
			findPath(b1, b2, expected, waterB1, /*waterB2 = b2*/b2, actionPath, solution, used);
			actionPath.remove(actionPath.size() - 1);
		}
		
		//CLEAR_B2
		if (waterB2 > 0) {
			actionPath.add(Action.CLEAR_B2);
			System.out.printf("%2s. %15s, b1: %2s, b2: %2s%n", (++counter), Action.CLEAR_B2, waterB1, 0);
			findPath(b1, b2, expected, waterB1, /*clear*/0, actionPath, solution, used);
			actionPath.remove(actionPath.size() - 1);
		}
		
		//FROM_B2_TO_B1
		if (waterB2 > 0 && waterB1 < b1) {
			actionPath.add(Action.FROM_B2_TO_B1);
			int diff = Math.min(b1 - waterB1, waterB2);
			System.out.printf("%2s. %15s, b1: %2s, b2: %2s%n", (++counter), Action.FROM_B2_TO_B1, waterB1 + diff, waterB2 - diff);
			findPath(b1, b2, expected, waterB1 + diff, waterB2 - diff, actionPath, solution, used);
			actionPath.remove(actionPath.size() - 1);
		}
	}
	
	public static void main(String[] args) {
		int b1 = 3; int b2 = 5; int expected = 4;
		
		List<Action> path = findPath(b1, b2, expected);		
		int waterB1 = 0, waterB2 = 0;
		System.out.printf("\nSolution: %2s%n", path.size());
		System.out.printf("b1: %2s, b2: %2s, Water b1: %2s, water b2: %2s%n", b1, b2, waterB1, waterB2);
		for (int i = 0; i < path.size(); i ++) {
			Action act = path.get(i);
			
			switch (act) {
				case FILL_B1:
					waterB1 = b1;
					break;
				case CLEAR_B1:
					waterB1 = 0;
					break;
				case FROM_B1_TO_B2:
					int diff = Math.min(b2 - waterB2, waterB1);
					waterB1 -= diff;
					waterB2 += diff;
					break;
				case FILL_B2:
					waterB2 = b2;
					break;
				case CLEAR_B2:
					waterB2 = 0;
					break;
				case FROM_B2_TO_B1:
					diff = Math.min(b1 - waterB1, waterB2);
					waterB2 -= diff;
					waterB1 += diff;
					break;
			}
									
			System.out.printf("%15s, b1: %2s, b2: %2s%n", act, waterB1, waterB2);
		}
	}
}
