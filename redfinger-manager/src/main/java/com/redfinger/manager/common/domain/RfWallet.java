package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfWallet extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.wallet_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_WALLET_ID = "wallet_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.wallet_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private Integer walletId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.user_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_USER_ID = "user_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.user_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.wallet_amount
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_WALLET_AMOUNT = "wallet_amount";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.wallet_amount
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private Integer walletAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.enable_status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.enable_status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.creater
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.creater
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.create_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.create_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.modifier
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.modifier
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.modify_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.modify_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.reorder
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.reorder
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.remark
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_wallet.remark
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private String remark;
    
    private Integer walletAccountCount;

    public Integer getWalletAccountCount() {
		return walletAccountCount;
	}

	public void setWalletAccountCount(Integer walletAccountCount) {
		this.walletAccountCount = walletAccountCount;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.wallet_id
     *
     * @return the value of rf_wallet.wallet_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public Integer getWalletId() {
        return walletId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.wallet_id
     *
     * @param walletId the value for rf_wallet.wallet_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.user_id
     *
     * @return the value of rf_wallet.user_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.user_id
     *
     * @param userId the value for rf_wallet.user_id
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.wallet_amount
     *
     * @return the value of rf_wallet.wallet_amount
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public Integer getWalletAmount() {
        return walletAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.wallet_amount
     *
     * @param walletAmount the value for rf_wallet.wallet_amount
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setWalletAmount(Integer walletAmount) {
        this.walletAmount = walletAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.status
     *
     * @return the value of rf_wallet.status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.status
     *
     * @param status the value for rf_wallet.status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.enable_status
     *
     * @return the value of rf_wallet.enable_status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.enable_status
     *
     * @param enableStatus the value for rf_wallet.enable_status
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.creater
     *
     * @return the value of rf_wallet.creater
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.creater
     *
     * @param creater the value for rf_wallet.creater
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.create_time
     *
     * @return the value of rf_wallet.create_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.create_time
     *
     * @param createTime the value for rf_wallet.create_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.modifier
     *
     * @return the value of rf_wallet.modifier
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.modifier
     *
     * @param modifier the value for rf_wallet.modifier
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.modify_time
     *
     * @return the value of rf_wallet.modify_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.modify_time
     *
     * @param modifyTime the value for rf_wallet.modify_time
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.reorder
     *
     * @return the value of rf_wallet.reorder
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.reorder
     *
     * @param reorder the value for rf_wallet.reorder
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_wallet.remark
     *
     * @return the value of rf_wallet.remark
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_wallet.remark
     *
     * @param remark the value for rf_wallet.remark
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}