package com.redfinger.manager.modules.label.web;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.label.service.LabelService;

@Controller
@RequestMapping(value = "/label/label")
public class LabelController extends BaseController{

	@Autowired
	LabelService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfLabel> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfLabel bean)
			throws Exception {
		List<RfLabel> list = service.initQuery(bean)
				.andEqualTo("labelType", bean.getLabelType())
				.andLike("labelName", bean.getLabelName())
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfLabel> pageInfo = new PageInfo<RfLabel>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfLabel bean)
			throws Exception {
		if (bean.getLabelId() == null) {

		} else {
			bean = service.get(bean.getLabelId());
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfLabel bean,String userLabelCode,String padLabelCode) throws Exception {
		if(StringUtils.isBlank(bean.getLabelType())){
			throw new BusinessException("请选择标签类型！");
		}
		if(StringUtils.isNotBlank(bean.getLabelName())){
			List<RfLabel> list = service.initQuery(bean).andEqualTo("labelName", bean.getLabelName().trim()).findAll();
			if(null != list && list.size()>0){
				throw new BusinessException(bean.getLabelName()+"此标签名称已经存在！");
			}
		}
		if(ConstantsDb.labelTypeUser().equals(bean.getLabelType())){
			if(StringUtils.isBlank(userLabelCode)){
				throw new BusinessException("请选择用户标签编码！");
			}
			bean.setLabelCode(userLabelCode);
		}
		
		if(ConstantsDb.labelTypePad().equals(bean.getLabelType())){
			if(StringUtils.isBlank(padLabelCode)){
				throw new BusinessException("请选择设备标签编码！");
			}
			bean.setLabelCode(padLabelCode);
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,String userLabelCode,String padLabelCode,
			HttpServletResponse response, Model model, RfLabel bean)
			throws Exception {
		if(StringUtils.isBlank(bean.getLabelType())){
			throw new BusinessException("请选择标签类型！");
		}
		if(StringUtils.isNotBlank(bean.getLabelName())){
			List<RfLabel> list = service.initQuery(bean).andEqualTo("labelName", bean.getLabelName()).andNotEqualTo("labelId", bean.getLabelId()).findAll();
			if(null != list && list.size()>0){
				throw new BusinessException(bean.getLabelName()+"此标签名称已经存在！");
			}
		}
		
		if(ConstantsDb.labelTypeUser().equals(bean.getLabelType())){
			if(StringUtils.isBlank(userLabelCode)){
				throw new BusinessException("请选择用户标签编码！");
			}
			bean.setLabelCode(userLabelCode);
		}
		
		if(ConstantsDb.labelTypePad().equals(bean.getLabelType())){
			if(StringUtils.isBlank(padLabelCode)){
				throw new BusinessException("请选择设备标签编码！");
			}
			bean.setLabelCode(padLabelCode);
		}
		
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfLabel bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfLabel bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfLabel bean)
			throws Exception {
		service.delete(request, bean);
	}
}
