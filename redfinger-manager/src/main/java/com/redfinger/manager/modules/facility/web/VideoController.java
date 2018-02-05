package com.redfinger.manager.modules.facility.web;

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
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfVideo;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.VideoService;

@Controller
@RequestMapping(value = "/facility/video")
public class VideoController extends BaseController {
	@Autowired
	ControlService controlService;
	@Autowired
	VideoService service;
	@Autowired
	IdcService idcService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfVideo> list(HttpServletRequest request, HttpServletResponse response, Model model, RfVideo bean) throws Exception {
		List<RfVideo> list = service.initQuery(bean)
				.andEqualTo("videoType", bean.getVideoType())
				.andLike("videoName", bean.getVideoName())
				.andLike("videCode", bean.getVideoCode())
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfVideo> pageInfo = new PageInfo<RfVideo>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfVideo bean) throws Exception {
		if (bean.getVideoId() == null) {

		} else {
			bean = service.get(bean.getVideoId());
			model.addAttribute("bean", bean);

		}
		List<RfControl> controlList = controlService.initQuery().findStopTrue();
		List<RfIdc> idcList = idcService.initQuery().findStopTrue();
		model.addAttribute("controlService", controlList);
		model.addAttribute("idcList", idcList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfVideo bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfVideo bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfVideo bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfVideo bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfVideo bean) throws Exception {
		service.delete(request, bean);
	}

}
