package com.redfinger.manager.modules.member.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.base.UMengPushMessage;
import com.redfinger.manager.common.base.UMengUtils;
import com.redfinger.manager.common.domain.PushUmengInfoLog;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserData;
import com.redfinger.manager.common.domain.RfUserDataExample;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.PushUmengInfoLogMapper;
import com.redfinger.manager.common.mapper.RfUserDataMapper;
import com.redfinger.manager.common.mapper.RfUserMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.PushStatus;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.log.service.PushUmengInfoLogService;
@Transactional
@Service
@PrimaryKeyAnnotation(field="userId")
public class UserDataService extends BaseService<RfUserData, RfUserDataExample, RfUserDataMapper> {
	public static Logger log = LoggerFactory.getLogger(UserDataService.class);
	
	@Autowired
	RfUserDataMapper mapper;
	@Autowired
	UMengUtils uMengUtils;
	@Autowired
	PushUmengInfoLogMapper infoLogMapper;
	@Autowired
	RfUserMapper userMapper;
	@Autowired
	PushUmengInfoLogService pushUmengInfoLogService;
	@Autowired
	ConfigService configService;
	
	
	public void sendUMengMessage(String beginTimeStr,String endTimeStr,String userEmail,
			String userMobilePhone, String userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("beginTimeStr", beginTimeStr);
		map.put("endTimeStr", endTimeStr);
		map.put("userEmail", userEmail);
		map.put("userMobilePhone", userMobilePhone);
		map.put("userId", userId);
		
		/*List<RfUserData> lists = mapper.selectUserData(map);*/
	}
	
