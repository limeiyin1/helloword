package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class TaskUserInterim extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_ID = "id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.user_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_USER_ID = "user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.user_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_TASK_ID = "task_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Integer taskId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_now
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_TASK_NOW = "task_now";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_now
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Integer taskNow;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_reach
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_TASK_REACH = "task_reach";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_reach
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Integer taskReach;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.award_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_AWARD_STATUS = "award_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.award_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String awardStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.category
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_CATEGORY = "category";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.category
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String category;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_name
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_TASK_NAME = "task_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_name
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String taskName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.remark
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.remark
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.award_amount
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_AWARD_AMOUNT = "award_amount";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.award_amount
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Integer awardAmount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.take_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_TAKE_TIME = "take_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.take_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Date takeTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.record_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_RECORD_TIME = "record_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.record_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Date recordTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.enable_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.enable_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.creater
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.creater
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.create_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.create_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.modifier
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.modifier
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.modify_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.modify_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.reorder
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.reorder
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.begin_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_BEGIN_TIME = "begin_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.begin_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Date beginTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.end_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_END_TIME = "end_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.end_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private Date endTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.thresholds
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_THRESHOLDS = "thresholds";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.thresholds
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String thresholds;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_ser
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public static final String FD_TASK_SER = "task_ser";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column task_user_interim.task_ser
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private String taskSer;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table task_user_interim
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.id
	 * @return  the value of task_user_interim.id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.id
	 * @param id  the value for task_user_interim.id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.user_id
	 * @return  the value of task_user_interim.user_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.user_id
	 * @param userId  the value for task_user_interim.user_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.task_id
	 * @return  the value of task_user_interim.task_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.task_id
	 * @param taskId  the value for task_user_interim.task_id
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.task_now
	 * @return  the value of task_user_interim.task_now
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Integer getTaskNow() {
		return taskNow;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.task_now
	 * @param taskNow  the value for task_user_interim.task_now
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setTaskNow(Integer taskNow) {
		this.taskNow = taskNow;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.task_reach
	 * @return  the value of task_user_interim.task_reach
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Integer getTaskReach() {
		return taskReach;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.task_reach
	 * @param taskReach  the value for task_user_interim.task_reach
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setTaskReach(Integer taskReach) {
		this.taskReach = taskReach;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.award_status
	 * @return  the value of task_user_interim.award_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getAwardStatus() {
		return awardStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.award_status
	 * @param awardStatus  the value for task_user_interim.award_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setAwardStatus(String awardStatus) {
		this.awardStatus = awardStatus == null ? null : awardStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.category
	 * @return  the value of task_user_interim.category
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.category
	 * @param category  the value for task_user_interim.category
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.task_name
	 * @return  the value of task_user_interim.task_name
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.task_name
	 * @param taskName  the value for task_user_interim.task_name
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName == null ? null : taskName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.remark
	 * @return  the value of task_user_interim.remark
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.remark
	 * @param remark  the value for task_user_interim.remark
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.award_amount
	 * @return  the value of task_user_interim.award_amount
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Integer getAwardAmount() {
		return awardAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.award_amount
	 * @param awardAmount  the value for task_user_interim.award_amount
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setAwardAmount(Integer awardAmount) {
		this.awardAmount = awardAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.take_time
	 * @return  the value of task_user_interim.take_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Date getTakeTime() {
		return takeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.take_time
	 * @param takeTime  the value for task_user_interim.take_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.record_time
	 * @return  the value of task_user_interim.record_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Date getRecordTime() {
		return recordTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.record_time
	 * @param recordTime  the value for task_user_interim.record_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.enable_status
	 * @return  the value of task_user_interim.enable_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.enable_status
	 * @param enableStatus  the value for task_user_interim.enable_status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.status
	 * @return  the value of task_user_interim.status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.status
	 * @param status  the value for task_user_interim.status
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.creater
	 * @return  the value of task_user_interim.creater
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.creater
	 * @param creater  the value for task_user_interim.creater
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.create_time
	 * @return  the value of task_user_interim.create_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.create_time
	 * @param createTime  the value for task_user_interim.create_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.modifier
	 * @return  the value of task_user_interim.modifier
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.modifier
	 * @param modifier  the value for task_user_interim.modifier
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.modify_time
	 * @return  the value of task_user_interim.modify_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.modify_time
	 * @param modifyTime  the value for task_user_interim.modify_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.reorder
	 * @return  the value of task_user_interim.reorder
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.reorder
	 * @param reorder  the value for task_user_interim.reorder
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.begin_time
	 * @return  the value of task_user_interim.begin_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.begin_time
	 * @param beginTime  the value for task_user_interim.begin_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.end_time
	 * @return  the value of task_user_interim.end_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.end_time
	 * @param endTime  the value for task_user_interim.end_time
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.thresholds
	 * @return  the value of task_user_interim.thresholds
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getThresholds() {
		return thresholds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.thresholds
	 * @param thresholds  the value for task_user_interim.thresholds
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setThresholds(String thresholds) {
		this.thresholds = thresholds == null ? null : thresholds.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column task_user_interim.task_ser
	 * @return  the value of task_user_interim.task_ser
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public String getTaskSer() {
		return taskSer;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column task_user_interim.task_ser
	 * @param taskSer  the value for task_user_interim.task_ser
	 * @mbggenerated  Mon Mar 21 14:37:21 CST 2016
	 */
	public void setTaskSer(String taskSer) {
		this.taskSer = taskSer == null ? null : taskSer.trim();
	}
}