package com.other;

import java.util.ArrayList;
import java.util.List;

public class Generics {

	public static void main(String[] args) {

		
//		List<Number> list = new ArrayList<>();
//		list.add(new Integer(1));
//		list.add(new Double(1.2));							
//		run(list);				

		
//		List<Person> persons = new ArrayList<>();
//		persons.add(new Person("p2"));
//		persons.add(new Person("p1"));
		
		
		List<Student> persons = new ArrayList<>();
		persons.add(new Student("p2"));
		persons.add(new Student("p1"));
		
		
		sort(persons);
			
	}
	
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		System.out.println("sort:" + list);
	} 
	
	

}
