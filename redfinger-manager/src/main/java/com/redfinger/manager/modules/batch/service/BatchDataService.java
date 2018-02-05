package com.redfinger.manager.modules.batch.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TkBatchData;
import com.redfinger.manager.common.domain.TkBatchDataExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TkBatchDataMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="dataId")
public class BatchDataService extends BaseService<TkBatchData, TkBatchDataExample, TkBatchDataMapper> {

	@Autowired
	private TkBatchDataMapper dataMapper;
	
	public int deleteByBatchId(HttpServletRequest request, Integer batchId) throws Exception{
		TkBatchDataExample dataExample = new TkBatchDataExample();
		dataExample.createCriteria().andBatchIdEqualTo(batchId);
		return dataMapper.deleteByExample(dataExample);
	}
	
	
	public TkBatchData selectByBatchIdAndDataCode(Integer batchId,String dataCode) {
		TkBatchDataExample dataExample = new TkBatchDataExample();
		dataExample.createCriteria().andBatchIdEqualTo(batchId).andDataCodeEqualTo(dataCode);
		return dataMapper.selectOneByExample(dataExample);
	}
	
	public TkBatchData selectByPk(Integer dataId) {
		return dataMapper.selectByPrimaryKey(dataId);
	}
}
