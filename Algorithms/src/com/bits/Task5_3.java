package com.bits;

public class Task5_3 {
	
//	public static int[] findLargestSmallest(int num) {
//		if (num <= 0) {
//			throw new IllegalArgumentException("num");
//		}
//		
//		//count '0'
//		int count = 0;
//		int cmp = 1;
//		for (int i = 0; i < 32; i ++) {
//			if ((cmp & num) == 0) {
//				count ++;
//			}
//			cmp <<= 1;
//		}
//		
//		int max = (~0) << (count - 1);
//		//clear sign bit
//		max &= (-1 >>> 1);
//		
//		int min = (~0) >>> count;
//		
//		return new int[] {min, max};		
//	}
	
	public static int getNext(int num) {
		int cmp = 1;
		int count1 = 0;
		int index0 = -1;
		
		//find first '0'
		for (int i = 0; i < 32; i ++) {
			if ((cmp & num) != 0) {
				count1 ++;
			} else if (count1 > 0) {
				//found
				index0 = i;
				break;
			}
			cmp <<= 1;
		}
		if (index0 == -1) {
			//not possible to find larger because it is already maximum
			return num;
		}
		
		//replace found '0' to 1
		num |= (1 << index0);
		
		//replace everything to '0's right to found zero
		num &= (~0) << index0;
		
		//add '1's to low positions
		num |= (1 << (count1 - 1)) - 1;
		
		return num;
	}
	
	public static int getPrevious(int num) {
		
		int count1 = 0, count0 = 0;
		int index1 = -1;
		
		int n = num;
		int cmp = 1;
		
		//find first '1'
		for (int i = 0; n > 0; i ++) {
			if ((n & cmp) == 0) {
				count0 ++;
			} else if (count0 > 0) {
				//found
				index1 = i;
				break;
			} else {
				count1 ++;
			}
			n >>>= 1;
		}
		if (index1 == -1) {
			throw new IllegalStateException("can't find");
		}
		
		//replace 1 -> 0
		num &= ~(1 << index1);
		
		//replace all in right to zeros
		num &= (~0) << index1;
		
		//add 1s right to 1
		int mask = (1 << (count1 + 1)) - 1;
		num |=  mask << (count0 - 1);
		
		return num;
	}
	
	public static void main(String[] args) {
		int num = 0b11011001111100;
		System.out.printf("Initial %s\n", Integer.toBinaryString(num));
		
//		int[] values = findLargestSmallest(num);
//		System.out.printf("min = %s\n", Integer.toBinaryString(values[0]));
//		System.out.printf("max = %s\n", Integer.toBinaryString(values[1]));
		
		int larger = getNext(num);
		System.out.printf("larger  %s\n", Integer.toBinaryString(larger));
		
		int prev = getPrevious(num);
		System.out.printf("previos %s\n", Integer.toBinaryString(prev));
		
	}

}
