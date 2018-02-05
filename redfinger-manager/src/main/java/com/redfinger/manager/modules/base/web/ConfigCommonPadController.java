package com.redfinger.manager.modules.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.CommonPadConfigCode;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.base.web.dto.SysConfigsDto;

@Controller
@RequestMapping(value = "/base/configCommonPad")
public class ConfigCommonPadController extends BaseController {
	@Autowired
	ConfigService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		SysConfig shareDay = service.selectByConfingCode(CommonPadConfigCode.COMMONPAD_SHARE_ADD_DAY);
		SysConfig shareTimes = service.selectByConfingCode(CommonPadConfigCode.COMMONPAD_SHARE_TIMES);
		SysConfig screenLimit = service.selectByConfingCode(CommonPadConfigCode.FREE_SCREEN_LIMIT_TIME);
		SysConfig uoloadLimit = service.selectByConfingCode(CommonPadConfigCode.FREE_UPLOAD_LIMIT_TIME);
		SysConfig weixinLImit = service.selectByConfingCode(CommonPadConfigCode.FREE_WEIXIN_LIMIT_TIME);
		SysConfig firstLimit = service.selectByConfingCode(CommonPadConfigCode.FREE_FIRST_LIMIT_TIME);
		model.addAttribute("shareDay",shareDay);
		model.addAttribute("shareTimes",shareTimes);
		model.addAttribute("screenLimit",screenLimit);
		model.addAttribute("uoloadLimit",uoloadLimit);
		model.addAttribute("weixinLImit",weixinLImit);
		model.addAttribute("firstLimit",firstLimit);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response,SysConfigsDto sysConfigs) throws Exception {
		List<SysConfig> sysConfigList = sysConfigs.getSysConfigs();
		for (SysConfig sysConfig : sysConfigList) {
			service.update(request, sysConfig);
		}
	}
}







