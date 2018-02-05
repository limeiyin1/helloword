package com.redfinger.manager.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.redfinger.manager.common.base.BaseDomain;

public class RfMsgTemplate extends BaseDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.template_id
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private Integer templateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.template_type
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private String templateType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.template_text
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private String templateText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.status
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.creater
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.create_time
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.modifier
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.modify_time
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.reorder
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_msg_template.enable_status
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    private String enableStatus;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.template_id
     *
     * @return the value of rf_msg_template.template_id
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.template_id
     *
     * @param templateId the value for rf_msg_template.template_id
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.template_type
     *
     * @return the value of rf_msg_template.template_type
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.template_type
     *
     * @param templateType the value for rf_msg_template.template_type
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.template_text
     *
     * @return the value of rf_msg_template.template_text
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public String getTemplateText() {
        return templateText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.template_text
     *
     * @param templateText the value for rf_msg_template.template_text
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.status
     *
     * @return the value of rf_msg_template.status
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.status
     *
     * @param status the value for rf_msg_template.status
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.creater
     *
     * @return the value of rf_msg_template.creater
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.creater
     *
     * @param creater the value for rf_msg_template.creater
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.create_time
     *
     * @return the value of rf_msg_template.create_time
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.create_time
     *
     * @param createTime the value for rf_msg_template.create_time
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.modifier
     *
     * @return the value of rf_msg_template.modifier
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.modifier
     *
     * @param modifier the value for rf_msg_template.modifier
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.modify_time
     *
     * @return the value of rf_msg_template.modify_time
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.modify_time
     *
     * @param modifyTime the value for rf_msg_template.modify_time
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.reorder
     *
     * @return the value of rf_msg_template.reorder
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.reorder
     *
     * @param reorder the value for rf_msg_template.reorder
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_msg_template.enable_status
     *
     * @return the value of rf_msg_template.enable_status
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_msg_template.enable_status
     *
     * @param enableStatus the value for rf_msg_template.enable_status
     *
     * @mbggenerated Fri Jun 09 15:32:55 CST 2017
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}