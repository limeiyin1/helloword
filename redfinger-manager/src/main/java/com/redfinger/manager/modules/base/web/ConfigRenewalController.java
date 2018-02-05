package com.redfinger.manager.modules.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.RedisConstants;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.redis.RedisService;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.base.service.ConfigService;

@Controller
@RequestMapping(value = "/base/configRenewal")
public class ConfigRenewalController extends BaseController{
	
	@Autowired
	private ConfigService service;
	@Autowired
	private RedisService redisService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String offlinePadCount = redisService.get(RedisConstants.OFFLINE_PAD_COUNT);
		String renewal_offline_pad_count = redisService.get(RedisConstants.RENEWAL_OFFLINE_PAD_COUNT);
		model.addAttribute("offlinePadCount", offlinePadCount != null ? offlinePadCount : 0);
		model.addAttribute("renewalOfflinePadCount", renewal_offline_pad_count != null ? renewal_offline_pad_count : 0);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysConfig config) throws Exception {
		/** 判断用户是否有操作的权限*/
		if(!SessionUtils.getCurrentPersmission(request).containsKey("button_update_renewal_config")){
			throw new BusinessException("你没有操作的权限!");
		}
		service.updateMap(request, config);
	}

}
