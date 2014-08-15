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
import java.util.Arrays;
import java.util.Comparator;


public class ExternalMergeSort2<T> {
	
	//in blocks
	private int memorySize;
	
	//in records
	private int blockSize;
	
	private Comparator<T> cmp;
	
	private IOFactory<T> ioFactory;
	
	private File workingDir;
	
	/**
	 * Factory to create input and output.
	 *
	 * @param <T> - record type
	 */
	interface IOFactory<T> {
		Input<T> createInput(File file, int blockSize) throws IOException;
		Output<T> createOutput(File file, int blockSize) throws IOException;
	}
	
	static class IntegerBinaryIOFactory implements IOFactory<Integer> {
		
		public Input<Integer> createInput(File file, int blockSize) throws IOException {
			Input<Integer> input = new IntegerBinaryInput(file, blockSize);
			return input;
		}

		public Output<Integer> createOutput(File file, int blockSize) throws IOException {			
			Output<Integer> output = new IntegerBinaryOutput(file, blockSize);
			return output;
		}
	} 
	
	/**
	 * Input.

	 * @param <T> - record type
	 */
	public interface Input<T> extends AutoCloseable {		
		void close() throws IOException;
		int read(T[] records) throws IOException;
		T readRecord(boolean remove) throws IOException;
		int getBlocksCount() throws IOException;
	}

	private static class IntegerBinaryInput implements Input<Integer> {
		
		private final static int bytesInRecord = 4;
		
		private DataInputStream in;
		
		private File file;
		private int blockSize;
		
		//for readRecord
		private Integer cached;		
		private boolean hasData = true;
		
