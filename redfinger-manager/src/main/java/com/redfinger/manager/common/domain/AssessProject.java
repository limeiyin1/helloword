package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class AssessProject extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_ID = "id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_NAME = "name";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.begin_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_BEGIN_TIME = "begin_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.begin_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Date beginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.end_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_END_TIME = "end_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.end_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.creater
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.creater
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.create_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.create_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.modifier
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.modifier
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.modify_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.modify_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.enable_status
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.enable_status
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.remark
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.remark
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.secret
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_SECRET = "secret";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_project.secret
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String secret;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table assess_project
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.id
     *
     * @return the value of assess_project.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.id
     *
     * @param id the value for assess_project.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.name
     *
     * @return the value of assess_project.name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.name
     *
     * @param name the value for assess_project.name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.begin_time
     *
     * @return the value of assess_project.begin_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.begin_time
     *
     * @param beginTime the value for assess_project.begin_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.end_time
     *
     * @return the value of assess_project.end_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.end_time
     *
     * @param endTime the value for assess_project.end_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.creater
     *
     * @return the value of assess_project.creater
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.creater
     *
     * @param creater the value for assess_project.creater
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.create_time
     *
     * @return the value of assess_project.create_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.create_time
     *
     * @param createTime the value for assess_project.create_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.modifier
     *
     * @return the value of assess_project.modifier
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.modifier
     *
     * @param modifier the value for assess_project.modifier
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.modify_time
     *
     * @return the value of assess_project.modify_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.modify_time
     *
     * @param modifyTime the value for assess_project.modify_time
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.enable_status
     *
     * @return the value of assess_project.enable_status
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.enable_status
     *
     * @param enableStatus the value for assess_project.enable_status
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.remark
     *
     * @return the value of assess_project.remark
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.remark
     *
     * @param remark the value for assess_project.remark
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_project.secret
     *
     * @return the value of assess_project.secret
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getSecret() {
        return secret;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_project.secret
     *
     * @param secret the value for assess_project.secret
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }
}