package com.redfinger.manager.modules.game.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppImage;
import com.redfinger.manager.common.domain.AppImageExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppImageMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameAppImageService extends BaseService<AppImage, AppImageExample, AppImageMapper> {

	public void insert1(HttpServletRequest request, AppImage bean1, String icon) throws Exception {

		/*
		 * String icon=request.getParameter("icon"); String []
		 * desc=request.getParameterValues("desc");
		 */
		// 先删除
		List<AppImage> imageList = this.initQuery().andEqualTo("apkId", bean1.getApkId()).andNotEqualTo("category", "recommend").findAll();
		for (AppImage AppImage : imageList) {
			this.remove(request, AppImage.getId());
		}

		// 再新增

		AppImage appIcon = new AppImage();
		if (icon != null) {
			appIcon.setUrl(icon);

			appIcon.setCategory("icon");

			appIcon.setApkId(bean1.getApkId());
			this.save(request, appIcon);
		}
		if (null != bean1.getIds()) {
			String[] img = bean1.getIds().split(",");
			for (String image : img) {
				if (image.equals("http://")) {
					return;
				}
				AppImage appImage = new AppImage();
				appImage.setApkId(bean1.getApkId());
				appImage.setUrl(image);
				appImage.setCategory("desc");
				this.save(request, appImage);
			}
		}
	}
	
	public void insertRecommendImage(HttpServletRequest request, AppImage bean, String recommendImage) throws Exception {
		// 先删除
		List<AppImage> imageList = this.initQuery().andEqualTo("apkId", bean.getApkId()).andEqualTo("category", "recommend").findAll();
			for (AppImage AppImage : imageList) {
					this.remove(request, AppImage.getId());
				}

		// 再新增
		AppImage image=new AppImage();
		image.setApkId(bean.getApkId());
		image.setUrl(recommendImage);
		image.setCategory("recommend");
		this.save(request, image);
	}

}
