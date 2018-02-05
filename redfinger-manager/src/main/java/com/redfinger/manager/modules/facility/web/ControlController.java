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
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.IdcService;

@Controller
@RequestMapping(value = "/facility/control")
public class ControlController extends BaseController {
	@Autowired
	IdcService idcService;
	@Autowired
	ControlService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfControl> list(HttpServletRequest request, HttpServletResponse response, Model model, RfControl bean) throws Exception {
		List<RfControl> list = service.initQuery(bean)
				.andEqualTo("controlType", bean.getControlType())
				.andLike("controlName", bean.getControlName())
				.andLike("controlCode", bean.getControlCode())
				.andLike("controlCode", bean.getControlCode())
				.addOrderClause("reorder", "asc")
				.addOrderClause("controlId", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfControl> pageInfo = new PageInfo<RfControl>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfControl bean) throws Exception {
		if (bean.getControlId() == null) {

		} else {
			bean = service.get(bean.getControlId());
			model.addAttribute("bean", bean);

		}
		List<RfIdc> idcList = idcService.initQuery().findDelTrue();
		model.addAttribute("idcList", idcList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfControl bean) throws Exception {
		if(StringUtils.isNotBlank(bean.getControlCode())){
			List<RfControl> controls = service.initQuery().andEqualTo("controlCode", bean.getControlCode()).findStopTrue();
			if(null != controls && controls.size()>0){
				throw new BusinessException("编码为："+bean.getControlCode()+"的控制节点已经存在");
			}
		
		}
		
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfControl bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfControl bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfControl bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfControl bean) throws Exception {
		service.remove(request, bean);
	}

}