	public Map<String,Object> sendUMeng(HttpServletRequest request,String uidS,RfNotice notice,String pushType) throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isEmpty(uidS)){
			log.warn("用户不能为空");
			throw new BusinessException("用户不能为空!");
		}
		StringBuffer deviceTokens = new StringBuffer();
		String[] ids = uidS.split(",");
		List<Integer> listIds = new ArrayList<Integer>();
		if(ids.length<500){
			for (String str : ids) {
				/*listIds.add(Integer.parseInt(str));*/
				if(StringUtils.isNotEmpty(str)){
					RfUserData data = this.initQuery().get(Integer.parseInt(str));
					RfUser user = userMapper.selectByPrimaryKey(Integer.parseInt(str));
					if(null != data && null != user){
						if(StringUtils.isNotEmpty(data.getDeviceToken())){
							PushUmengInfoLog infoLog = new PushUmengInfoLog();
							infoLog.setUserId(Integer.parseInt(str));
							infoLog.setUserMobilePhone(user.getUserMobilePhone());
							infoLog.setTitle(notice.getNoticeTitle());
							infoLog.setNoticeContent(notice.getNoticeContent());
							infoLog.setPushType(pushType);
							infoLog.setPushStatus(PushStatus.NO_PUSH);
							
							infoLog.setDeviceToken(data.getDeviceToken());
							infoLog.setCreater(SessionUtils.getCurrentUserId(request));
							infoLog.setCreateTime(new Date());
							infoLog.setStatus(YesOrNoStatus.YES);
							infoLog.setEnableStatus(YesOrNoStatus.YES);
							infoLog.setRemark("公告推送");
							Integer id = this.insertVo(infoLog);
							
							Map<String,Object> map = this.sendMessage(data.getDeviceToken(), notice);
							UMengPushMessage um = new UMengPushMessage();
							Map<String,Object> errorMap = um.readErrorPro();
							if(!"SUCCESS".equals(map.get("ret"))){
							    log.debug("push notice fail：[userMobilePhone{},deviceToken{},noticeContent{}]",new Object[]{user.getUserMobilePhone(),data.getDeviceToken(),notice.getNoticeContent()});
							    pushUmengInfoLogService.updateToFinishError(request,id, errorMap.get(map.get("errorCode")).toString());
							    params.clear();
							    params.put("ret", map.get("ret"));
							    params.put("errorMessage", errorMap.get(map.get("errorCode")).toString());
							    break;
							}else{
								log.debug("push notice success：[userMobilePhone{},deviceToken{},noticeContent{}]",new Object[]{user.getUserMobilePhone(),data.getDeviceToken(),notice.getNoticeContent()});
								pushUmengInfoLogService.updateSendToUMeng(request,id, map.get("msgId").toString());
								params.clear();
							    params.put("ret", map.get("ret"));
							    params.put("errorMessage", map.get("msgId").toString());
							}
						}else{
							log.info(user.getUserMobilePhone() + " is not have device token"+user.getUserId());
						}
					}else{
						log.info("userId:"+str+"没有详情信息");
						continue;
					}
				}
			}
		}
		return params;
	}
	
	/**
	 * 广播式发送
	 * @param deviceToken
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sendAllUserMessage(HttpServletRequest request,
			RfNotice notice,String pushType,Integer id) throws Exception{
		String label = "";
		SysConfig config = configService.selectByConfingCode("config_umeng_filter_label");
		if(null == config){
			label = "debug";
		}else{
			label = config.getConfigValue();
		}
		
		JSONObject json = new JSONObject();
		json.put("appkey", uMengUtils.getAppkey());
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		json.put("timestamp", timestamp);
		json.put("type", "broadcast");
		json.put("alias", "alias");
		json.put("alias_type", "alias");
		
		JSONObject tagJson = new JSONObject(); 
		tagJson.put("tag", label);
		JSONObject andJson = new JSONObject(); 
		andJson.put("and", "["+tagJson.toString()+"]");
		JSONObject whereJson = new JSONObject(); 
		whereJson.put("where", andJson);
		
		json.put("filter", whereJson);
		
		JSONObject payLoadJson = new JSONObject();
		payLoadJson.put("display_type", "notification");
		
		JSONObject bodyJson = new JSONObject();
		bodyJson.put("ticker","红手指");
		bodyJson.put("title","红手指");
		bodyJson.put("text",notice.getNoticeContent());
		bodyJson.put("after_open","go_custom");
		bodyJson.put("custom", "1");
		bodyJson.put("builder_id",1);
		bodyJson.put("play_vibrate","true");
        bodyJson.put("play_lights","true");
        bodyJson.put("play_sound","true");
		
		payLoadJson.put("body", bodyJson);
		json.put("payload", payLoadJson);
		
		JSONObject policyJson = new JSONObject();
		policyJson.put("start_time", DateUtils.getAfterMinuteDate(new Date(), 2));
		policyJson.put("expire_time", DateUtils.getAfterDate(new Date(), 24));
		
		json.put("policy", policyJson);
		json.put("description", notice.getNoticeContent());
		
		UMengPushMessage uMeng = new UMengPushMessage();
		Map<String,Object> map = uMeng.messageContents(json, uMengUtils.getHost(), uMengUtils.getMothed(), 
				uMengUtils.getUserAgent(), uMengUtils.getAppkey(), 
				uMengUtils.getAppMasterSecret());
		UMengPushMessage um = new UMengPushMessage();
		Map<String,Object> errorMap = um.readErrorPro();
		if(!"SUCCESS".equals(map.get("ret"))){
		    log.debug("broadcast push notice fail：[noticeContent{}]",new Object[]{notice.getNoticeContent()});
		    pushUmengInfoLogService.updateToFinishError(request,id, errorMap.get(map.get("errorCode")).toString());
		    map.put("errorMessage", errorMap.get(map.get("errorCode")));
		}else{
			log.debug("broadcast push notice success：[userMobilePhone{},deviceToken{},noticeContent{}]",new Object[]{notice.getNoticeContent()});
			pushUmengInfoLogService.updateSendToUMeng(request,id, map.get("taskId").toString());
			
		}
		
		return map;
	}
	
	/**
	 * 单个推送公告
	 * @param deviceToken
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sendMessage(String deviceToken,RfNotice notice) throws Exception{
		String label = "";
		SysConfig config = configService.selectByConfingCode("config_umeng_filter_label");
		if(null == config){
			label = "debug";
		}else{
			label = config.getConfigValue();
		}
		
		JSONObject json = new JSONObject();
		json.put("appkey", uMengUtils.getAppkey());
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		json.put("timestamp", timestamp);
		json.put("type", "unicast");
		json.put("device_tokens", deviceToken.toString());
		json.put("alias", "alias");
		json.put("alias_type", "alias");
		
		JSONObject tagJson = new JSONObject(); 
		tagJson.put("tag", label);
		JSONObject andJson = new JSONObject(); 
		andJson.put("and", "["+tagJson.toString()+"]");
		JSONObject whereJson = new JSONObject(); 
		whereJson.put("where", andJson);
		
		json.put("filter", whereJson);
		
		JSONObject payLoadJson = new JSONObject();
		payLoadJson.put("display_type", "notification");
		
		JSONObject bodyJson = new JSONObject();
		bodyJson.put("ticker","红手指");
		bodyJson.put("title","红手指");
		bodyJson.put("text",notice.getNoticeContent());
		bodyJson.put("after_open","go_custom");
		bodyJson.put("custom", "1");
		bodyJson.put("builder_id",1);
		bodyJson.put("play_vibrate","true");
        bodyJson.put("play_lights","true");
        bodyJson.put("play_sound","true");
		
		payLoadJson.put("body", bodyJson);
		json.put("payload", payLoadJson);
		
		JSONObject policyJson = new JSONObject();
		policyJson.put("start_time", DateUtils.getAfterMinuteDate(new Date(),2));
		policyJson.put("expire_time", DateUtils.getAfterDate(new Date(), 24));
		
		json.put("policy", policyJson);
		json.put("description", notice.getNoticeContent());
		
		UMengPushMessage uMeng = new UMengPushMessage();
		Map<String,Object> map = uMeng.messageContents(json, uMengUtils.getHost(), uMengUtils.getMothed(), 
				uMengUtils.getUserAgent(), uMengUtils.getAppkey(), 
				uMengUtils.getAppMasterSecret());
		
		return map;
	}
	
	/**
	 * 单个推送公告
	 * @param deviceToken
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sendOne(HttpServletRequest request,String deviceToken,RfNotice notice,Integer id) throws Exception{
		String label = "";
		SysConfig config = configService.selectByConfingCode("config_umeng_filter_label");
		if(null == config){
			label = "debug";
		}else{
			label = config.getConfigValue();
		}
		
		JSONObject json = new JSONObject();
		json.put("appkey", uMengUtils.getAppkey());
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		json.put("timestamp", timestamp);
		json.put("type", "unicast");
		json.put("device_tokens", deviceToken.toString());
		json.put("alias", "alias");
		json.put("alias_type", "alias");
		
		JSONObject tagJson = new JSONObject(); 
		tagJson.put("tag", label);
		JSONObject andJson = new JSONObject(); 
		andJson.put("and", "["+tagJson.toString()+"]");
		JSONObject whereJson = new JSONObject(); 
		whereJson.put("where", andJson);
		
		json.put("filter", whereJson);
		
		JSONObject payLoadJson = new JSONObject();
		payLoadJson.put("display_type", "notification");
		
		JSONObject bodyJson = new JSONObject();
		bodyJson.put("ticker","红手指");
		bodyJson.put("title","红手指");
		bodyJson.put("text",notice.getNoticeContent());
		bodyJson.put("after_open","go_custom");
		bodyJson.put("custom", "1");
		bodyJson.put("builder_id",1);
		bodyJson.put("play_vibrate","true");
        bodyJson.put("play_lights","true");
        bodyJson.put("play_sound","true");
		
		payLoadJson.put("body", bodyJson);
		json.put("payload", payLoadJson);
		
		JSONObject policyJson = new JSONObject();
		policyJson.put("start_time", DateUtils.getAfterMinuteDate(new Date(),2));
		policyJson.put("expire_time", DateUtils.getAfterDate(new Date(), 24));
		
		json.put("policy", policyJson);
		json.put("description", notice.getNoticeContent());
		
		UMengPushMessage uMeng = new UMengPushMessage();
		Map<String,Object> map = uMeng.messageContents(json, uMengUtils.getHost(), uMengUtils.getMothed(), 
				uMengUtils.getUserAgent(), uMengUtils.getAppkey(), 
				uMengUtils.getAppMasterSecret());
		
		Map<String,Object> errorMap = uMeng.readErrorPro();
		if(!"SUCCESS".equals(map.get("ret"))){
		    map.put("errorMessage", errorMap.get(map.get("errorCode")));
		    
		    pushUmengInfoLogService.updateToFinishError(request,id, errorMap.get(map.get("errorCode")).toString());
		    
		    throw new BusinessException("推送公告失败，"+errorMap.get(map.get("errorCode")).toString());
		}else{
			pushUmengInfoLogService.updateSendToUMeng(request,id, map.get("taskId").toString());
		}
		return map;
	}
	
	/**
	 * 对部分用户，进行文件播放推送公告
	 * @param deviceToken
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sendPartUser(HttpServletRequest request,String deviceToken,RfNotice notice,String pushType,String pushExist,Integer id) throws Exception{
		UMengPushMessage uMeng = new UMengPushMessage();
		
		Map<String,Object> map = uMeng.fileContents(uMengUtils.getAppkey(), 
				uMengUtils.getAppMasterSecret(), deviceToken);
		String label = "";
		SysConfig config = configService.selectByConfingCode("config_umeng_filter_label");
		if(null == config){
			label = "debug";
		}else{
			label = config.getConfigValue();
		}
		
		UMengPushMessage um = new UMengPushMessage();
		Map<String,Object> errorMap = um.readErrorPro();
		if(!"SUCCESS".equals(map.get("ret"))){
		    map.put("errorMessage", errorMap.get(map.get("errorCode")));
		    
		    pushUmengInfoLogService.updateToFinishError(request,id, errorMap.get(map.get("errorCode")).toString());
		    
		    throw new BusinessException("推送公告失败，"+errorMap.get(map.get("errorCode")).toString());
		}else{
			String fileId = map.get("fileId").toString();
			
			JSONObject json = new JSONObject();
			json.put("appkey", uMengUtils.getAppkey());
			String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
			json.put("timestamp", timestamp);
			json.put("type", "filecast");
			json.put("file_id", fileId);
			json.put("alias", "alias");
			json.put("alias_type", "alias");
			
			JSONObject tagJson = new JSONObject(); 
			tagJson.put("tag", label);
			JSONObject andJson = new JSONObject(); 
			andJson.put("and", "["+tagJson.toString()+"]");
			JSONObject whereJson = new JSONObject(); 
			whereJson.put("where", andJson);
			
			json.put("filter", whereJson);
			
			JSONObject payLoadJson = new JSONObject();
			payLoadJson.put("display_type", "notification");
			
			JSONObject bodyJson = new JSONObject();
			bodyJson.put("ticker","红手指");
			bodyJson.put("title","红手指");
			bodyJson.put("text",notice.getNoticeContent());
			bodyJson.put("after_open","go_custom");
			bodyJson.put("custom", "1");
			bodyJson.put("builder_id",1);
			bodyJson.put("play_vibrate","true");
	        bodyJson.put("play_lights","true");
	        bodyJson.put("play_sound","true");
			
			payLoadJson.put("body", bodyJson);
			json.put("payload", payLoadJson);
			
			JSONObject policyJson = new JSONObject();
			policyJson.put("start_time", DateUtils.getAfterMinuteDate(new Date(),2));
			policyJson.put("expire_time", DateUtils.getAfterDate(new Date(), 24));
			
			json.put("policy", policyJson);
			json.put("description", notice.getNoticeContent());
			
			
			UMengPushMessage uMengPush = new UMengPushMessage();
			Map<String,Object> params = uMengPush.messageContents(json, uMengUtils.getHost(), uMengUtils.getMothed(), 
					uMengUtils.getUserAgent(), uMengUtils.getAppkey(), 
					uMengUtils.getAppMasterSecret());
			
			if(!"SUCCESS".equals(params.get("ret"))){
				pushUmengInfoLogService.updateToFinishError(request,id, errorMap.get(map.get("errorCode")).toString());
				throw new BusinessException("推送公告失败，"+errorMap.get(params.get("errorCode")).toString());
			}else{
				log.debug("filecast push notice success：[noticeContent{},pushExist]",new Object[]{notice.getNoticeContent(),pushExist});
				pushUmengInfoLogService.updateSendToUMeng(request,id, params.get("taskId").toString());
				
			}
			
			log.info(params.toString());
			
			return params;
		}
	}
	
	
	/**
	 * 广播式推送公告
	 * @param deviceToken
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sendAllUser(HttpServletRequest request,RfNotice notice,String pushType,Integer id) throws Exception{
		String label = "";
		SysConfig config = configService.selectByConfingCode("config_umeng_filter_label");
		if(null == config){
			label = "debug";
		}else{
			label = config.getConfigValue();
		}
		
		JSONObject json = new JSONObject();
		json.put("appkey", uMengUtils.getAppkey());
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		json.put("timestamp", timestamp);
		json.put("type", "broadcast");
		json.put("alias", "alias");
		json.put("alias_type", "alias");
		
		JSONObject tagJson = new JSONObject(); 
		tagJson.put("tag", label);
		JSONObject andJson = new JSONObject(); 
		andJson.put("and", "["+tagJson.toString()+"]");
		JSONObject whereJson = new JSONObject(); 
		whereJson.put("where", andJson);
		
		json.put("filter", whereJson);
		
		JSONObject payLoadJson = new JSONObject();
		payLoadJson.put("display_type", "notification");
		
		JSONObject bodyJson = new JSONObject();
		bodyJson.put("ticker","红手指");
		bodyJson.put("title","红手指");
		bodyJson.put("text",notice.getNoticeContent());
		bodyJson.put("after_open","go_custom");
		bodyJson.put("custom", "1");
		bodyJson.put("builder_id",1);
		bodyJson.put("play_vibrate","true");
        bodyJson.put("play_lights","true");
        bodyJson.put("play_sound","true");
		
		payLoadJson.put("body", bodyJson);
		json.put("payload", payLoadJson);
		
		JSONObject policyJson = new JSONObject();
		policyJson.put("start_time", DateUtils.getAfterMinuteDate(new Date(),2));
		policyJson.put("expire_time", DateUtils.getAfterDate(new Date(), 24));
		
		json.put("policy", policyJson);
		json.put("description", notice.getNoticeContent());
		
		UMengPushMessage uMengPush = new UMengPushMessage();
		Map<String,Object> params = uMengPush.messageContents(json, uMengUtils.getHost(), uMengUtils.getMothed(), 
				uMengUtils.getUserAgent(), uMengUtils.getAppkey(), 
				uMengUtils.getAppMasterSecret());
		
		
		Map<String,Object> errorMap = uMengPush.readErrorPro();
		if(!"SUCCESS".equals(params.get("ret"))){
			
			pushUmengInfoLogService.updateToFinishError(request,id, errorMap.get(params.get("errorCode")).toString());
			
		    log.debug("broadcast push notice fail：[noticeContent{}]",new Object[]{notice.getNoticeContent()});
		    throw new BusinessException("推送公告失败，"+errorMap.get(params.get("errorCode")).toString());
		}else{
			log.debug("broadcast push notice success：[noticeContent{}]",new Object[]{notice.getNoticeContent()});
			pushUmengInfoLogService.updateSendToUMeng(request,id, params.get("taskId").toString());
			
		}
		
		return params;
	}
	
	public Integer insertVo(PushUmengInfoLog vo){
		infoLogMapper.insert(vo);
		return vo.getId();
	}
	
	public List<RfUserData> selectDeviceToken(Map<String,Object> map){
		return mapper.selectDeviceTokenByMap(map);
	}
	
	public List<RfUserData> selectDeviceTokenByPad(Map<String,Object> map){
		return mapper.selectDeviceTokenByPad(map);
	}
	
	public RfUserData getUserData(Integer userId, boolean secureFlag) throws Exception{
		RfUserData userData = this.get(userId);
		if(userData != null && secureFlag){
			if(StringUtils.isNotBlank(userData.getBankCard())){
				int length = userData.getBankCard().length();
				String secureStr = "";
				for (int i=0;i<length-4;i++) {
					secureStr += "*";
				}
				length = length <= 4 ? 4:length;
				String bankCard = userData.getBankCard().substring(length-4);
				userData.setBankCard(secureStr+bankCard);
			}
			if(StringUtils.isNotBlank(userData.getBankUsername())){
				int length = userData.getBankUsername().length();
				String secureStr = "";
				for (int i=0;i<length-1;i++) {
					secureStr += "*";
				}
				String bankUsername = userData.getBankUsername().substring(length-1);
				userData.setBankUsername(secureStr+bankUsername);
			}
//			if(StringUtils.isNotBlank(userData.getContactPhone())){
//				int length = userData.getContactPhone().length();
//				String secureStr = "";
//				for (int i=0;i<length-4;i++) {
//					secureStr += "*";
//				}
//				String contactPhone = userData.getContactPhone().substring(length-4);
//				userData.setContactPhone(secureStr+contactPhone);
//			}
			if(StringUtils.isNotBlank(userData.getSecurePwd())){
				userData.setSecurePwd("********");
			}
		}
		return userData;
	}
}
