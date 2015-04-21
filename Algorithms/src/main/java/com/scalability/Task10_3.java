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
	
	private int findFreeBlock(File file, final int blocksCount, final int blockSize) throws IOException {
		int[] blocks = new int[blocksCount];
		
		//read file and count elements in block
		try (Scanner sc = new Scanner(new FileInputStream(file));) {
			int num;
			while (sc.hasNextInt() && (num = sc.nextInt()) >= 0) {
				blocks[blockIndex(num, blockSize)] ++;
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
		
		return freeBlock;
	}
	
	//blocksCount and blockSize should be power of 2
	public int findFreeNumSmallMemory(File file, final int blocksCount, final int blockSize) throws IOException {
		final int vector_item_size = 32; //because we use int 
		
		int freeBlock;
		if ((freeBlock = findFreeBlock(file, blocksCount, blockSize)) != -1) {						
			
			//find free num in block: read from file
			int[] bitVector = new int[blockSize / vector_item_size]; 
			try (Scanner sc = new Scanner(new FileInputStream(file));) {
				int num;
				while (sc.hasNextInt() && (num = sc.nextInt()) >= 0) {
					if (blockIndex(num, blockSize) == freeBlock) {
						num = num % blockSize;
						bitVector[num / vector_item_size] |= 1 << (num % vector_item_size);	
					}				
				}
			}
			
			//find free num in bitVector
			for (int i = 0; i < bitVector.length; i ++) {
				if (bitVector[i] != 0xFFFFFFFF) { //has free space
					for (int j = 0; j < vector_item_size; j ++) {
						if ((bitVector[i] & (1 << j)) == 0) {
							return freeBlock * blockSize + i * vector_item_size + j;  
						}
					}
				}
			}
		}
		return -1; // no result
	}

	private int blockIndex(int num, int size) {	
		return num / size;
	}
	
	public static void main(String[] args) throws Exception {
		Task10_3 task = new Task10_3();
		//System.out.println(task.findFreeNumBigMemory());
		
		int blocksCount = 1 << 5;
		int blockSize = 1 << 26;
		System.out.println(task.findFreeNumSmallMemory(new File("src/main/resources/task10_3_input.txt"), blocksCount, blockSize));
	}
	
	
}
