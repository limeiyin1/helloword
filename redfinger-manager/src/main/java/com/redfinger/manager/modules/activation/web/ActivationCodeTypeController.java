package com.redfinger.manager.modules.activation.web;

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
import com.redfinger.manager.common.domain.RfActivationCode;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.NumberUtils;
import com.redfinger.manager.common.utils.PadTypeUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.activation.service.ActivationCodeService;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;
@Controller
@RequestMapping(value="/activation/type")
public class ActivationCodeTypeController extends BaseController {
	@Autowired
	private ActivationCodeTypeService service;
	@Autowired
	private ActivationCodeService codeService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 向请求域中添加设备类型列表
		model.addAttribute("padType",PadTypeUtils.DICT_MAP);
		
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfActivationCodeType> list(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean) throws Exception {
		List<RfActivationCodeType> list = service.initQuery(bean)
				.andLike("activationTypeName", bean.getActivationTypeName())
				.andLike("activationTypeCode", bean.getActivationTypeCode())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andEqualTo("padType", bean.getPadType()) // 根据设备类型查询
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfActivationCodeType rfActivationCodeType : list) {
				rfActivationCodeType.getMap().put("padType", PadTypeUtils.DICT_MAP.get(rfActivationCodeType.getPadType()));
				if(null != rfActivationCodeType.getPadTime()){
					rfActivationCodeType.setPadTime(NumberUtils.dividedTime(rfActivationCodeType.getPadTime()));
				}
			
			}
		}
		
		PageInfo<RfActivationCodeType> pageInfo = new PageInfo<RfActivationCodeType>(list);
		return pageInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "getCodeList")
	public PageInfo<RfActivationCode> getCodeList(HttpServletRequest request, HttpServletResponse response, Model model,
			RfActivationCode bean) throws Exception {
		List<RfActivationCode> list = codeService.initQuery()
				.andLike("activationCode", bean.getActivationCode())
				.andEqualTo("typeId", bean.getTypeId())
				.andEqualTo("padType", bean.getPadType())
				.addOrderClause("reorder", "asc")
				.addOrderClause("createTime", "desc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfActivationCode rfActivationCode : list) {
				if(null != rfActivationCode.getTypeId()){
					RfActivationCodeType rfActivationCodeType = service.get(rfActivationCode.getTypeId());
					rfActivationCode.getMap().put("activationTypeName", rfActivationCodeType.getActivationTypeName());
					rfActivationCode.getMap().put("batchNumber", rfActivationCodeType.getActivationTypeCode());
				}
				if(StringUtils.isNotBlank(rfActivationCode.getPadType())){
					rfActivationCode.getMap().put("padTypeName", PadTypeUtils.DICT_MAP.get(rfActivationCode.getPadType()));
				}
				if(StringUtils.isNotBlank(rfActivationCode.getActivationStatus())){
					rfActivationCode.getMap().put("activationStatusName", YesOrNoStatus.DICT_MAP.get(rfActivationCode.getActivationStatus()));
				}
				
				if(null != rfActivationCode.getPadTime()){
					rfActivationCode.setPadTime(NumberUtils.dividedTime(rfActivationCode.getPadTime()));
				}
			}
		}
		
		PageInfo<RfActivationCode> pageInfo = new PageInfo<RfActivationCode>(list);
		return pageInfo;
	}
	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean)
			throws Exception {
		model.addAttribute("padType", PadTypeUtils.DICT_MAP);
		if (bean.getTypeId() != null) {
			bean = service.get(bean.getTypeId());
			if(null != bean.getPadTime()){
				bean.setPadTime(NumberUtils.dividedTime(bean.getPadTime()));
			}
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "batchEditForm")
	public String batchEditForm(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean)
			throws Exception {
		model.addAttribute("padType", PadTypeUtils.DICT_MAP);
		if (bean.getTypeId() != null) {
			bean = service.get(bean.getTypeId());
			if(null != bean.getPadTime()){
				bean.setPadTime(NumberUtils.dividedTime(bean.getPadTime()));
			}
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "batchEditActivation")
	public void batchEditActivation(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean,
			String activationCode,String padType) throws Exception {
		log.info("[typeId={},padType={},padTime{}]", bean.getTypeId(),bean.getPadType(),bean.getPadTime());
		service.updateActivation(request, bean);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfActivationCodeType bean) throws Exception {
		if(null == bean) {
			throw new BusinessException("输入不能为空");
		}
		
		if(null == bean.getUseLimit()){
			throw new BusinessException("同一类型使用限制不能为空");
		}
		
		// 判断模块编码是否已经存在
		String name = bean.getActivationTypeName();
		List<RfActivationCodeType> list = service.initQuery().andEqualTo("activationTypeName", name).singleStopTrue();
		
		if (null!=list && list.size()>0) {
			throw new BusinessException("此激活码的类型已经存在！");
		}
		
		if(null != bean.getPadTime()){
			if(bean.getPadTime() <= 0){
				throw new BusinessException("绑定时长必须大于0！");
			}
			
			bean.setPadTime(NumberUtils.multiplyTime(bean.getPadTime()));
		}
		service.save(request, bean);
	}
	
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean)
			throws Exception {
		if(null == bean) {
			throw new BusinessException("输入不能为空");
		}
		
		if(null == bean.getUseLimit()){
			throw new BusinessException("同一类型使用限制不能为空");
		}
		
		if(null != bean.getPadTime()){
			if(bean.getPadTime() <= 0){
				throw new BusinessException("绑定时长必须大于0！");
			}
			bean.setPadTime(NumberUtils.multiplyTime(bean.getPadTime()));
		}
		service.update(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCodeType bean)
			throws Exception {
		service.remove(request, bean);
	}
}
