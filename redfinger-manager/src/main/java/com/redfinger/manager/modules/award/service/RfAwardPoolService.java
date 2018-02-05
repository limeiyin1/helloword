package com.redfinger.manager.modules.award.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfAwardBatch;
import com.redfinger.manager.common.domain.RfAwardPool;
import com.redfinger.manager.common.domain.RfAwardPoolExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfAwardPoolMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.award.pogo.POAwardPool;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class RfAwardPoolService extends BaseService<RfAwardPool, RfAwardPoolExample, RfAwardPoolMapper> {
	@Autowired
	RfAwardPoolMapper mapper;
	@Autowired
	RfAwardBatchService awardBatchService;

	public Integer insetBatch( HttpServletRequest request,List<RfAwardPool> list) {
		String userId = SessionUtils.getCurrentUserId(request);
		Date time = new Date();
		for (RfAwardPool bean : list) {
			bean.setCreater(userId);
			bean.setCreateTime(time);
			bean.setStatus("1");
			bean.setEnableStatus("1");
		}
		int len = mapper.insertBatch(list);
		return len;
	}
	
	public void saveBatchAwardPool(HttpServletRequest request, POAwardPool bean, RfAwardBatch award) throws Exception{
		Integer awardId = award.getAwardId();
		Integer winningRate = award.getWinningRate();
		String giveStatus = bean.getGiveStatus();
		String receiveStatus = bean.getReceiveStatus();
		String awardPoolType = award.getAwardType();
		List<RfAwardPool> list = new ArrayList<RfAwardPool>();
		int count = 0;
		for (int i = 1; i <= bean.getAwardNumber(); i++) {
			RfAwardPool pool = new RfAwardPool();
			pool.setAwardId(awardId);
			pool.setAwardMargin(1);
			pool.setAwardTotal(1);
			pool.setWinningRate(winningRate);
			pool.setGiveStatus(giveStatus);
			pool.setReceiveStatus(receiveStatus);
			pool.setAwardCode(StringUtils.getRandomString(8));
			pool.setAwardPoolType(awardPoolType);
			list.add(pool);
			
			count++;
			if(count == 500){
				insetBatch(request, list);
				count = 0;
				list.clear();
			} else if(i == bean.getAwardNumber() && list.size()>0){
				insetBatch(request, list);
			}
			
		}

		// 更新奖品余量
		RfAwardBatch upAward = new RfAwardBatch();
		upAward.setAwardId(awardId);
		upAward.setAwardMargin(award.getAwardMargin() - bean.getAwardNumber());
		awardBatchService.update(request, upAward);
		
	}

}
