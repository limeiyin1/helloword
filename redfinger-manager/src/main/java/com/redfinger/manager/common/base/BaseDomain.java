package com.redfinger.manager.common.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Maps;

@JsonInclude(Include.NON_NULL)
public class BaseDomain implements Serializable {

	private static final long serialVersionUID = -3068228184404269814L;
	@JsonIgnore
	private Integer page;		//页码
	@JsonIgnore
	private Integer rows;		//每条条数
	@JsonIgnore
	private String ids;			//ID的字符串集合，逗号分隔
	private Map<String, Object> map = Maps.newHashMap();	//额外属性
	public String sort;			//排序字段
	public String order;		//排序方式desc,asc
	public boolean checked;		//是否被选中，树形表用
	public List<Object> children;
	public String beginTimeStr;
	public String endTimeStr;

	public Integer getPage() {
		if (this.page == null || this.page <= 1) {
			this.page = 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		if (this.rows == null || this.rows <= 1) {
			this.rows = 20;
		}
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<Object> getChildren() {
		return children;
	}

	public void setChildren(List<Object> children) {
		this.children = children;
	}

	public String getBeginTimeStr() {
		return this.beginTimeStr = beginTimeStr == null ? null : beginTimeStr.trim();
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return this.endTimeStr = endTimeStr == null ? null : endTimeStr.trim();
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
	

	
	
	

}
