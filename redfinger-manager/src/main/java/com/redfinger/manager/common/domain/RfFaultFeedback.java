package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfFaultFeedback extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FAULT_FEEDBACK_ID = "fault_feedback_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer faultFeedbackId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CLASS_ID = "class_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer classId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.small_class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_SMALL_CLASS_ID = "small_class_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.small_class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer smallClassId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_source
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FEEDBACK_SOURCE = "feedback_source";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_source
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String feedbackSource;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FEEDBACK_IP = "feedback_ip";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String feedbackIp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_CODE = "pad_code";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String padCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_contact
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FEEDBACK_CONTACT = "feedback_contact";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_contact
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String feedbackContact;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FEEDBACK_CONTENT = "feedback_content";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String feedbackContent;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_handle
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FEEDBACK_HANDLE = "feedback_handle";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_handle
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String feedbackHandle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FEEDBACK_STATUS = "feedback_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String feedbackStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.finish_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FINISH_TIME = "finish_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.finish_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date finishTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_qq
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_FEEDBACK_QQ = "feedback_qq";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.feedback_qq
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String feedbackQq;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_USER_ID = "user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_ID = "pad_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer padId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.promoter
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PROMOTER = "promoter";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.promoter
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String promoter;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.last_handler
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_LAST_HANDLER = "last_handler";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.last_handler
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String lastHandler;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.batch_number
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_BATCH_NUMBER = "batch_number";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.batch_number
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer batchNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_USER_ID_TMP = "user_id_tmp";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_fault_feedback.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String userIdTmp;
	
	public static final String FD_CLIENT_SOURCE = "client_source";
	
	private String clientSource;
	
	public static final String  FD_OPERATE_TYPE= "operate_type";
	
	private String operateType;
	
	/** 更换设备后的设备编码*/
	private String renewalPadCode;
	/** 处理完毕确认类型(无需, 当前用户, 所有客服)*/
	private String replyType;
	
	/** 更换设备后的设备编码*/
	public String getRenewalPadCode() {
		return renewalPadCode;
	}

	/** 设置更换设备后的设备编码*/
	public void setRenewalPadCode(String renewalPadCode) {
		this.renewalPadCode = renewalPadCode;
	}
	
	/** 获取处理完毕确认类型(无需, 当前用户, 所有客服)*/
	public String getReplyType() {
		return replyType;
	}

	/** 设置处理完毕确认类型(0无需, 1当前用户, 2所有客服)*/
	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}



	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_fault_feedback
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.fault_feedback_id
	 * @return  the value of rf_fault_feedback.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getFaultFeedbackId() {
		return faultFeedbackId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.fault_feedback_id
	 * @param faultFeedbackId  the value for rf_fault_feedback.fault_feedback_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFaultFeedbackId(Integer faultFeedbackId) {
		this.faultFeedbackId = faultFeedbackId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.class_id
	 * @return  the value of rf_fault_feedback.class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getClassId() {
		return classId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.class_id
	 * @param classId  the value for rf_fault_feedback.class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.small_class_id
	 * @return  the value of rf_fault_feedback.small_class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getSmallClassId() {
		return smallClassId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.small_class_id
	 * @param smallClassId  the value for rf_fault_feedback.small_class_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setSmallClassId(Integer smallClassId) {
		this.smallClassId = smallClassId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.feedback_source
	 * @return  the value of rf_fault_feedback.feedback_source
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getFeedbackSource() {
		return feedbackSource;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.feedback_source
	 * @param feedbackSource  the value for rf_fault_feedback.feedback_source
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFeedbackSource(String feedbackSource) {
		this.feedbackSource = feedbackSource == null ? null : feedbackSource.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.feedback_ip
	 * @return  the value of rf_fault_feedback.feedback_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getFeedbackIp() {
		return feedbackIp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.feedback_ip
	 * @param feedbackIp  the value for rf_fault_feedback.feedback_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFeedbackIp(String feedbackIp) {
		this.feedbackIp = feedbackIp == null ? null : feedbackIp.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.pad_code
	 * @return  the value of rf_fault_feedback.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getPadCode() {
		return padCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.pad_code
	 * @param padCode  the value for rf_fault_feedback.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadCode(String padCode) {
		this.padCode = padCode == null ? null : padCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.feedback_contact
	 * @return  the value of rf_fault_feedback.feedback_contact
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getFeedbackContact() {
		return feedbackContact;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.feedback_contact
	 * @param feedbackContact  the value for rf_fault_feedback.feedback_contact
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFeedbackContact(String feedbackContact) {
		this.feedbackContact = feedbackContact == null ? null : feedbackContact.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.feedback_content
	 * @return  the value of rf_fault_feedback.feedback_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getFeedbackContent() {
		return feedbackContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.feedback_content
	 * @param feedbackContent  the value for rf_fault_feedback.feedback_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent == null ? null : feedbackContent.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.feedback_handle
	 * @return  the value of rf_fault_feedback.feedback_handle
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getFeedbackHandle() {
		return feedbackHandle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.feedback_handle
	 * @param feedbackHandle  the value for rf_fault_feedback.feedback_handle
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFeedbackHandle(String feedbackHandle) {
		this.feedbackHandle = feedbackHandle == null ? null : feedbackHandle.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.feedback_status
	 * @return  the value of rf_fault_feedback.feedback_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.feedback_status
	 * @param feedbackStatus  the value for rf_fault_feedback.feedback_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus == null ? null : feedbackStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.finish_time
	 * @return  the value of rf_fault_feedback.finish_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.finish_time
	 * @param finishTime  the value for rf_fault_feedback.finish_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.status
	 * @return  the value of rf_fault_feedback.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.status
	 * @param status  the value for rf_fault_feedback.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.creater
	 * @return  the value of rf_fault_feedback.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.creater
	 * @param creater  the value for rf_fault_feedback.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.create_time
	 * @return  the value of rf_fault_feedback.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.create_time
	 * @param createTime  the value for rf_fault_feedback.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.modifier
	 * @return  the value of rf_fault_feedback.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.modifier
	 * @param modifier  the value for rf_fault_feedback.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.modify_time
	 * @return  the value of rf_fault_feedback.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.modify_time
	 * @param modifyTime  the value for rf_fault_feedback.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.reorder
	 * @return  the value of rf_fault_feedback.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.reorder
	 * @param reorder  the value for rf_fault_feedback.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.remark
	 * @return  the value of rf_fault_feedback.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.remark
	 * @param remark  the value for rf_fault_feedback.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.enable_status
	 * @return  the value of rf_fault_feedback.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.enable_status
	 * @param enableStatus  the value for rf_fault_feedback.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.feedback_qq
	 * @return  the value of rf_fault_feedback.feedback_qq
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getFeedbackQq() {
		return feedbackQq;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.feedback_qq
	 * @param feedbackQq  the value for rf_fault_feedback.feedback_qq
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setFeedbackQq(String feedbackQq) {
		this.feedbackQq = feedbackQq == null ? null : feedbackQq.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.user_id
	 * @return  the value of rf_fault_feedback.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.user_id
	 * @param userId  the value for rf_fault_feedback.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.pad_id
	 * @return  the value of rf_fault_feedback.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getPadId() {
		return padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.pad_id
	 * @param padId  the value for rf_fault_feedback.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadId(Integer padId) {
		this.padId = padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.promoter
	 * @return  the value of rf_fault_feedback.promoter
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getPromoter() {
		return promoter;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.promoter
	 * @param promoter  the value for rf_fault_feedback.promoter
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPromoter(String promoter) {
		this.promoter = promoter == null ? null : promoter.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.last_handler
	 * @return  the value of rf_fault_feedback.last_handler
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getLastHandler() {
		return lastHandler;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.last_handler
	 * @param lastHandler  the value for rf_fault_feedback.last_handler
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setLastHandler(String lastHandler) {
		this.lastHandler = lastHandler == null ? null : lastHandler.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.batch_number
	 * @return  the value of rf_fault_feedback.batch_number
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getBatchNumber() {
		return batchNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.batch_number
	 * @param batchNumber  the value for rf_fault_feedback.batch_number
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_fault_feedback.user_id_tmp
	 * @return  the value of rf_fault_feedback.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getUserIdTmp() {
		return userIdTmp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_fault_feedback.user_id_tmp
	 * @param userIdTmp  the value for rf_fault_feedback.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setUserIdTmp(String userIdTmp) {
		this.userIdTmp = userIdTmp == null ? null : userIdTmp.trim();
	}

	public String getClientSource() {
		return clientSource;
	}

	public void setClientSource(String clientSource) {
		this.clientSource = clientSource;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
	private String padStatus;
	
	public static final String FD_PAD_STATUS = "pad_status";

	public String getPadStatus() {
		return padStatus;
	}

	public void setPadStatus(String padStatus) {
		this.padStatus = padStatus == null ? null : padStatus.trim();
	}
	private String vmStatus;
	public static final String FD_VM_STATUS = "vm_status";

	public String getVmStatus() {
		return vmStatus;
	}

	public void setVmStatus(String vmStatus) {
		this.vmStatus = vmStatus == null ? null : vmStatus.trim();
	}
}