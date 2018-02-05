package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfGmonitorDataList extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.serial_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_SERIAL_ID = "serial_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.serial_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String serialId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_ID = "pad_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer padId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PAD_CODE = "pad_code";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String padCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_USER_ID = "user_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_GAME_ID = "game_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer gameId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_GAME_NAME = "game_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String gameName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.package_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_PACKAGE_NAME = "package_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.package_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String packageName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.game_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_GAME_STATUS = "game_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.game_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String gameStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_gmonitor_data_list.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_gmonitor_data_list
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.serial_id
	 * @return  the value of rf_gmonitor_data_list.serial_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getSerialId() {
		return serialId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.serial_id
	 * @param serialId  the value for rf_gmonitor_data_list.serial_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setSerialId(String serialId) {
		this.serialId = serialId == null ? null : serialId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.pad_id
	 * @return  the value of rf_gmonitor_data_list.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getPadId() {
		return padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.pad_id
	 * @param padId  the value for rf_gmonitor_data_list.pad_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadId(Integer padId) {
		this.padId = padId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.pad_code
	 * @return  the value of rf_gmonitor_data_list.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getPadCode() {
		return padCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.pad_code
	 * @param padCode  the value for rf_gmonitor_data_list.pad_code
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPadCode(String padCode) {
		this.padCode = padCode == null ? null : padCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.user_id
	 * @return  the value of rf_gmonitor_data_list.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.user_id
	 * @param userId  the value for rf_gmonitor_data_list.user_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.game_id
	 * @return  the value of rf_gmonitor_data_list.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getGameId() {
		return gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.game_id
	 * @param gameId  the value for rf_gmonitor_data_list.game_id
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.game_name
	 * @return  the value of rf_gmonitor_data_list.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.game_name
	 * @param gameName  the value for rf_gmonitor_data_list.game_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName == null ? null : gameName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.package_name
	 * @return  the value of rf_gmonitor_data_list.package_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.package_name
	 * @param packageName  the value for rf_gmonitor_data_list.package_name
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName == null ? null : packageName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.game_status
	 * @return  the value of rf_gmonitor_data_list.game_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getGameStatus() {
		return gameStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.game_status
	 * @param gameStatus  the value for rf_gmonitor_data_list.game_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus == null ? null : gameStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.enable_status
	 * @return  the value of rf_gmonitor_data_list.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.enable_status
	 * @param enableStatus  the value for rf_gmonitor_data_list.enable_status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.status
	 * @return  the value of rf_gmonitor_data_list.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.status
	 * @param status  the value for rf_gmonitor_data_list.status
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.creater
	 * @return  the value of rf_gmonitor_data_list.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.creater
	 * @param creater  the value for rf_gmonitor_data_list.creater
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.create_time
	 * @return  the value of rf_gmonitor_data_list.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.create_time
	 * @param createTime  the value for rf_gmonitor_data_list.create_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.modifier
	 * @return  the value of rf_gmonitor_data_list.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.modifier
	 * @param modifier  the value for rf_gmonitor_data_list.modifier
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.modify_time
	 * @return  the value of rf_gmonitor_data_list.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.modify_time
	 * @param modifyTime  the value for rf_gmonitor_data_list.modify_time
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.reorder
	 * @return  the value of rf_gmonitor_data_list.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.reorder
	 * @param reorder  the value for rf_gmonitor_data_list.reorder
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_gmonitor_data_list.remark
	 * @return  the value of rf_gmonitor_data_list.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_gmonitor_data_list.remark
	 * @param remark  the value for rf_gmonitor_data_list.remark
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}