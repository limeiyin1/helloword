package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class AgentConsult extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.consult_id
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_CONSULT_ID = "consult_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.consult_id
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private Integer consultId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.type
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_TYPE = "type";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.type
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_CONTENT = "content";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.name
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_NAME = "name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.name
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.phonenumber
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_PHONENUMBER = "phonenumber";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.phonenumber
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private Integer phonenumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.qqno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_QQNO = "qqno";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.qqno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private Integer qqno;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.wechatno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_WECHATNO = "wechatno";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.wechatno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String wechatno;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.create_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.create_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.answer_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_ANSWER_TIME = "answer_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.answer_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private Date answerTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.answer_person
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_ANSWER_PERSON = "answer_person";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.answer_person
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String answerPerson;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.answer_content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_ANSWER_CONTENT = "answer_content";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.answer_content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String answerContent;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.modifier
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.modifier
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.modify_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.modify_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.enable_status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.enable_status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.reorder
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.reorder
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.remark
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_consult.remark
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.consult_id
	 * @return  the value of agent_consult.consult_id
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public Integer getConsultId() {
		return consultId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.consult_id
	 * @param consultId  the value for agent_consult.consult_id
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setConsultId(Integer consultId) {
		this.consultId = consultId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.type
	 * @return  the value of agent_consult.type
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getType() {
		return type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.type
	 * @param type  the value for agent_consult.type
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.content
	 * @return  the value of agent_consult.content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.content
	 * @param content  the value for agent_consult.content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.name
	 * @return  the value of agent_consult.name
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.name
	 * @param name  the value for agent_consult.name
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.phonenumber
	 * @return  the value of agent_consult.phonenumber
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public Integer getPhonenumber() {
		return phonenumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.phonenumber
	 * @param phonenumber  the value for agent_consult.phonenumber
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setPhonenumber(Integer phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.qqno
	 * @return  the value of agent_consult.qqno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public Integer getQqno() {
		return qqno;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.qqno
	 * @param qqno  the value for agent_consult.qqno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setQqno(Integer qqno) {
		this.qqno = qqno;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.wechatno
	 * @return  the value of agent_consult.wechatno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getWechatno() {
		return wechatno;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.wechatno
	 * @param wechatno  the value for agent_consult.wechatno
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setWechatno(String wechatno) {
		this.wechatno = wechatno == null ? null : wechatno.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.create_time
	 * @return  the value of agent_consult.create_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.create_time
	 * @param createTime  the value for agent_consult.create_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.answer_time
	 * @return  the value of agent_consult.answer_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public Date getAnswerTime() {
		return answerTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.answer_time
	 * @param answerTime  the value for agent_consult.answer_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.answer_person
	 * @return  the value of agent_consult.answer_person
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getAnswerPerson() {
		return answerPerson;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.answer_person
	 * @param answerPerson  the value for agent_consult.answer_person
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setAnswerPerson(String answerPerson) {
		this.answerPerson = answerPerson == null ? null : answerPerson.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.status
	 * @return  the value of agent_consult.status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.status
	 * @param status  the value for agent_consult.status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.answer_content
	 * @return  the value of agent_consult.answer_content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getAnswerContent() {
		return answerContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.answer_content
	 * @param answerContent  the value for agent_consult.answer_content
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent == null ? null : answerContent.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.modifier
	 * @return  the value of agent_consult.modifier
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.modifier
	 * @param modifier  the value for agent_consult.modifier
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.modify_time
	 * @return  the value of agent_consult.modify_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.modify_time
	 * @param modifyTime  the value for agent_consult.modify_time
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.enable_status
	 * @return  the value of agent_consult.enable_status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.enable_status
	 * @param enableStatus  the value for agent_consult.enable_status
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.reorder
	 * @return  the value of agent_consult.reorder
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.reorder
	 * @param reorder  the value for agent_consult.reorder
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_consult.remark
	 * @return  the value of agent_consult.remark
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_consult.remark
	 * @param remark  the value for agent_consult.remark
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}