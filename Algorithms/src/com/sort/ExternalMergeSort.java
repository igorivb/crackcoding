package com.sort;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import com.sort.MergeOutput.OutputTextImpl;

//TODO: make it generic
public class ExternalMergeSort {

	private final File inputFile;
	private final int blockSize;
	private final int memorySize;
	
	private final int blocksInFile;
	private final File workingDir;
	
	private final Comparator<Integer> cmp;						
	
	
	//TODO: don't hard code it
	public final static int bytesInRecord = 8;
	
	public static class TimeMeasure {
		long start;
		long end;
		String msg;
		String indent = "";
		
		public TimeMeasure(String msg) {
			this(msg, 0);
		}
		
		public TimeMeasure(String msg, int ind) {
			this.msg = msg;
			
			if (ind > 0) {
				for (int i = 0; i < ind; i ++) {
					indent = "  " + indent;
				}
			}
			
			start();
			
			System.out.println(indent + "Start - " + msg);
		}				
		
		public void start() {
			start = System.currentTimeMillis();
		}
		
		public void end() {
			end = System.currentTimeMillis();
		}		
		
		public String diff() {
			double val = (end - start) / 1000.0;
			BigDecimal bd = new BigDecimal(val, new MathContext(4, RoundingMode.CEILING));			
			return bd.toString();
		}
		
		public void endAndPrint() {
			end();
			System.out.println(indent + "End - " + msg + ": " + diff());
		}
	}
	
	protected MergeInput createInput(File file) throws IOException {
		return new InputTextImpl(file, this.blockSize, this.getBlockSizeInBytes());
	}
	
	protected MergeOutput createOutput(File file) throws IOException {
		return new OutputTextImpl(file, this.getBlockSizeInBytes());
	}
	
