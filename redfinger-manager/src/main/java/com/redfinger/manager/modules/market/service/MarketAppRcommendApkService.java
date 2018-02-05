package com.redfinger.manager.modules.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppRecommend;
import com.redfinger.manager.common.domain.AppRecommendApk;
import com.redfinger.manager.common.domain.AppRecommendApkExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppRecommendApkMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketAppRcommendApkService extends BaseService<AppRecommendApk, AppRecommendApkExample, AppRecommendApkMapper> {

	public void insert(HttpServletRequest request, AppRecommend bean, String appApkId) throws Exception {

		// 接收页面传来的参数
		String[] arr = request.getParameterValues("recommendreorder");

		// 先删除
		List<AppRecommendApk> removeList = this.initQuery().andEqualTo("recommendId", bean.getId()).findAll();
		for (AppRecommendApk appRecommendApk : removeList) {
			this.remove(request, appRecommendApk.getId());
		}

		// 再新增
		if (StringUtils.isNoneEmpty(appApkId)) {
			List<String> result = Lists.newArrayList();
			List<String> permissions = Lists.newArrayList(StringUtils.split(appApkId, ","));
			for (String id : permissions) {
				result.add(id);
			}

			int count = 0;
			for (String id : result) {
				AppRecommendApk appRecommendApk = new AppRecommendApk();
				appRecommendApk.setReorder(Integer.parseInt(arr[count++]));
				appRecommendApk.setRecommendId(bean.getId());
				appRecommendApk.setApkId(Integer.parseInt(id));
				this.save(request, appRecommendApk);
			}
		}
	}
}
