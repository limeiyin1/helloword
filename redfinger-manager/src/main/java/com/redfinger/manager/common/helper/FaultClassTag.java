package com.redfinger.manager.common.helper;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.redfinger.manager.modules.base.service.ClassService;

public class FaultClassTag extends SimpleTagSupport {

	private String type;
	private Integer classId;
	private String sepereate = " - ";

	@Override
	public void doTag() throws JspException, IOException {
		
		
		ClassService classService = SpringContextHolder.getBean(ClassService.class);
		
		String className = classService.getFullClass(DictHelper.getDictValueByKey(type), classId, sepereate);
		
		this.getJspContext().getOut().write(className);
		

	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getSepereate() {
		return sepereate;
	}

	public void setSepereate(String sepereate) {
		this.sepereate = sepereate;
	}

}
