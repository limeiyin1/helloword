package com.redfinger.manager.modules.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.MarketGame;
import com.redfinger.manager.common.domain.MarketGameResource;
import com.redfinger.manager.common.domain.MarketGameResourceExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.MarketGameResourceMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketGameResourceService extends BaseService<MarketGameResource, MarketGameResourceExample, MarketGameResourceMapper> {

	public void insert(HttpServletRequest request, MarketGame bean, String resourceIds) throws Exception {
		// 先删除
		List<MarketGameResource> removeList = this.initQuery().andEqualTo("gameId", bean.getId()).findAll();
		for (MarketGameResource rameResource : removeList) {
			this.remove(request, rameResource.getId());
		}
		// 再新增
		if (StringUtils.isNotEmpty(resourceIds)) {
			List<String> ids = Lists.newArrayList(StringUtils.split(resourceIds, ","));
			for (String id : ids) {
				MarketGameResource gameResource = new MarketGameResource();
				gameResource.setGameId(bean.getId());
				gameResource.setResourceId(Integer.parseInt(id));
				this.save(request, gameResource);
			}
		}
	}
}
