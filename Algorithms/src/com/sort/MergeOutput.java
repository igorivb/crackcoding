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

		public void writeRecord(int record) throws IOException {
			//make it bytesInRecord					
			String str = String.format("%" + ExternalMergeSort.bytesInRecord + "s", record);			
			out.write(str);
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
