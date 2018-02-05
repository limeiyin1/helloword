package com.redfinger.manager.modules.member.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.LogWechartOperate;
import com.redfinger.manager.common.domain.RfWechartUser;
import com.redfinger.manager.common.domain.RfWechartUserExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWechartUserMapper;
import com.redfinger.manager.modules.log.service.LogWechartOperateService;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class RfWechartUserService extends BaseService<RfWechartUser, RfWechartUserExample, RfWechartUserMapper> {

	/** 操作类型：解绑 **/
	public final static String UNBUNDLING = "2";
	
	@Autowired
	LogWechartOperateService logService;

	public void unBundling(HttpServletRequest request, RfWechartUser bean) throws Exception{
		// 解除微信绑定
		this.remove(request, bean.getId());
		// 添加解绑记录
		LogWechartOperate log = new LogWechartOperate();
		log.setUserId(bean.getUserId());
		log.setOpenid(bean.getOpenid());
		log.setForeignOperateId(bean.getId());
		log.setOperateType(UNBUNDLING);
		logService.save(request, log);
	}
}
