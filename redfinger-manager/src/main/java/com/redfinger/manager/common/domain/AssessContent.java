package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;

public class AssessContent extends BaseDomain implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_ID = "id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.assess_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_ASSESS_POINT = "assess_point";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.assess_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String assessPoint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.other_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_OTHER_POINT = "other_point";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.other_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String otherPoint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.target_admin
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_TARGET_ADMIN = "target_admin";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.target_admin
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String targetAdmin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.project_id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_PROJECT_ID = "project_id";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.project_id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Integer projectId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.need_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_NEED_ASSESS_NUM = "need_assess_num";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.need_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Integer needAssessNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.aleady_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_ALEADY_ASSESS_NUM = "aleady_assess_num";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.aleady_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private Integer aleadyAssessNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.project_name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public static final String FD_PROJECT_NAME = "project_name";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column assess_content.project_name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private String projectName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table assess_content
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.id
     *
     * @return the value of assess_content.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.id
     *
     * @param id the value for assess_content.id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.assess_point
     *
     * @return the value of assess_content.assess_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getAssessPoint() {
        return assessPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.assess_point
     *
     * @param assessPoint the value for assess_content.assess_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setAssessPoint(String assessPoint) {
        this.assessPoint = assessPoint == null ? null : assessPoint.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.other_point
     *
     * @return the value of assess_content.other_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getOtherPoint() {
        return otherPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.other_point
     *
     * @param otherPoint the value for assess_content.other_point
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setOtherPoint(String otherPoint) {
        this.otherPoint = otherPoint == null ? null : otherPoint.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.target_admin
     *
     * @return the value of assess_content.target_admin
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getTargetAdmin() {
        return targetAdmin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.target_admin
     *
     * @param targetAdmin the value for assess_content.target_admin
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setTargetAdmin(String targetAdmin) {
        this.targetAdmin = targetAdmin == null ? null : targetAdmin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.project_id
     *
     * @return the value of assess_content.project_id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.project_id
     *
     * @param projectId the value for assess_content.project_id
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.need_assess_num
     *
     * @return the value of assess_content.need_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Integer getNeedAssessNum() {
        return needAssessNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.need_assess_num
     *
     * @param needAssessNum the value for assess_content.need_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setNeedAssessNum(Integer needAssessNum) {
        this.needAssessNum = needAssessNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.aleady_assess_num
     *
     * @return the value of assess_content.aleady_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public Integer getAleadyAssessNum() {
        return aleadyAssessNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.aleady_assess_num
     *
     * @param aleadyAssessNum the value for assess_content.aleady_assess_num
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setAleadyAssessNum(Integer aleadyAssessNum) {
        this.aleadyAssessNum = aleadyAssessNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column assess_content.project_name
     *
     * @return the value of assess_content.project_name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column assess_content.project_name
     *
     * @param projectName the value for assess_content.project_name
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }
}