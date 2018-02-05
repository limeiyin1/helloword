package com.redfinger.manager.modules.game.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfGameMonitorCfg;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.game.service.GameMonitorService;

@Controller
@RequestMapping(value = "/game/monitor")
public class GameMonitorServiceController extends BaseController {
	@Autowired
	GameMonitorService service;


	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
   
	//页面查询的时候返回数据
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGameMonitorCfg> list(HttpServletRequest request, HttpServletResponse response, Model model, RfGameMonitorCfg bean) throws Exception {
		List<RfGameMonitorCfg> list = service.initQuery(bean)
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.andEqualTo("monitorPort", bean.getMonitorPort())
				.andLike("gameName", bean.getGameName())
				.andLike("packageName", bean.getPackageName())
				.addOrderClause("createTime", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfGameMonitorCfg> pageInfo = new PageInfo<RfGameMonitorCfg>(list);
		return pageInfo;
	}

	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfGameMonitorCfg bean) throws Exception {
		if (bean.getCfgId() == null) {

		} else {
			bean = service.get(bean.getCfgId());
		}
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	
	//保存的时候出现的界面
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfGameMonitorCfg bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfGameMonitorCfg bean) throws Exception {
	
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfGameMonitorCfg bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfGameMonitorCfg bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGameMonitorCfg bean) throws Exception {
		service.delete(request, bean);
	}
	


}
