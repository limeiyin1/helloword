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
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.CacheRedisConstant;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.redis.CacheRedisService;
import com.redfinger.manager.common.utils.NoticeTypeUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.notice.service.NoticeService;

@Controller
@RequestMapping(value = "/notice/notice")
public class NoticeController extends BaseController {
	@Autowired
	NoticeService service;

	@Autowired
	UserService userService;
	
	@Autowired
	private CacheRedisService cacheRedisService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfNotice> list(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean, String selectloginTime) throws Exception {

		List<RfNotice> list = service.initQuery(bean)
				.andLike("noticeTitle", bean.getNoticeTitle())
				.andLike("noticeContent", bean.getNoticeContent())
				.andEqualTo("popStatus", bean.getPopStatus())
				.andEqualTo("popExpired", bean.getPopExpired())
				.andGreaterThanOrEqualTo("modifyTime", bean.getModifyTime()).addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfNotice rfNotice : list) {
				if(StringUtils.isNotBlank(rfNotice.getNoticeType())){
					rfNotice.getMap().put("noticeTypeName", NoticeTypeUtils.DICT_MAP.get(rfNotice.getNoticeType()));
				}
			}
		}
		
		PageInfo<RfNotice> pageInfo = new PageInfo<RfNotice>(list);
		
		return pageInfo;

	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {

		if (bean.getNoticeId() == null) {

		} else {
			bean = service.get(bean.getNoticeId());

		}

		model.addAttribute("bean", bean);
		model.addAttribute("yesOrNo", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {
		service.save(request, bean);
	}
	@RequestMapping(value = "look")
	public String look(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {
		bean = service.get(bean.getNoticeId());
	model.addAttribute("bean", bean);
	return this.toPage(request, response, model);
	}


	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {
		service.update(request, bean);
		/** 更改用户发送公告, 清空redis中所有的公告数据*/
		try{
			cacheRedisService.delByModule(CacheRedisConstant.USERNOTICEPAGE_MODULE);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {
		service.delete(request, bean);
	}

	@RequestMapping(value = "noticeForm")
	public String noticeForm(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice notice) {

		if ("all".equals(notice.getIds())) {
			List<RfUser> userList = userService.initQuery().findDelTrue();
			model.addAttribute("userList", userList);
		} else {
			List<RfUser> userList = userService.initQuery().findDelTrue();
			model.addAttribute("userList", userList);
			Integer noticeid = Integer.parseInt(notice.getIds());
			model.addAttribute("notice", service.initQuery().get(noticeid));
		}

		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "notice")
	public void notice(HttpServletRequest request, HttpServletResponse response, Model model, List<String> uidS, RfNotice notice) throws Exception {

	}
	
	@RequestMapping(value = "cancelPop")
	public void cancelPop(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice bean) throws Exception {
		service.cancelPop(request, bean.getIds());
	}
	
	
}
