package com.sort;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class InputTextImpl implements MergeInput {
	
	private final File file;
	private final int blockSize;
	private final int blockSizeInBytes;
	private int bytesInRecord;
	
	private boolean hasData = true;
	private Reader in;
	
	//cache block for reading record by record
	private MergeRecords cachedBlock = MergeRecords.empty;
					
	public InputTextImpl(File file, int bytesInRecord, int blockSize) throws IOException {
		this.file = file;
		this.blockSize = blockSize;
		this.blockSizeInBytes = blockSize * bytesInRecord;
		this.bytesInRecord = bytesInRecord;
		
		//buffer = block
		in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"), blockSizeInBytes);
		
	}
	
	/**
	 * Read records by blocks. 
	 * If there's no more data, then Records#size = 0.  
	 * 
	 * @param blocksToRead
	 * @return records
	 */
	public MergeRecords read(int blocksToRead) throws IOException {
		if (this.hasData) {	
			
			//clear cache
			this.cachedBlock = MergeRecords.empty;
			
			int count = blocksToRead * blockSize;
			int[] records = new int[count];
			int i = 0;
			try {
				for (; i < count; i ++) {
					
					//int record = this.in.read();
					char[] cbuf = new char[bytesInRecord];
					int c = in.read(cbuf);
					if (c != -1) {
						
						//int record = Integer.valueOf(new String(cbuf).replaceAll(" ", ""));
						
						int offset = 0;
						for (int n = cbuf.length; offset < n && cbuf[offset] == ' '; offset ++);
						
						String str = new String(cbuf, offset, cbuf.length - offset);
						int record = Integer.valueOf(str);
						
						records[i] = record;	
					} else {
						break;
					}					
				}										
			} catch (EOFException e) {
				hasData = false;
			}
			return new MergeRecords(records, 0, i);
		} else {
			return MergeRecords.empty;
		}
	}

	/**
	 * Read record.
	 * <p>If there's no more data, return null.</p>
	 * 
	 * <b>Note:</b> don't mix calls to this method with {@link MergeInput#read(int)}
	 * 
	 * @param remove - remove record after reading, otherwise peek.
	 * @return
	 */
	public Integer readRecord(boolean remove) throws IOException {
		if (this.cachedBlock.isEmpty()) {			
			this.cachedBlock = this.read(1);
			if (this.cachedBlock.isEmpty()) {
				//no more data to read
				return null;				
			}	
		}		
		
		int start = this.cachedBlock.getStart();
		Integer record = this.cachedBlock.getRecord(start);
		if (remove) {
			this.cachedBlock.setStart(start + 1);
		}
		return record;
	}
	
	@Override
	public void close() throws IOException {
		 this.cachedBlock = MergeRecords.empty;
		 
		 this.hasData = false;
		 
		 if (this.in != null) {
			 try {
				 this.in.close();					 
			 } catch (IOException ie) { /* ignore */ }
			 this.in = null;
		 }
	}
	
	@Override
	public String toString() {
		String str = String.format("%s[file: %s, blockSize: %s]", this.getClass().getSimpleName(), file.getAbsolutePath(), blockSize);
		return str;
	}
}
