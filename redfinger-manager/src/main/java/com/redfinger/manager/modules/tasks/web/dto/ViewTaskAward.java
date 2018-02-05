package com.redfinger.manager.modules.tasks.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.redfinger.manager.common.domain.TkTaskAward;

public class ViewTaskAward {

	List<TkTaskAward> taskAwardList = new ArrayList<TkTaskAward>();

	public List<TkTaskAward> getTaskAwardList() {
		return taskAwardList;
	}

	public void setTaskAwardList(List<TkTaskAward> taskAwardList) {
		this.taskAwardList = taskAwardList;
	}
}
