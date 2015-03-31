package com.tree.not_my;


public class StringElement implements DynamicSetElement{

	Comparable key;
	
	public StringElement(Comparable key) {
		this.key = key;
	}
	
	@Override
	public void setKey(Comparable key) {
		this.key = key;
		
	}

	@Override
	public Comparable getKey() {
		return key;
	}

	@Override
	public int compareTo(Object e) {
		return this.key.compareTo(e);
	}
	
	@Override
	public String toString() {	
		return key.toString();
	}

}
