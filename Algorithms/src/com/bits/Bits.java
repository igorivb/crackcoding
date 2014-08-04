package com.bits;

public class Bits {

	public static boolean getBit(int num, int i) {
		//PRE: i positive
		return ((num >> i) & 1) == 1;
	} 
	
	public static boolean getBit2(int num, int i) {
		//PRE: i positive
		return (num & (1 << i)) != 0;
	} 
	
	//set 1
	public static int setBit(int num, int pos) {		
		return num | (1 << pos);
	}
	
	//set 0
	public static int clearBit(int num, int pos) {
		return num & (~(1 << pos));
	}
	
	//clear all bits from the most significant bit through i (inclusive), we do:
	static int clearBitsMSBthroughI(int num, int i) {
		//return (-1 >>> (31 - i)) & num;
		return ((1 << i) - 1) & num;
	}

	//clear all bits from i through 0 (inclusive), we do:
	static int clearBitsIthrough0(int num, int i) {
		return (-1 << (i + 1)) & num;
	}
	
	static int updateBit(int num, int pos, boolean val) {
		//set to 0: & 0
		//set to val: || val
		
		num = (~(1 << pos)) & num;
		num = ((val ? 1 : 0) << pos) | num;
		return num;
	}
	
	public static void main(String[] args) {
		int num = Integer.valueOf("0101", 2);
		
		
		
		num = updateBit(num, 0, false);
		
		System.out.println(Integer.toString(num, 2));
		
		
	}
}
