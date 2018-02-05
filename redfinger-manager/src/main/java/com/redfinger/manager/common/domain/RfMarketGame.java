package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfMarketGame extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.relation_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_RELATION_ID = "relation_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.relation_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private Integer relationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.market_type
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_MARKET_TYPE = "market_type";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.market_type
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private String marketType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.game_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_GAME_ID = "game_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.game_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private Integer gameId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.enable_status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_ENABLE_STATUS = "enable_status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.enable_status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private String enableStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.creater
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.creater
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.create_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.create_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.modifier
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.modifier
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.modify_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_MODIFY_TIME = "modify_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.modify_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.reorder
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.reorder
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.remark
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_market_game.remark
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_market_game
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.relation_id
     *
     * @return the value of rf_market_game.relation_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.relation_id
     *
     * @param relationId the value for rf_market_game.relation_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.market_type
     *
     * @return the value of rf_market_game.market_type
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public String getMarketType() {
        return marketType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.market_type
     *
     * @param marketType the value for rf_market_game.market_type
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setMarketType(String marketType) {
        this.marketType = marketType == null ? null : marketType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.game_id
     *
     * @return the value of rf_market_game.game_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.game_id
     *
     * @param gameId the value for rf_market_game.game_id
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.status
     *
     * @return the value of rf_market_game.status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.status
     *
     * @param status the value for rf_market_game.status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.enable_status
     *
     * @return the value of rf_market_game.enable_status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public String getEnableStatus() {
        return enableStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.enable_status
     *
     * @param enableStatus the value for rf_market_game.enable_status
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.creater
     *
     * @return the value of rf_market_game.creater
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.creater
     *
     * @param creater the value for rf_market_game.creater
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.create_time
     *
     * @return the value of rf_market_game.create_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.create_time
     *
     * @param createTime the value for rf_market_game.create_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.modifier
     *
     * @return the value of rf_market_game.modifier
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.modifier
     *
     * @param modifier the value for rf_market_game.modifier
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.modify_time
     *
     * @return the value of rf_market_game.modify_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.modify_time
     *
     * @param modifyTime the value for rf_market_game.modify_time
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.reorder
     *
     * @return the value of rf_market_game.reorder
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.reorder
     *
     * @param reorder the value for rf_market_game.reorder
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_market_game.remark
     *
     * @return the value of rf_market_game.remark
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_market_game.remark
     *
     * @param remark the value for rf_market_game.remark
     *
     * @mbggenerated Mon Jun 12 16:19:48 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}