package com.sort;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.sort.ExternalMergeSort2.IOFactory;
import com.sort.ExternalMergeSort2.Input;
import com.sort.ExternalMergeSort2.IntegerBinaryIOFactory;
import com.sort.ExternalMergeSort2.Output;

public class ExternalMergeSort2Test {
	
	static <T> void convertFilesToText(File dir, IOFactory<T> ioFactory) throws IOException {				
		File[] files = dir.listFiles(file -> file.isFile() && file.getName().endsWith(".data"));
		for (File inputFile : files) {
			File outputFile = new File(inputFile.getParentFile(), inputFile.getName() + ".txt");
			convertFileToText(inputFile, outputFile, ioFactory);			
		}
	}
	
	static <T> void convertFileToText(File inputFile, File outputFile, IOFactory<T> ioFactory) throws IOException {
		int bufSize = 1024;		
		try (
			Input<T> input = ioFactory.createInput(inputFile); 
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile), bufSize)) {								
			for (;;) {
				T record = input.readRecord();
				if (record != null) {
					String val = String.valueOf(record);
					out.write(val);
					out.write("\n");
				} else {
					break;
				}
			}				
		}
	}
	
	
	@Test
	public void test() throws Exception {

		int memorySize = 4;		
		int blockSize = 3;
	
		File workingDir = new File("target/external-sort-test");
		File inputFile = new File(workingDir, "input.data");		
		
		Comparator<Integer> cmp = Integer::compare;
		IOFactory<Integer> ioFactory = new IntegerBinaryIOFactory(blockSize); 
		
		//prepare test data
		int testRecords = 30;
		
		//clear and create new
		if (workingDir.exists()) {
			FileUtils.deleteDirectory(workingDir);
		}	
		workingDir.mkdirs();		
		
		List<Integer> initialArray = new ArrayList<>(testRecords);						
		try (Output<Integer> out = ioFactory.createOutput(inputFile)) {
			Random rand = new Random(System.currentTimeMillis());
			for (int i = 0; i < testRecords; i ++) {
				int num = rand.nextInt(100); 
				out.writeRecord(num);
				
				initialArray.add(num);
			}
		}				
				
		//sort
		ExternalMergeSort2<Integer> sort = new ExternalMergeSort2<>(Integer.class, memorySize, blockSize, ioFactory, cmp, workingDir);
		File outputFile = sort.sort(inputFile);
		System.out.println("Output: " + outputFile.getAbsolutePath());
		
		//verify results
		convertFilesToText(workingDir, ioFactory);
		
		List<Integer> result = new ArrayList<>();
		try (Input<Integer> in = ioFactory.createInput(outputFile)) {
			for (;;) {
				Integer record = in.readRecord();
				if (record != null) {
					result.add(record);
				} else {
					break;
				}
			}			
			
		}
		
		Collections.sort(initialArray, cmp);
		
		String msg = String.format("Expected: %s\nResult:   %s", initialArray, result);
		assertEquals(msg, initialArray, result);
	}
		
}
