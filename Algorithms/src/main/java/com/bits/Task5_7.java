package com.bits;


public class Task5_7 {

	public static int findMissing(int[] array) {
		int prev = fetch(array[0], 0);
		int next = 0;
		
		for (int i = 1; i < array.length; i ++) {
			next = fetch(array[i], 0);
			if (prev == next) {
				//found
				int nextValue = readValue(array[i]);
				return nextValue - 1;
			}
			prev = next;
		}
		
		//not found
		return -1;
	}
	
	private static int readValue(int num) {
		int value = 0;
		for (int i = 0, length = 32; i < length; i ++) {
			int bit = fetch(num, i);
			value |= bit << i;
		}
		return value;
	}
	
	private static int fetch(int num, int pos) {
		return (num >>> pos) & 1;
	}

	public static void main(String[] args) {
		int[] array = {
			0b0000,
			0b0001,
			0b0010,
			0b0011,
			0b0100,
			0b0101,
			0b0110
		};
		
		int missing = findMissing(array);
		System.out.println(Integer.toBinaryString(missing));
	}
}
