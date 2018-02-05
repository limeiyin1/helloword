package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class TaskGameCheck extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_CHECK_ID = "check_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private Integer checkId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.task_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_TASK_ID = "task_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.task_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private Integer taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.user_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_USER_ID = "user_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.user_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.user_mobile_phone
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_USER_MOBILE_PHONE = "user_mobile_phone";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.user_mobile_phone
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private String userMobilePhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_game_account
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_CHECK_GAME_ACCOUNT = "check_game_account";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_game_account
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private String checkGameAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_status
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_CHECK_STATUS = "check_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_status
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private String checkStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.create_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.create_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_CHECK_TIME = "check_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private Date checkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_person
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_CHECK_PERSON = "check_person";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.check_person
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private String checkPerson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.remark
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_game_check.remark
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.check_id
     *
     * @return the value of task_game_check.check_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public Integer getCheckId() {
        return checkId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.check_id
     *
     * @param checkId the value for task_game_check.check_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.task_id
     *
     * @return the value of task_game_check.task_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.task_id
     *
     * @param taskId the value for task_game_check.task_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.user_id
     *
     * @return the value of task_game_check.user_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.user_id
     *
     * @param userId the value for task_game_check.user_id
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.user_mobile_phone
     *
     * @return the value of task_game_check.user_mobile_phone
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public String getUserMobilePhone() {
        return userMobilePhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.user_mobile_phone
     *
     * @param userMobilePhone the value for task_game_check.user_mobile_phone
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setUserMobilePhone(String userMobilePhone) {
        this.userMobilePhone = userMobilePhone == null ? null : userMobilePhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.check_game_account
     *
     * @return the value of task_game_check.check_game_account
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public String getCheckGameAccount() {
        return checkGameAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.check_game_account
     *
     * @param checkGameAccount the value for task_game_check.check_game_account
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setCheckGameAccount(String checkGameAccount) {
        this.checkGameAccount = checkGameAccount == null ? null : checkGameAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.check_status
     *
     * @return the value of task_game_check.check_status
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public String getCheckStatus() {
        return checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.check_status
     *
     * @param checkStatus the value for task_game_check.check_status
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.create_time
     *
     * @return the value of task_game_check.create_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.create_time
     *
     * @param createTime the value for task_game_check.create_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.check_time
     *
     * @return the value of task_game_check.check_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.check_time
     *
     * @param checkTime the value for task_game_check.check_time
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.check_person
     *
     * @return the value of task_game_check.check_person
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public String getCheckPerson() {
        return checkPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.check_person
     *
     * @param checkPerson the value for task_game_check.check_person
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson == null ? null : checkPerson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_game_check.remark
     *
     * @return the value of task_game_check.remark
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_game_check.remark
     *
     * @param remark the value for task_game_check.remark
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}