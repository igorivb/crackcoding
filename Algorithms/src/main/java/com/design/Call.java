package com.design;

public class Call {	
	
	private final String caller;
	
	private final int id;
	
	private Employee employee;
	private Rank rank = Rank.RESIDENT;
	private CallStatus status = CallStatus.NEW;		
	
	public Call(String caller, int id) {
		this.caller = caller;
		this.id = id;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public void reply(String reply) {
		System.out.println(reply);
	}
	
	public void setStatus(CallStatus status) {
		setStatus(status, null);
	}
	
	public void setStatus(CallStatus status, String details) {
		this.status = status;
		String msg = String.format("Change status: %12s|\tid: %3d|\tcaller: %10s|\temployee: %10s|\trank: %10s",
			status.msg(), id, caller, (employee != null ? employee.getName() : "<none>"), rank);
		if (details != null) {
			msg += "|\tdetails: " + details;
		}
		reply(msg);
	}
	
	public CallStatus getStatus() {
		return status;
	}
	
	public int getId() {
		return id;
	}

	public void increaseRank() {
		int next = rank.val() + 1;
		for (Rank r : Rank.values()) {
			if (next == r.val()) {
				rank = r;
				break;
			}
		}
		
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}		
}
