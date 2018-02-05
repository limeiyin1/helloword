package com.redfinger.manager.common.bean.view;

import java.util.List;

public class View {

	private String name;
	private String table;
	private String include;
	private String exclude;
	private List<Join> join;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<Join> getJoin() {
		return join;
	}

	public void setJoin(List<Join> join) {
		this.join = join;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

}
