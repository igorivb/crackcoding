package com.bits;

public class Task5_6 {

	public static int swap(int num) {
		int odd = (num & 0x55555555) << 1;
		int even = (num & 0xAAAAAAAA) >>> 1;
		return odd | even;
	}
	
	public static void main(String[] args) {
		int num = 0b0111;
		
		int res = swap(num);
		System.out.println(Integer.toBinaryString(res));
	}
}
