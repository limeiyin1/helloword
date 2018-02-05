package com.redfinger.manager.common.base;

import java.util.List;

public class TreeDomain implements java.io.Serializable{

	private static final long serialVersionUID = -8675557907018213899L;
	private String id;
	private String text;
	private String iconCls;
	private String attributes;
	private boolean checked;
	private List<TreeDomain> children;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<TreeDomain> getChildren() {
		return children;
	}
	public void setChildren(List<TreeDomain> children) {
		this.children = children;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	
	
}
