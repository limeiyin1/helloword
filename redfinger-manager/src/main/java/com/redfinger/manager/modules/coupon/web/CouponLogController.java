package com.redfinger.manager.modules.coupon.web;

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
import com.redfinger.manager.common.domain.RfActivationCodeLog;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfCouponLog;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.ActivationCodeOperateType;
import com.redfinger.manager.common.utils.CouponTypeLogUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.coupon.service.CouponLogService;
import com.redfinger.manager.modules.coupon.service.CouponTypeService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value="/coupon/couponLog")
public class CouponLogController extends BaseController  {
	
	@Autowired
	private CouponLogService service;
	@Autowired
	private UserService userService;
	@Autowired
	private CouponTypeService couponTypeService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		List<RfCouponType> list = couponTypeService.initQuery().findAll();
		
		model.addAttribute("typeList", list);
		model.addAttribute("map", CouponTypeLogUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfCouponLog> list(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfCouponLog bean) throws Exception {
		
		List<RfCouponLog> list = service.initQuery(bean)
				.andEqualTo("typeId", bean.getTypeId())
				.andEqualTo("logType", bean.getLogType())
				.andLike("couponCode", bean.getCouponCode())
				.addOrderClause("createTime", "desc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfCouponLog log : list) {
				if(null != log.getUserId()){
					RfUser user = userService.get(log.getUserId());
					log.getMap().put("userMobilePhone", user.getUserMobilePhone());
				}
				
				if(null != log.getTypeId()){
					log.getMap().put("couponTypeName", couponTypeService.get(log.getTypeId()).getTypeName());
				}
				
				if(StringUtils.isNotBlank(log.getLogType())){
					log.getMap().put("logTypeName", CouponTypeLogUtils.DICT_MAP.get(log.getLogType()));
				}
			}
		}
		
		PageInfo<RfCouponLog> pageInfo = new PageInfo<RfCouponLog>(list);
		return pageInfo;
	}
}
