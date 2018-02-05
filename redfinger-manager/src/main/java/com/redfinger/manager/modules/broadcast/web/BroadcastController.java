package com.redfinger.manager.modules.broadcast.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfBroadcast;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfCoupon;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfVideo;
import com.redfinger.manager.common.utils.BroadcastLinkTypeUtils;
import com.redfinger.manager.common.utils.BroadcastType;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.broadcast.service.BroadcastService;

@Controller
@RequestMapping(value="/broadcast/publish")
public class BroadcastController  extends BaseController {

	@Autowired
	BroadcastService service;

	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("types", BroadcastType.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfBroadcast> list(HttpServletRequest request, HttpServletResponse response, Model model, RfBroadcast bean) throws Exception {
		
		List<RfBroadcast> list = service.initQuery(bean)
				.andLike("broadcastContent", bean.getBroadcastContent())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andEqualTo("broadcastType", bean.getBroadcastType())
				.addOrderClause("createTime", "desc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfBroadcast rfBroadcast : list) {
				
			}
		}
		
		PageInfo<RfBroadcast> pageInfo = new PageInfo<RfBroadcast>(list);
		return pageInfo;
	}
	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfBroadcast bean) throws Exception {
		if (bean.getBroadcastId() == null) {

		} else {
			bean = service.get(bean.getBroadcastId());
			model.addAttribute("bean", bean);
		}
		
		String contentlength = ConfigConstantsDb.broadcastContentLength();
		int length = StringUtils.isNotBlank(contentlength) ? Integer.parseInt(contentlength) : 20;
		model.addAttribute("contentLength", length);
		model.addAttribute("linkTypes", BroadcastLinkTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfBroadcast bean,
			String braodcastStartTimeStr,String braodcastEndTimeStr) throws Exception {
		if(StringUtils.isNotBlank(braodcastStartTimeStr)){
			bean.setBroadcastStartTime(DateUtils.parseDate(braodcastStartTimeStr));
		}
		
		if(StringUtils.isNotBlank(braodcastEndTimeStr)){
			bean.setBroadcastEndTime(DateUtils.parseDate(braodcastEndTimeStr));
		}
		bean.setBroadcastType(ConstantsDb.background());
		bean.setClickRate(0);
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfBroadcast bean,
			String braodcastStartTimeStr,String braodcastEndTimeStr) throws Exception {
		if(StringUtils.isNotBlank(braodcastStartTimeStr)){
			bean.setBroadcastStartTime(DateUtils.parseDate(braodcastStartTimeStr));
		}
		
		if(StringUtils.isNotBlank(braodcastEndTimeStr)){
			bean.setBroadcastEndTime(DateUtils.parseDate(braodcastEndTimeStr));
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfBroadcast bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfBroadcast bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfBroadcast bean) throws Exception {
		service.delete(request, bean);
	}
}
