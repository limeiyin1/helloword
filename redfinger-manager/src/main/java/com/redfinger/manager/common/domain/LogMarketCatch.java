package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class LogMarketCatch extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ID = "id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_NAME = "name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.begin_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_BEGIN_TIME = "begin_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.begin_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date beginTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.end_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_END_TIME = "end_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.end_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date endTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_GAME_ID = "game_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer gameId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_GAME_NAME = "game_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String gameName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.resource_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_RESOURCE_ID = "resource_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.resource_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer resourceId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.resource_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_RESOURCE_NAME = "resource_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.resource_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String resourceName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.result_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_RESULT_STATUS = "result_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.result_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String resultStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.exception_msg
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_EXCEPTION_MSG = "exception_msg";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.exception_msg
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String exceptionMsg;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_market_catch.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table log_market_catch
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.id
	 * @return  the value of log_market_catch.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.id
	 * @param id  the value for log_market_catch.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.name
	 * @return  the value of log_market_catch.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.name
	 * @param name  the value for log_market_catch.name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.begin_time
	 * @return  the value of log_market_catch.begin_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.begin_time
	 * @param beginTime  the value for log_market_catch.begin_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.end_time
	 * @return  the value of log_market_catch.end_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.end_time
	 * @param endTime  the value for log_market_catch.end_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.game_id
	 * @return  the value of log_market_catch.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getGameId() {
		return gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.game_id
	 * @param gameId  the value for log_market_catch.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.game_name
	 * @return  the value of log_market_catch.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.game_name
	 * @param gameName  the value for log_market_catch.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName == null ? null : gameName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.resource_id
	 * @return  the value of log_market_catch.resource_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.resource_id
	 * @param resourceId  the value for log_market_catch.resource_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.resource_name
	 * @return  the value of log_market_catch.resource_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.resource_name
	 * @param resourceName  the value for log_market_catch.resource_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName == null ? null : resourceName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.result_status
	 * @return  the value of log_market_catch.result_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.result_status
	 * @param resultStatus  the value for log_market_catch.result_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus == null ? null : resultStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.exception_msg
	 * @return  the value of log_market_catch.exception_msg
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.exception_msg
	 * @param exceptionMsg  the value for log_market_catch.exception_msg
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg == null ? null : exceptionMsg.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.creater
	 * @return  the value of log_market_catch.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.creater
	 * @param creater  the value for log_market_catch.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.create_time
	 * @return  the value of log_market_catch.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.create_time
	 * @param createTime  the value for log_market_catch.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_market_catch.remark
	 * @return  the value of log_market_catch.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_market_catch.remark
	 * @param remark  the value for log_market_catch.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}