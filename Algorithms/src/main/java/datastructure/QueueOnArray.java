package datastructure;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class QueueOnArray<T> {

	Object[] mas = new Object[2];
	/*
	 * [start, end)
	 * Empty: end - start == 0
	 */
	int start = 0, end = 0;
	
	public void enqueue(T obj) {
		if (end == mas.length) {
			mas = Arrays.copyOf(mas, mas.length << 1); //extend
		}
		mas[end] = obj;
		end ++;
	}
	
	public T dequeue() {
		if (!isEmpty()) {
			@SuppressWarnings("unchecked")
			T obj = (T) mas[start];
			mas[start] = null;
			start ++;
			
			if (isEmpty()) {
				start = end = 0;
			}
			
			return obj;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return end - start;
	}

	public static void main(String[] args) {
		QueueOnArray<String> q = new QueueOnArray<>();
		q.enqueue("a");
		q.enqueue("b");
		q.enqueue("c");
		
		for (int i = 0, n = q.size(); i < n; i ++) {
			System.out.println(q.dequeue());
		}		
		System.out.println("empty: " + q.isEmpty());
	}
}
