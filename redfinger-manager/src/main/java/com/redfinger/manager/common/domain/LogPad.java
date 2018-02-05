package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class LogPad extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ID = "id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.category
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CATEGORY = "category";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.category
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String category;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_ID = "pad_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer padId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_CODE = "pad_code";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String padCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.oper_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_OPER_IP = "oper_ip";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.oper_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String operIp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.oper_from
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_OPER_FROM = "oper_from";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.oper_from
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String operFrom;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.oper_connect
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_OPER_CONNECT = "oper_connect";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.oper_connect
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String operConnect;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column log_pad.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table log_pad
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.id
	 * @return  the value of log_pad.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.id
	 * @param id  the value for log_pad.id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.creater
	 * @return  the value of log_pad.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.creater
	 * @param creater  the value for log_pad.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.create_time
	 * @return  the value of log_pad.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.create_time
	 * @param createTime  the value for log_pad.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.category
	 * @return  the value of log_pad.category
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.category
	 * @param category  the value for log_pad.category
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.pad_id
	 * @return  the value of log_pad.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getPadId() {
		return padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.pad_id
	 * @param padId  the value for log_pad.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadId(Integer padId) {
		this.padId = padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.pad_code
	 * @return  the value of log_pad.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getPadCode() {
		return padCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.pad_code
	 * @param padCode  the value for log_pad.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadCode(String padCode) {
		this.padCode = padCode == null ? null : padCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.oper_ip
	 * @return  the value of log_pad.oper_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getOperIp() {
		return operIp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.oper_ip
	 * @param operIp  the value for log_pad.oper_ip
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setOperIp(String operIp) {
		this.operIp = operIp == null ? null : operIp.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.oper_from
	 * @return  the value of log_pad.oper_from
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getOperFrom() {
		return operFrom;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.oper_from
	 * @param operFrom  the value for log_pad.oper_from
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setOperFrom(String operFrom) {
		this.operFrom = operFrom == null ? null : operFrom.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.oper_connect
	 * @return  the value of log_pad.oper_connect
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getOperConnect() {
		return operConnect;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.oper_connect
	 * @param operConnect  the value for log_pad.oper_connect
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setOperConnect(String operConnect) {
		this.operConnect = operConnect == null ? null : operConnect.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column log_pad.remark
	 * @return  the value of log_pad.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column log_pad.remark
	 * @param remark  the value for log_pad.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}