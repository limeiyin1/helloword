package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfSmsResultInfo extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.result_info_id
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_RESULT_INFO_ID = "result_info_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.result_info_id
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private Integer resultInfoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.result_code
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_RESULT_CODE = "result_code";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.result_code
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String resultCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.result_code_detail
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_RESULT_CODE_DETAIL = "result_code_detail";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.result_code_detail
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String resultCodeDetail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.sms_result_type
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_SMS_RESULT_TYPE = "sms_result_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.sms_result_type
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String smsResultType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.reorder
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.reorder
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.enable_status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.enable_status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.creater
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.creater
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.create_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.create_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.modifier
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.modifier
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.modify_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.modify_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.remark
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_sms_result_info.remark
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_sms_result_info
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.result_info_id
     *
     * @return the value of rf_sms_result_info.result_info_id
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public Integer getResultInfoId() {
        return resultInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.result_info_id
     *
     * @param resultInfoId the value for rf_sms_result_info.result_info_id
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setResultInfoId(Integer resultInfoId) {
        this.resultInfoId = resultInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.result_code
     *
     * @return the value of rf_sms_result_info.result_code
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.result_code
     *
     * @param resultCode the value for rf_sms_result_info.result_code
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.result_code_detail
     *
     * @return the value of rf_sms_result_info.result_code_detail
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getResultCodeDetail() {
        return resultCodeDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.result_code_detail
     *
     * @param resultCodeDetail the value for rf_sms_result_info.result_code_detail
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setResultCodeDetail(String resultCodeDetail) {
        this.resultCodeDetail = resultCodeDetail == null ? null : resultCodeDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.sms_result_type
     *
     * @return the value of rf_sms_result_info.sms_result_type
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getSmsResultType() {
        return smsResultType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.sms_result_type
     *
     * @param smsResultType the value for rf_sms_result_info.sms_result_type
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setSmsResultType(String smsResultType) {
        this.smsResultType = smsResultType == null ? null : smsResultType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.reorder
     *
     * @return the value of rf_sms_result_info.reorder
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.reorder
     *
     * @param reorder the value for rf_sms_result_info.reorder
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.status
     *
     * @return the value of rf_sms_result_info.status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.status
     *
     * @param status the value for rf_sms_result_info.status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.enable_status
     *
     * @return the value of rf_sms_result_info.enable_status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.enable_status
     *
     * @param enableStatus the value for rf_sms_result_info.enable_status
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.creater
     *
     * @return the value of rf_sms_result_info.creater
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.creater
     *
     * @param creater the value for rf_sms_result_info.creater
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.create_time
     *
     * @return the value of rf_sms_result_info.create_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.create_time
     *
     * @param createTime the value for rf_sms_result_info.create_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.modifier
     *
     * @return the value of rf_sms_result_info.modifier
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.modifier
     *
     * @param modifier the value for rf_sms_result_info.modifier
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.modify_time
     *
     * @return the value of rf_sms_result_info.modify_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.modify_time
     *
     * @param modifyTime the value for rf_sms_result_info.modify_time
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_sms_result_info.remark
     *
     * @return the value of rf_sms_result_info.remark
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_sms_result_info.remark
     *
     * @param remark the value for rf_sms_result_info.remark
     *
     * @mbggenerated Mon Feb 27 19:03:27 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}