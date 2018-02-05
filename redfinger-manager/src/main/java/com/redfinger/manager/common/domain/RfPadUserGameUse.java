package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfPadUserGameUse extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.use_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_USE_ID = "use_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.use_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer useId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_ID = "pad_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer padId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_GAME_ID = "game_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer gameId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.use_count
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_USE_COUNT = "use_count";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.use_count
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer useCount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_USER_ID = "user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_USER_ID_TMP = "user_id_tmp";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_pad_user_game_use.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String userIdTmp;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_pad_user_game_use
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.use_id
	 * @return  the value of rf_pad_user_game_use.use_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getUseId() {
		return useId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.use_id
	 * @param useId  the value for rf_pad_user_game_use.use_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setUseId(Integer useId) {
		this.useId = useId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.pad_id
	 * @return  the value of rf_pad_user_game_use.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getPadId() {
		return padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.pad_id
	 * @param padId  the value for rf_pad_user_game_use.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadId(Integer padId) {
		this.padId = padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.game_id
	 * @return  the value of rf_pad_user_game_use.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getGameId() {
		return gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.game_id
	 * @param gameId  the value for rf_pad_user_game_use.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.use_count
	 * @return  the value of rf_pad_user_game_use.use_count
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getUseCount() {
		return useCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.use_count
	 * @param useCount  the value for rf_pad_user_game_use.use_count
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.status
	 * @return  the value of rf_pad_user_game_use.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.status
	 * @param status  the value for rf_pad_user_game_use.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.creater
	 * @return  the value of rf_pad_user_game_use.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.creater
	 * @param creater  the value for rf_pad_user_game_use.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.create_time
	 * @return  the value of rf_pad_user_game_use.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.create_time
	 * @param createTime  the value for rf_pad_user_game_use.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.modifier
	 * @return  the value of rf_pad_user_game_use.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.modifier
	 * @param modifier  the value for rf_pad_user_game_use.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.modify_time
	 * @return  the value of rf_pad_user_game_use.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.modify_time
	 * @param modifyTime  the value for rf_pad_user_game_use.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.reorder
	 * @return  the value of rf_pad_user_game_use.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.reorder
	 * @param reorder  the value for rf_pad_user_game_use.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.remark
	 * @return  the value of rf_pad_user_game_use.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.remark
	 * @param remark  the value for rf_pad_user_game_use.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.enable_status
	 * @return  the value of rf_pad_user_game_use.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.enable_status
	 * @param enableStatus  the value for rf_pad_user_game_use.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.user_id
	 * @return  the value of rf_pad_user_game_use.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.user_id
	 * @param userId  the value for rf_pad_user_game_use.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_pad_user_game_use.user_id_tmp
	 * @return  the value of rf_pad_user_game_use.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getUserIdTmp() {
		return userIdTmp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_pad_user_game_use.user_id_tmp
	 * @param userIdTmp  the value for rf_pad_user_game_use.user_id_tmp
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setUserIdTmp(String userIdTmp) {
		this.userIdTmp = userIdTmp == null ? null : userIdTmp.trim();
	}
}