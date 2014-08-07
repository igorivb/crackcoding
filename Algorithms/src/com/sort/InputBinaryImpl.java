package com.sort;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InputBinaryImpl implements MergeInput {
	
	private final File file;
	private final int blockSize;
	private final int blockSizeInBytes;
	
	private boolean hasData = true;
	private DataInputStream in;
	
	//cache block for reading record by record
	private Records cachedBlock;
	private int cacheIndex = 0;
					
	public InputBinaryImpl(File file, int blockSize, int blockSizeInBytes) throws IOException {
		this.file = file;
		this.blockSize = blockSize;
		this.blockSizeInBytes = blockSizeInBytes;
		
		//buffer = block
		this.in = new DataInputStream(new BufferedInputStream(new FileInputStream(file), blockSizeInBytes));
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
					int record = this.in.readInt();
					records[i] = record;
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
