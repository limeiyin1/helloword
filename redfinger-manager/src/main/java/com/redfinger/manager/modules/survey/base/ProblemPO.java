package com.redfinger.manager.modules.survey.base;

import java.util.Date;
import java.util.List;

import com.redfinger.manager.common.domain.RfProblem;
import com.redfinger.manager.common.domain.RfProblemAnswer;
public class ProblemPO extends RfProblem{

	private static final long serialVersionUID = 1L;

    private Integer problemId;

    private String problemContent;

    private String problemType;

    private String enableStatus;

    private String status;

    private String creater;

    private Date createTime;

    private String modifier;

    private Date modifyTime;

    private Integer reorder;

    private String remark;
    
	private String isMust;


	private RfProblem rfProblem;// 问题
	private List<RfProblemAnswer> list_rfProblemAnswer ; // 所有答案
	
	private Integer listSize;
	
	

	public ProblemPO() {
		super();
	}
	public ProblemPO( RfProblem rfProblem) {
		super();
		this.rfProblem = rfProblem;
		this.problemId = rfProblem.getProblemId();
		this.problemContent = rfProblem.getProblemContent();
		this.problemType = rfProblem.getProblemType();
		this.enableStatus = rfProblem.getEnableStatus();
		this.status = rfProblem.getStatus();
		this.creater = rfProblem.getCreater();
		this.createTime = rfProblem.getCreateTime();
		this.modifier = rfProblem.getModifier();
		this.modifyTime = rfProblem.getModifyTime();
		this.reorder = rfProblem.getReorder();
		this.remark = rfProblem.getRemark();
		this.isMust = rfProblem.getIsMust();
	}
	public Integer getProblemId() {
		return problemId;
	}
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}
	public String getProblemContent() {
		return problemContent;
	}
	public void setProblemContent(String problemContent) {
		this.problemContent = problemContent;
	}
	public String getProblemType() {
		return problemType;
	}
	public void setProblemType(String problemType) {
		this.problemType = problemType;
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
	public RfProblem getRfProblem() {
		return rfProblem;
	}
	public void setRfProblem(RfProblem rfProblem) {
		this.rfProblem = rfProblem;
	}
	public List<RfProblemAnswer> getList_rfProblemAnswer() {
		return list_rfProblemAnswer;
	}
	public void setList_rfProblemAnswer(List<RfProblemAnswer> list_rfProblemAnswer) {
		this.list_rfProblemAnswer = list_rfProblemAnswer;
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
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	

}
