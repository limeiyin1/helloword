package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfDevice extends BaseDomain implements Serializable {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_ID = "device_id";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private Integer deviceId;
	
	private Integer idcId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_manage_control_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_MANAGE_CONTROL_ID = "device_manage_control_id";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_manage_control_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private Integer deviceManageControlId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_name
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_NAME = "device_name";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_name
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String deviceName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_code
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_CODE = "device_code";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_code
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String deviceCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_ip
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_IP = "device_ip";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_ip
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String deviceIp;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_STATUS = "device_status";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String deviceStatus;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_STATUS = "status";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String status;
	
	private String enableStatus;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.creater
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_CREATER = "creater";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.creater
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String creater;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.create_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_CREATE_TIME = "create_time";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.create_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.modifier
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_MODIFIER = "modifier";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.modifier
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String modifier;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.modify_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_MODIFY_TIME = "modify_time";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.modify_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private Date modifyTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.reorder
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_REORDER = "reorder";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.reorder
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private Integer reorder;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.remark
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_REMARK = "remark";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.remark
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String remark;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_type
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_TYPE = "device_type";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_type
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String deviceType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_source
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public static final String FD_DEVICE_SOURCE = "device_source";

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rf_device.device_source
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private String deviceSource;
	
	public static final String FD_DEVICE_USE = "device_use";
	private String deviceUse;
	
	//TODO
	public static final String FD_ROM_VERSION = "rom_version";
	private String romVersion;

	public String getRomVersion() {
		return romVersion;
	}

	public void setRomVersion(String romVersion) {
		this.romVersion = romVersion == null ? null : romVersion.trim();
	}
	
	public static final String FD_RAM_SIZE = "ram_size";
	private Integer ramSize;

	public Integer getRamSize() {
		return ramSize;
	}

	public void setRamSize(Integer ramSize) {
		this.ramSize = ramSize;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rf_device
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_id
	 * @return  the value of rf_device.device_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public Integer getDeviceId() {
		return deviceId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_id
	 * @param deviceId  the value for rf_device.device_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_manage_control_id
	 * @return  the value of rf_device.device_manage_control_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public Integer getDeviceManageControlId() {
		return deviceManageControlId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_manage_control_id
	 * @param deviceManageControlId  the value for rf_device.device_manage_control_id
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceManageControlId(Integer deviceManageControlId) {
		this.deviceManageControlId = deviceManageControlId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_name
	 * @return  the value of rf_device.device_name
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_name
	 * @param deviceName  the value for rf_device.device_name
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName == null ? null : deviceName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_code
	 * @return  the value of rf_device.device_code
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getDeviceCode() {
		return deviceCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_code
	 * @param deviceCode  the value for rf_device.device_code
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode == null ? null : deviceCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_ip
	 * @return  the value of rf_device.device_ip
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getDeviceIp() {
		return deviceIp;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_ip
	 * @param deviceIp  the value for rf_device.device_ip
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp == null ? null : deviceIp.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_status
	 * @return  the value of rf_device.device_status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_status
	 * @param deviceStatus  the value for rf_device.device_status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus == null ? null : deviceStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.status
	 * @return  the value of rf_device.status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.status
	 * @param status  the value for rf_device.status
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.creater
	 * @return  the value of rf_device.creater
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.creater
	 * @param creater  the value for rf_device.creater
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.create_time
	 * @return  the value of rf_device.create_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.create_time
	 * @param createTime  the value for rf_device.create_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.modifier
	 * @return  the value of rf_device.modifier
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.modifier
	 * @param modifier  the value for rf_device.modifier
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.modify_time
	 * @return  the value of rf_device.modify_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.modify_time
	 * @param modifyTime  the value for rf_device.modify_time
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.reorder
	 * @return  the value of rf_device.reorder
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public Integer getReorder() {
		return reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.reorder
	 * @param reorder  the value for rf_device.reorder
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.remark
	 * @return  the value of rf_device.remark
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.remark
	 * @param remark  the value for rf_device.remark
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_type
	 * @return  the value of rf_device.device_type
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_type
	 * @param deviceType  the value for rf_device.device_type
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType == null ? null : deviceType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rf_device.device_source
	 * @return  the value of rf_device.device_source
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public String getDeviceSource() {
		return deviceSource;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rf_device.device_source
	 * @param deviceSource  the value for rf_device.device_source
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	public void setDeviceSource(String deviceSource) {
		this.deviceSource = deviceSource == null ? null : deviceSource.trim();
	}

	public Integer getIdcId() {
		return idcId;
	}

	public void setIdcId(Integer idcId) {
		this.idcId = idcId;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	
	public String getDeviceUse() {
	    return deviceUse;
	}
	
	public void setDeviceUse(String deviceUse) {
	    this.deviceUse = deviceUse == null ? null : deviceUse.trim();
	}
}