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
import com.redfinger.manager.common.domain.RfGameChannel;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.modules.game.service.GameChannelService;

@Controller
@RequestMapping(value = "/game/channel")
public class GameChannelController extends BaseController {

	@Autowired
	private GameChannelService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);  
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGameChannel> list(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannel bean) throws Exception {
		List<RfGameChannel> list = service.initQuery(bean)
				.andLike("channelName", bean.getChannelName())
				.addOrderClause("createTime", "desc")
				.addOrderClause("channelId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		PageInfo<RfGameChannel> pageInfo = new PageInfo<RfGameChannel>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannel bean) throws Exception {
		if (bean.getChannelId() != null) {
			bean = service.get(bean.getChannelId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannel bean) throws Exception {
		int count = service.initQuery().andEqualTo("channelName", bean.getChannelName()).countDelTrue();
		if(count > 0){
			throw new BusinessException("渠道["+bean.getChannelName()+"]已存在，请修改后再保存");
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannel bean) throws Exception {
		int count = service.initQuery().andEqualTo("channelName", bean.getChannelName()).andNotEqualTo("channelId", bean.getChannelId()).countDelTrue();
		if(count > 0){
			throw new BusinessException("渠道["+bean.getChannelName()+"]已存在，请修改后再保存");
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannel bean) throws Exception {
		service.start(request, bean);
	}
	
	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannel bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannel bean) throws Exception {
		service.delete(request, bean);
	}
	
}
