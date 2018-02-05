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
import com.redfinger.manager.common.domain.RfActivationCodeLog;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.utils.ActivationCodeOperateType;
import com.redfinger.manager.common.utils.PadTypeUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.activation.service.ActivationCodeLogService;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;
@Controller
@RequestMapping(value="/activation/activationLog")
public class ActivationLogController extends BaseController  {
	@Autowired
	private ActivationCodeLogService logService;
	@Autowired
	private ActivationCodeTypeService activationCodeTypeService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfActivationCodeType> typeList = activationCodeTypeService.initQuery().findStopTrue();
		model.addAttribute("typeList", typeList);
		model.addAttribute("map", ActivationCodeOperateType.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfActivationCodeLog> list(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfActivationCodeLog bean) throws Exception {
		
		List<RfActivationCodeLog> list = logService.initQuery(bean)
				.andEqualTo("typeId", bean.getTypeId())
				.andEqualTo("opearteType", bean.getOpearteType())
				.andLike("activationCode", bean.getActivationCode())
				.addOrderClause("createTime", "desc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfActivationCodeLog log : list) {
				if(null != log.getTypeId()){
					RfActivationCodeType rfActivationCodeType = activationCodeTypeService.get(log.getTypeId());
					log.getMap().put("activationTypeName", rfActivationCodeType.getActivationTypeName());
				}
				if(StringUtils.isNotBlank(log.getOpearteType())){
					log.getMap().put("operateTypeName", ActivationCodeOperateType.DICT_MAP.get(log.getOpearteType()));
				}
				
			}
		}
		
		PageInfo<RfActivationCodeLog> pageInfo = new PageInfo<RfActivationCodeLog>(list);
		return pageInfo;
	}
}
