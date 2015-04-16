package com.scalability;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class Task10_3 {

	public int findFreeNumBigMemory() {
		byte[] bitVector = new byte[1 << 28];
		
		try (Scanner sc = new Scanner(System.in)) {
			int num;
			while (sc.hasNextInt() && (num = sc.nextInt()) >= 0) {				
				//bitVector[num / 8] |= 1 << (num % 8); 
				bitVector[num >> 3] |= 1 << (num & ((1 << 3) - 1));
			}
			
			for (int i = 0; i < bitVector.length; i ++) {
				if (bitVector[i] != (byte) 0xFF) {
					for (int j = 0; j < 8; j ++) {
						if ((bitVector[i] & (1 << j)) == 0) { //found
							return i * 8 + j;
						}
					}
				}
			}
		} 
				
		return -1; //not found
	}
	
	public int findFreeNumSmallMemory(File file, final int blocksCount, final int blockSize) throws IOException {
		int[] blocks = new int[blocksCount];
		
		//read file and count elements in block
		try (Scanner sc = new Scanner(new FileInputStream(file));) {
			int num;
			while (sc.hasNextInt() && (num = sc.nextInt()) >= 0) {
				blocks[blockIndex(num, blocksCount)] ++;
			}
		}
		
		//find not full block
		int freeBlock = -1;
		for (int i = 0; i < blocksCount; i ++) {
			if (blocks[i] < blockSize) {
				freeBlock = i;
				break;
			}
		}
		
		if (freeBlock == -1) {
			return freeBlock;// no result
		}
		
		//find free num in block: read from file
		final int int_size = 32; 
		
		int[] bitVector = new int[blockSize / int_size]; //TODO: can loose some memory		
		try (Scanner sc = new Scanner(new FileInputStream(file));) {
			int num;
			while (sc.hasNextInt() && (num = sc.nextInt()) >= 0) {
				if (blockIndex(num, blocksCount) == freeBlock) {											
					bitVector[num % bitVector.length] |= 1 << (num % int_size);	
				}				
			}
		}
		
		//find free num in bitVector
		for (int i = 0; i < bitVector.length; i ++) {
			if (bitVector[i] != 0xFFFFFFFF) { //has free space
				for (int j = 0; j < int_size; j ++) {
					if ((bitVector[i] & (1 << j)) == 0) {
						return freeBlock * blockSize + i * int_size + j;  
					}
				}
			}
		}
		
		return -1; //should never go here
	}

	private int blockIndex(int num, int size) {	
		return num % size;
	}
	
	public static void main(String[] args) {
		Task10_3 task = new Task10_3();
		System.out.println(task.findFreeNumBigMemory());
	}
}
