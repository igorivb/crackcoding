package com.sort;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static com.sort.ExternalMergeSort.bytesInRecord;

/**
 * Output
 */
public interface MergeOutput extends AutoCloseable {
	
	void writeRecord(int record) throws IOException;
	
	void close() throws IOException;
	
	/**
	 * Binary Output
	 */
	public static class OutputBinaryImpl implements MergeOutput {
		private final File file;
		private final int blockSizeInBytes;
		
		private DataOutputStream out;
		
		public OutputBinaryImpl(File file, int blockSizeInBytes) throws IOException {
			this.file = file;
			this.blockSizeInBytes = blockSizeInBytes;
			
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
		private final File file;
		private final int blockSizeInBytes;
		
		private Writer out;
		
		public OutputTextImpl(File file, int blockSizeInBytes) throws IOException {
			this.file = file;
			this.blockSizeInBytes = blockSizeInBytes;

			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"), blockSizeInBytes);
		}

		
		static final char[] zeros;
		static {
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
