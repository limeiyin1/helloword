package com.redfinger.manager.common.base;

public class SysPermission implements Comparable<SysPermission> {
	private String code;
	private String parentCode;
	private String name;
	private String url;
	private Integer grade ;		//1,导航;2,菜单;3,按钮;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Override
	public int compareTo(SysPermission o) {
		return this.getGrade().compareTo(o.getGrade());
	}
	

}
