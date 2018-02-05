package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class AgentUser extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_USER_ID = "user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_pwd
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_USER_PWD = "user_pwd";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_pwd
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String userPwd;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_USER_NAME = "user_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String userName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_image_url
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_USER_IMAGE_URL = "user_image_url";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_image_url
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String userImageUrl;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_mobile_phone
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_USER_MOBILE_PHONE = "user_mobile_phone";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_mobile_phone
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String userMobilePhone;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_email
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_USER_EMAIL = "user_email";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_email
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String userEmail;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_USER_STATUS = "user_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.user_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String userStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.register_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_REGISTER_IP = "register_ip";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.register_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String registerIp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.login_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_LOGIN_TYPE = "login_type";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.login_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String loginType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.login_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_LOGIN_IP = "login_ip";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.login_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String loginIp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.login_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_LOGIN_TIME = "login_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.login_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Date loginTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.reorder
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.reorder
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_user.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table agent_user
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.user_id
	 * @return  the value of agent_user.user_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.user_id
	 * @param userId  the value for agent_user.user_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.user_pwd
	 * @return  the value of agent_user.user_pwd
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getUserPwd() {
		return userPwd;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.user_pwd
	 * @param userPwd  the value for agent_user.user_pwd
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd == null ? null : userPwd.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.user_name
	 * @return  the value of agent_user.user_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.user_name
	 * @param userName  the value for agent_user.user_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.user_image_url
	 * @return  the value of agent_user.user_image_url
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getUserImageUrl() {
		return userImageUrl;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.user_image_url
	 * @param userImageUrl  the value for agent_user.user_image_url
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl == null ? null : userImageUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.user_mobile_phone
	 * @return  the value of agent_user.user_mobile_phone
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getUserMobilePhone() {
		return userMobilePhone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.user_mobile_phone
	 * @param userMobilePhone  the value for agent_user.user_mobile_phone
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setUserMobilePhone(String userMobilePhone) {
		this.userMobilePhone = userMobilePhone == null ? null : userMobilePhone.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.user_email
	 * @return  the value of agent_user.user_email
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.user_email
	 * @param userEmail  the value for agent_user.user_email
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail == null ? null : userEmail.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.user_status
	 * @return  the value of agent_user.user_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.user_status
	 * @param userStatus  the value for agent_user.user_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus == null ? null : userStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.register_ip
	 * @return  the value of agent_user.register_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getRegisterIp() {
		return registerIp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.register_ip
	 * @param registerIp  the value for agent_user.register_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp == null ? null : registerIp.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.login_type
	 * @return  the value of agent_user.login_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.login_type
	 * @param loginType  the value for agent_user.login_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType == null ? null : loginType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.login_ip
	 * @return  the value of agent_user.login_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.login_ip
	 * @param loginIp  the value for agent_user.login_ip
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp == null ? null : loginIp.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.login_time
	 * @return  the value of agent_user.login_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.login_time
	 * @param loginTime  the value for agent_user.login_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.status
	 * @return  the value of agent_user.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.status
	 * @param status  the value for agent_user.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.creater
	 * @return  the value of agent_user.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.creater
	 * @param creater  the value for agent_user.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.create_time
	 * @return  the value of agent_user.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.create_time
	 * @param createTime  the value for agent_user.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.modifier
	 * @return  the value of agent_user.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.modifier
	 * @param modifier  the value for agent_user.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.modify_time
	 * @return  the value of agent_user.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.modify_time
	 * @param modifyTime  the value for agent_user.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.reorder
	 * @return  the value of agent_user.reorder
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.reorder
	 * @param reorder  the value for agent_user.reorder
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.remark
	 * @return  the value of agent_user.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.remark
	 * @param remark  the value for agent_user.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_user.enable_status
	 * @return  the value of agent_user.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_user.enable_status
	 * @param enableStatus  the value for agent_user.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}
}