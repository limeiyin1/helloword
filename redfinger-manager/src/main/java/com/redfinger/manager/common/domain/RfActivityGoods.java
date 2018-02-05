package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfActivityGoods extends BaseDomain implements Serializable {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.act_goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_ACT_GOODS_ID = "act_goods_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.act_goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer actGoodsId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_ACTIVITY_ID = "activity_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer activityId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_GOODS_ID = "goods_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer goodsId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.rbc_amount
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_RBC_AMOUNT = "rbc_amount";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.rbc_amount
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer rbcAmount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.creater
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.creater
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.create_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.create_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.modifier
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.modifier
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.modify_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.modify_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.reorder
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.reorder
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.remark
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.remark
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.enable_status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.enable_status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.lottery_count
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_LOTTERY_COUNT = "lottery_count";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.lottery_count
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer lotteryCount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_ACTIVITY_PRICE = "activity_price";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer activityPrice;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_img
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_ACTIVITY_IMG = "activity_img";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_img
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private String activityImg;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.coupon_type_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_COUPON_TYPE_ID = "coupon_type_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.coupon_type_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private Integer couponTypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_show_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public static final String FD_ACTIVITY_SHOW_PRICE = "activity_show_price";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_activity_goods.activity_show_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private String activityShowPrice;
	
	private Integer padTime;
	
	private Integer padTimeTwo;
	
	
	public Integer getPadTimeTwo() {
		return padTimeTwo;
	}

	public void setPadTimeTwo(Integer padTimeTwo) {
		this.padTimeTwo = padTimeTwo;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_activity_goods
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.act_goods_id
	 * @return  the value of rf_activity_goods.act_goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getActGoodsId() {
		return actGoodsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.act_goods_id
	 * @param actGoodsId  the value for rf_activity_goods.act_goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setActGoodsId(Integer actGoodsId) {
		this.actGoodsId = actGoodsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.activity_id
	 * @return  the value of rf_activity_goods.activity_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.activity_id
	 * @param activityId  the value for rf_activity_goods.activity_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.goods_id
	 * @return  the value of rf_activity_goods.goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getGoodsId() {
		return goodsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.goods_id
	 * @param goodsId  the value for rf_activity_goods.goods_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.rbc_amount
	 * @return  the value of rf_activity_goods.rbc_amount
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getRbcAmount() {
		return rbcAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.rbc_amount
	 * @param rbcAmount  the value for rf_activity_goods.rbc_amount
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setRbcAmount(Integer rbcAmount) {
		this.rbcAmount = rbcAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.status
	 * @return  the value of rf_activity_goods.status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.status
	 * @param status  the value for rf_activity_goods.status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.creater
	 * @return  the value of rf_activity_goods.creater
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.creater
	 * @param creater  the value for rf_activity_goods.creater
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.create_time
	 * @return  the value of rf_activity_goods.create_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.create_time
	 * @param createTime  the value for rf_activity_goods.create_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.modifier
	 * @return  the value of rf_activity_goods.modifier
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.modifier
	 * @param modifier  the value for rf_activity_goods.modifier
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.modify_time
	 * @return  the value of rf_activity_goods.modify_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.modify_time
	 * @param modifyTime  the value for rf_activity_goods.modify_time
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.reorder
	 * @return  the value of rf_activity_goods.reorder
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.reorder
	 * @param reorder  the value for rf_activity_goods.reorder
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.remark
	 * @return  the value of rf_activity_goods.remark
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.remark
	 * @param remark  the value for rf_activity_goods.remark
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.enable_status
	 * @return  the value of rf_activity_goods.enable_status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.enable_status
	 * @param enableStatus  the value for rf_activity_goods.enable_status
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.lottery_count
	 * @return  the value of rf_activity_goods.lottery_count
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getLotteryCount() {
		return lotteryCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.lottery_count
	 * @param lotteryCount  the value for rf_activity_goods.lottery_count
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setLotteryCount(Integer lotteryCount) {
		this.lotteryCount = lotteryCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.activity_price
	 * @return  the value of rf_activity_goods.activity_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getActivityPrice() {
		return activityPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.activity_price
	 * @param activityPrice  the value for rf_activity_goods.activity_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setActivityPrice(Integer activityPrice) {
		this.activityPrice = activityPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.activity_img
	 * @return  the value of rf_activity_goods.activity_img
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public String getActivityImg() {
		return activityImg;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.activity_img
	 * @param activityImg  the value for rf_activity_goods.activity_img
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg == null ? null : activityImg.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.coupon_type_id
	 * @return  the value of rf_activity_goods.coupon_type_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public Integer getCouponTypeId() {
		return couponTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.coupon_type_id
	 * @param couponTypeId  the value for rf_activity_goods.coupon_type_id
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setCouponTypeId(Integer couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_activity_goods.activity_show_price
	 * @return  the value of rf_activity_goods.activity_show_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public String getActivityShowPrice() {
		return activityShowPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_activity_goods.activity_show_price
	 * @param activityShowPrice  the value for rf_activity_goods.activity_show_price
	 * @mbggenerated  Wed Jan 11 15:57:39 CST 2017
	 */
	public void setActivityShowPrice(String activityShowPrice) {
		this.activityShowPrice = activityShowPrice == null ? null : activityShowPrice.trim();
	}

	public Integer getPadTime() {
		return padTime;
	}

	public void setPadTime(Integer padTime) {
		this.padTime = padTime;
	}
}