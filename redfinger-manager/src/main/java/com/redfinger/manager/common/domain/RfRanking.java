package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfRanking extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.ranking_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_RANKING_ID = "ranking_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.ranking_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Integer rankingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.user_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_USER_ID = "user_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.user_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.ranking_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_RANKING_TYPE = "ranking_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.ranking_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private String rankingType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_RANKING = "ranking";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Integer ranking;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.num
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_NUM = "num";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.num
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.start_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_START_TIME = "start_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.start_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.end_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_END_TIME = "end_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.end_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.stat_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_STAT_TYPE = "stat_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.stat_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private String statType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.stat_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_STAT_TIME = "stat_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.stat_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Date statTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.enable_status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.enable_status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.creater
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.creater
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.create_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.create_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.modifier
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.modifier
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.modify_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.modify_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.reorder
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.reorder
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.remark
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_ranking.remark
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.ranking_id
     *
     * @return the value of rf_ranking.ranking_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Integer getRankingId() {
        return rankingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.ranking_id
     *
     * @param rankingId the value for rf_ranking.ranking_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setRankingId(Integer rankingId) {
        this.rankingId = rankingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.user_id
     *
     * @return the value of rf_ranking.user_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.user_id
     *
     * @param userId the value for rf_ranking.user_id
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.ranking_type
     *
     * @return the value of rf_ranking.ranking_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public String getRankingType() {
        return rankingType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.ranking_type
     *
     * @param rankingType the value for rf_ranking.ranking_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setRankingType(String rankingType) {
        this.rankingType = rankingType == null ? null : rankingType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.ranking
     *
     * @return the value of rf_ranking.ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Integer getRanking() {
        return ranking;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.ranking
     *
     * @param ranking the value for rf_ranking.ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.num
     *
     * @return the value of rf_ranking.num
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.num
     *
     * @param num the value for rf_ranking.num
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.start_time
     *
     * @return the value of rf_ranking.start_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.start_time
     *
     * @param startTime the value for rf_ranking.start_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.end_time
     *
     * @return the value of rf_ranking.end_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.end_time
     *
     * @param endTime the value for rf_ranking.end_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.stat_type
     *
     * @return the value of rf_ranking.stat_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public String getStatType() {
        return statType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.stat_type
     *
     * @param statType the value for rf_ranking.stat_type
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setStatType(String statType) {
        this.statType = statType == null ? null : statType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.stat_time
     *
     * @return the value of rf_ranking.stat_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Date getStatTime() {
        return statTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.stat_time
     *
     * @param statTime the value for rf_ranking.stat_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.status
     *
     * @return the value of rf_ranking.status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.status
     *
     * @param status the value for rf_ranking.status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.enable_status
     *
     * @return the value of rf_ranking.enable_status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.enable_status
     *
     * @param enableStatus the value for rf_ranking.enable_status
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.creater
     *
     * @return the value of rf_ranking.creater
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.creater
     *
     * @param creater the value for rf_ranking.creater
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.create_time
     *
     * @return the value of rf_ranking.create_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.create_time
     *
     * @param createTime the value for rf_ranking.create_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.modifier
     *
     * @return the value of rf_ranking.modifier
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.modifier
     *
     * @param modifier the value for rf_ranking.modifier
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.modify_time
     *
     * @return the value of rf_ranking.modify_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.modify_time
     *
     * @param modifyTime the value for rf_ranking.modify_time
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.reorder
     *
     * @return the value of rf_ranking.reorder
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.reorder
     *
     * @param reorder the value for rf_ranking.reorder
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_ranking.remark
     *
     * @return the value of rf_ranking.remark
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_ranking.remark
     *
     * @param remark the value for rf_ranking.remark
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}