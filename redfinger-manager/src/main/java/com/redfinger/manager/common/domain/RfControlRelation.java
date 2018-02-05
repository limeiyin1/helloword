package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class RfControlRelation extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.relation_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_RELATION_ID = "relation_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.relation_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Integer relationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.user_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_USER_CONTROL_ID = "user_control_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.user_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Integer userControlId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.pad_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_PAD_CONTROL_ID = "pad_control_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.pad_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Integer padControlId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.manage_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_MANAGE_CONTROL_ID = "manage_control_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.manage_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Integer manageControlId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.pad_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_PAD_VIDEO_ID = "pad_video_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.pad_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Integer padVideoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.user_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_USER_VIDEO_ID = "user_video_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.user_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Integer userVideoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.status
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_STATUS = "status";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.status
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.creater
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_CREATER = "creater";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.creater
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.create_time
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_CREATE_TIME = "create_time";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.create_time
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.modifier
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_MODIFIER = "modifier";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.modifier
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.reorder
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_REORDER = "reorder";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.reorder
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private Integer reorder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.remark
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public static final String FD_REMARK = "remark";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rf_control_relation.remark
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.relation_id
     *
     * @return the value of rf_control_relation.relation_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.relation_id
     *
     * @param relationId the value for rf_control_relation.relation_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.user_control_id
     *
     * @return the value of rf_control_relation.user_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Integer getUserControlId() {
        return userControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.user_control_id
     *
     * @param userControlId the value for rf_control_relation.user_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setUserControlId(Integer userControlId) {
        this.userControlId = userControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.pad_control_id
     *
     * @return the value of rf_control_relation.pad_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Integer getPadControlId() {
        return padControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.pad_control_id
     *
     * @param padControlId the value for rf_control_relation.pad_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setPadControlId(Integer padControlId) {
        this.padControlId = padControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.manage_control_id
     *
     * @return the value of rf_control_relation.manage_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Integer getManageControlId() {
        return manageControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.manage_control_id
     *
     * @param manageControlId the value for rf_control_relation.manage_control_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setManageControlId(Integer manageControlId) {
        this.manageControlId = manageControlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.pad_video_id
     *
     * @return the value of rf_control_relation.pad_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Integer getPadVideoId() {
        return padVideoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.pad_video_id
     *
     * @param padVideoId the value for rf_control_relation.pad_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setPadVideoId(Integer padVideoId) {
        this.padVideoId = padVideoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.user_video_id
     *
     * @return the value of rf_control_relation.user_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Integer getUserVideoId() {
        return userVideoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.user_video_id
     *
     * @param userVideoId the value for rf_control_relation.user_video_id
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setUserVideoId(Integer userVideoId) {
        this.userVideoId = userVideoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.status
     *
     * @return the value of rf_control_relation.status
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.status
     *
     * @param status the value for rf_control_relation.status
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.creater
     *
     * @return the value of rf_control_relation.creater
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.creater
     *
     * @param creater the value for rf_control_relation.creater
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.create_time
     *
     * @return the value of rf_control_relation.create_time
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.create_time
     *
     * @param createTime the value for rf_control_relation.create_time
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.modifier
     *
     * @return the value of rf_control_relation.modifier
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.modifier
     *
     * @param modifier the value for rf_control_relation.modifier
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.reorder
     *
     * @return the value of rf_control_relation.reorder
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public Integer getReorder() {
        return reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.reorder
     *
     * @param reorder the value for rf_control_relation.reorder
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rf_control_relation.remark
     *
     * @return the value of rf_control_relation.remark
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rf_control_relation.remark
     *
     * @param remark the value for rf_control_relation.remark
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    
    private String userControlName;
    private String padControlName;
    private String manageControlName;
    private String padVideoName;
    private String userVideoName;
    
    public String getUserControlName() {
		return userControlName;
	}

	public void setUserControlName(String userControlName) {
		this.userControlName = userControlName;
	}

	public String getPadControlName() {
		return padControlName;
	}

	public void setPadControlName(String padControlName) {
		this.padControlName = padControlName;
	}

	public String getManageControlName() {
		return manageControlName;
	}

	public void setManageControlName(String manageControlName) {
		this.manageControlName = manageControlName;
	}

	public String getPadVideoName() {
		return padVideoName;
	}

	public void setPadVideoName(String padVideoName) {
		this.padVideoName = padVideoName;
	}

	public String getUserVideoName() {
		return userVideoName;
	}

	public void setUserVideoName(String userVideoName) {
		this.userVideoName = userVideoName;
	}


	
    
}