	private void writeRecordsToFile(File file, int[] records, int fromIndex, int toIndex) throws IOException {				
		try (MergeOutput out = this.createOutput(file)) {
			for (int i = fromIndex; i < toIndex; i ++) {
				int record = records[i];
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
		
		System.out.println("Records in block: " + blockSize);
		System.out.println("Blocks in memory: " + memorySize);
		System.out.println("Blocks in file: " + blocksInFile);
	}

	private int calculateBlocksInFile() {
		return divide((int) this.inputFile.length(), getBlockSizeInBytes());
	}
	
	private int getBlockSizeInBytes() {
		//this is because we work with int. Ideally it should come from Record class
		return this.blockSize * bytesInRecord;
	}
	
	protected void internalSort(int[] data, int fromIndex, int toIndex) {
		Arrays.sort(data, fromIndex, toIndex);
	}
	
	private static int divide(int a, int b) {
		return (int) Math.ceil(1.0 * a / b);
	}
	
	private File getOutputFile(int iteration, int num) {
		File file = new File(this.workingDir, String.format("sort_%s_%s", iteration, num));
		return file;
	}
	
	public File sort() throws IOException {
		
		TimeMeasure t = new TimeMeasure("Sort");
		
		File[] runFiles = this.partialSort(this.inputFile);
		
		//stop if there's only 1 file
		int iteration = 1;
		while (runFiles.length > 1) {
			runFiles = this.merge(runFiles, iteration ++);
		}
		
		t.endAndPrint();
		
		return runFiles[0];
	} 	
	
	private File[] merge(File[] inputFiles, int counter) throws IOException {
		//leave 1 block for output
		int filesToProcess = this.memorySize - 1;
		int iterationsCount = divide(inputFiles.length, filesToProcess);
		File[] outputFiles = new File[iterationsCount];
		
		String msg = String.format("Merging, iteration: %s, input files count: %s, iterations count: %s", counter, inputFiles.length, iterationsCount);
		TimeMeasure totalTime = new TimeMeasure(msg);
		
		for (int i = 0; i < iterationsCount; i ++) {						
			
			TimeMeasure iterTime = new TimeMeasure("Iteration in group", 1);
			
			//iterate files in group, find min element and write it to output			
			int count = Math.min(filesToProcess, inputFiles.length - filesToProcess * i);		
			
			MergeInput[] inputs = new MergeInput[count];
			for (int j = 0; j < count; j ++) {
				inputs[j] = this.createInput(inputFiles[filesToProcess * i + j]);
			}
						
			File outFile = this.getOutputFile(counter, i);
			outputFiles[i] = outFile;
			
			try (MergeOutput out = this.createOutput(outFile)) {	
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
				for (MergeInput in : inputs) {
					try {
						in.close();	
					} catch (IOException ie) { /* ignore */ }					
				}								
			}
			
			iterTime.endAndPrint();
		}
		
		totalTime.endAndPrint();
		
		return outputFiles;
	}
	
	private File[] partialSort(File inputFile) throws IOException {
		TimeMeasure totalTime = new TimeMeasure("Partial Sort");
		
		int iterationsCount = divide(this.blocksInFile, this.memorySize);
		File[] outputFiles = new File[iterationsCount];
		
		System.out.printf("Partial sort, iterations count: %s\n", iterationsCount);
		
		try (MergeInput in = this.createInput(this.inputFile)) {			
			for (int i = 0; i < iterationsCount; i ++) {
				//read, sort and store data in tmp file
								
				TimeMeasure t = new TimeMeasure("PartialSort " + i, 1);				
				
				File outFile = this.getOutputFile(0, i);
				outputFiles[i] = outFile;
							
				TimeMeasure tRead = new TimeMeasure("Read from file", 2);				
				MergeInput.Records records = in.read(this.memorySize);
				tRead.endAndPrint();
				
				TimeMeasure tSort = new TimeMeasure("Internal sort", 2);
				this.internalSort(records.getRecords(), 0, records.getSize());
				tSort.endAndPrint();
				
				TimeMeasure tWrite = new TimeMeasure("Write to file", 2);
				this.writeRecordsToFile(outFile, records.getRecords(), 0, records.getSize());
				tWrite.endAndPrint();
				
				t.endAndPrint();				
			}						
		}
		
		totalTime.endAndPrint();
		
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
	private static void prepareTestData(File file, int blockSize, int memorySize, int testRecords, boolean force) throws IOException {
		if (file.exists() && !force) {			
			return;			
		}		
		
		TimeMeasure t = new TimeMeasure("Prepare test data");
		
		try (MergeOutput out = new OutputTextImpl(file, 5 * blockSize * bytesInRecord);) {
			
			Random rand = new Random(System.currentTimeMillis());
			for (int i = 0, size = testRecords /*blockSize * memorySize * 10*/; i < size; i ++) {
				int num = rand.nextInt(9999999); 
				out.writeRecord(num);
			}
		}
		
		t.endAndPrint();
	}
	
	public static void main(String[] args) throws IOException {
		//minimal
//		final int blockSize = 5;				
//		final int memorySize = 4;
		
//		/*
//		 * BlockSize - 1MB / bytesInRecord
//		 * Records: 10millions
//		 */
//		//recordsCount: 1MB / bytesInRecord
//		final int blockSize = 1 * 1024 * 1024 / bytesInRecord;		
//		final int memorySize = 4;		
//		final int testRecords = 10_000_000;
		
		/*
		 * BlockSize - 10MB / bytesInRecord
		 * Records: 100 millions
		 */
		//recordsCount: 1MB / bytesInRecord
		final int blockSize = 10 * 1024 * 1024 / bytesInRecord;		
		final int memorySize = 8;		
		final int testRecords = 100_000_000;
				
		File inputFile;
		if (args.length > 0) {
			inputFile = new File(args[0]);
		} else {
			inputFile = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/input.data");
			prepareTestData(inputFile, blockSize, memorySize, testRecords, false);
		}		
		
		Comparator<Integer> cmp = Integer::compareTo;	
		
		File outputFile = sort(inputFile, blockSize, memorySize, cmp);
		System.out.printf("Output file: %s.\n", outputFile.getAbsolutePath());
		
		//output
//		try (MergeInput in = new InputTextImpl(outputFile, blockSize, blockSize * bytesInRecord)) {
//			while (true) {
//				MergeInput.Records records = in.read(1);
//				if (records.getSize() == 0) {
//					break;
//				}
//				StringBuilder str = new StringBuilder();
//				for (int i = 0; i < records.getSize(); i ++) {
//					for (int record : records.getRecords()) {
//						str.append(String.format("%2s", record)).append(",");		
//					}								
//				}	
//				System.out.println(str);
//			}			
//		}	
	}
}
