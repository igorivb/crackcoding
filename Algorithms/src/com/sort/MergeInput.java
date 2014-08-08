package com.sort;

import java.io.IOException;

public interface MergeInput extends AutoCloseable {	
	
	MergeRecords read(int blocksToRead) throws IOException;
	
	Integer readRecord(boolean remove) throws IOException;
	
	void close() throws IOException;
}			
