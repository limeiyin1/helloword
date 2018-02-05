package com.redfinger.manager.modules.goods.web;

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
import com.redfinger.manager.common.domain.RfGoodsType;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.goods.service.GoodsTypeService;

@Controller
@RequestMapping(value = "/goods/typeManage")
public class GoodsTypeController extends BaseController {

	@Autowired
	GoodsTypeService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGoodsType> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsType bean)
			throws Exception {
		List<RfGoodsType> list = service.initQuery(bean)
				.andLike("typeName", bean.getTypeName())
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfGoodsType> pageInfo = new PageInfo<RfGoodsType>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsType bean)
			throws Exception {
		model.addAttribute("yesOrNo", YesOrNoStatus.DICT_MAP);
		if (bean.getTypeId() == null) {

		} else {
			bean = service.get(bean.getTypeId());
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfGoodsType bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsType bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfGoodsType bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfGoodsType bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsType bean)
			throws Exception {
		service.remove(request, bean);
	}
}
