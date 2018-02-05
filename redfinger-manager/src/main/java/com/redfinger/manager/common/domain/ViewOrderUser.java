package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class ViewOrderUser extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ORDER_ID = "order_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pad_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_PAD_ID = "pad_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pad_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer padId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pad_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_PAD_CODE = "pad_code";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pad_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String padCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pad_grade
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_PAD_GRADE = "pad_grade";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pad_grade
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String padGrade;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.goods_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_GOODS_ID = "goods_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.goods_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_ID = "user_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ORDER_TYPE = "order_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String orderType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_biz_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ORDER_BIZ_TYPE = "order_biz_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_biz_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String orderBizType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_price
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ORDER_PRICE = "order_price";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_price
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer orderPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.real_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_REAL_FEE = "real_fee";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.real_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer realFee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.real_pay_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_REAL_PAY_AMOUNT = "real_pay_amount";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.real_pay_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer realPayAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.favourable_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_FAVOURABLE_FEE = "favourable_fee";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.favourable_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer favourableFee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ORDER_STATUS = "order_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String orderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pay_mode_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_PAY_MODE_CODE = "pay_mode_code";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.pay_mode_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String payModeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.online_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ONLINE_TIME = "online_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.online_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer onlineTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.finish_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_FINISH_TIME = "finish_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.finish_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date finishTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ORDER_CREATE_TIME = "order_create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.order_create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date orderCreateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.rbc_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_RBC_AMOUNT = "rbc_amount";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.rbc_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer rbcAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_SOURCE = "user_source";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userSource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_MOBILE_PHONE_T2 = "user_mobile_phone_t2";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userMobilePhoneT2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_EMAIL_T2 = "user_email_t2";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userEmailT2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_SOURCE_T2 = "user_source_t2";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_order_user.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userSourceT2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table view_order_user
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.order_id
     *
     * @return the value of view_order_user.order_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.order_id
     *
     * @param orderId the value for view_order_user.order_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.pad_id
     *
     * @return the value of view_order_user.pad_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getPadId() {
        return padId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.pad_id
     *
     * @param padId the value for view_order_user.pad_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setPadId(Integer padId) {
        this.padId = padId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.pad_code
     *
     * @return the value of view_order_user.pad_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getPadCode() {
        return padCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.pad_code
     *
     * @param padCode the value for view_order_user.pad_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setPadCode(String padCode) {
        this.padCode = padCode == null ? null : padCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.pad_grade
     *
     * @return the value of view_order_user.pad_grade
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getPadGrade() {
        return padGrade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.pad_grade
     *
     * @param padGrade the value for view_order_user.pad_grade
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setPadGrade(String padGrade) {
        this.padGrade = padGrade == null ? null : padGrade.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.goods_id
     *
     * @return the value of view_order_user.goods_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.goods_id
     *
     * @param goodsId the value for view_order_user.goods_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.user_id
     *
     * @return the value of view_order_user.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.user_id
     *
     * @param userId the value for view_order_user.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.status
     *
     * @return the value of view_order_user.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.status
     *
     * @param status the value for view_order_user.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.order_type
     *
     * @return the value of view_order_user.order_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.order_type
     *
     * @param orderType the value for view_order_user.order_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.order_biz_type
     *
     * @return the value of view_order_user.order_biz_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getOrderBizType() {
        return orderBizType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.order_biz_type
     *
     * @param orderBizType the value for view_order_user.order_biz_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOrderBizType(String orderBizType) {
        this.orderBizType = orderBizType == null ? null : orderBizType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.order_price
     *
     * @return the value of view_order_user.order_price
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getOrderPrice() {
        return orderPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.order_price
     *
     * @param orderPrice the value for view_order_user.order_price
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.real_fee
     *
     * @return the value of view_order_user.real_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getRealFee() {
        return realFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.real_fee
     *
     * @param realFee the value for view_order_user.real_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setRealFee(Integer realFee) {
        this.realFee = realFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.real_pay_amount
     *
     * @return the value of view_order_user.real_pay_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getRealPayAmount() {
        return realPayAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.real_pay_amount
     *
     * @param realPayAmount the value for view_order_user.real_pay_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setRealPayAmount(Integer realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.favourable_fee
     *
     * @return the value of view_order_user.favourable_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getFavourableFee() {
        return favourableFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.favourable_fee
     *
     * @param favourableFee the value for view_order_user.favourable_fee
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setFavourableFee(Integer favourableFee) {
        this.favourableFee = favourableFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.order_status
     *
     * @return the value of view_order_user.order_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.order_status
     *
     * @param orderStatus the value for view_order_user.order_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.pay_mode_code
     *
     * @return the value of view_order_user.pay_mode_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getPayModeCode() {
        return payModeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.pay_mode_code
     *
     * @param payModeCode the value for view_order_user.pay_mode_code
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setPayModeCode(String payModeCode) {
        this.payModeCode = payModeCode == null ? null : payModeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.enable_status
     *
     * @return the value of view_order_user.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.enable_status
     *
     * @param enableStatus the value for view_order_user.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.online_time
     *
     * @return the value of view_order_user.online_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getOnlineTime() {
        return onlineTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.online_time
     *
     * @param onlineTime the value for view_order_user.online_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOnlineTime(Integer onlineTime) {
        this.onlineTime = onlineTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.finish_time
     *
     * @return the value of view_order_user.finish_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.finish_time
     *
     * @param finishTime the value for view_order_user.finish_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.creater
     *
     * @return the value of view_order_user.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.creater
     *
     * @param creater the value for view_order_user.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.create_time
     *
     * @return the value of view_order_user.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.create_time
     *
     * @param createTime the value for view_order_user.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.modifier
     *
     * @return the value of view_order_user.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.modifier
     *
     * @param modifier the value for view_order_user.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.modify_time
     *
     * @return the value of view_order_user.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.modify_time
     *
     * @param modifyTime the value for view_order_user.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.reorder
     *
     * @return the value of view_order_user.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.reorder
     *
     * @param reorder the value for view_order_user.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.remark
     *
     * @return the value of view_order_user.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.remark
     *
     * @param remark the value for view_order_user.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.order_create_time
     *
     * @return the value of view_order_user.order_create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.order_create_time
     *
     * @param orderCreateTime the value for view_order_user.order_create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.rbc_amount
     *
     * @return the value of view_order_user.rbc_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getRbcAmount() {
        return rbcAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.rbc_amount
     *
     * @param rbcAmount the value for view_order_user.rbc_amount
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setRbcAmount(Integer rbcAmount) {
        this.rbcAmount = rbcAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.user_source
     *
     * @return the value of view_order_user.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserSource() {
        return userSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.user_source
     *
     * @param userSource the value for view_order_user.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserSource(String userSource) {
        this.userSource = userSource == null ? null : userSource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.user_mobile_phone_t2
     *
     * @return the value of view_order_user.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserMobilePhoneT2() {
        return userMobilePhoneT2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.user_mobile_phone_t2
     *
     * @param userMobilePhoneT2 the value for view_order_user.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserMobilePhoneT2(String userMobilePhoneT2) {
        this.userMobilePhoneT2 = userMobilePhoneT2 == null ? null : userMobilePhoneT2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.user_email_t2
     *
     * @return the value of view_order_user.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserEmailT2() {
        return userEmailT2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.user_email_t2
     *
     * @param userEmailT2 the value for view_order_user.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserEmailT2(String userEmailT2) {
        this.userEmailT2 = userEmailT2 == null ? null : userEmailT2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_order_user.user_source_t2
     *
     * @return the value of view_order_user.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserSourceT2() {
        return userSourceT2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_order_user.user_source_t2
     *
     * @param userSourceT2 the value for view_order_user.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserSourceT2(String userSourceT2) {
        this.userSourceT2 = userSourceT2 == null ? null : userSourceT2.trim();
    }
}