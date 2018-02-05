package com.redfinger.manager.modules.grant.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.redfinger.manager.common.domain.RfPadGrant;
import com.redfinger.manager.common.domain.RfPadGrantCode;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.jsm.MessageProducer;
import com.redfinger.manager.common.mapper.RfPadMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.GrantControlUtils;
import com.redfinger.manager.common.utils.GrantModeUtils;
import com.redfinger.manager.common.utils.GrantWatchUtils;
import com.redfinger.manager.modules.facility.service.PadGrantService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.grant.service.PadGrantCodeService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/grant/code")
public class PadGrantCodeController extends BaseController{
	@Autowired
	private PadGrantCodeService codeService;
	@Autowired
	private PadService padService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("grantContrls", GrantControlUtils.DICT_MAP);
		model.addAttribute("grantWatchs", GrantWatchUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPadGrantCode> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPadGrantCode bean, 
			String begin, String end,String padName)
			throws Exception {
		Integer padId = null;
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		
		if(StringUtils.isNotBlank(padName)){
			List<RfPad> list = padService.initQuery().andEqualTo("padName", padName).findAll();
			if(null != list && list.size()>0){
				padId = list.get(0).getPadId();
			}else{
				padId = 0;
			}
		}
		
		List<RfPadGrantCode> list = codeService.initQuery(bean)
				.andLike("grantCode", bean.getGrantCode())
				.andGreaterThanOrEqualTo("grantTime", beginTime)
				.andLessThan("grantTime", endTime)
				.andEqualTo("grantControl", bean.getGrantControl())
				.andEqualTo("grantWatch", bean.getGrantWatch())
				.andEqualTo("padId", padId)
				.addOrderClause("createTime", "desc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0){
			List<Integer> userIds = new ArrayList<Integer>();
			List<Integer> padIds = new ArrayList<Integer>();
			List<RfUser> users = null;
			List<RfPad> pads = null;
			for (RfPadGrantCode rfPadGrantCode : list) {
				if(null != rfPadGrantCode.getUserId()){
					userIds.add(rfPadGrantCode.getUserId());
					rfPadGrantCode.getMap().put("grantorUserName", userService.get(rfPadGrantCode.getUserId()).getUserMobilePhone());
				}
				if(null != rfPadGrantCode.getPadId()){
					padIds.add(rfPadGrantCode.getPadId());
					rfPadGrantCode.getMap().put("padName", padService.get(rfPadGrantCode.getPadId()).getPadName());
				}
				
				if(StringUtils.isNotBlank(rfPadGrantCode.getGrantControl())){
					rfPadGrantCode.getMap().put("grantControlCode", GrantControlUtils.DICT_MAP.get(rfPadGrantCode.getGrantControl()));
				}
				if(StringUtils.isNotBlank(rfPadGrantCode.getGrantWatch())){
					rfPadGrantCode.getMap().put("grantWatchCode", GrantWatchUtils.DICT_MAP.get(rfPadGrantCode.getGrantWatch()));
				}
			}
			
			/*if(userIds.size()>0){
				users = userService.initQuery().andIn("userId", userIds).findAll();
			}
			
			if(padIds.size()>0){
				pads = padService.initQuery().andIn("padId", padIds).findAll();
			}
			
			for (RfPadGrantCode rfPadGrantCode : list) {
				for (RfPad rfPad : pads) {
					log.info(rfPad.getPadId() +"== "+rfPadGrantCode.getPadId());
					if(rfPad.getPadId() == rfPadGrantCode.getPadId()){
						rfPadGrantCode.getMap().put("padName", rfPad.getPadName());
					}
				}
				
				for (RfUser rfUser : users) {
					log.info(rfPadGrantCode.getUserId() +"== "+rfUser.getUserId());
					if(rfPadGrantCode.getUserId() == rfUser.getUserId()){
						rfPadGrantCode.getMap().put("grantorUserName", rfUser.getUserMobilePhone());
					}
				}
			}*/
		}
		
		
		        
		PageInfo<RfPadGrantCode> pageInfo = new PageInfo<RfPadGrantCode>(list);

		return pageInfo;
	}
	
	
}
