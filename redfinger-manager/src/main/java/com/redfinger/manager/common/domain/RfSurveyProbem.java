package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfSurveyProbem extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ID = "id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.survey_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_SURVEY_ID = "survey_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.survey_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer surveyId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PROBLEM_ID = "problem_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer problemId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_survey_probem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_survey_probem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.id
	 * @return  the value of rf_survey_probem.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.id
	 * @param id  the value for rf_survey_probem.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.survey_id
	 * @return  the value of rf_survey_probem.survey_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getSurveyId() {
		return surveyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.survey_id
	 * @param surveyId  the value for rf_survey_probem.survey_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.problem_id
	 * @return  the value of rf_survey_probem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getProblemId() {
		return problemId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.problem_id
	 * @param problemId  the value for rf_survey_probem.problem_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.enable_status
	 * @return  the value of rf_survey_probem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.enable_status
	 * @param enableStatus  the value for rf_survey_probem.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.status
	 * @return  the value of rf_survey_probem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.status
	 * @param status  the value for rf_survey_probem.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.creater
	 * @return  the value of rf_survey_probem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.creater
	 * @param creater  the value for rf_survey_probem.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.create_time
	 * @return  the value of rf_survey_probem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.create_time
	 * @param createTime  the value for rf_survey_probem.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.modifier
	 * @return  the value of rf_survey_probem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.modifier
	 * @param modifier  the value for rf_survey_probem.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.modify_time
	 * @return  the value of rf_survey_probem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.modify_time
	 * @param modifyTime  the value for rf_survey_probem.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.reorder
	 * @return  the value of rf_survey_probem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.reorder
	 * @param reorder  the value for rf_survey_probem.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_survey_probem.remark
	 * @return  the value of rf_survey_probem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_survey_probem.remark
	 * @param remark  the value for rf_survey_probem.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}