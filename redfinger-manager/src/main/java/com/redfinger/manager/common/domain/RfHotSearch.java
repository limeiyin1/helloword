package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfHotSearch extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.id
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_ID = "id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.id
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.search_name
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_SEARCH_NAME = "search_name";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.search_name
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private String searchName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.search_count
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_SEARCH_COUNT = "search_count";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.search_count
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private Integer searchCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.enable_status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.enable_status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.reorder
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.reorder
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.creater
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.creater
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.create_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.create_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.modifier
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.modifier
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.modify_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.modify_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.remark
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_hot_search.remark
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_hot_search
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.id
     *
     * @return the value of rf_hot_search.id
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.id
     *
     * @param id the value for rf_hot_search.id
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.search_name
     *
     * @return the value of rf_hot_search.search_name
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.search_name
     *
     * @param searchName the value for rf_hot_search.search_name
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName == null ? null : searchName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.search_count
     *
     * @return the value of rf_hot_search.search_count
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public Integer getSearchCount() {
        return searchCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.search_count
     *
     * @param searchCount the value for rf_hot_search.search_count
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.status
     *
     * @return the value of rf_hot_search.status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.status
     *
     * @param status the value for rf_hot_search.status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.enable_status
     *
     * @return the value of rf_hot_search.enable_status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.enable_status
     *
     * @param enableStatus the value for rf_hot_search.enable_status
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.reorder
     *
     * @return the value of rf_hot_search.reorder
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.reorder
     *
     * @param reorder the value for rf_hot_search.reorder
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.creater
     *
     * @return the value of rf_hot_search.creater
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.creater
     *
     * @param creater the value for rf_hot_search.creater
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.create_time
     *
     * @return the value of rf_hot_search.create_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.create_time
     *
     * @param createTime the value for rf_hot_search.create_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.modifier
     *
     * @return the value of rf_hot_search.modifier
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.modifier
     *
     * @param modifier the value for rf_hot_search.modifier
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.modify_time
     *
     * @return the value of rf_hot_search.modify_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.modify_time
     *
     * @param modifyTime the value for rf_hot_search.modify_time
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_hot_search.remark
     *
     * @return the value of rf_hot_search.remark
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_hot_search.remark
     *
     * @param remark the value for rf_hot_search.remark
     *
     * @mbggenerated Thu Feb 16 16:36:58 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}