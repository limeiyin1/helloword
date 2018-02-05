package com.redfinger.manager.modules.order.web;

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
import com.redfinger.manager.common.domain.RfPayMode;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.order.service.PayModeService;

@Controller
@RequestMapping(value="/order/paymode")
public class PayModeController extends BaseController{
	
	@Autowired
	PayModeService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPayMode> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPayMode bean)
			throws Exception {
		List<RfPayMode> list = service.initQuery()
				.andLike("payModeName", bean.getPayModeName())
				.addOrderClause("reorder", " asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null != list && list.size() > 0){
			for (RfPayMode rfPayMode : list) {
				if(StringUtils.isNotBlank(rfPayMode.getIsDefault())){
					rfPayMode.getMap().put("isDefaultName", YesOrNoStatus.DICT_MAP.get(rfPayMode.getIsDefault()));
				}
			}
		}
		
		
		PageInfo<RfPayMode> pageInfo = new PageInfo<RfPayMode>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPayMode bean)
			throws Exception {
		if (bean.getPayModeCode() == null) {

		} else {
			bean = service.get(bean.getPayModeCode());
			model.addAttribute("bean", bean);

		}
		model.addAttribute("yesOrNo", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPayMode bean)
			throws Exception {
		RfPayMode mode = service.get(bean.getPayModeCode());
		int count = service.initQuery().andEqualTo("osType", mode.getOsType()).andEqualTo("isDefault", YesOrNoStatus.YES)
				.andNotEqualTo("payModeCode", mode.getPayModeCode()).countStopTrue();
		
		if(count > 0){
			throw new BusinessException(mode.getOsType()+"端已经存在默认方式");
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfPayMode bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfPayMode bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPayMode bean)
			throws Exception {
		service.delete(request, bean);
	}

}
