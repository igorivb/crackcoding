package com.design;

public class Task8_2 {

	public static void main(String[] args) throws Exception {
		int residentsSize = 8;
		int managersSize = 4;
		int directorsSize = 2;
		int callsMaximum = 20;
		int callWaitTimeout = 2;		
		
		CallCenter callCenter = new CallCenter(residentsSize, managersSize, directorsSize,
				callsMaximum, callWaitTimeout);
		
		for (int i = 0; i < 1000; i ++) {
			callCenter.handle(new Call("caller_" + i, i));
		}
		
		callCenter.close();
	}
}
