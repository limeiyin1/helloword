package com.redfinger.manager.modules.facility.dto;

import com.redfinger.manager.common.base.BaseDomain;

public class UserPadDto  extends BaseDomain{

	private Integer userId;
	private Integer padId;
	private String padName;
	private String userPadName;
	private String padCode;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPadId() {
		return padId;
	}
	public void setPadId(Integer padId) {
		this.padId = padId;
	}
	public String getPadName() {
		return padName;
	}
	public void setPadName(String padName) {
		this.padName = padName;
	}
	public String getUserPadName() {
		return userPadName;
	}
	public void setUserPadName(String userPadName) {
		this.userPadName = userPadName;
	}
	public String getPadCode() {
		return padCode;
	}
	public void setPadCode(String padCode) {
		this.padCode = padCode;
	}
}
