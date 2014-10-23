package com.datastructure.stack;

/**
 * Describe how you could use a single array to implement three stacks.
 */
public class Task3_1 {

	interface IStack<T> {
		void push(int stackNum, T value);
		T pop(int stackNum);
		T peek(int stackNum);
		boolean isEmpty(int stackNum);
	}
	
	static class FixedRegionsStack<T> implements IStack<T> {
		Object[] mas;
		int[] tops;
		final int stackSize;
		
		public FixedRegionsStack(int stackSize, int stacksCount) {
			this.stackSize = stackSize;
			mas = new Object[stackSize * stacksCount];
			tops = new int[stacksCount];
		}
		
		public void push(int stackNum, T value) {
			if (tops[stackNum] == stackSize) { //check overflow
				throw new RuntimeException(String.format("Stack %s is full", stackNum));
			}
			int ind = getAbsoluteIndex(stackNum);			
			mas[ind] = value;			
			tops[stackNum] ++;
		}
		
		public T pop(int stackNum) {
			if (isEmpty(stackNum)) { //check underflow
				throw new RuntimeException(String.format("Stack %s is empty", stackNum));
			}
			int ind = getAbsoluteIndex(stackNum) - 1;
			@SuppressWarnings("unchecked")
			T res = (T) mas[ind];
			mas[ind] = null;
			tops[stackNum] --;
			
			return res;
		}
		
		public T peek(int stackNum) {			
			if (isEmpty(stackNum)) { //check underflow
				throw new RuntimeException(String.format("Stack %s is empty", stackNum));
			}
			int ind = getAbsoluteIndex(stackNum) - 1;
			@SuppressWarnings("unchecked")
			T res = (T) mas[ind];
			return res;
		}
		
		public boolean isEmpty(int stackNum) {
			return tops[stackNum] == 0;
		}

		private int getAbsoluteIndex(int stackNum) {
			return tops[stackNum] + stackNum * stackSize;
		}
	}
	
	
	//------------------------------------- Extensible regions-based stack
	
	static class ExtensibleRegionsStack<T> implements IStack<T> {
		Object[] mas;
		int[] tops;
		int[] ends;
		
		public ExtensibleRegionsStack(int stackSize, int stacksCount) {			
			mas = new Object[stackSize * stacksCount];			
			ends = new int[stacksCount];
			tops = new int[stacksCount];
			for (int i = 0; i < stacksCount; i ++) {
				ends[i] = (i + 1) * stackSize - 1;
				tops[i] = i > 0 ? ends[i - 1] + 1 : 0;
			}
		}
		
		@Override
		public void push(int stackNum, T value) {			
			int end = ends[stackNum];					
			int ind = tops[stackNum];
			
			if (ind == end + 1) {
				extend(stackNum);
			}
			
			mas[ind] = value;
			tops[stackNum] ++;
		}

		@Override
		public T pop(int stackNum) {
			if (isEmpty(stackNum)) { //check underflow
				throw new RuntimeException(String.format("Stack %s is empty", stackNum));
			}
						
			int ind = tops[stackNum] - 1;
			
			@SuppressWarnings("unchecked")
			T res = (T) mas[ind];
			mas[ind] = null;
			tops[stackNum] --;
			
			return res;
		}

		@Override
		public T peek(int stackNum) {
			if (isEmpty(stackNum)) { //check underflow
				throw new RuntimeException(String.format("Stack %s is empty", stackNum));
			}
			
			int ind = tops[stackNum] - 1;
			
			@SuppressWarnings("unchecked")
			T res = (T) mas[ind];						
			return res;
		}

		@Override
		public boolean isEmpty(int stackNum) {
			int start = stackNum > 0 ? ends[stackNum - 1] + 1 : 0;  			
			int ind = tops[stackNum];			
			return ind == start;
		}
		
		private void extend(int stackNum) {
			int start = stackNum > 0 ? ends[stackNum - 1] + 1 : 0;  
			int end = ends[stackNum];				
			int diff = end - start + 1; //double length
			
			//copy data to new array 
			Object[] newMas = new Object[mas.length + diff];
								
			System.arraycopy(mas, 0, newMas, 0, end + 1); //copy 'before' elements as is
			if (end + 1 < mas.length) {
				System.arraycopy(mas, end + 1, newMas, end + diff + 1, mas.length - end - 1);	
			}			
			
			//update indexes: shift only 'after' indexes
			for (int i = stackNum; i < ends.length; i ++) {
				ends[i] += diff;
			}
			for (int i = stackNum + 1; i < tops.length; i ++) {
				tops[i] += diff;
			}				
			
			mas = newMas;
		}
	}
	
	
	public static void main(String[] args) {
		int stackSize = 2; int stacksCount = 3;
		//IStack<Integer> stack = new FixedRegionsStack<>(stackSize, stacksCount);
		IStack<Integer> stack = new ExtensibleRegionsStack<>(stackSize, stacksCount);
		
		stack.push(0, 1);		
		stack.push(1, 11);		
		stack.push(2, 22);
				
		stack.push(1, 110);
		stack.push(1, 1100); //mas should be extended here
		
		stack.push(2, 220);
		stack.push(2, 2200); //mas should be extended here
		
		
		stack.push(0, 10);		
		stack.push(0, 100); //mas should be extended here
		
		stack.push(0, 1000);
		stack.push(0, 1000); //mas should be extended here
		
		System.out.println(stack);
		
		
//		stack.push(0, 10);		
//		stack.push(1, 110);		
//		stack.push(2, 220);				
//		
//		System.out.println(stack.pop(0) + " " + stack.pop(0));
//		System.out.println(stack.pop(1) + " " + stack.pop(1));
//		System.out.println(stack.pop(2) + " " + stack.pop(2));
//		
//		stack.push(0, 1);
//		System.out.println(stack.peek(0) + " " + stack.peek(0) + " " + stack.peek(0)); 
		
	}
}
