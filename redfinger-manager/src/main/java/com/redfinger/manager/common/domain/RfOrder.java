package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfOrder extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ORDER_ID = "order_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String orderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pad_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_PAD_ID = "pad_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pad_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer padId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pad_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_PAD_CODE = "pad_code";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pad_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String padCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pad_grade
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_PAD_GRADE = "pad_grade";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pad_grade
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String padGrade;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.goods_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_GOODS_ID = "goods_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.goods_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer goodsId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.user_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_USER_ID = "user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.user_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ORDER_TYPE = "order_type";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String orderType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_biz_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ORDER_BIZ_TYPE = "order_biz_type";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_biz_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String orderBizType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_price
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ORDER_PRICE = "order_price";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_price
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer orderPrice;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.real_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_REAL_FEE = "real_fee";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.real_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer realFee;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.real_pay_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_REAL_PAY_AMOUNT = "real_pay_amount";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.real_pay_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer realPayAmount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.favourable_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_FAVOURABLE_FEE = "favourable_fee";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.favourable_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer favourableFee;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ORDER_STATUS = "order_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String orderStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pay_mode_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_PAY_MODE_CODE = "pay_mode_code";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.pay_mode_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String payModeCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.enable_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.enable_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.online_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ONLINE_TIME = "online_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.online_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer onlineTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.finish_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_FINISH_TIME = "finish_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.finish_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Date finishTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.creater
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.creater
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.modifier
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.modifier
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.modify_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.modify_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.reorder
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.reorder
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.remark
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.remark
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_ORDER_CREATE_TIME = "order_create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.order_create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Date orderCreateTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.rbc_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_RBC_AMOUNT = "rbc_amount";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.rbc_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private Integer rbcAmount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.user_source
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_USER_SOURCE = "user_source";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.user_source
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String userSource;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.owner
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_OWNER = "owner";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.owner
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String owner;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.customer_ip
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_CUSTOMER_IP = "customer_ip";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.customer_ip
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String customerIp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.goods_name
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public static final String FD_GOODS_NAME = "goods_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_order.goods_name
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private String goodsName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_order
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.order_id
	 * @return  the value of rf_order.order_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.order_id
	 * @param orderId  the value for rf_order.order_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.pad_id
	 * @return  the value of rf_order.pad_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getPadId() {
		return padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.pad_id
	 * @param padId  the value for rf_order.pad_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setPadId(Integer padId) {
		this.padId = padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.pad_code
	 * @return  the value of rf_order.pad_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getPadCode() {
		return padCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.pad_code
	 * @param padCode  the value for rf_order.pad_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setPadCode(String padCode) {
		this.padCode = padCode == null ? null : padCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.pad_grade
	 * @return  the value of rf_order.pad_grade
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getPadGrade() {
		return padGrade;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.pad_grade
	 * @param padGrade  the value for rf_order.pad_grade
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setPadGrade(String padGrade) {
		this.padGrade = padGrade == null ? null : padGrade.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.goods_id
	 * @return  the value of rf_order.goods_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getGoodsId() {
		return goodsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.goods_id
	 * @param goodsId  the value for rf_order.goods_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.user_id
	 * @return  the value of rf_order.user_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.user_id
	 * @param userId  the value for rf_order.user_id
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.status
	 * @return  the value of rf_order.status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.status
	 * @param status  the value for rf_order.status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.order_type
	 * @return  the value of rf_order.order_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.order_type
	 * @param orderType  the value for rf_order.order_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType == null ? null : orderType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.order_biz_type
	 * @return  the value of rf_order.order_biz_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getOrderBizType() {
		return orderBizType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.order_biz_type
	 * @param orderBizType  the value for rf_order.order_biz_type
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOrderBizType(String orderBizType) {
		this.orderBizType = orderBizType == null ? null : orderBizType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.order_price
	 * @return  the value of rf_order.order_price
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getOrderPrice() {
		return orderPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.order_price
	 * @param orderPrice  the value for rf_order.order_price
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.real_fee
	 * @return  the value of rf_order.real_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getRealFee() {
		return realFee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.real_fee
	 * @param realFee  the value for rf_order.real_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setRealFee(Integer realFee) {
		this.realFee = realFee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.real_pay_amount
	 * @return  the value of rf_order.real_pay_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getRealPayAmount() {
		return realPayAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.real_pay_amount
	 * @param realPayAmount  the value for rf_order.real_pay_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setRealPayAmount(Integer realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.favourable_fee
	 * @return  the value of rf_order.favourable_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getFavourableFee() {
		return favourableFee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.favourable_fee
	 * @param favourableFee  the value for rf_order.favourable_fee
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setFavourableFee(Integer favourableFee) {
		this.favourableFee = favourableFee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.order_status
	 * @return  the value of rf_order.order_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.order_status
	 * @param orderStatus  the value for rf_order.order_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus == null ? null : orderStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.pay_mode_code
	 * @return  the value of rf_order.pay_mode_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getPayModeCode() {
		return payModeCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.pay_mode_code
	 * @param payModeCode  the value for rf_order.pay_mode_code
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode == null ? null : payModeCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.enable_status
	 * @return  the value of rf_order.enable_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.enable_status
	 * @param enableStatus  the value for rf_order.enable_status
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.online_time
	 * @return  the value of rf_order.online_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getOnlineTime() {
		return onlineTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.online_time
	 * @param onlineTime  the value for rf_order.online_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOnlineTime(Integer onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.finish_time
	 * @return  the value of rf_order.finish_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.finish_time
	 * @param finishTime  the value for rf_order.finish_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.creater
	 * @return  the value of rf_order.creater
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.creater
	 * @param creater  the value for rf_order.creater
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.create_time
	 * @return  the value of rf_order.create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.create_time
	 * @param createTime  the value for rf_order.create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.modifier
	 * @return  the value of rf_order.modifier
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.modifier
	 * @param modifier  the value for rf_order.modifier
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.modify_time
	 * @return  the value of rf_order.modify_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.modify_time
	 * @param modifyTime  the value for rf_order.modify_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.reorder
	 * @return  the value of rf_order.reorder
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.reorder
	 * @param reorder  the value for rf_order.reorder
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.remark
	 * @return  the value of rf_order.remark
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.remark
	 * @param remark  the value for rf_order.remark
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.order_create_time
	 * @return  the value of rf_order.order_create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.order_create_time
	 * @param orderCreateTime  the value for rf_order.order_create_time
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.rbc_amount
	 * @return  the value of rf_order.rbc_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public Integer getRbcAmount() {
		return rbcAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.rbc_amount
	 * @param rbcAmount  the value for rf_order.rbc_amount
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setRbcAmount(Integer rbcAmount) {
		this.rbcAmount = rbcAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.user_source
	 * @return  the value of rf_order.user_source
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getUserSource() {
		return userSource;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.user_source
	 * @param userSource  the value for rf_order.user_source
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setUserSource(String userSource) {
		this.userSource = userSource == null ? null : userSource.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.owner
	 * @return  the value of rf_order.owner
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.owner
	 * @param owner  the value for rf_order.owner
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setOwner(String owner) {
		this.owner = owner == null ? null : owner.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.customer_ip
	 * @return  the value of rf_order.customer_ip
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getCustomerIp() {
		return customerIp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.customer_ip
	 * @param customerIp  the value for rf_order.customer_ip
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp == null ? null : customerIp.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_order.goods_name
	 * @return  the value of rf_order.goods_name
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_order.goods_name
	 * @param goodsName  the value for rf_order.goods_name
	 * @mbggenerated  Fri Mar 17 17:10:13 CST 2017
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}
}