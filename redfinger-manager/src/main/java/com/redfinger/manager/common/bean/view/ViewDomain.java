package com.redfinger.manager.common.bean.view;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="views")
public class ViewDomain {
	
	private List<View> view;

	public List<View> getView() {
		return view;
	}

	public void setView(List<View> view) {
		this.view = view;
	}
	
	
}
