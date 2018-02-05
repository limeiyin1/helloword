package com.redfinger.manager.modules.survey.base;

import java.util.Date;
import java.util.List;

import com.redfinger.manager.common.domain.RfSurvey;

public class SurveyPO extends RfSurvey{
    private static final long serialVersionUID = 1L;
    
    private Integer surveyId;
    
    private String surveyName;
    
    private String surveyContent;
    
    private String enableStatus;
    
    private String status;
    
    private String creater;
    
    private Date createTime;
    
    private String modifier;
    
    private Date modifyTime;
    
    private Integer reorder;
    
    private String remark;

	private RfSurvey rfSurvey; // 问卷
	private List<ProblemPO> list_problemVO; //问题VO
	
	private Integer listSize;
	


	public SurveyPO() {
		super();
	}
	public SurveyPO(RfSurvey rfSurvey) {
		super();
		this.rfSurvey = rfSurvey;
		this.surveyId = rfSurvey.getSurveyId();
		this.surveyName = rfSurvey.getSurveyName();
		this.surveyContent = rfSurvey.getSurveyContent();
		this.enableStatus = rfSurvey.getEnableStatus();
		this.status = rfSurvey.getStatus();
		this.creater = rfSurvey.getCreater();
		this.createTime = rfSurvey.getCreateTime();
		this.modifier = rfSurvey.getModifier();
		this.modifyTime = rfSurvey.getModifyTime();
		this.reorder = rfSurvey.getReorder();
		this.remark = rfSurvey.getRemark();
	}
	public Integer getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}
	public String getSurveyName() {
		return surveyName;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	public String getSurveyContent() {
		return surveyContent;
	}
	public void setSurveyContent(String surveyContent) {
		this.surveyContent = surveyContent;
	}
	public String getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getReorder() {
		return reorder;
	}
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public RfSurvey getRfSurvey() {
		return rfSurvey;
	}
	public void setRfSurvey(RfSurvey rfSurvey) {
		this.rfSurvey = rfSurvey;
	}
	public List<ProblemPO> getList_problemVO() {
		return list_problemVO;
	}
	public void setList_problemVO(List<ProblemPO> list_problemVO) {
		this.list_problemVO = list_problemVO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getListSize() {
		return listSize;
	}
	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

}
