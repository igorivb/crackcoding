package com.threads;

import java.util.ArrayList;
import java.util.List;

public class MyLock {
	
	final int id;
	
	List<MyLock> children = new ArrayList<>();
	
	public MyLock(int id) {
		this.id = id;
	}
}
