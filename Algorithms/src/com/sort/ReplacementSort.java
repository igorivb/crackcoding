package com.sort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Random;

//TODO: implement it
public class ReplacementSort {

	/**
	 * Input
	 */
	private static class Input implements AutoCloseable {
		
		private File file;
		private DataInputStream in;
		
		public Input(File file, int blockSize) throws IOException {
			this.file = file;			
			this.in = new DataInputStream(new BufferedInputStream(new FileInputStream(file), blockSize * 4));
		}
		
		public int read() throws IOException {		
			return this.in.readInt();
		}
		
		@Override
		public void close() throws Exception {
			if (this.in != null) {
				try {
					this.in.close();	
				} catch (IOException ie) { /* ignore */ }				
				this.in = null;
			}			
		}
	}
	
	/**
	 * Output
	 */
	private static class Output implements AutoCloseable {

		private File dir;
		private int counter = 0;
		
		private final int blockSize;
		private final String prefix = "sortout";
		
		//only 1 out can be open in the moment		
		private DataOutputStream out;
		
		public Output(File dir, int blockSize) throws IOException {
			this.dir = dir;
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			this.blockSize = blockSize;
			
			changeOutput();
		}
		
		public void write(int num) throws IOException {
			out.writeInt(num);
		}
		
		public void changeOutput() throws IOException {						
			//close previous if any
			if (this.out != null) {
				try {
					this.out.flush();
					this.out.close();
					this.out = null;
				} catch (IOException ie) { /* ignore */ }					
			}
			
			File file = new File(this.dir, this.prefix + "_" + counter);
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), blockSize * 4));
			
			this.counter ++;
		}
		
//		public void write(int num) throws IOException {
//			//check if need to write to another file
//			if (counter % (blockSize * blocksInFile) == 0) {
//				
//				//close previous if any
//				if (this.out != null) {
//					try {
//						this.out.close();
//						this.out = null;
//					} catch (IOException ie) { /* ignore */ }					
//				}
//				
//				int fileNum = counter / (blockSize * blocksInFile);
//				File file = new File(this.dir, this.prefix + "_" + fileNum);
//				System.out.printf("Output to file: %s.\n", file.getAbsolutePath());
//				out = new DataOutputStream(new FileOutputStream(file));
//			}
//			
//			//write
//			this.out.writeInt(num);
//			this.counter ++;
//			
//			//do flush if block is filled
//			if (counter % blockSize == 0) {
//				System.out.println("Flushing output.");
//				this.out.flush();
//			}
//		}
		
		@Override
		public void close() throws Exception {
			if (this.out != null) {
				try {
					this.out.close();
				} catch (IOException ie) { /* ignore */ }
				this.out = null;
			}			
		}		
	}
	
	/**
	 * Heap
	 */
	private static class Heap {
		
		private final Comparator<Integer> cmp;
		private int[] mas;
		
		private int size;
		//last <= size
		private int last;				
		
		public Heap(int[] mas, int last, int size, Comparator<Integer> cmp) {
			this.mas = mas;
			this.last = last;
			this.size = size;
			this.cmp = cmp;
		}
		
		public int getLeft(int i) {
			int res = 2 * i + 1; 
			return res <= this.last ? res : -1;
		}
		
		public int getRight(int i) {
			int res = getLeft(i);
			return (res >= 0 && (res + 1) <= last) ? res + 1 : -1;
		}
		
		//min-heap
		public void heapify(int i) {
			for (;;) {
				int left = this.getLeft(i);
				int right = this.getRight(i);
				int max = -1;
				
				if (left >= 0) {
					max = left;
					
					if (right >= 0) {
						max = cmp.compare(this.mas[left], this.mas[right]) < 0 ? left : right;
					}
				}
				
				if (max != -1 && cmp.compare(mas[i], mas[max]) > 0) {
					//swap
					int tmp = mas[i];
					mas[i] = mas[max];
					mas[max] = tmp;
							
					i = max;
				} else {
					break;
				}
			}
		}
	}
	
	private Input in;
	private Output out;
	
	private Heap heap;
	
	public ReplacementSort(Input in, Output out) {
		this.in = in;
		this.out = out;
	}
	
	public void sort(int arrayLength, Comparator<Integer> cmp) throws IOException {
				
		Heap heap = this.createHeap(arrayLength, cmp);		
		boolean hasInput = true;
		
		//process till there are elements in heap
		while (heap.size > 0) {
			
			//write root to output
			int root = heap.mas[0];
			out.write(root);

			if (hasInput) {
				try {
					int input = in.read();	
					
					//check if input < lastOutput, if so add it in the end and shrink array
					if (cmp.compare(input, root) < 0) {
						heap.mas[0] = heap.last;
						heap.mas[heap.last] = input;
						heap.last --;
					} else {
						heap.mas[0] = input;
						heap.heapify(0);
					}	
				} catch (EOFException e) {
					//no more input
					hasInput = false;
				}								
			}
			
			if (!hasInput) {		
				//heapify till we have elements
				heap.heapify(0);
			}						
		}				
	}
	
	private Heap createHeap(int arrayLength, Comparator<Integer> cmp) throws IOException {		
		int mas[] = new int[arrayLength];
	
		int i;
		for (i = 0; i < arrayLength; i ++) {
			try {
				int num = in.read();
				mas[i] = num;
			} catch (EOFException ie) {
				//ignore
				break;
			}					
		}
								
		Heap heap = new Heap(mas, i - 1, i, cmp);	
		
		//heapify
		for (i = (heap.last - 1) >> 1; i >= 0; i --) {
			heap.heapify(i);
		}
		
		return heap;
	}
	
	private static void prepareTestData(File file, int blockSize, int blocksInFile, boolean force) throws Exception {
		if (file.exists() && !force) {			
			return;			
		}
		
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file));) {
			
			Random rand = new Random(System.currentTimeMillis());
			for (int i = 0, size = blockSize * blocksInFile * 4; i < size; i ++) {
				int num = rand.nextInt(100); 
				out.writeInt(num);
			}
			out.flush();
		}
	}
	
	public static void main(String[] args) throws Exception {
			
		final int blockSize = 4;
		final int blocksInFile = 3;
		final int arrayLength = blockSize * 2;
		
		File inFile = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/input.data");
		File outDir = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/output");
		
		prepareTestData(inFile, blockSize, blocksInFile, false);
		
		try (Input in = new Input(inFile, blockSize); Output out = new Output(outDir, blockSize);) {
			Comparator<Integer> cmp = Integer::compareTo;	
			
			ReplacementSort sort = new ReplacementSort(in, out);			
			sort.sort(arrayLength, cmp);
		}
	}
}
