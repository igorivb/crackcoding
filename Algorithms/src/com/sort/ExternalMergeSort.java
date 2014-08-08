package com.sort;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sort.MergeOutput.OutputBinaryImpl;
import com.sort.MergeOutput.OutputTextImpl;

//TODO: make it generic
public class ExternalMergeSort {

	private final File inputFile;
	
	private int bytesInRecord;
	private final int blockSize;
	private final int memorySize;
	
	private final int blocksInFile;
	private final File workingDir;
	
	private final Comparator<Integer> cmp;						
	
	protected MergeInput createInput(File file) throws IOException {
		if (binary) {
			return new InputBinaryImpl(file, bytesInRecord, this.blockSize);	
		} else {
			return new InputTextImpl(file, bytesInRecord, this.blockSize);
		}		
	}
	
	public static MergeOutput createOutput(File file, int bytesInRecord, int blockSize) throws IOException {
		if (binary) {
			return new OutputBinaryImpl(file, bytesInRecord, blockSize);			
		} else {
			return new OutputTextImpl(file, bytesInRecord, blockSize);
		}				
	}
	
	protected MergeOutput createOutput(File file) throws IOException {		
		return createOutput(file, this.bytesInRecord, this.blockSize);
	}
	
	private void writeRecordsToFile(File file, MergeRecords records) throws IOException {				
		try (MergeOutput out = this.createOutput(file)) {			
			out.write(records);			
		}
	}
	
	public ExternalMergeSort(File inputFile, int bytesInRecord, int blockSize, int memorySize,
			Comparator<Integer> cmp) throws IOException {
		super();
		this.inputFile = inputFile;
		this.bytesInRecord = bytesInRecord;
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
		
		System.out.println("Binary: " + binary);
		System.out.println("Bytes in record: " + bytesInRecord);
		System.out.println("Records in block: " + blockSize);
		System.out.println("Blocks in memory: " + memorySize);
		System.out.println("Blocks in file: " + blocksInFile);
	}

	private int calculateBlocksInFile() {
		int blockSizeInBytes = this.blockSize * bytesInRecord;
		return divide((int) this.inputFile.length(), blockSizeInBytes);
	}
	
//	private int getBlockSizeInBytes() {
//		//this is because we work with int. Ideally it should come from Record class
//		return this.blockSize * bytesInRecord;
//	}
	
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
			
			TimeMeasure iterTime = new TimeMeasure("Iterating in group: " + i, 1);
			
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
				MergeRecords records = in.read(this.memorySize);
				tRead.endAndPrint();
				
				TimeMeasure tSort = new TimeMeasure("Internal sort", 2);
				this.internalSort(records.getRecords(), records.getStart(), records.getEnd());
				tSort.endAndPrint();
				
				TimeMeasure tWrite = new TimeMeasure("Write to file", 2);
				this.writeRecordsToFile(outFile, records);
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
	public static File sort(File inputFile, int bytesInRecord, int blockSize, int memorySize, Comparator<Integer> cmp) throws IOException {
		ExternalMergeSort sort = new ExternalMergeSort(inputFile, bytesInRecord, blockSize, memorySize, cmp);
		return sort.sort();
	}
	
	//method for testing
	private static void prepareTestData(File file, int bytesInRecord, int blockSize, int memorySize, int testRecords, boolean force) throws IOException {
		if (file.exists() && !force) {			
			return;			
		}		
		
		TimeMeasure t = new TimeMeasure("Prepare test data");
				
		try (MergeOutput out = createOutput(file, bytesInRecord, 5 * blockSize)) {
			
			Random rand = new Random(System.currentTimeMillis());
			for (int i = 0, size = testRecords /*blockSize * memorySize * 10*/; i < size; i ++) {
				int num = rand.nextInt(9999999); 
				out.writeRecord(num);
			}
		}
		
		t.endAndPrint();
	}
	
	static boolean binary = false;
	
	
	static int binaryToText(File inputFile, File outputFile, int bytesInRecord, int blockSize, int memorySize) throws IOException {
		
		TimeMeasure t = new TimeMeasure("Binary to text");
		
		int recordsCount = 0;
		
		try (
			InputBinaryImpl input = new InputBinaryImpl(inputFile, bytesInRecord, blockSize);
			OutputTextImpl output = new OutputTextImpl(outputFile, /* double for text*/ bytesInRecord * 2, blockSize);
		) {			
			MergeRecords records = input.read(memorySize - 1);
			while (!records.isEmpty()) {			
				recordsCount += records.getSize();
				output.write(records);
				records = input.read(memorySize - 1);
			}
		}
		
		t.endAndPrint();
		
		return recordsCount;
	}
	
	/**
	 * Example of command line: 
	 * --prepareTestData true --sort true --binaryToText true --binary true --removeTestData true
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		//process input params			
		Map<String, String> params = new HashMap<>();		
		for (int i = 0; i < args.length - 1; i += 2) {
			String key = args[i].trim();
			String value = args[i + 1].trim();						
			params.put(key, value);			
		}		
		
		boolean prepareTestData = Boolean.valueOf(params.get("--prepareTestData"));		 		
		boolean binaryToText = Boolean.valueOf(params.get("--binaryToText"));		
		boolean sort = Boolean.valueOf(params.get("--sort"));
		
		binary = Boolean.valueOf(params.get("--binary"));
		boolean removeTestData = Boolean.valueOf(params.get("--removeTestData"));
						
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
		
		
		
		//TODO: don't hard code it
		int bytesInRecord = binary ? 4 : 8;		
		
		
		/*
		 * BlockSize - 10MB / bytesInRecord
		 * Records: 100 millions
		 */
		//recordsCount: 1MB / bytesInRecord
		final int blockSize = 10 * 1024 * 1024 / bytesInRecord;		
		final int memorySize = 8;		
		final int testRecords = 100_000_000;														
		
		File sortOuputFile = null;
		if (prepareTestData || sort) {
			
			File inputFile;
			if (params.containsKey("--inputFile")) {
				inputFile = new File(params.get("--inputFile"));
			} else {
				inputFile = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/input.data");
			}	
		
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Starting...");
			
			//prepare test data
			if (prepareTestData) {
				prepareTestData(inputFile, bytesInRecord, blockSize, memorySize, testRecords, removeTestData);
			} 
			
			if (sort) {
				//sort
				Comparator<Integer> cmp = Integer::compareTo;	
				
				sortOuputFile = sort(inputFile, bytesInRecord, blockSize, memorySize, cmp);
				System.out.printf("Output file: %s.\n", sortOuputFile.getAbsolutePath());		
			}			
		}
		
		//binary to text
		if (binaryToText) {
			File inputFile = null;
			File outputFile = null;
			
			if (sort && binary) {
				//in case of binary sort, we transform its output to text
				inputFile = sortOuputFile;
				outputFile = new File(inputFile.getParentFile(), "output-from-binary.data");
			} else {
				inputFile = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/working-input.data-1407500615078/sort_1_0");
				outputFile = new File("C:/Users/iburilo/git/crackcoding/Algorithms/resources/working-input.data-1407500615078/output-from-binary.data");
			}
						
			int recordsCount = binaryToText(inputFile, outputFile, bytesInRecord, blockSize, memorySize);
			System.out.println("BinaryToText, recordsCount: " + recordsCount);
		} 
	}
}
