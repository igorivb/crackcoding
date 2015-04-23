package com.design;

public enum CallStatus {

	NEW("new"), PENDING("pending"), IN_PROGRESS("in progress"), REJECTED("rejected"), COMPLETED("completed");
	
	String msg;
	
	CallStatus(String msg) {
		this.msg = msg;
	} 
	
	public String msg() {
		return this.msg;
	}
}
