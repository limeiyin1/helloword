package com.redfinger.manager.modules.notice.web;

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
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserNotice;
import com.redfinger.manager.common.domain.RfUserNoticeExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.notice.service.NoticeService;
import com.redfinger.manager.modules.notice.service.UserNoticeService;

@Controller
@RequestMapping(value = "/notice/userNotice")
public class UserNoticeController extends BaseController {
	@Autowired
	UserNoticeService service;
	@Autowired
	NoticeService noticeService;
	@Autowired
	UserService fingerUserService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfUserNotice> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfUserNotice bean, String title, String content, String pop, String phone,Integer externalUserId) throws Exception {
		
		Integer userId = null;
		//会员ID查询 yirongze修改于 17-8-22
		if ((null != phone && !("".equals(phone)))||externalUserId!=null) {
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(phone,externalUserId);
			if (null != user) {
				userId = user.getUserId();
			} else {
				userId = -1;
			}
		}
		service.initQuery(bean);
		RfUserNoticeExample example =(RfUserNoticeExample) service.getExample();
		if (StringUtils.isNotBlank(title)) {
			title = title.contains("%") ? title.replaceAll("%", "\\\\%") : title;
			example.getMap().put("noticeTitle", title);
		}
		if (StringUtils.isNotBlank(content)) {
			content = content.contains("%") ? content.replaceAll("%", "\\\\%") : content;
			example.getMap().put("noticeContent", content);
		}
		if (StringUtils.isNotBlank(pop)) {
			example.getMap().put("popStatus", pop);
		}
		if (StringUtils.isNotBlank(title) || StringUtils.isNotBlank(content) || StringUtils.isNotBlank(pop)) {
			example.getMap().put("status", ConstantsDb.globalStatusNomarl());
			example.getMap().put("enableStatus", ConstantsDb.globalEnableStatusNomarl());
		}
		List<RfUserNotice> list = service
				.andGreaterThanOrEqualTo("noticeCreateTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("noticeCreateTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.andEqualTo("userId", userId)
				.andLike("userNoticeStatus", bean.getUserNoticeStatus())
				.addOrderClause("userNoticeId", "desc")
				.pageAll(bean.getPage(), bean.getRows());
		for (RfUserNotice rfUserNotice : list) {
			RfUser userN = fingerUserService.load(rfUserNotice.getUserId());
			rfUserNotice.getMap().put("noticeTitle", noticeService.load(rfUserNotice.getNoticeId()).getNoticeTitle());
			rfUserNotice.getMap().put("noticeCreater", noticeService.load(rfUserNotice.getNoticeId()).getCreater());
			rfUserNotice.getMap().put("noticeContent",
					noticeService.load(rfUserNotice.getNoticeId()).getNoticeContent());
			rfUserNotice.getMap().put("noticeCreateTime",
					rfUserNotice.getNoticeCreateTime());
			rfUserNotice.getMap().put("userMobilePhone",
					("".equals(userN.getUserMobilePhone()) || null == userN.getUserMobilePhone()) ? "--"
							: userN.getUserMobilePhone());
			rfUserNotice.getMap().put("userEmail",
					("".equals(userN.getUserEmail()) || null == userN.getUserEmail()) ? "--" : userN.getUserEmail());
			rfUserNotice.getMap().put("popStatus", noticeService.load(rfUserNotice.getNoticeId()).getPopStatus());
			
			/** 查询客户端ID*/
			if(rfUserNotice.getUserId() != null){
				/** 根据用户Id查询用户*/
				RfUser rfUser = userService.load(rfUserNotice.getUserId());
				/** 查询客户端ID*/
				rfUserNotice.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<RfUserNotice> pageInfo = new PageInfo<RfUserNotice>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfUserNotice bean)
			throws Exception {

		if (bean.getUserId() == null) {

		} else {
			bean = service.get(bean.getUserNoticeId());

		}

		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfUserNotice bean)
			throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfUserNotice bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfUserNotice bean)
			throws Exception {

		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfUserNotice bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfUserNotice bean)
			throws Exception {

		service.delete(request, bean);
	}

}