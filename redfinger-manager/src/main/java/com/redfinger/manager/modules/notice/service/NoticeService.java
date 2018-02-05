package com.redfinger.manager.modules.notice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.PopExpiredType;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfNoticeExample;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserData;
import com.redfinger.manager.common.domain.RfUserNotice;
import com.redfinger.manager.common.domain.ViewPadUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfNoticeMapper;
import com.redfinger.manager.common.mapper.RfUserNoticeMapper;
import com.redfinger.manager.common.redis.CacheRedisService;
import com.redfinger.manager.common.utils.NoticeRedisUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.ViewPadUserService;
import com.redfinger.manager.modules.member.service.UserDataService;
import com.redfinger.manager.modules.notice.dto.UMengInfoDto;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "noticeId")
public class NoticeService extends BaseService<RfNotice, RfNoticeExample, RfNoticeMapper> {
	@Autowired
	UserNoticeService userNoticeService;
	@Autowired
	PadService padService;
	@Autowired
	ViewPadUserService padUserService;
	@Autowired
	RfNoticeMapper noticeMapper;
	@Autowired
	RfUserNoticeMapper userNoticeMapper;
	@Autowired
	UserDataService userDataService;
	@Autowired
	private CacheRedisService cacheRedisService;
	
	public void insert(HttpServletRequest request, String uidS, RfNotice notice) throws Exception {
		if (uidS != null) {
			this.save(request, notice);
			userNoticeService.insert(request, uidS, notice);
		}
	}
	
	public void insertNotice(HttpServletRequest request, RfNotice notice) throws Exception {
		if (notice != null) {
			this.save(request, notice);
		}
	}
	
	public UMengInfoDto saveAllUserNotice(HttpServletRequest request, RfNotice notice,String isPush) throws Exception {
		RfNotice rfNotice = this.saveNotice(request, notice);
		UMengInfoDto dto = new UMengInfoDto();
		dto.setRfNotice(rfNotice);
		return dto;
	}
	
	public void pushAllUMengInfo(HttpServletRequest request,String isPush,RfNotice notice,
			String pushType,Integer id) throws Exception{
		if(YesOrNoStatus.YES.equals(isPush)){
			Map<String,Object> map = userDataService.sendAllUserMessage(request,notice,pushType,id);
			if(!"SUCCESS".equals(map.get("ret"))){
				throw new BusinessException(map.get("errorMessage").toString());
			}
		}else{
			log.info("您选择不发送公告。push:"+isPush);
		}
	}
	
