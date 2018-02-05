package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfActivationCodeLog extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.log_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_LOG_ID = "log_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.log_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Integer logId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.opearte_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_OPEARTE_TYPE = "opearte_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.opearte_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String opearteType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.activation_prefix
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_ACTIVATION_PREFIX = "activation_prefix";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.activation_prefix
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String activationPrefix;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.user_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_USER_ID = "user_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.user_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.code_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_CODE_ID = "code_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.code_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Integer codeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.activation_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_ACTIVATION_STATUS = "activation_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.activation_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String activationStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.type_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_TYPE_ID = "type_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.type_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Integer typeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.batch_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_BATCH_NUMBER = "batch_number";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.batch_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String batchNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.pad_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_PAD_TYPE = "pad_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.pad_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String padType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.start_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_START_TIME = "start_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.start_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.end_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_END_TIME = "end_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.end_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.creater
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.creater
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.create_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.create_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.modifier
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.modifier
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.modify_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.modify_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.reorder
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.reorder
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.enable_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.enable_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.add_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_ADD_NUMBER = "add_number";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.add_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private Integer addNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.remark
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.remark
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.operate_ip
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_OPERATE_IP = "operate_ip";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.operate_ip
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String operateIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.activation_code
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public static final String FD_ACTIVATION_CODE = "activation_code";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_activation_code_log.activation_code
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private String activationCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.log_id
     *
     * @return the value of rf_activation_code_log.log_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Integer getLogId() {
        return logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.log_id
     *
     * @param logId the value for rf_activation_code_log.log_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.opearte_type
     *
     * @return the value of rf_activation_code_log.opearte_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getOpearteType() {
        return opearteType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.opearte_type
     *
     * @param opearteType the value for rf_activation_code_log.opearte_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setOpearteType(String opearteType) {
        this.opearteType = opearteType == null ? null : opearteType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.activation_prefix
     *
     * @return the value of rf_activation_code_log.activation_prefix
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getActivationPrefix() {
        return activationPrefix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.activation_prefix
     *
     * @param activationPrefix the value for rf_activation_code_log.activation_prefix
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setActivationPrefix(String activationPrefix) {
        this.activationPrefix = activationPrefix == null ? null : activationPrefix.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.user_id
     *
     * @return the value of rf_activation_code_log.user_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.user_id
     *
     * @param userId the value for rf_activation_code_log.user_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.code_id
     *
     * @return the value of rf_activation_code_log.code_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Integer getCodeId() {
        return codeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.code_id
     *
     * @param codeId the value for rf_activation_code_log.code_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.activation_status
     *
     * @return the value of rf_activation_code_log.activation_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getActivationStatus() {
        return activationStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.activation_status
     *
     * @param activationStatus the value for rf_activation_code_log.activation_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus == null ? null : activationStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.type_id
     *
     * @return the value of rf_activation_code_log.type_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.type_id
     *
     * @param typeId the value for rf_activation_code_log.type_id
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.batch_number
     *
     * @return the value of rf_activation_code_log.batch_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getBatchNumber() {
        return batchNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.batch_number
     *
     * @param batchNumber the value for rf_activation_code_log.batch_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber == null ? null : batchNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.pad_type
     *
     * @return the value of rf_activation_code_log.pad_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getPadType() {
        return padType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.pad_type
     *
     * @param padType the value for rf_activation_code_log.pad_type
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setPadType(String padType) {
        this.padType = padType == null ? null : padType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.start_time
     *
     * @return the value of rf_activation_code_log.start_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.start_time
     *
     * @param startTime the value for rf_activation_code_log.start_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.end_time
     *
     * @return the value of rf_activation_code_log.end_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.end_time
     *
     * @param endTime the value for rf_activation_code_log.end_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.creater
     *
     * @return the value of rf_activation_code_log.creater
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.creater
     *
     * @param creater the value for rf_activation_code_log.creater
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.create_time
     *
     * @return the value of rf_activation_code_log.create_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.create_time
     *
     * @param createTime the value for rf_activation_code_log.create_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.modifier
     *
     * @return the value of rf_activation_code_log.modifier
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.modifier
     *
     * @param modifier the value for rf_activation_code_log.modifier
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.modify_time
     *
     * @return the value of rf_activation_code_log.modify_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.modify_time
     *
     * @param modifyTime the value for rf_activation_code_log.modify_time
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.reorder
     *
     * @return the value of rf_activation_code_log.reorder
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.reorder
     *
     * @param reorder the value for rf_activation_code_log.reorder
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.status
     *
     * @return the value of rf_activation_code_log.status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.status
     *
     * @param status the value for rf_activation_code_log.status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.enable_status
     *
     * @return the value of rf_activation_code_log.enable_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.enable_status
     *
     * @param enableStatus the value for rf_activation_code_log.enable_status
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.add_number
     *
     * @return the value of rf_activation_code_log.add_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public Integer getAddNumber() {
        return addNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.add_number
     *
     * @param addNumber the value for rf_activation_code_log.add_number
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setAddNumber(Integer addNumber) {
        this.addNumber = addNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.remark
     *
     * @return the value of rf_activation_code_log.remark
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.remark
     *
     * @param remark the value for rf_activation_code_log.remark
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.operate_ip
     *
     * @return the value of rf_activation_code_log.operate_ip
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getOperateIp() {
        return operateIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.operate_ip
     *
     * @param operateIp the value for rf_activation_code_log.operate_ip
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp == null ? null : operateIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_activation_code_log.activation_code
     *
     * @return the value of rf_activation_code_log.activation_code
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_activation_code_log.activation_code
     *
     * @param activationCode the value for rf_activation_code_log.activation_code
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode == null ? null : activationCode.trim();
    }
}