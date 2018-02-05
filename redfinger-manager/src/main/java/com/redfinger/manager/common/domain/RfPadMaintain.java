package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfPadMaintain extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.pad_maintain_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_MAINTAIN_ID = "pad_maintain_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.pad_maintain_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer padMaintainId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_ID = "pad_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer padId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.service_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_SERVICE_NAME = "service_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.service_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String serviceName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.service_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_SERVICE_CONTENT = "service_content";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.service_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String serviceContent;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.is_good
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_IS_GOOD = "is_good";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.is_good
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String isGood;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_maintain.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_pad_maintain
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.pad_maintain_id
	 * @return  the value of rf_pad_maintain.pad_maintain_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getPadMaintainId() {
		return padMaintainId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.pad_maintain_id
	 * @param padMaintainId  the value for rf_pad_maintain.pad_maintain_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadMaintainId(Integer padMaintainId) {
		this.padMaintainId = padMaintainId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.pad_id
	 * @return  the value of rf_pad_maintain.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getPadId() {
		return padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.pad_id
	 * @param padId  the value for rf_pad_maintain.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadId(Integer padId) {
		this.padId = padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.service_name
	 * @return  the value of rf_pad_maintain.service_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.service_name
	 * @param serviceName  the value for rf_pad_maintain.service_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName == null ? null : serviceName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.service_content
	 * @return  the value of rf_pad_maintain.service_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getServiceContent() {
		return serviceContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.service_content
	 * @param serviceContent  the value for rf_pad_maintain.service_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent == null ? null : serviceContent.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.is_good
	 * @return  the value of rf_pad_maintain.is_good
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getIsGood() {
		return isGood;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.is_good
	 * @param isGood  the value for rf_pad_maintain.is_good
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setIsGood(String isGood) {
		this.isGood = isGood == null ? null : isGood.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.status
	 * @return  the value of rf_pad_maintain.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.status
	 * @param status  the value for rf_pad_maintain.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.creater
	 * @return  the value of rf_pad_maintain.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.creater
	 * @param creater  the value for rf_pad_maintain.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.create_time
	 * @return  the value of rf_pad_maintain.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.create_time
	 * @param createTime  the value for rf_pad_maintain.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.modifier
	 * @return  the value of rf_pad_maintain.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.modifier
	 * @param modifier  the value for rf_pad_maintain.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.modify_time
	 * @return  the value of rf_pad_maintain.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.modify_time
	 * @param modifyTime  the value for rf_pad_maintain.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.reorder
	 * @return  the value of rf_pad_maintain.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.reorder
	 * @param reorder  the value for rf_pad_maintain.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.remark
	 * @return  the value of rf_pad_maintain.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.remark
	 * @param remark  the value for rf_pad_maintain.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_maintain.enable_status
	 * @return  the value of rf_pad_maintain.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_maintain.enable_status
	 * @param enableStatus  the value for rf_pad_maintain.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}
}