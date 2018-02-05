package com.redfinger.manager.modules.activity.dto;

public class RfActivityGoodsDto {
	private Integer actGoodsId;// 活动产品id
	private Integer goodsId;// 产品id
	private String couponTypeIds;// 优惠劵类型id,多个
	private String couponTypeNames;// 优惠劵类型名称,多个
	private Integer padTime; // 赠送设备时长
	private Integer padTimeTwo; // 赠送设备时长
	
	public Integer getPadTimeTwo() {
		return padTimeTwo;
	}
	public void setPadTimeTwo(Integer padTimeTwo) {
		this.padTimeTwo = padTimeTwo;
	}
	public Integer getActGoodsId() {
		return actGoodsId;
	}
	public void setActGoodsId(Integer actGoodsId) {
		this.actGoodsId = actGoodsId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getCouponTypeIds() {
		return couponTypeIds;
	}
	public void setCouponTypeIds(String couponTypeIds) {
		this.couponTypeIds = couponTypeIds;
	}
	public String getCouponTypeNames() {
		return couponTypeNames;
	}
	public void setCouponTypeNames(String couponTypeNames) {
		this.couponTypeNames = couponTypeNames;
	}
	
	public Integer getPadTime() {
		return padTime;
	}

	public void setPadTime(Integer padTime) {
		this.padTime = padTime;
	}


}
