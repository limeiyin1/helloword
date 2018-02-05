package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class ViewTaskRecord extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.record_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_RECORD_ID = "record_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.record_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Integer recordId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.user_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_USER_ID = "user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.user_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.task_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_TASK_ID = "task_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.task_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Integer taskId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.take_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_TAKE_TIME = "take_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.take_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Date takeTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.record_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_RECORD_TIME = "record_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.record_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Date recordTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.award_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_AWARD_STATUS = "award_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.award_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String awardStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.award_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_AWARD_TIME = "award_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.award_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Date awardTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.award_amount
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_AWARD_AMOUNT = "award_amount";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.award_amount
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Integer awardAmount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.enable_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.enable_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.creater
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.creater
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.create_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.create_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.modifier
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.modifier
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.modify_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.modify_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.reorder
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.reorder
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.remark
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.remark
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.user_mobile_phone_t2
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_USER_MOBILE_PHONE_T2 = "user_mobile_phone_t2";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.user_mobile_phone_t2
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String userMobilePhoneT2;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.name_t3
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public static final String FD_NAME_T3 = "name_t3";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column view_task_record.name_t3
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private String nameT3;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table view_task_record
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.record_id
	 * @return  the value of view_task_record.record_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Integer getRecordId() {
		return recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.record_id
	 * @param recordId  the value for view_task_record.record_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.user_id
	 * @return  the value of view_task_record.user_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.user_id
	 * @param userId  the value for view_task_record.user_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.task_id
	 * @return  the value of view_task_record.task_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.task_id
	 * @param taskId  the value for view_task_record.task_id
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.take_time
	 * @return  the value of view_task_record.take_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Date getTakeTime() {
		return takeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.take_time
	 * @param takeTime  the value for view_task_record.take_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.record_time
	 * @return  the value of view_task_record.record_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Date getRecordTime() {
		return recordTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.record_time
	 * @param recordTime  the value for view_task_record.record_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.award_status
	 * @return  the value of view_task_record.award_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getAwardStatus() {
		return awardStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.award_status
	 * @param awardStatus  the value for view_task_record.award_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setAwardStatus(String awardStatus) {
		this.awardStatus = awardStatus == null ? null : awardStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.award_time
	 * @return  the value of view_task_record.award_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Date getAwardTime() {
		return awardTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.award_time
	 * @param awardTime  the value for view_task_record.award_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.award_amount
	 * @return  the value of view_task_record.award_amount
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Integer getAwardAmount() {
		return awardAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.award_amount
	 * @param awardAmount  the value for view_task_record.award_amount
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setAwardAmount(Integer awardAmount) {
		this.awardAmount = awardAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.enable_status
	 * @return  the value of view_task_record.enable_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.enable_status
	 * @param enableStatus  the value for view_task_record.enable_status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.status
	 * @return  the value of view_task_record.status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.status
	 * @param status  the value for view_task_record.status
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.creater
	 * @return  the value of view_task_record.creater
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.creater
	 * @param creater  the value for view_task_record.creater
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.create_time
	 * @return  the value of view_task_record.create_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.create_time
	 * @param createTime  the value for view_task_record.create_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.modifier
	 * @return  the value of view_task_record.modifier
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.modifier
	 * @param modifier  the value for view_task_record.modifier
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.modify_time
	 * @return  the value of view_task_record.modify_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.modify_time
	 * @param modifyTime  the value for view_task_record.modify_time
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.reorder
	 * @return  the value of view_task_record.reorder
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.reorder
	 * @param reorder  the value for view_task_record.reorder
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.remark
	 * @return  the value of view_task_record.remark
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.remark
	 * @param remark  the value for view_task_record.remark
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.user_mobile_phone_t2
	 * @return  the value of view_task_record.user_mobile_phone_t2
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getUserMobilePhoneT2() {
		return userMobilePhoneT2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.user_mobile_phone_t2
	 * @param userMobilePhoneT2  the value for view_task_record.user_mobile_phone_t2
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setUserMobilePhoneT2(String userMobilePhoneT2) {
		this.userMobilePhoneT2 = userMobilePhoneT2 == null ? null : userMobilePhoneT2.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column view_task_record.name_t3
	 * @return  the value of view_task_record.name_t3
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public String getNameT3() {
		return nameT3;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column view_task_record.name_t3
	 * @param nameT3  the value for view_task_record.name_t3
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	public void setNameT3(String nameT3) {
		this.nameT3 = nameT3 == null ? null : nameT3.trim();
	}
}