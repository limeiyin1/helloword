package com.redfinger.manager.modules.log.dto;

import java.util.Date;

import com.redfinger.manager.common.domain.ViewLogActivity;

public class ViewLogActivityDto extends ViewLogActivity{
	private String userPhone;
	private String inviteePhone;
	private Date signTime;
	private String orderPrice;
	private String goodsName;
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getInviteePhone() {
		return inviteePhone;
	}
	public void setInviteePhone(String inviteePhone) {
		this.inviteePhone = inviteePhone;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	
	
}
