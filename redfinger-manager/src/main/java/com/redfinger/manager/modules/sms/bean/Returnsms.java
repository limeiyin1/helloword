package com.redfinger.manager.modules.sms.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Returnsms {

	private String returnstatus;
	private String message;
	private Integer remainpoint;
	private Integer taskID;
	public String getReturnstatus() {
		return returnstatus;
	}
	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getRemainpoint() {
		return remainpoint;
	}
	public void setRemainpoint(Integer remainpoint) {
		this.remainpoint = remainpoint;
	}
	public Integer getTaskID() {
		return taskID;
	}
	public void setTaskID(Integer taskID) {
		this.taskID = taskID;
	}
	public Integer getSuccessCounts() {
		return successCounts;
	}
	public void setSuccessCounts(Integer successCounts) {
		this.successCounts = successCounts;
	}
	private Integer successCounts;
}
