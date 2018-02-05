package com.redfinger.manager.modules.notice.dto;

import java.util.Date;
import java.util.List;

import com.redfinger.manager.common.domain.RfNotice;

public class UMengInfoDto {

	private List<Integer> userIds;//多个userId
	private RfNotice rfNotice;//公告
	private Integer userId;//用户id
	private String userMobilePhone;//手机号码
	private String userEmail;//邮箱
	private Date startDate;//登录开始时间
	private Date endDate;//登录结束时间
	private String padName;//设备名称
	private String padCode;//设备编码
	private String padId;//设备ip
	
	
	public List<Integer> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
	public RfNotice getRfNotice() {
		return rfNotice;
	}
	public void setRfNotice(RfNotice rfNotice) {
		this.rfNotice = rfNotice;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserMobilePhone() {
		return userMobilePhone;
	}
	public void setUserMobilePhone(String userMobilePhone) {
		this.userMobilePhone = userMobilePhone;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPadName() {
		return padName;
	}
	public void setPadName(String padName) {
		this.padName = padName;
	}
	public String getPadCode() {
		return padCode;
	}
	public void setPadCode(String padCode) {
		this.padCode = padCode;
	}
	public String getPadId() {
		return padId;
	}
	public void setPadId(String padId) {
		this.padId = padId;
	}
}
