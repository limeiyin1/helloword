package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfGoodsInfo extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_INFO_ID = "info_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private Integer infoId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_name
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_INFO_NAME = "info_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_name
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String infoName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_content
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_INFO_CONTENT = "info_content";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_content
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String infoContent;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_icon
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_INFO_ICON = "info_icon";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_icon
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String infoIcon;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_color
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_INFO_COLOR = "info_color";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.info_color
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String infoColor;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.enable_status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.enable_status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.creater
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.creater
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.create_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.create_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.modifier
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.modifier
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.modify_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.modify_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.reorder
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_REORDER = "reorder";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.reorder
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private Integer reorder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.remark
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.remark
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.goods_type_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_GOODS_TYPE_ID = "goods_type_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.goods_type_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private Integer goodsTypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_goods_info
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public static final String FD_FREE_USE_LIMIT = "free_use_limit";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_goods_info.goods_type_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private Integer freeUseLimit;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_goods_info
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.info_id
	 * @return  the value of rf_goods_info.info_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public Integer getInfoId() {
		return infoId;
	}

	public Integer getFreeUseLimit() {
		return freeUseLimit;
	}

	public void setFreeUseLimit(Integer freeUseLimit) {
		this.freeUseLimit = freeUseLimit;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.info_id
	 * @param infoId  the value for rf_goods_info.info_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.info_name
	 * @return  the value of rf_goods_info.info_name
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getInfoName() {
		return infoName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.info_name
	 * @param infoName  the value for rf_goods_info.info_name
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setInfoName(String infoName) {
		this.infoName = infoName == null ? null : infoName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.info_content
	 * @return  the value of rf_goods_info.info_content
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getInfoContent() {
		return infoContent;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.info_content
	 * @param infoContent  the value for rf_goods_info.info_content
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent == null ? null : infoContent.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.info_icon
	 * @return  the value of rf_goods_info.info_icon
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getInfoIcon() {
		return infoIcon;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.info_icon
	 * @param infoIcon  the value for rf_goods_info.info_icon
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setInfoIcon(String infoIcon) {
		this.infoIcon = infoIcon == null ? null : infoIcon.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.info_color
	 * @return  the value of rf_goods_info.info_color
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getInfoColor() {
		return infoColor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.info_color
	 * @param infoColor  the value for rf_goods_info.info_color
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setInfoColor(String infoColor) {
		this.infoColor = infoColor == null ? null : infoColor.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.status
	 * @return  the value of rf_goods_info.status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.status
	 * @param status  the value for rf_goods_info.status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.enable_status
	 * @return  the value of rf_goods_info.enable_status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.enable_status
	 * @param enableStatus  the value for rf_goods_info.enable_status
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.creater
	 * @return  the value of rf_goods_info.creater
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.creater
	 * @param creater  the value for rf_goods_info.creater
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.create_time
	 * @return  the value of rf_goods_info.create_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.create_time
	 * @param createTime  the value for rf_goods_info.create_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.modifier
	 * @return  the value of rf_goods_info.modifier
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.modifier
	 * @param modifier  the value for rf_goods_info.modifier
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.modify_time
	 * @return  the value of rf_goods_info.modify_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.modify_time
	 * @param modifyTime  the value for rf_goods_info.modify_time
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.reorder
	 * @return  the value of rf_goods_info.reorder
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.reorder
	 * @param reorder  the value for rf_goods_info.reorder
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.remark
	 * @return  the value of rf_goods_info.remark
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.remark
	 * @param remark  the value for rf_goods_info.remark
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_goods_info.goods_type_id
	 * @return  the value of rf_goods_info.goods_type_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public Integer getGoodsTypeId() {
		return goodsTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_goods_info.goods_type_id
	 * @param goodsTypeId  the value for rf_goods_info.goods_type_id
	 * @mbggenerated  Fri Dec 30 10:40:10 CST 2016
	 */
	public void setGoodsTypeId(Integer goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
}