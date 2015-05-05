package com.threads;

public class Stick1 {

	public final int num;
	
	private Philosopher1 owner;
	
	public Stick1(int num) {
		this.num = num;
	}
	
	public void setOwner(Philosopher1 owner) {
		if (this.owner != null) {
			throw new IllegalStateException("Failed to get stick: " + toString());
		}
		this.owner = owner;
	}
	
	void intern_release() {
		if (this.owner == null) {
			throw new IllegalStateException("Failed to release stick: " + this.toString());
		}
		this.owner = null;
	}

	public boolean isFree() {
		return this.owner == null;
	}
	
	@Override
	public String toString() {	
		return "Stick_" + num + ", owner: " + owner;
	}
}
