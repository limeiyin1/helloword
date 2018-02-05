package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfPadTask extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_TASK_ID = "task_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private Integer taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.pad_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_PAD_ID = "pad_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.pad_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private Integer padId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_command
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_TASK_COMMAND = "task_command";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_command
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String taskCommand;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_TASK_STATUS = "task_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String taskStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.creater
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.creater
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.create_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.create_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.modifier
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.modifier
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.modify_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.modify_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.reorder
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.reorder
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.remark
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.remark
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.enable_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.enable_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_result_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_TASK_RESULT_STATUS = "task_result_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_result_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String taskResultStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_result_info
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_TASK_RESULT_INFO = "task_result_info";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_result_info
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String taskResultInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.command_type
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_COMMAND_TYPE = "command_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.command_type
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String commandType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.batch_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_BATCH_ID = "batch_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.batch_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private Integer batchId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.pad_code
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_PAD_CODE = "pad_code";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.pad_code
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String padCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.user_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_USER_ID = "user_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.user_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_source
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public static final String FD_TASK_SOURCE = "task_source";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_pad_task.task_source
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private String taskSource;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_pad_task
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.task_id
     *
     * @return the value of rf_pad_task.task_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.task_id
     *
     * @param taskId the value for rf_pad_task.task_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.pad_id
     *
     * @return the value of rf_pad_task.pad_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public Integer getPadId() {
        return padId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.pad_id
     *
     * @param padId the value for rf_pad_task.pad_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setPadId(Integer padId) {
        this.padId = padId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.task_command
     *
     * @return the value of rf_pad_task.task_command
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getTaskCommand() {
        return taskCommand;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.task_command
     *
     * @param taskCommand the value for rf_pad_task.task_command
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setTaskCommand(String taskCommand) {
        this.taskCommand = taskCommand == null ? null : taskCommand.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.task_status
     *
     * @return the value of rf_pad_task.task_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.task_status
     *
     * @param taskStatus the value for rf_pad_task.task_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.status
     *
     * @return the value of rf_pad_task.status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.status
     *
     * @param status the value for rf_pad_task.status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.creater
     *
     * @return the value of rf_pad_task.creater
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.creater
     *
     * @param creater the value for rf_pad_task.creater
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.create_time
     *
     * @return the value of rf_pad_task.create_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.create_time
     *
     * @param createTime the value for rf_pad_task.create_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.modifier
     *
     * @return the value of rf_pad_task.modifier
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.modifier
     *
     * @param modifier the value for rf_pad_task.modifier
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.modify_time
     *
     * @return the value of rf_pad_task.modify_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.modify_time
     *
     * @param modifyTime the value for rf_pad_task.modify_time
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.reorder
     *
     * @return the value of rf_pad_task.reorder
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.reorder
     *
     * @param reorder the value for rf_pad_task.reorder
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.remark
     *
     * @return the value of rf_pad_task.remark
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.remark
     *
     * @param remark the value for rf_pad_task.remark
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.enable_status
     *
     * @return the value of rf_pad_task.enable_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.enable_status
     *
     * @param enableStatus the value for rf_pad_task.enable_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.task_result_status
     *
     * @return the value of rf_pad_task.task_result_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getTaskResultStatus() {
        return taskResultStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.task_result_status
     *
     * @param taskResultStatus the value for rf_pad_task.task_result_status
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setTaskResultStatus(String taskResultStatus) {
        this.taskResultStatus = taskResultStatus == null ? null : taskResultStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.task_result_info
     *
     * @return the value of rf_pad_task.task_result_info
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getTaskResultInfo() {
        return taskResultInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.task_result_info
     *
     * @param taskResultInfo the value for rf_pad_task.task_result_info
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setTaskResultInfo(String taskResultInfo) {
        this.taskResultInfo = taskResultInfo == null ? null : taskResultInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.command_type
     *
     * @return the value of rf_pad_task.command_type
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.command_type
     *
     * @param commandType the value for rf_pad_task.command_type
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setCommandType(String commandType) {
        this.commandType = commandType == null ? null : commandType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.batch_id
     *
     * @return the value of rf_pad_task.batch_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.batch_id
     *
     * @param batchId the value for rf_pad_task.batch_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.pad_code
     *
     * @return the value of rf_pad_task.pad_code
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getPadCode() {
        return padCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.pad_code
     *
     * @param padCode the value for rf_pad_task.pad_code
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setPadCode(String padCode) {
        this.padCode = padCode == null ? null : padCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.user_id
     *
     * @return the value of rf_pad_task.user_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.user_id
     *
     * @param userId the value for rf_pad_task.user_id
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_pad_task.task_source
     *
     * @return the value of rf_pad_task.task_source
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public String getTaskSource() {
        return taskSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_pad_task.task_source
     *
     * @param taskSource the value for rf_pad_task.task_source
     *
     * @mbggenerated Thu Aug 11 15:04:30 CST 2016
     */
    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource == null ? null : taskSource.trim();
    }
}