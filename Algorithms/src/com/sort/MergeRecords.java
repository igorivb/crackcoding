package com.sort;


public class MergeRecords {
	
	private int[] records;
	
	//[start, end) - can be changed
	private int start;
	private int end;
				
	public MergeRecords(int[] records, int start, int end) {
		super();
		this.records = records;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Note: don't modify array because it is internal representation.
	 * @return
	 */
	public int[] getRecords() {
		return records;
	}

	public int getSize() {
		return end - start;
	}
	
	/**
	 * inclusive
	 */
	public int getStart() {
		return this.start;
	}
		
	public void setStart(int start) {
		this.start = start;
	}
	
	/**
	 * Exclusive
	 */
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}

	public boolean isEmpty() {
		return this.getSize() == 0;
	}
	
	public int getRecord(int i) {
		if (i < getStart() || i >= getEnd()) {
			String msg = String.format("Incorrect index. Index: %s, start: %s, end: %s", i, start, getEnd());
			throw new IllegalArgumentException(msg);
		}		
		return this.records[i];
	}
	
	static MergeRecords empty = new MergeRecords(new int[0], 0, 0);
} 
