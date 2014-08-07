package com.sort;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ExternalMergeSort {

	private final File inputFile;
	private final int blockSize;
	private final int memorySize;
	
	private final int blocksInFile;
	private final File workingDir;
	
	private final Comparator<Integer> cmp;	
			
	//TODO: make it generic
	private static class Input implements AutoCloseable {

		private final File file;
		private final int blockSize;
		
		public Input(File file, int blockSize) {
			this.file = file;
			this.blockSize = blockSize;
		}
		
		/**
		 * Read records by blocks. 
		 * If there's no more data, result array has zero length.
		 * 
		 * @param blocksToRead
		 * @return records
		 */
		public int[] read(int blocksToRead) {
			//TODO: implement
			return new int[0];
		}
				
		/**
		 * Read record.
		 * If there's no more data, return null.
		 * 
		 * @param remove - if remove record after reading, otherwise peek.
		 * @return
		 */
		public Integer readRecord(boolean remove) {
			//TODO: implement
			return 0;
		}
		
		@Override
		public void close() throws IOException {
			//TODO: implement
		}		
	}
	
	private static class Output implements AutoCloseable {
		private final File file;
		private final int blockSizeInBytes;
		
		private DataOutputStream out;
		
		public Output(File file, int blockSizeInBytes) throws IOException {
			this.file = file;
			this.blockSizeInBytes = blockSizeInBytes;
			
			this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), blockSizeInBytes));
		}

		public void writeRecord(int record) throws IOException {
			out.writeInt(record);
		}
		
		@Override
		public void close() throws IOException {
			// TODO implement			
		}
	}
	
	protected Input createInput(File file) {
		return new Input(file, this.blockSize);
	}
	
	protected Output createOutput(File file) throws IOException {
		return new Output(file, this.getBlockSizeInBytes());
	}
	
	private void writeRecordsToFile(File file, int[] records) throws IOException {				
		try (Output out = this.createOutput(file)) {
			for (int record : records) {
				out.writeRecord(record);	
			}
		}
	}
	
	public ExternalMergeSort(File inputFile, int blockSize, int memorySize,
			Comparator<Integer> cmp) throws IOException {
		super();
		this.inputFile = inputFile;
		this.blockSize = blockSize;
		this.memorySize = memorySize;
		this.cmp = cmp;
		
		this.blocksInFile = this.calculateBlocksInFile();
		
		//prepare working dir
		this.workingDir = new File(
			inputFile.getParentFile(), 
			String.format("working-%s-%s", inputFile.getName(), System.currentTimeMillis()));
		if (!this.workingDir.mkdirs()) {
			throw new IOException(String.format("Failed to create working directory: %s", this.workingDir.getAbsolutePath()));
		}
	}

	private int calculateBlocksInFile() {
		//TODO: implement
		return 0;
	}
	
	private int getBlockSizeInBytes() {
		//this is because we work with int. Ideally it should come from Record class
		return this.blockSize * 4;
	}
	
	private static void internalSort(int[] data) {
		Arrays.sort(data);
	}
	
	private static int divide(int a, int b) {
		return (int) Math.ceil(1.0 * a / b);
	}
	
	private File getOutputFile(int iteration, int num) {
		File file = new File(this.workingDir, String.format("sort_%s_%s", iteration, num));
		return file;
	}
	
	public File sort() throws IOException {
		File[] runFiles = this.partialSort(this.inputFile);
		
		//stop if there's only 1 file
		int iteration = 1;
		while (runFiles.length > 1) {
			runFiles = this.merge(runFiles, iteration ++);
		}		
		return runFiles[0];
	} 	
	
	private File[] merge(File[] inputFiles, int counter) throws IOException {
		//leave 1 block for output
		int filesToProcess = this.memorySize - 1;
		int iterationsCount = divide(inputFiles.length, filesToProcess);
		File[] outputFiles = new File[iterationsCount];
		
		for (int i = 0; i < iterationsCount; i ++) {						
			
			//iterate files in group, find min element and write it to output			
			int count = Math.min(filesToProcess, inputFiles.length - filesToProcess * i);		
			
			Input[] inputs = new Input[count];
			for (int j = 0; j < count; j ++) {
				inputs[j] = this.createInput(inputFiles[filesToProcess * i + j]);
			}
						
			File outFile = this.getOutputFile(counter, i);
			outputFiles[i] = outFile;
			
			try (Output out = this.createOutput(outFile)) {	
				//check first records in all files till there is data
				for (;;) {				
					Integer min = null;
					Integer minFile = null;
					
					for (int j = 0; j < count; j ++) {
						Integer record = inputs[j].readRecord(false);
						if (record != null && (min == null || (this.cmp.compare(min, record) > 0))) {
							min = record;
							minFile = j;
						}
					}	
					
					if (min != null) {
						//write min to output and remove from input file
						out.writeRecord(min);
						inputs[minFile].readRecord(true);
					} else {
						//no more data: stop processing
						break;
					}
				}
			} finally {
				// close inputs, output is closed in try-with block
				for (Input in : inputs) {
					try {
						in.close();	
					} catch (IOException ie) { /* ignore */ }					
				}								
			}
		}
		
		return outputFiles;
	}
	
	private File[] partialSort(File inputFile) throws IOException {		
		int iterationsCount = divide(this.blocksInFile, this.memorySize);
		File[] outputFiles = new File[iterationsCount];
		
		try (Input in = this.createInput(this.inputFile)) {			
			for (int i = 0; i < iterationsCount; i ++) {
				//read, sort and store data in tmp file
				
				File outFile = this.getOutputFile(0, i);
				outputFiles[i] = outFile;
								
				int[] data = in.read(this.memorySize);
				this.internalSort(data);
				
				this.writeRecordsToFile(outFile, data);								
			}						
		}
		return outputFiles;
	}
	
	/**
	 * 
	 * @param inputFile - input file with records
	 * @param blockSize - number of records in block
	 * @param memorySize - number of blocks in memory
	 * @param cmp - comparator
	 * @return output sorted file
	 */
	public static File sort(File inputFile, int blockSize, int memorySize, Comparator<Integer> cmp) throws IOException {
		ExternalMergeSort sort = new ExternalMergeSort(inputFile, blockSize, memorySize, cmp);
		return sort.sort();
	}
	
	//method for testing
	private static void prepareTestData(File inputFile, int blockSize, boolean force) {
		//TODO: implement
	}
	
	public static void main(String[] args) throws IOException {
		final int blockSize = 5;				
		final int memorySize = 4;
				
		File inputFile = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/input.data");
		//File outDir = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/output");
		
		Comparator<Integer> cmp = Integer::compareTo;	
		
		prepareTestData(inputFile, blockSize, false);
		
		File outputFile = sort(inputFile, blockSize, memorySize, cmp);
		System.out.printf("Output file: %s.\n", outputFile.getAbsolutePath());		
	}

}
