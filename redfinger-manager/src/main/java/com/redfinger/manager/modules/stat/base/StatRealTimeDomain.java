package com.redfinger.manager.modules.stat.base;

import java.util.Date;

public class StatRealTimeDomain {

	private String name;
	private Integer id;
	private Integer online;
	private Integer offline;
	private Date time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public Integer getOffline() {
		return offline;
	}
	public void setOffline(Integer offline) {
		this.offline = offline;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}


}
