package com.redfinger.manager.modules.member.web;

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
import com.redfinger.manager.common.domain.ViewUserData;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.base.service.AreaService;
import com.redfinger.manager.modules.member.service.ViewUserDataService;
@Controller
@RequestMapping(value = "/member/userData")
public class ViewUserDataController extends BaseController {
@Autowired
ViewUserDataService service;
@Autowired
AreaService areaService;
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	@ResponseBody
	@RequestMapping(value="list")
	public PageInfo<ViewUserData>list(HttpServletRequest request,HttpServletResponse response,Model model,ViewUserData bean)throws Exception{
		List<ViewUserData> list=service.initQuery(bean)
				.andLike("userNameT2", bean.getUserNameT2())
				.andLike("userEmailT2", bean.getUserEmailT2())
				.andLike("userMobilePhoneT2", bean.getUserMobilePhoneT2())
				.andEqualTo("loginCountT2", bean.getLoginCountT2())
				.andGreaterThanOrEqualTo("loginTimeT2",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("loginTimeT2",DateUtils.parseDateAddOneDay(bean.getEndTimeStr()) )
				.andEqualTo("userGenderT2", bean.getUserGenderT2())
				.andEqualTo("externalUserIdT2", bean.getExternalUserIdT2())
				.addOrderClause("createTime", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (ViewUserData viewUserData : list) {
			viewUserData.getMap().put("province", viewUserData.getProvinceId()==null?null:areaService.load(viewUserData.getProvinceId()).getName());
			viewUserData.getMap().put("city", viewUserData.getCityId()==null?null:areaService.load(viewUserData.getCityId()).getName());
			viewUserData.getMap().put("nationality", viewUserData.getNationalityId()==null?"":viewUserData.getNationalityId()==0?"中华人民共和国":"");
			viewUserData.getMap().put("contry", viewUserData.getCountyId()==null?null:areaService.load(viewUserData.getCountyId()).getName());
		}
		PageInfo<ViewUserData> pageInfo = new PageInfo<ViewUserData>(list);
		return pageInfo;
	}
}
