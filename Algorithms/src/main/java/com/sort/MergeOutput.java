package com.sort;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Output
 */
public interface MergeOutput extends AutoCloseable {
	
	void writeRecord(int record) throws IOException;					
	
	void close() throws IOException;
	
	public default void write(MergeRecords records) throws IOException {
		for (int i = records.getStart(); i < records.getEnd(); i ++) {
			int record = records.getRecord(i);
			this.writeRecord(record);	
		}						
	}
	
	/**
	 * Binary Output
	 */
	public static class OutputBinaryImpl implements MergeOutput {
		//private final File file;
		private final int blockSizeInBytes;
		
		private DataOutputStream out;
		
		public OutputBinaryImpl(File file, int bytesInRecord, int blockSize) throws IOException {
			//this.file = file;
			this.blockSizeInBytes = blockSize * bytesInRecord;
			
			this.out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file), blockSizeInBytes));
		}

		public void writeRecord(int record) throws IOException {
			out.writeInt(record);
		}
		
		@Override
		public void close() throws IOException {
			if (this.out != null) {
				try {
					this.out.close();
				} catch (IOException e) { /* ignore */ }
				this.out = null;
			}	
		}
	}
	
	/**
	 * Text Output
	 */
	public static class OutputTextImpl implements MergeOutput {
		//private final File file;
		private final int blockSizeInBytes;
		private int bytesInRecord;
		
		private final char[] zeros;
		
		private Writer out;
		
		public OutputTextImpl(File file, int bytesInRecord, int blockSize) throws IOException {
			//this.file = file;
			this.blockSizeInBytes = blockSize * bytesInRecord;
			this.bytesInRecord = bytesInRecord;
			
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"), blockSizeInBytes);
			
			//init zeros
			zeros = new char[bytesInRecord];
			for (int i = 0; i < bytesInRecord; i ++) {
				zeros[i] = ' '; 
			}				
		}
								
		public void writeRecord(int record) throws IOException {
			//make it bytesInRecord					
			
			//String str = String.format("%" + bytesInRecord + "s", record);
			
			StringBuilder str = new StringBuilder(bytesInRecord);
			str.append(record);
			int diff = bytesInRecord - str.length();
			if (diff > 0) {
				str.insert(0, zeros, 0, diff);
			}
			
			out.write(str.toString());
		}
		
		@Override
		public void close() throws IOException {
			if (this.out != null) {
				try {
					this.out.close();
				} catch (IOException e) { /* ignore */ }
				this.out = null;
			}	
		}
	}
}
