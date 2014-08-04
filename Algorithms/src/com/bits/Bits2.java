package com.bits;

public class Bits2 {

	public static boolean getBit(int num, int i) {
		return ((num >>> i) & 1) == 1;		
	} 	
	
	public static boolean getBit2(int num, int i) {
		return ((1 << i) & num) != 0;
	} 	
	
	//set 1
	public static int setBit(int num, int pos) {		
		return (1 << pos) | num;
	}
	
	//set 0
	public static int clearBit(int num, int pos) {
		return (~(1 << pos)) & num;
	}
	
	//clear all bits from the most significant bit through i (inclusive):
	static int clearBitsMSBthroughI(int num, int i) {
		return ((1 << i) - 1) & num;
	}

	//clear all bits from i through 0 (inclusive), we do:
	static int clearBitsIthrough0(int num, int i) {
		return (-1 << i) & num;
	}
	
	static int clearBitsIthrough0_2(int num, int i) {
		return (~((1 << i) - 1)) & num;
	}
	
	static int updateBit(int num, int pos, boolean val) {
		//TODO
		return -1;
	}
	
	public static void main(String[] args) {
		int num = Integer.valueOf("1111", 2);
		
		
		
		int num1 = clearBitsIthrough0(num, 2);
		int num2 = clearBitsIthrough0_2(num, 2);
		
		System.out.println(num1 == num2);
		//System.out.println(Integer.toString(num, 2));
		
		
	}
}
