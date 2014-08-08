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
	private MergeRecords cachedBlock = MergeRecords.empty;
					
	public InputBinaryImpl(File file, int bytesInRecord, int blockSize) throws IOException {
		this.file = file;
		this.blockSize = blockSize;
		this.blockSizeInBytes = blockSize * bytesInRecord;
		
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
	public MergeRecords read(int blocksToRead) throws IOException {
		if (this.hasData) {	
			
			//clear cache
			this.cachedBlock = MergeRecords.empty;
			
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
