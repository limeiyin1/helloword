package com.redfinger.manager.modules.game.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.AppCategoryApk;
import com.redfinger.manager.common.domain.AppCategoryApkExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppCategoryApkMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameAppCategoryApkService extends BaseService<AppCategoryApk,AppCategoryApkExample,AppCategoryApkMapper>{

	public void insert(HttpServletRequest request, AppApk bean, String categoryApkId) throws Exception {
		//接收页面传来的参数
		String[] arr=request.getParameterValues("categoryreorder");
		
		// 先删除
		List<AppCategoryApk> removeList = this.initQuery().andEqualTo("apkId", bean.getId()).findAll();
		for (AppCategoryApk AppCategoryApk : removeList) {
			this.remove(request, AppCategoryApk.getId());
		}
		// 再新增
		if (StringUtils.isNoneEmpty(categoryApkId)) {
			List<String> result = Lists.newArrayList();
			List<String> permissions = Lists.newArrayList(StringUtils.split(categoryApkId, ","));
			for (String id : permissions) {
				result.add(id);
			}
			int count=0;
			for (String id : result) {
				AppCategoryApk appCategoryApk = new AppCategoryApk();
				appCategoryApk.setReorder(Integer.parseInt(arr[count++]));
				appCategoryApk.setCategoryId(Integer.parseInt(id));
				appCategoryApk.setApkId(bean.getId());
				this.save(request, appCategoryApk);
			}
		}

	}
}