	/**
	 * 根据选择保存公告
	 * @param request
	 * @param notice
	 * @param uIds
	 * @param isPush
	 * @return
	 * @throws Exception
	 */
	public UMengInfoDto saveSelectUserNotice(HttpServletRequest request, RfNotice notice, String uIds,String isPush) throws Exception{
		RfNotice rfNotice = this.saveNotice(request, notice);
		Map<String,Object> params = new HashMap<String,Object>();
		
		List<Integer> lists = new ArrayList<Integer>();
		if(StringUtils.isNotEmpty(uIds)){
			String str[] = uIds.split(",");
			for (String id : str) {
				lists.add(Integer.parseInt(id));
			}
		}
		
		params.put("userIdIn", lists);
		params.put("noticeId", rfNotice.getNoticeId());
		params.put("creater", SessionUtils.getCurrentUserId(request));
		params.put("modifier", SessionUtils.getCurrentUserId(request));
		userNoticeMapper.insertUserNotice(params);
		
		/** ***修改redis中的用户公告******/
		try{
			List<RfUserNotice> userNoticeList = userNoticeService.initQuery().andEqualTo("noticeId", rfNotice.getNoticeId()).findAll();
			NoticeRedisUtils.addNoticeRedis(cacheRedisService, userNoticeList, rfNotice);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		UMengInfoDto dto = new UMengInfoDto();
		dto.setRfNotice(rfNotice);
		dto.setUserIds(lists);
		return dto;
	}
	
	/**
	 * 按条件推送公告
	 * @param isPush
	 * @param params
	 * @param notice
	 * @param pushType
	 * @throws Exception
	 */
	public void pushUmengInfo(HttpServletRequest request,String isPush,Map<String,Object> params,
			RfNotice notice,String pushType,String pushExist,Integer id) throws Exception{
		if(YesOrNoStatus.YES.equals(isPush)){
			List<RfUserData> list = userDataService.selectDeviceToken(params);
	    	if(null != list && list.size()>0){
	    		StringBuffer str = new StringBuffer();
	    		for(int i=0;i<list.size();i++){
	    			if(i==list.size()-1){
	    				str.append(list.get(i).getDeviceToken());
	    			}else{
	    				str.append(list.get(i).getDeviceToken()+"\n");
	    			}
	    		}
	    		
	    		userDataService.sendPartUser(request,str.toString(), notice,pushType,pushExist,id);
	    	}else{
	    		throw new BusinessException("查询出的用户没有设备,推送不了公告！");
	    	}
		}else{
			log.info("您选择不发送公告。push:"+isPush);
		}
	}
	
	/**
	 * 对部分用户发送公告
	 * @param request
	 * @param notice
	 * @param user
	 * @param isPush
	 * @return
	 * @throws Exception
	 */
	public void savePartUserNotice(HttpServletRequest request, RfNotice notice, RfUser user,String isPush,String userMobilePhones) throws Exception{
		userMobilePhones = userMobilePhones.replaceAll(" ", "");
		userMobilePhones = userMobilePhones.replaceAll("\r", "");
		String [] phones = userMobilePhones.split("\n");
		
		if(phones.length < 1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		if(phones.length > 1500){
			throw new BusinessException("号码个数不能超过1500个");
		}
		
		List<String> mobilePhoneList = new ArrayList<String>();
		for(String phone : phones){
			if(StringUtils.isNotBlank(phone)){
				mobilePhoneList.add(phone);
			}
		}
		
		mobilePhoneList = this.cleanRepeat(mobilePhoneList);//清理重复数据
		
		Collections.sort(mobilePhoneList);
		
		
		RfNotice rfNotice = this.saveNotice(request, notice);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		List<List<String>> phoneLists = createList(mobilePhoneList, 1000);
		for (List<String> list : phoneLists) {
			params.clear();
			params.put("creater", SessionUtils.getCurrentUserId(request));
			params.put("modifier", SessionUtils.getCurrentUserId(request));
			params.put("noticeId", rfNotice.getNoticeId());
			params.put("userMobilePhoneIn", list);
			userNoticeMapper.insertUserNotice(params);
			
			// 查询手机号对应的用户id
			/*List<RfUser> sendNoticeUserList = userService.initQuery().andIn("userMobilePhone", list).findAll();
			if(sendNoticeUserList != null && sendNoticeUserList.size() > 0){
				for (RfUser rfUser : sendNoticeUserList) {
					if(rfUser != null && rfUser.getUserId() != null){
						userIdList.add(rfUser.getUserId());
					}
				}
			}*/
		}
		try{
			List<RfUserNotice> userNoticeList = userNoticeService.initQuery().andEqualTo("noticeId", rfNotice.getNoticeId()).findAll();
			
			NoticeRedisUtils.addNoticeRedis(cacheRedisService, userNoticeList, notice);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据设备条件保存公告
	 * @param request
	 * @param bean
	 * @param padCodes
	 * @throws Exception
	 */
	public void padCodeNotice(HttpServletRequest request,RfNotice bean, String padCodes) throws Exception {
		if(StringUtils.isEmpty(bean.getNoticeTitle())){
			throw new BusinessException("公告标题不能为空");
		}
		if(StringUtils.isEmpty(bean.getNoticeContent())){
			throw new BusinessException("公告内容不能为空");
		}
		this.save(request, bean);
		if(StringUtils.isEmpty(padCodes)){
			throw new BusinessException("请输入要发送公告的设备号");
		}
		padCodes=padCodes.replaceAll(" ", "");
		padCodes=padCodes.replaceAll("\r", "");
		String [] codes=padCodes.split("\n");
		if(codes.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		List<Integer>list=Lists.newArrayList();
		List<RfUserNotice> userNotices = Lists.newArrayList();
		for (String string : codes) {
		ViewPadUser param=padUserService.getPadCode(string);
		if(null==param){
			throw new BusinessException("错误：001 输入了无效参数，没有设备编号绑定记录"+string);
		}
		if(null==param.getUserIdT2()){
			throw new BusinessException("错误：001 输入了无效参数，绑定参数异常,绑定ID:"+param.getUserPadIdT2()+"设备编号:"+string);
			}
			if (!list.contains(param.getUserIdT2())) {
				list.add(param.getUserIdT2());
				RfUserNotice userNotice = new RfUserNotice();
				userNotice.setNoticeId(bean.getNoticeId());
				userNotice.setUserId(param.getUserIdT2());
				userNotice.setUserNoticeStatus(YesOrNoStatus.NO);
				userNoticeService.save(request, userNotice);
				
				userNotices.add(userNotice);
			}
		}
		
		try{
			NoticeRedisUtils.addNoticeRedis(cacheRedisService, userNotices, bean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public RfNotice saveNotice(HttpServletRequest request, RfNotice notice){
		noticeMapper.insert(notice);
		return notice;
	}

	/**
	 * 根据设备条件推送公告
	 * @param request
	 * @param notice
	 * @param pad
	 * @param isPush
	 * @param pushType
	 * @throws Exception
	 */
	public UMengInfoDto savePadNotice(HttpServletRequest request, RfNotice notice, RfPad pad,String isPush,String pushType) throws Exception{
		RfNotice rfNotice = this.saveNotice(request, notice);
		Map<String,Object> params = new HashMap<String,Object>();
		UMengInfoDto dto = new UMengInfoDto();
		if(StringUtils.isNotEmpty(pad.getPadName())){
			params.put("padName", pad.getPadName());
			dto.setPadName(pad.getPadName());
		}
		
		if(StringUtils.isNotEmpty(pad.getPadCode())){
			params.put("padCode", pad.getPadCode());
			dto.setPadCode(pad.getPadCode());
		}
		
		if(StringUtils.isNotEmpty(pad.getPadIp())){
			params.put("padIp", pad.getPadIp());
			dto.setPadId(pad.getPadIp());
		}
		
		params.put("creater", SessionUtils.getCurrentUserId(request));
		params.put("modifier", SessionUtils.getCurrentUserId(request));
		params.put("noticeId", rfNotice.getNoticeId());
		
		userNoticeMapper.insertPadNotice(params);
		
		dto.setRfNotice(rfNotice);
		
		return dto;
		
	}
	
	public void sendPadUmengInfo(HttpServletRequest request,String isPush,Map<String,Object> params, 
			RfNotice notice,String pushType,String pushExist,Integer id) throws Exception{
		if(YesOrNoStatus.YES.equals(isPush)){
			List<RfUserData> list = userDataService.selectDeviceTokenByPad(params);
	    	if(null != list && list.size()>0){
	    		StringBuffer str = new StringBuffer();
	    		for(int i=0;i<list.size();i++){
	    			if(i==list.size()-1){
	    				str.append(list.get(i).getDeviceToken());
	    			}else{
	    				str.append(list.get(i).getDeviceToken()+"\n");
	    			}
	    		}
	    		
	    		userDataService.sendPartUser(request, str.toString(), notice,pushType,pushExist,id);
	    	}else{
	    		throw new BusinessException("查询出的用户没有设备,推送不了公告！");
	    	}
		}
	}
	
	public void cancelPop(HttpServletRequest request, String ids) throws Exception{
		if(ids != null){
			String[] noticeIds = ids.split(",");
			for (String id : noticeIds) {
				RfNotice notice = new RfNotice();
				notice.setNoticeId(Integer.parseInt(id));
				notice.setPopExpired(PopExpiredType.INVALID);
				update(request, notice);
			}
		}
	}
}
