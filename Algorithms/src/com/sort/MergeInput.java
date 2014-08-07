package com.sort;

import java.io.IOException;

public interface MergeInput extends AutoCloseable {
	/**
	 * Records. 
	 */
	public static class Records {
		private final int[] records;
		private final int size;				
		
		public Records(int[] records, int size) {
			super();
			this.records = records;
			this.size = size;
		}
		
		public int[] getRecords() {
			return records;
		}

		public int getSize() {
			return size;
		}

		static Records empty = new Records(new int[0], 0);
	} 
	
	Records read(int blocksToRead) throws IOException;
	
	Integer readRecord(boolean remove) throws IOException;
	
	void close() throws IOException;
}			
