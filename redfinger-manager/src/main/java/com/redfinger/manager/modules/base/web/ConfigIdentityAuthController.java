package com.redfinger.manager.modules.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.helper.ConfigHelper;
import com.redfinger.manager.modules.base.service.ConfigService;

@Controller
@RequestMapping(value = "/base/configIdentityAuth")
public class ConfigIdentityAuthController extends BaseController {

	@Autowired
	ConfigService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String config_identity_auth = ConfigHelper.getValueByCode("config_identity_auth");
		model.addAttribute("config_identity_auth", config_identity_auth);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysConfig config) throws Exception {
		service.updateMap(request, config);
	}

}
