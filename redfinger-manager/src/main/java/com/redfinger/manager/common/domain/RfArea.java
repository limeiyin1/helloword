package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfArea extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ID = "id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_NAME = "name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.parent_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PARENT_ID = "parent_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.parent_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer parentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.grade
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_GRADE = "grade";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.grade
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer grade;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_area.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_area
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.id
	 * @return  the value of rf_area.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.id
	 * @param id  the value for rf_area.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.name
	 * @return  the value of rf_area.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.name
	 * @param name  the value for rf_area.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.parent_id
	 * @return  the value of rf_area.parent_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.parent_id
	 * @param parentId  the value for rf_area.parent_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.grade
	 * @return  the value of rf_area.grade
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.grade
	 * @param grade  the value for rf_area.grade
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.create_time
	 * @return  the value of rf_area.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.create_time
	 * @param createTime  the value for rf_area.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.modify_time
	 * @return  the value of rf_area.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.modify_time
	 * @param modifyTime  the value for rf_area.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.creater
	 * @return  the value of rf_area.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.creater
	 * @param creater  the value for rf_area.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.modifier
	 * @return  the value of rf_area.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.modifier
	 * @param modifier  the value for rf_area.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.reorder
	 * @return  the value of rf_area.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.reorder
	 * @param reorder  the value for rf_area.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.remark
	 * @return  the value of rf_area.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.remark
	 * @param remark  the value for rf_area.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.status
	 * @return  the value of rf_area.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.status
	 * @param status  the value for rf_area.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_area.enable_status
	 * @return  the value of rf_area.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_area.enable_status
	 * @param enableStatus  the value for rf_area.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}
}