package com.datastructure.stack;

import java.util.LinkedList;

/**
 * An animal shelter holds only dogs and cats, and operates on a strictly "first in, first
 * out" basis. People must adopt either the "oldest" (based on arrival time) of all animals
 * at the shelter, or they can select whether they would prefer a dog or a cat (and will
 * receive the oldest animal of that type). They cannot select which specific animal they
 * would like. Create the data structures to maintain this system and implement operations
 * such as enqueue, dequeueAny, dequeueDog and dequeueCat. You may
 * use the built-in LinkedList data structure.
 */
public class Task3_7 {

	public static class Animal implements Comparable<Animal> {
		String name;
		int order;
		
		@Override
		public int compareTo(Animal that) {
			return this.order == that.order ? 0 : (this.order < that.order ? -1 : 1);
		}		
	} 
	
	public static class Dog extends Animal { }
	
	public static class Cat extends Animal { }
	
	private static class AnimalQueue {
		private LinkedList<Cat> cats = new LinkedList<>();
		private LinkedList<Dog> dogs = new LinkedList<>();
		private int order;
		
		public void enqueue(Animal animal) {
			animal.order = order ++;
			if (animal instanceof Cat) {			
				cats.add((Cat) animal);
			} else {			
				dogs.add((Dog) animal);
			}
		}
		public Animal dequeueAny() {
			if (cats.isEmpty()) return dogs.removeFirst();		
			if (dogs.isEmpty()) return cats.removeFirst();
			return cats.getFirst().compareTo(dogs.getFirst()) < 0 ? cats.removeFirst() : dogs.removeFirst();		
		}
		public Dog dequeueDog() {
			return dogs.removeFirst();
		} 
		
		public Cat dequeueCat() {
			return this.cats.removeFirst();
		}
	}
	
		
}
