package com.redfinger.manager.modules.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.modules.base.service.ConfigService;


@Controller
@RequestMapping(value = "/base/configProbational")
public class ConfigProbationalController extends BaseController {
	@Autowired
	ConfigService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysConfig config) throws Exception {
		Integer oldValue = Integer.parseInt(ConfigConstantsDb.configProbationPad());
		Integer newValue= Integer.parseInt(config.getMap().get("config_probation_pad").toString());
		int diff=newValue-oldValue;
		service.updateMap(request, config);
		service.updateConfigProbationalBindPadCount(diff);
	}

}
