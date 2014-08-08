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
	
	private boolean hasData = true;
	private Reader in;
	
	//cache block for reading record by record
	private Records cachedBlock;
	private int cacheIndex = 0;
					
	public InputTextImpl(File file, int blockSize, int blockSizeInBytes) throws IOException {
		this.file = file;
		this.blockSize = blockSize;
		this.blockSizeInBytes = blockSizeInBytes;
		
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
	public Records read(int blocksToRead) throws IOException {
		if (this.hasData) {	
			
			//clear cache
			this.cachedBlock = null;
			this.cacheIndex = 0;
			
			int count = blocksToRead * blockSize;
			int[] records = new int[count];
			int i = 0;
			try {
				for (; i < count; i ++) {
					
					//int record = this.in.read();
					char[] cbuf = new char[ExternalMergeSort.bytesInRecord];
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
			return new Records(records, i);
		} else {
			return Records.empty;
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
		if (this.cachedBlock == null || this.cacheIndex >= this.cachedBlock.getSize()) {
			Records records = this.read(1);
			if (records.getSize() == 0) {
				//no more data to read
				return null;
			} else {
				this.cachedBlock = records;
				this.cacheIndex = 0;
			}
		}
		
		Integer record = this.cachedBlock.getRecords()[this.cacheIndex];
		if (remove) {
			this.cacheIndex ++;
		}
		return record;
	}
	
	@Override
	public void close() throws IOException {
		 this.cachedBlock = null;
		 this.cacheIndex = 0;
		 
		 this.hasData = false;
		 
		 if (this.in != null) {
			 try {
				 this.in.close();					 
			 } catch (IOException ie) { /* ignore */ }
			 this.in = null;
		 }
	}		
}
