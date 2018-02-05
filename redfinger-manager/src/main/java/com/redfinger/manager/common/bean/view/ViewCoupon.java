package com.redfinger.manager.common.bean.view;

import java.util.ArrayList;
import java.util.List;

import com.redfinger.manager.common.domain.RfCouponGoods;
import com.redfinger.manager.modules.coupon.dto.RfCouponGood;

public class ViewCoupon {

	private List<RfCouponGood> couponGoodsList = new ArrayList<RfCouponGood>();
	private List<RfCouponGood> discountGoodsList = new ArrayList<RfCouponGood>();
	
	public List<RfCouponGood> getCouponGoodsList() {
		return couponGoodsList;
	}
	public void setCouponGoodsList(List<RfCouponGood> couponGoodsList) {
		this.couponGoodsList = couponGoodsList;
	}
	public List<RfCouponGood> getDiscountGoodsList() {
		return discountGoodsList;
	}
	public void setDiscountGoodsList(List<RfCouponGood> discountGoodsList) {
		this.discountGoodsList = discountGoodsList;
	}
	
}
