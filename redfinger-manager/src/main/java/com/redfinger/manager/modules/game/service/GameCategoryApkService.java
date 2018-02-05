package com.redfinger.manager.modules.game.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppCategory;
import com.redfinger.manager.common.domain.AppCategoryApk;
import com.redfinger.manager.common.domain.AppCategoryApkExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppCategoryApkMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameCategoryApkService extends BaseService<AppCategoryApk,AppCategoryApkExample,AppCategoryApkMapper>{

	public void insert(HttpServletRequest request, AppCategory bean, String appApkId) throws Exception {
		//接收页面传来的参数
		String[] arr=request.getParameterValues("categoryreorder");
		
		// 先删除
		List<AppCategoryApk> removeList = this.initQuery().andEqualTo("categoryId", bean.getId()).findAll();
		for (AppCategoryApk appCategoryApk : removeList) {
			this.remove(request, appCategoryApk.getId());
		}
		// 再新增
		if (StringUtils.isNoneEmpty(appApkId)) {
			List<String> result = Lists.newArrayList();
			List<String> permissions = Lists.newArrayList(StringUtils.split(appApkId, ","));
			for (String id : permissions) {
				result.add(id);
			}
			int count=0;
			for (String id : result) {
				AppCategoryApk appCategoryApk = new AppCategoryApk();
				appCategoryApk.setReorder(Integer.parseInt(arr[count++]));
				appCategoryApk.setCategoryId(bean.getId());
				appCategoryApk.setApkId(Integer.parseInt(id));
				this.save(request, appCategoryApk);
			}
		}

	}
}
