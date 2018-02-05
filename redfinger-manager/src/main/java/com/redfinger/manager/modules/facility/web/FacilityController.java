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
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.modules.facility.service.FacilityService;
import com.redfinger.manager.modules.facility.service.PadService;

@Controller
@RequestMapping(value = "/facility/facility")
public class FacilityController extends BaseController {
	@Autowired
	private FacilityService service;
	@Autowired
	private PadService padService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfFacility> list(HttpServletRequest request, HttpServletResponse response, Model model, RfFacility facility) throws Exception {
		List<RfFacility> list = service.initQuery(facility)
				.andLike("facilityName", facility.getFacilityName())
				.addOrderClause("reorder", "asc")
				.addOrderForce(facility.getSort(), facility.getOrder())
				.pageDelTrue(facility.getPage(), facility.getRows());
		
		for (RfFacility rfFacility : list) {
			//总设备数量
			Integer allCount = padService.initQuery().andEqualTo("padSource", rfFacility.getFacilityCode()).countDelTrue();
			rfFacility.getMap().put("allCount", allCount);
		}
		PageInfo<RfFacility> pageInfo = new PageInfo<RfFacility>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfFacility facility) throws Exception {
		if (facility.getFacilityId() != null) {
			facility = service.get(facility.getFacilityId());
			model.addAttribute("facility", facility);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfFacility facility) throws Exception {
		service.save(request, facility);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfFacility facility) throws Exception {
		service.update(request, facility);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfFacility facility) throws Exception {
		service.start(request, facility);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfFacility facility) throws Exception {
		service.stopFacility(request, facility);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfFacility facility) throws Exception {
		service.deleteFacility(request, facility);
	}

}
