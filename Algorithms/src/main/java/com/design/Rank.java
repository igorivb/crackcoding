package com.design;

public enum Rank {
	
	RESIDENT(0), MANAGER(1), DIRECTOR(2);
	
	private int val;
	
	Rank(int val) {
		this.val = val;
	}
	
	public int val() {
		return val;
	}
}
