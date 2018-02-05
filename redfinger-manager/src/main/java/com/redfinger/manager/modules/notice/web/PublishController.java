package com.redfinger.manager.modules.notice.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.redfinger.manager.common.domain.PushUmengInfoLog;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserData;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.jsm.PushNoticeProducer;
import com.redfinger.manager.common.redis.CacheRedisService;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.NoticeTypeUtils;
import com.redfinger.manager.common.utils.PushStatus;
import com.redfinger.manager.common.utils.PushType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserDataService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.notice.dto.UMengInfoDto;
import com.redfinger.manager.modules.notice.service.NoticeService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/notice/publish")
public class PublishController extends BaseController {
	@Autowired
	UserService service;
	@Autowired
	NoticeService noticeService;
	@Autowired
	UserDataService userDataService;
	@Autowired
	PushNoticeProducer pushNoticeProducer;
	@Autowired
	private CacheRedisService cacheRedisService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfUser> list(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		if (StringUtils.isNotBlank(bean.getBeginTimeStr())||StringUtils.isNotBlank(bean.getEndTimeStr())) {
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date stardate =null;
			Date enddate =null;
			if(StringUtils.isNotBlank(bean.getBeginTimeStr())){
				stardate= fmt.parse(bean.getBeginTimeStr());
			}
			if(StringUtils.isNotBlank(bean.getEndTimeStr())){
				enddate = fmt.parse(bean.getEndTimeStr());
			}
			List<RfUser> list = service.initQuery(bean).andGreaterThanOrEqualTo("loginTime", stardate).andLessThanOrEqualTo("loginTime", enddate)
					.andEqualTo("userId", bean.getUserId())
					.andEqualTo("externalUserId", bean.getExternalUserId())// 根据客户端ID查询
					.andLike("userMobilePhone", bean.getUserMobilePhone()).andLike("userEmail", bean.getUserEmail()).addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
			PageInfo<RfUser> pageInfo = new PageInfo<RfUser>(list);
			return pageInfo;
		} else {
			List<RfUser> list = service.initQuery(bean)
					.andEqualTo("userId", bean.getUserId())
					.andEqualTo("externalUserId", bean.getExternalUserId())// 根据客户端ID查询
					.andLike("userMobilePhone", bean.getUserMobilePhone())
					.andLike("userEmail", bean.getUserEmail())
					.addOrderClause("reorder", "asc")
					.addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
			PageInfo<RfUser> pageInfo = new PageInfo<RfUser>(list);
			return pageInfo;
		}

	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		if (bean.getUserId() == null) {

		} else {
			bean = service.get(bean.getUserId());
		}
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "noticeForm")
	public String roleForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) {
		if(bean.getIds().length()<1){
			throw new BusinessException("请确认你选择的要发送公告的用户信息");
		}
		String user[] = bean.getIds().split(",");
		List<RfUser> userList = new ArrayList<RfUser>();
		for (String uid : user) {
			userList.add(service.initQuery().get(Integer.parseInt(uid)));
		}
		model.addAttribute("userList", userList);
		return this.toPage(request, response, model);
	}
	
	
	
	@RequestMapping(value = "noticeUmeng")
	public String noticeUmeng(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws ParseException {
		Map<String,Object> map = new HashMap<String,Object>();
		Integer userId = bean.getUserId();
		String userMobilePhone = bean.getUserMobilePhone();
		String userEmail = bean.getUserEmail();
		String beginTimeStr = bean.getBeginTimeStr();
		String endTimeStr = bean.getEndTimeStr();
		log.info("[user{},userMobilePhone{},userEmail{},beginTimeStr{},endTimeStr{}]",
				new Object[]{userId,userMobilePhone,userEmail,beginTimeStr,endTimeStr});
		
		if(null == userId && StringUtils.isEmpty(userMobilePhone) &&
				StringUtils.isEmpty(userEmail) && StringUtils.isEmpty(beginTimeStr) &&
				StringUtils.isEmpty(endTimeStr)){
			throw new BusinessException("请填写查询条件！");
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("userMobilePhone", userMobilePhone);
		model.addAttribute("beginTimeStr", beginTimeStr);
		model.addAttribute("endTimeStr", endTimeStr);
		
		if(null != userId){
			map.put("userId", userId);
		}
		
		if(StringUtils.isNotBlank(userMobilePhone)){
			map.put("userMobilePhone", userMobilePhone);
		}
		
		if(StringUtils.isNotBlank(userEmail)){
			map.put("userEmail", userEmail);
			
		}
		
		if(StringUtils.isNotBlank(beginTimeStr)){
			map.put("starDate", DateUtils.strExchangeDate(beginTimeStr));
			
		}
		
		if(StringUtils.isNotBlank(endTimeStr)){
			map.put("endDate", DateUtils.strExchangeDate(endTimeStr));
		}
		
		return this.toPage(request, response, model);
	}
	
	/**
	 * 选中发送公告
	 * @param request
	 * @param response
	 * @param model
	 * @param uidS
	 * @param notice
	 * @param user
	 * @param isPush
	 * @throws Exception
	 */
	@RequestMapping(value = "noticeOne")
	public void noticeOne(HttpServletRequest request, HttpServletResponse response, Model model, 
			String uidS, RfNotice notice, RfUser user,String isPush) throws Exception {
		log.info("[title{},noticeContent]", new Object[]{notice.getNoticeTitle(),notice.getNoticeContent()});
		if(null==notice.getNoticeTitle() || "".equals(notice.getNoticeTitle())){
			throw new BusinessException("公告标题不能为空");
		}
	    if(null==notice.getNoticeContent() || "".equals(notice.getNoticeContent())){
	    	throw new BusinessException("公告内容不能为空");
		}
	    if(StringUtils.isBlank(uidS)){
	    	throw new BusinessException("请选择用户！");
	    }
	    log.info("userIds:"+uidS);
	    
	    if(StringUtils.isEmpty(isPush)){
	    	isPush=YesOrNoStatus.NO;
	    }
	    notice.setNoticeType(NoticeTypeUtils.PART);
		notice.setStatus(YesOrNoStatus.YES);
		notice.setEnableStatus(YesOrNoStatus.YES);
		notice.setCreater(SessionUtils.getCurrentUserId(request));
		notice.setCreateTime(new Date());
		
		UMengInfoDto dto = null;
		try{
			dto = noticeService.saveSelectUserNotice(request, notice, uidS,isPush);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("新增个人公告报错！");
		}
		
		try{
			if(null != dto){
				Map<String,Object> params = new HashMap<String,Object>();
				
				if(YesOrNoStatus.YES.equals(isPush)){
					params.put("userIdIn", uidS);
					String pushExist = JSONObject.fromObject(params).toString();
					
					PushUmengInfoLog umengInfo = new PushUmengInfoLog();
		    		umengInfo.setTitle(notice.getNoticeTitle());
		    		umengInfo.setNoticeContent(notice.getNoticeContent());
		    		umengInfo.setPushExist(pushExist);
		    		umengInfo.setPushType(PushType.SELECT_NOTICE_PUSH);
		    		umengInfo.setPushStatus(PushStatus.PUSH_FAIL);
		    		umengInfo.setStatus(YesOrNoStatus.YES);
		    		umengInfo.setEnableStatus(YesOrNoStatus.YES);
		    		umengInfo.setCreater(SessionUtils.getCurrentUserId(request));
		    		umengInfo.setCreateTime(new Date());
		    		
		    		Integer id = userDataService.insertVo(umengInfo);
		    		if(null != id){
		    			pushNoticeProducer.sendMessage(id.toString());
		    		}else{
		    			throw new BusinessException("新增友盟推送公告日志报错！");
		    		}
		    		
		    		/*params.clear();
		    		params.put("userIdIn", dto.getUserIds());
					noticeService.pushUmengInfo(request,isPush, params, dto.getRfNotice(),PushType.SELECT_NOTICE_PUSH,pushExist,id);*/
				}else{
					log.info("您选择不发送公告。push:"+isPush);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("新增友盟推送公告日志报错！");
		}
	}

	/***
	 * 对部分用户发送公告
	 * @param request
	 * @param response
	 * @param model
	 * @param uidS
	 * @param notice
	 * @param user
	 * @param isPush
	 * @param userMobilePhones
	 * @throws Exception
	 */
	@RequestMapping(value = "sendPartNotice")
	public void notice(HttpServletRequest request, HttpServletResponse response, Model model, 
			String uidS, RfNotice notice, RfUser user,String isPush,String userMobilePhones,String popExpiredTimeStr) throws Exception {
		log.info("[title{},noticeContent]", new Object[]{notice.getNoticeTitle(),notice.getNoticeContent()});
		if(null==notice.getNoticeTitle() || "".equals(notice.getNoticeTitle())){
			throw new BusinessException("公告标题不能为空");
		}
	    if(null==notice.getNoticeContent() || "".equals(notice.getNoticeContent())){
	    	throw new BusinessException("公告内容不能为空");
		}	
	    if(StringUtils.isBlank(userMobilePhones)){
	    	throw new BusinessException("号码不能为空");
		}
		notice.setNoticeType(NoticeTypeUtils.PART);
		notice.setStatus(YesOrNoStatus.YES);
		notice.setEnableStatus(YesOrNoStatus.YES);
		notice.setCreater(SessionUtils.getCurrentUserId(request));
		notice.setCreateTime(new Date());
		if(popExpiredTimeStr != null){
			notice.setPopExpiredTime(DateUtils.parseDate(popExpiredTimeStr));
		}
		notice.setPopExpired(notice.getPopStatus());
		noticeService.savePartUserNotice(request, notice, user,isPush,userMobilePhones);
	}
	
	/**
	 * 推送公告
	 * @param request
	 * @param response
	 * @param model
	 * @param uidS
	 * @param notice
	 * @param userId
	 * @param userMobilePhone
	 * @param userEmail
	 * @param beginTimeStr
	 * @param endTimeStr
	 * @param isTotal
	 * @throws Exception
	 */
	@RequestMapping(value = "umeng")
	public void umeng(HttpServletRequest request, HttpServletResponse response, Model model, String uidS, RfNotice notice,
			Integer userId,String userMobilePhone,String userEmail,String beginTimeStr,String endTimeStr,
			String isTotal) throws Exception {
		log.info("umeng notice send [title{},noticeContent]", new Object[]{notice.getNoticeTitle(),notice.getNoticeContent()});
		log.info("[user{},userMobilePhone{},userEmail{},beginTimeStr{},endTimeStr{}]",
				new Object[]{userId,userMobilePhone,userEmail,beginTimeStr,endTimeStr});
		if(null==notice.getNoticeTitle()||"".equals(notice.getNoticeTitle())){
			throw new BusinessException("公告标题不能为空");
		}
	    if(null==notice.getNoticeContent()||"".equals(notice.getNoticeContent())){
	    	throw new BusinessException("公告内容不能为空");
		}	
	    
	    if("0".equals(isTotal)){//按条件推送
			if(null == userId && StringUtils.isEmpty(userMobilePhone) &&
					StringUtils.isEmpty(userEmail) && StringUtils.isEmpty(beginTimeStr) &&
					StringUtils.isEmpty(endTimeStr)){
				throw new BusinessException("请填写查询用户的条件！");
			}
		    
		    Map<String,Object> map = new HashMap<String,Object>();
		    Map<String,Object> params = new HashMap<String,Object>();
		    if(null != userId){
				map.put("userId", userId);
				params.put("userId", userId.toString());
			}
			
			if(StringUtils.isNotBlank(userMobilePhone)){
				map.put("userMobilePhone", userMobilePhone);
				params.put("userMobilePhone", userMobilePhone);
			}
			
			if(StringUtils.isNotBlank(userEmail)){
				map.put("userEmail", userEmail);
				params.put("userEmail", userEmail);
			}
			
			if(StringUtils.isNotBlank(beginTimeStr)){
				map.put("startDate", DateUtils.strExchangeDate(beginTimeStr));
				params.put("startDate", beginTimeStr);
			}
			
			if(StringUtils.isNotBlank(endTimeStr)){
				map.put("endDate", DateUtils.strExchangeDate(endTimeStr));
				params.put("endDate", endTimeStr);
			}
	    	List<RfUserData> list = userDataService.selectDeviceToken(map);
	    	if(null != list && list.size()>0){
	    		StringBuffer str = new StringBuffer();
	    		for(int i=0;i<list.size();i++){
	    			if(i==list.size()-1){
	    				str.append(list.get(i).getDeviceToken());
	    			}else{
	    				str.append(list.get(i).getDeviceToken()+"\n");
	    			}
	    		}
	    		
	    		String pushExist = JSONObject.fromObject(params).toString();
	    		PushUmengInfoLog umengInfo = new PushUmengInfoLog();
	    		
	    		umengInfo.setTitle(notice.getNoticeTitle());
	    		umengInfo.setNoticeContent(notice.getNoticeContent());
	    		umengInfo.setPushExist(pushExist);
	    		umengInfo.setPushType(PushType.PART_NOTICE_PUSH);
	    		umengInfo.setPushStatus(PushStatus.PUSH_FAIL);
	    		umengInfo.setStatus(YesOrNoStatus.YES);
	    		umengInfo.setEnableStatus(YesOrNoStatus.YES);
	    		umengInfo.setCreater(SessionUtils.getCurrentUserId(request));
	    		umengInfo.setCreateTime(new Date());
	    		
	    		Integer id = userDataService.insertVo(umengInfo);
	    		
	    		if(null != id){//发送jms
	    			pushNoticeProducer.sendMessage(id.toString());
	    		}else{
	    			throw new BusinessException("新增友盟推送公告日志报错！");
	    		}
	    		
	    		/*noticeService.pushUmengInfo(request,YesOrNoStatus.YES,map,notice,PushType.PART_NOTICE_PUSH,pushExist, id);*/
	    	}else{
	    		log.info("查询出的用户没有设备！");
	    	}
	    }else{//全部用户推送
	    	PushUmengInfoLog umengInfo = new PushUmengInfoLog();
    		
    		umengInfo.setTitle(notice.getNoticeTitle());
    		umengInfo.setNoticeContent(notice.getNoticeContent());
    		umengInfo.setPushType(PushType.ALL_NOTICE_PUSH);
    		umengInfo.setPushStatus(PushStatus.PUSH_FAIL);
    		umengInfo.setStatus(YesOrNoStatus.YES);
    		umengInfo.setEnableStatus(YesOrNoStatus.YES);
    		umengInfo.setCreater(SessionUtils.getCurrentUserId(request));
    		umengInfo.setCreateTime(new Date());
    		
    		Integer id = userDataService.insertVo(umengInfo);
	    	
    		if(null != id){//发送jms
    			pushNoticeProducer.sendMessage(id.toString());
    		}else{
    			throw new BusinessException("新增友盟推送公告日志报错！");
    		}
    		
	    	/*userDataService.sendAllUser(request,notice,PushType.ALL_NOTICE_PUSH,id);*/
	    }
	}

	/**
	 * 发送部分公告
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sendPartsForm")
	public String sendPartsForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "noticeTotalForm")
	public String noticeTotalForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) {
		
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "noticeTotalPush")
	public void noticeTotalPush(HttpServletRequest request, HttpServletResponse response, Model model, String uidS, RfNotice notice, 
			String popExpiredTimeStr,String isPush) throws Exception {
		UMengInfoDto dto = null;
		if(null==notice.getNoticeTitle()||"".equals(notice.getNoticeTitle())){
			throw new BusinessException("公告标题不能为空");
		}
	    if(null==notice.getNoticeContent()||"".equals(notice.getNoticeContent())){
	    	throw new BusinessException("公告内容不能为空");
		}	
	    
	    if(StringUtils.isEmpty(isPush)){
	    	isPush = YesOrNoStatus.NO;
	    }
	    
	    try {
			//发送所有用户
			notice.setNoticeType(NoticeTypeUtils.TOTAL);
			notice.setCreater(SessionUtils.getCurrentUserId(request));
			notice.setCreateTime(new Date());
			notice.setStatus(YesOrNoStatus.YES);
			notice.setEnableStatus(YesOrNoStatus.YES);
			if(popExpiredTimeStr != null){
				notice.setPopExpiredTime(DateUtils.parseDate(popExpiredTimeStr));
			}
			dto = noticeService.saveAllUserNotice(request, notice, isPush);
			
			/** 向所有用户发送公告, 清空redis中所有的公告数据*/
			try{
				cacheRedisService.delByModule(CacheRedisConstant.USERNOTICEPAGE_MODULE);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		} catch (Exception e) {
			throw new BusinessException("发送公告失败，请联系管理员");
		}
		
		try {
			if(YesOrNoStatus.YES.equals(isPush)){
				PushUmengInfoLog umengInfo = new PushUmengInfoLog();
	    		umengInfo.setTitle(notice.getNoticeTitle());
	    		umengInfo.setNoticeContent(notice.getNoticeContent());
	    		umengInfo.setPushType(PushType.ALL_NOTICE_PUSH);
	    		umengInfo.setPushStatus(PushStatus.PUSH_FAIL);
	    		umengInfo.setStatus(YesOrNoStatus.YES);
	    		umengInfo.setEnableStatus(YesOrNoStatus.YES);
	    		umengInfo.setCreater(SessionUtils.getCurrentUserId(request));
	    		umengInfo.setCreateTime(new Date());
	    		
	    		Integer id = userDataService.insertVo(umengInfo);
	    		if(null != id){//发送jms
	    			pushNoticeProducer.sendMessage(id.toString());
	    		}else{
	    			throw new BusinessException("公告推送失败，请联系管理员");
	    		}
				/*noticeService.pushAllUMengInfo(request, isPush, notice, PushType.ALL_NOTICE_PUSH,id);*/
			}else{
				log.info("您选择不发送公告。push:"+isPush);
			}
		} catch (Exception e) {
			throw new BusinessException("新增友盟推送公告日志报错！");
		}
	}
	
	
	@RequestMapping(value = "houUmeng")
	public String houUmeng(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {

		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "umengGroup")
	public String umengGroup(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {

		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "pushFilter")
	public void pushFilter(HttpServletRequest request, HttpServletResponse response, Model model, RfNotice notice,String filter) throws Exception {
		if(StringUtils.isEmpty(filter)){
			throw new BusinessException("请输入用户组！");
		}
		PushUmengInfoLog umengInfo = new PushUmengInfoLog();
		
		umengInfo.setTitle(notice.getNoticeTitle());
		umengInfo.setNoticeContent(notice.getNoticeContent());
		umengInfo.setPushType(PushType.GROUP_CAST_PUSH);
		umengInfo.setPushStatus(PushStatus.PUSH_FAIL);
		umengInfo.setStatus(YesOrNoStatus.YES);
		umengInfo.setEnableStatus(YesOrNoStatus.YES);
		umengInfo.setCreater(SessionUtils.getCurrentUserId(request));
		umengInfo.setCreateTime(new Date());
		umengInfo.setPushExist(filter);
		Integer id = userDataService.insertVo(umengInfo);
    	
		if(null != id){//发送jms
			pushNoticeProducer.sendMessage(id.toString());
		}else{
			throw new BusinessException("新增友盟推送公告日志报错！");
		}
	}
}
