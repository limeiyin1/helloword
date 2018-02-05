package com.redfinger.manager.modules.batch.dto;

import java.util.ArrayList;
import java.util.List;

import com.redfinger.manager.common.domain.TkBatchData;

public class ViewBatchData {

	private List<TkBatchData> batchDataList = new ArrayList<TkBatchData>();

	public List<TkBatchData> getBatchDataList() {
		return batchDataList;
	}

	public void setBatchDataList(List<TkBatchData> batchDataList) {
		this.batchDataList = batchDataList;
	}

}
