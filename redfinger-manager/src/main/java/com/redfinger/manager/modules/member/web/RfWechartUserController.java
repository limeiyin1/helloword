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
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfWechartUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.member.service.RfWechartUserService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/member/wechart")
public class RfWechartUserController extends BaseController {
	@Autowired
	RfWechartUserService service;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfWechartUser> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfWechartUser bean, Integer externalUserId) throws Exception {
		
		/** 数据表用户ID*/
		Integer userId = null;
		/** 根据客户端ID查询用户ID*/
		if (externalUserId!=null) {
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(null,externalUserId);
			if (user != null) {
				userId = user.getUserId();
			} else {
				userId = -1;
			}
		}
		
		BaseService<RfWechartUser, ?, ?> base = service.initQuery(bean).andLike("nickname", bean.getNickname())
				.andLike("status", bean.getStatus()).andLike("userEmail", bean.getUserEmail())
				.andLike("userMobilePhone", bean.getUserMobilePhone())
				.andEqualTo("userId", userId)
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "asc").addOrderForce(bean.getSort(), bean.getOrder());
		if (bean.getGroupid() != null) {
			base.andIn("groupid", new Object[] { bean.getGroupid() });
		}
		List<RfWechartUser> list = base.pageAll(bean.getPage(), bean.getRows());
		if(list != null && list.size() > 0){
			for (RfWechartUser rfWechartUser : list) {
				/** 查询客户端ID*/
				if(rfWechartUser.getUserId() != null){
					/** 根据用户ID查询用户 */
					RfUser rfUser = userService.load(rfWechartUser.getUserId());
					/** 查询客户端ID*/
					rfWechartUser.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
			}
		}
		PageInfo<RfWechartUser> pageInfo = new PageInfo<RfWechartUser>(list);
		return pageInfo;
	}

	// 解绑页面
	@RequestMapping(value = "relieveForm")
	public String relieveFormu(HttpServletRequest request, HttpServletResponse response, Model model, Integer id) {

		RfWechartUser rfWechartUser = service.get(id);
		// 判断是否正常数据
		if (!"1".equals(rfWechartUser.getEnableStatus()) || !"1".equals(rfWechartUser.getStatus())) {
			// 抛出
			throw new BusinessException("未绑定，无需解绑！");
		}
		model.addAttribute("bean", rfWechartUser);
		return this.toPage(request, response, model);
	}

	// 解绑操作
	@RequestMapping(value = "unbundling")
	public void unBundling(HttpServletRequest request, HttpServletResponse response, Model model, RfWechartUser bean) {
		try {
			service.unBundling(request, bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解绑失败！");
		}
	}
}
