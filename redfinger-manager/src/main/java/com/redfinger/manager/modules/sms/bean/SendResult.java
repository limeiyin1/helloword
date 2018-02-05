package com.redfinger.manager.modules.sms.bean;

public class SendResult {

	private int total;
	private int success;
	private int fault;
	private int finish;

	public SendResult(int total) {
		super();
		this.total = total;
		this.success = 0;
		this.fault = 0;
		this.finish = 0;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFault() {
		return fault;
	}

	public void setFault(int fault) {
		this.fault = fault;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}
	

}
