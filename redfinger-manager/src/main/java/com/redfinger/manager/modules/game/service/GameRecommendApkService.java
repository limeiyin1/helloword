package com.redfinger.manager.modules.game.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.AppRecommendApk;
import com.redfinger.manager.common.domain.AppRecommendApkExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppRecommendApkMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameRecommendApkService extends BaseService<AppRecommendApk,AppRecommendApkExample,AppRecommendApkMapper>{

	public void insert(HttpServletRequest request, AppApk bean, String recommendApkId) throws Exception {
		//接收页面传来的参数
				String[] arr=request.getParameterValues("recommendreorder");
		
		// 先删除
		List<AppRecommendApk> removeList = this.initQuery().andEqualTo("apkId", bean.getId()).findAll();
		for (AppRecommendApk appRecommendApk : removeList) {
			this.remove(request, appRecommendApk.getId());
		}
		// 再新增
		if (StringUtils.isNoneEmpty(recommendApkId)) {
			List<String> result = Lists.newArrayList();
			List<String> permissions = Lists.newArrayList(StringUtils.split(recommendApkId, ","));
			for (String id : permissions) {
				result.add(id);
			}
			int count=0;
			for (String id : result) {
				AppRecommendApk appRecommendApk = new AppRecommendApk();
				appRecommendApk.setReorder(Integer.parseInt(arr[count++]));
				appRecommendApk.setRecommendId(Integer.parseInt(id));;
				appRecommendApk.setApkId(bean.getId());
				this.save(request, appRecommendApk);
			}
		}

	}
}
