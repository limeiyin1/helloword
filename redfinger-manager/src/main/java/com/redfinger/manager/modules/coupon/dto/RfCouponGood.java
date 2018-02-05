package com.redfinger.manager.modules.coupon.dto;

public class RfCouponGood {
	private Integer goodsId;//产品id
	private String goodsName;//产品名称
	private Integer goodsPrice;//产品价格
	private Integer couponMoney;//优惠金额
	private Integer discountMoney;//折扣金额  goodsPrice*(1-xx%)
	private Integer discount;//折扣：%
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Integer getCouponMoney() {
		return couponMoney;
	}
	public void setCouponMoney(Integer couponMoney) {
		this.couponMoney = couponMoney;
	}
	public Integer getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(Integer discountMoney) {
		this.discountMoney = discountMoney;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