		public IntegerBinaryInput(File file, int blockSize) throws IOException {
			this.file = file;
			this.blockSize = blockSize;
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(file), blockSize * bytesInRecord));
		}
		
		@Override
		public int read(Integer[] records) throws IOException {
			int i = 0;			
			for (int n = records.length; i < n; i ++) {					
				if ((records[i] = this.readRecord0()) == null) {
					break;
				}
			}						
			return i;
		}
		
		@Override
		public int getBlocksCount() {
			return divide(this.file.length(), blockSize * bytesInRecord);
		}
		
		@Override
		public Integer readRecord(boolean remove) throws IOException {						
			Integer record = null;
			
			if (hasData) {
				if (cached != null) {
					record = cached;
				} else {
					if ((record = readRecord0()) == null) {
						hasData = false;
					}				
				}					
				
				cached = remove ? null : record;
			}
			
			return record;
		}
		
		private Integer readRecord0() throws IOException {
			Integer record = null;
			try {
				record = this.in.readInt();	
			} catch (EOFException e) {  /* ignore */ }
			return record;
		}
		
		@Override
		public void close() throws IOException {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ie) { /* ignore */ }
				this.in = null;
			}			
		}			
	}
	
	/**
	 * Ouput.
	 *
	 * @param <T> - record type
	 */
	public interface Output<T> extends AutoCloseable {
		void close() throws IOException;
		void write(T[] records, int len) throws IOException;	
		void writeRecord(T record) throws IOException;
	}
	
	
	private static class IntegerBinaryOutput implements Output<Integer> {

		private File file;
		private int blockSize;
		
		private DataOutputStream out;
		
		public IntegerBinaryOutput(File file, int blockSize) throws IOException {
			this.file = file;
			this.blockSize = blockSize;
			
			this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), blockSize * IntegerBinaryInput.bytesInRecord));
		}
		
		@Override
		public void write(Integer[] records, int len) throws IOException {
			for (int i = 0; i < len; i ++) {
				writeRecord(records[i]);
			}			
		}
		
		public void writeRecord(Integer record) throws IOException {
			out.writeInt(record);
		}
		
		@Override
		public void close() throws IOException {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ie) { /* ignore */ }
				this.out = null;
			}	
		}		
		
	}
	
	public ExternalMergeSort2(int memorySize, int blockSize, IOFactory<T> ioFactory, Comparator<T> cmp, File workingDir) {
		this.memorySize = memorySize;
		this.blockSize = blockSize;
		this.cmp = cmp;
		this.ioFactory = ioFactory;
		this.workingDir = workingDir;
	}
	
	private File[] partialSort(File inputFile) throws IOException {
		
		File[] outputFiles = null;
		
		try (Input<T> input = ioFactory.createInput(inputFile, this.blockSize)) {
			
			//reserve 1 block for output
			int blocksToRead = this.memorySize - 1;
			
			int iterationsNum = divide(input.getBlocksCount(), blocksToRead);
			
			outputFiles = new File[iterationsNum];
			
			for (int i = 0; i < iterationsNum; i ++) {
				//read
				@SuppressWarnings("unchecked")			
				T[] records = (T[]) new Object[blocksToRead * this.blockSize];				
				int c = input.read(records);
				
				//sort
				this.internalSort(records, c);
				
				//write
				outputFiles[i] = this.getOutputFile(0, i);			
				try (Output<T> output = ioFactory.createOutput(outputFiles[i], this.blockSize)) {
					output.write(records, c);	
				}				
			}
		}
						
		return outputFiles;
	}		

	static int divide(long a, long b) {
		return (int) Math.floor((double) a / b);
	}

	private void internalSort(T[] records, int len) {
		Arrays.sort(records, 0, len, this.cmp);		
	}	

	private File getOutputFile(int group, int num) {
		File file = new File(workingDir, "part_" + group + "_" + "num");
		return file;
	}

	private File[] merge(File[] inputFiles, int stage) throws IOException {		
		
		//reserve 1 block for output
		int filesToProcessCount = this.memorySize - 1;
		
		int iterationsNum = divide(inputFiles.length, filesToProcessCount);
		
		File[] outputFiles = new File[iterationsNum];
		
		for (int i = 0; i < iterationsNum; i ++) {
		
			//process group
			outputFiles[i] = this.getOutputFile(stage, i);			
			try (Output<T> output = ioFactory.createOutput(outputFiles[i], this.blockSize)) {
				
				int inputsCount = Math.min(filesToProcessCount, inputFiles.length - i * filesToProcessCount);
				
				//create and init inputs
				@SuppressWarnings("unchecked")
				Input<T> inputs[] = new Input[inputsCount];
				for (int j = 0; j < inputsCount; j ++) {
					inputs[j] = this.ioFactory.createInput(inputFiles[i * filesToProcessCount + j], this.blockSize);
				}
				
				this.mergeGroup(inputs, output);
				
				//close outputs
				for (int j = 0; j < inputsCount; j ++) {
					try {
						inputs[j].close();	
					} catch (IOException e) { /* ignore */ }					
				}
			}
		}
		
		return outputFiles;
	}
	
	
	private void mergeGroup(Input<T>[] inputs, Output<T> output) throws IOException {
		for (;;) {
			T min = null;
			int minIndex = -1;
			
			for (int i = 0; i < inputs.length; i ++) {				
				T record = inputs[i].readRecord(false);
				if (record != null && (min == null || cmp.compare(min, record) > 0)) {
					min = record;
					minIndex = i;
				}
			}
			
			if (min != null) {
				output.writeRecord(min);
				
				//remove from input
				inputs[minIndex].readRecord(true);				
			} else {
				break;
			}
		}
	}

	public File sort(File inputFile) throws IOException {
		
		File[] runFiles = this.partialSort(inputFile);
		
		//merge
		int i = 0;
		while (runFiles.length > 1) {
			runFiles = this.merge(runFiles, ++ i);
		}
		
		return runFiles[0];
	}
	
	public static void main(String[] args) throws IOException {
		//TODO: init values
		int memorySize = 8;		
		int blockSize = 100;
		File inputFile = null;
		IOFactory<Integer> ioFactory = new IntegerBinaryIOFactory(); 
		Comparator<Integer> cmp = Integer::compare;
		File workingDir = null;

		
		ExternalMergeSort2<Integer> sort = new ExternalMergeSort2<>(memorySize, blockSize, ioFactory, cmp, workingDir);
		File output = sort.sort(inputFile);
		System.out.println("Output: " + output.getAbsolutePath());
	}

}
