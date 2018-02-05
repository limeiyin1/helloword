package com.redfinger.manager.modules.wechart.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfWechartTemplate;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.jsm.WeixinProducer;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.WechartTemplateStatus;
import com.redfinger.manager.common.utils.WechartTemplateType;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.wechart.service.WechartTemplateService;

@Controller
@RequestMapping(value="/wechart/template")
public class WechartTemplateController extends BaseController{
	@Autowired
	private WechartTemplateService service;
	@Autowired
	private UserService userService;
	@Autowired
	private WeixinProducer weixinProducer;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		Map<String,String> types = WechartTemplateType.DICT_MAP;
		model.addAttribute("types", types);
		Map<String,String> statuss = WechartTemplateStatus.DICT_MAP;
		model.addAttribute("statuss", statuss);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfWechartTemplate> list(HttpServletRequest request,
			HttpServletResponse response, Model model, String userMobilePhone,RfWechartTemplate bean)
			throws Exception {
		
		if(StringUtils.isNotBlank(userMobilePhone)){
			RfUser mapper = userService.getUserByUserPhone(userMobilePhone);
			if(null != mapper){
				bean.setUserId(mapper.getUserId());
			}else{
				bean.setUserId(-1);
			}
		}
		
		List<RfWechartTemplate> list = service.initQuery(bean)
				.andEqualTo("userId", bean.getUserId())
				.andEqualTo("padCode", bean.getPadCode())
				.andEqualTo("templateType", bean.getTemplateType())
				.andEqualTo("templateStatus", bean.getTemplateStatus())
				.addOrderClause("createTime", "desc")
				.addOrderClause("templateId", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0){
			for (RfWechartTemplate template : list) {
				if(StringUtils.equals(template.getTemplateType(), WechartTemplateType.GAME_ABNORMAL)){
					template.getMap().put("title", template.getFaultPerformance());
					template.getMap().put("templateTypeName", WechartTemplateType.DICT_MAP.get(WechartTemplateType.GAME_ABNORMAL));
					template.getMap().put("templateStatusName", WechartTemplateStatus.DICT_MAP.get(template.getTemplateStatus()));
				}else if(StringUtils.equals(template.getTemplateType(), WechartTemplateType.REPLACE_PAD)){
					template.getMap().put("title", template.getEvent());
					template.getMap().put("templateTypeName", WechartTemplateType.DICT_MAP.get(WechartTemplateType.REPLACE_PAD));
					template.getMap().put("templateStatusName", WechartTemplateStatus.DICT_MAP.get(template.getTemplateStatus()));
				}else if(StringUtils.equals(template.getTemplateType(), WechartTemplateType.PAD_EXPIRATION)){
					template.getMap().put("title", "您购买的"+template.getMessageName()+"有效期至"+DateUtils.formatDate(template.getMessageExpdate(), "yyyy年MM月dd"));
					template.getMap().put("templateTypeName", WechartTemplateType.DICT_MAP.get(WechartTemplateType.PAD_EXPIRATION));
					template.getMap().put("templateStatusName", WechartTemplateStatus.DICT_MAP.get(template.getTemplateStatus()));
				}else if(StringUtils.equals(template.getTemplateType(), WechartTemplateType.PAD_WARM)){
					template.getMap().put("title", template.getEvent());
					template.getMap().put("templateTypeName", WechartTemplateType.DICT_MAP.get(WechartTemplateType.PAD_WARM));
					template.getMap().put("templateStatusName", WechartTemplateStatus.DICT_MAP.get(template.getTemplateStatus()));
				}
				
				if(null != template.getUserId()){
					RfUser user = userService.get(template.getUserId());
					if(null != user){
						template.getMap().put("userMobilePhone", user.getUserMobilePhone());
					}
				}
			}
		}
		PageInfo<RfWechartTemplate> pageInfo = new PageInfo<RfWechartTemplate>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "sentMessage")
	public void sentMessage(HttpServletRequest request, HttpServletResponse response, Model model, RfWechartTemplate bean) throws Exception {
		if(null == bean.getTemplateId()){
			throw new BusinessException("请选择微信消息");
		}
		
		RfWechartTemplate template = service.get(bean.getTemplateId());
		if(null == template){
			throw new BusinessException("您选择的微信消息不存在");
		}
		
		if(StringUtils.equals(template.getTemplateStatus(), WechartTemplateStatus.SEND_SUCCESS) || 
				StringUtils.equals(template.getTemplateStatus(), WechartTemplateStatus.NO_SEND)){
			throw new BusinessException("只能选择发送失败的微信消息，请重新选择");
		}
		
		//向用户发送jms
		JSONObject sendMessageObj = new JSONObject();
		sendMessageObj.put("templateId", template.getTemplateId());
		String sendMessageStr = sendMessageObj.toString();
		log.info("renewal sendMessageStr: "+ sendMessageStr);
		// 发送jms消息
		weixinProducer.sendMessage(sendMessageStr);
	}
}
