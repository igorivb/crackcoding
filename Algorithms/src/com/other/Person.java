package com.other;

public class Person implements Comparable<Person> {

	String name;
	
	public Person(String name) {
		this.name = name;
	}
	
	//@Override
	public int compareTo(Person o) {
		return this.name.compareToIgnoreCase(o.name);
	}
	
	@Override
	public String toString() {	
		return this.getClass().getName() + ": " + this.name;
	}

}
