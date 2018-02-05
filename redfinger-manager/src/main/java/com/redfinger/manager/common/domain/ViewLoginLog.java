package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class ViewLoginLog extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.log_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_LOG_ID = "log_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.log_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer logId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.log_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_LOG_TYPE = "log_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.log_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String logType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_id_tmp
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_ID_TMP = "user_id_tmp";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_id_tmp
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userIdTmp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.login_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_LOGIN_TIME = "login_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.login_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date loginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.logout_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_LOGOUT_TIME = "logout_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.logout_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date logoutTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.login_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_LOGIN_TYPE = "login_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.login_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String loginType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.ip_address
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_IP_ADDRESS = "ip_address";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.ip_address
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String ipAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.mac_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_MAC_ID = "mac_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.mac_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String macId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_ID = "user_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.client_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_CLIENT_TYPE = "client_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.client_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String clientType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.imei
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_IMEI = "imei";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.imei
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String imei;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.mobile_model
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_MOBILE_MODEL = "mobile_model";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.mobile_model
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String mobileModel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.os_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_OS_TYPE = "os_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.os_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String osType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_SOURCE = "user_source";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userSource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_MOBILE_PHONE_T2 = "user_mobile_phone_t2";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userMobilePhoneT2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_EMAIL_T2 = "user_email_t2";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userEmailT2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public static final String FD_USER_SOURCE_T2 = "user_source_t2";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column view_login_log.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private String userSourceT2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table view_login_log
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.log_id
     *
     * @return the value of view_login_log.log_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getLogId() {
        return logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.log_id
     *
     * @param logId the value for view_login_log.log_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.log_type
     *
     * @return the value of view_login_log.log_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getLogType() {
        return logType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.log_type
     *
     * @param logType the value for view_login_log.log_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.user_id_tmp
     *
     * @return the value of view_login_log.user_id_tmp
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserIdTmp() {
        return userIdTmp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.user_id_tmp
     *
     * @param userIdTmp the value for view_login_log.user_id_tmp
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserIdTmp(String userIdTmp) {
        this.userIdTmp = userIdTmp == null ? null : userIdTmp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.login_time
     *
     * @return the value of view_login_log.login_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.login_time
     *
     * @param loginTime the value for view_login_log.login_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.logout_time
     *
     * @return the value of view_login_log.logout_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getLogoutTime() {
        return logoutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.logout_time
     *
     * @param logoutTime the value for view_login_log.logout_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.login_type
     *
     * @return the value of view_login_log.login_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.login_type
     *
     * @param loginType the value for view_login_log.login_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setLoginType(String loginType) {
        this.loginType = loginType == null ? null : loginType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.ip_address
     *
     * @return the value of view_login_log.ip_address
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.ip_address
     *
     * @param ipAddress the value for view_login_log.ip_address
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.mac_id
     *
     * @return the value of view_login_log.mac_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getMacId() {
        return macId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.mac_id
     *
     * @param macId the value for view_login_log.mac_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setMacId(String macId) {
        this.macId = macId == null ? null : macId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.status
     *
     * @return the value of view_login_log.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.status
     *
     * @param status the value for view_login_log.status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.creater
     *
     * @return the value of view_login_log.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.creater
     *
     * @param creater the value for view_login_log.creater
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.create_time
     *
     * @return the value of view_login_log.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.create_time
     *
     * @param createTime the value for view_login_log.create_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.modifier
     *
     * @return the value of view_login_log.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.modifier
     *
     * @param modifier the value for view_login_log.modifier
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.modify_time
     *
     * @return the value of view_login_log.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.modify_time
     *
     * @param modifyTime the value for view_login_log.modify_time
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.reorder
     *
     * @return the value of view_login_log.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.reorder
     *
     * @param reorder the value for view_login_log.reorder
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.remark
     *
     * @return the value of view_login_log.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.remark
     *
     * @param remark the value for view_login_log.remark
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.enable_status
     *
     * @return the value of view_login_log.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.enable_status
     *
     * @param enableStatus the value for view_login_log.enable_status
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.user_id
     *
     * @return the value of view_login_log.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.user_id
     *
     * @param userId the value for view_login_log.user_id
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.client_type
     *
     * @return the value of view_login_log.client_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.client_type
     *
     * @param clientType the value for view_login_log.client_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setClientType(String clientType) {
        this.clientType = clientType == null ? null : clientType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.imei
     *
     * @return the value of view_login_log.imei
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getImei() {
        return imei;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.imei
     *
     * @param imei the value for view_login_log.imei
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.mobile_model
     *
     * @return the value of view_login_log.mobile_model
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getMobileModel() {
        return mobileModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.mobile_model
     *
     * @param mobileModel the value for view_login_log.mobile_model
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel == null ? null : mobileModel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.os_type
     *
     * @return the value of view_login_log.os_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getOsType() {
        return osType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.os_type
     *
     * @param osType the value for view_login_log.os_type
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setOsType(String osType) {
        this.osType = osType == null ? null : osType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.user_source
     *
     * @return the value of view_login_log.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserSource() {
        return userSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.user_source
     *
     * @param userSource the value for view_login_log.user_source
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserSource(String userSource) {
        this.userSource = userSource == null ? null : userSource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.user_mobile_phone_t2
     *
     * @return the value of view_login_log.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserMobilePhoneT2() {
        return userMobilePhoneT2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.user_mobile_phone_t2
     *
     * @param userMobilePhoneT2 the value for view_login_log.user_mobile_phone_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserMobilePhoneT2(String userMobilePhoneT2) {
        this.userMobilePhoneT2 = userMobilePhoneT2 == null ? null : userMobilePhoneT2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.user_email_t2
     *
     * @return the value of view_login_log.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserEmailT2() {
        return userEmailT2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.user_email_t2
     *
     * @param userEmailT2 the value for view_login_log.user_email_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserEmailT2(String userEmailT2) {
        this.userEmailT2 = userEmailT2 == null ? null : userEmailT2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column view_login_log.user_source_t2
     *
     * @return the value of view_login_log.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public String getUserSourceT2() {
        return userSourceT2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column view_login_log.user_source_t2
     *
     * @param userSourceT2 the value for view_login_log.user_source_t2
     *
     * @mbggenerated Thu Mar 31 19:54:20 CST 2016
     */
    public void setUserSourceT2(String userSourceT2) {
        this.userSourceT2 = userSourceT2 == null ? null : userSourceT2.trim();
    }
}