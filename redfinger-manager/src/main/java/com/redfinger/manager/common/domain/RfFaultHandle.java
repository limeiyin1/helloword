package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfFaultHandle extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.fault_handle_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FAULT_HANDLE_ID = "fault_handle_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.fault_handle_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer faultHandleId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.admin_user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ADMIN_USER_ID = "admin_user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.admin_user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String adminUserId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FAULT_FEEDBACK_ID = "fault_feedback_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer faultFeedbackId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.is_solve
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_IS_SOLVE = "is_solve";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.is_solve
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String isSolve;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_handle.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_fault_handle
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.fault_handle_id
	 * @return  the value of rf_fault_handle.fault_handle_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getFaultHandleId() {
		return faultHandleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.fault_handle_id
	 * @param faultHandleId  the value for rf_fault_handle.fault_handle_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFaultHandleId(Integer faultHandleId) {
		this.faultHandleId = faultHandleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.admin_user_id
	 * @return  the value of rf_fault_handle.admin_user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getAdminUserId() {
		return adminUserId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.admin_user_id
	 * @param adminUserId  the value for rf_fault_handle.admin_user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId == null ? null : adminUserId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.fault_feedback_id
	 * @return  the value of rf_fault_handle.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getFaultFeedbackId() {
		return faultFeedbackId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.fault_feedback_id
	 * @param faultFeedbackId  the value for rf_fault_handle.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFaultFeedbackId(Integer faultFeedbackId) {
		this.faultFeedbackId = faultFeedbackId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.is_solve
	 * @return  the value of rf_fault_handle.is_solve
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getIsSolve() {
		return isSolve;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.is_solve
	 * @param isSolve  the value for rf_fault_handle.is_solve
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setIsSolve(String isSolve) {
		this.isSolve = isSolve == null ? null : isSolve.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.status
	 * @return  the value of rf_fault_handle.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.status
	 * @param status  the value for rf_fault_handle.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.creater
	 * @return  the value of rf_fault_handle.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.creater
	 * @param creater  the value for rf_fault_handle.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.create_time
	 * @return  the value of rf_fault_handle.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.create_time
	 * @param createTime  the value for rf_fault_handle.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.modifier
	 * @return  the value of rf_fault_handle.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.modifier
	 * @param modifier  the value for rf_fault_handle.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.modify_time
	 * @return  the value of rf_fault_handle.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.modify_time
	 * @param modifyTime  the value for rf_fault_handle.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.reorder
	 * @return  the value of rf_fault_handle.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.reorder
	 * @param reorder  the value for rf_fault_handle.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.remark
	 * @return  the value of rf_fault_handle.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.remark
	 * @param remark  the value for rf_fault_handle.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_handle.enable_status
	 * @return  the value of rf_fault_handle.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_handle.enable_status
	 * @param enableStatus  the value for rf_fault_handle.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}
}