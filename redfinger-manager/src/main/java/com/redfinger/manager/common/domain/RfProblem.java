package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfProblem extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PROBLEM_ID = "problem_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer problemId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.problem_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PROBLEM_CONTENT = "problem_content";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.problem_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String problemContent;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.problem_type
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PROBLEM_TYPE = "problem_type";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.problem_type
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String problemType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.is_must
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_IS_MUST = "is_must";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_problem.is_must
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String isMust;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.problem_id
	 * @return  the value of rf_problem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getProblemId() {
		return problemId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.problem_id
	 * @param problemId  the value for rf_problem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.problem_content
	 * @return  the value of rf_problem.problem_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getProblemContent() {
		return problemContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.problem_content
	 * @param problemContent  the value for rf_problem.problem_content
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setProblemContent(String problemContent) {
		this.problemContent = problemContent == null ? null : problemContent.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.problem_type
	 * @return  the value of rf_problem.problem_type
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getProblemType() {
		return problemType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.problem_type
	 * @param problemType  the value for rf_problem.problem_type
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setProblemType(String problemType) {
		this.problemType = problemType == null ? null : problemType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.enable_status
	 * @return  the value of rf_problem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.enable_status
	 * @param enableStatus  the value for rf_problem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.status
	 * @return  the value of rf_problem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.status
	 * @param status  the value for rf_problem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.creater
	 * @return  the value of rf_problem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.creater
	 * @param creater  the value for rf_problem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.create_time
	 * @return  the value of rf_problem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.create_time
	 * @param createTime  the value for rf_problem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.modifier
	 * @return  the value of rf_problem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.modifier
	 * @param modifier  the value for rf_problem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.modify_time
	 * @return  the value of rf_problem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.modify_time
	 * @param modifyTime  the value for rf_problem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.reorder
	 * @return  the value of rf_problem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.reorder
	 * @param reorder  the value for rf_problem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.remark
	 * @return  the value of rf_problem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.remark
	 * @param remark  the value for rf_problem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_problem.is_must
	 * @return  the value of rf_problem.is_must
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getIsMust() {
		return isMust;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_problem.is_must
	 * @param isMust  the value for rf_problem.is_must
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setIsMust(String isMust) {
		this.isMust = isMust == null ? null : isMust.trim();
	}
}