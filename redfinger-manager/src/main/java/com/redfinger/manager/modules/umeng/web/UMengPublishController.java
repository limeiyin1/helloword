package com.redfinger.manager.modules.umeng.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.PushUmengInfoLog;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.jsm.PushNoticeProducer;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.PushStatus;
import com.redfinger.manager.common.utils.PushType;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserDataService;
import com.redfinger.manager.modules.notice.service.NoticeService;
import com.redfinger.manager.modules.umeng.service.UmengInfoService;
@Controller
@RequestMapping(value = "/umeng/publish")
public class UMengPublishController  extends BaseController {
	@Autowired
	UmengInfoService service;
	@Autowired
	UserDataService userDataService;
	@Autowired
	NoticeService noticeService;
	@Autowired
	PushNoticeProducer pushNoticeProducer;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		map = PushType.DICT_MAP;
		model.addAttribute("pushType", map);
		model.addAttribute("pushStatus", PushStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<PushUmengInfoLog> list(HttpServletRequest request, HttpServletResponse response, Model model, PushUmengInfoLog bean) throws Exception {
		
		Date startDate = null;
		Date endDate = null;
		if(StringUtils.isNotBlank(bean.getBeginTimeStr())){
			startDate = DateUtils.parseDate(bean.getBeginTimeStr());
		}
		
		if(StringUtils.isNotBlank(bean.getEndTimeStr())){
			endDate = DateUtils.parseDate(bean.getEndTimeStr());
		}
		
		List<PushUmengInfoLog> list = service.initQuery()
		.andLike("title", bean.getTitle())
		.andLike("noticeContent", bean.getNoticeContent())
		.andEqualTo("pushType", bean.getPushType())
		.andEqualTo("pushStatus", bean.getPushStatus())
		.andGreaterThanOrEqualTo("createTime", startDate)
		.andLessThanOrEqualTo("endTime", endDate)
		.addOrderClause("createTime", "desc")
		.addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (PushUmengInfoLog infoLog : list) {
				if(StringUtils.isNotBlank(infoLog.getPushType())){
					infoLog.getMap().put("pushTypeName", PushType.DICT_MAP.get(infoLog.getPushType()));
				}
				if(StringUtils.isNotBlank(infoLog.getPushStatus())){
					infoLog.getMap().put("pushStatusName", PushStatus.DICT_MAP.get(infoLog.getPushStatus()));
				}
			}
		}
		
		PageInfo<PushUmengInfoLog> pageInfo = new PageInfo<PushUmengInfoLog>(list);
		return pageInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "rePush")
	public void rePush(HttpServletRequest request, HttpServletResponse response, Model model, PushUmengInfoLog bean) throws Exception {
		if(null != bean.getId()){
			pushNoticeProducer.sendMessage(bean.getId().toString());
    		
			/*PushUmengInfoLog infoLog = service.get(bean.getId());
			
			RfNotice notice = new RfNotice();	
			notice.setNoticeTitle(infoLog.getTitle());
			notice.setNoticeContent(infoLog.getNoticeContent());
			
			Map<String,Object> params = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(infoLog.getPushType())){
				if(PushType.SELECT_NOTICE_PUSH.equals(infoLog.getPushType())){
					if(StringUtils.isNotBlank(infoLog.getPushExist())){
						Map<String,Object> map = this.resolveJson(infoLog.getPushExist());
						if(map.containsKey("userIdIn")){
							String uids = map.get("userIdIn").toString();
							String uid[] = uids.split(",");
							List<Integer> ids = new ArrayList<Integer>();
							for (String string : uid) {
								ids.add(Integer.parseInt(string));
							}
							params.put("userIdIn", ids);
							noticeService.pushUmengInfo(request, YesOrNoStatus.YES, 
									params, notice, PushType.SELECT_NOTICE_PUSH, infoLog.getPushExist(), bean.getId());
						}
					}else{
						throw new BusinessException("此条公告没有条件");
					}
				}else if(PushType.PART_NOTICE_PUSH.equals(infoLog.getPushType())){
					if(StringUtils.isNotBlank(infoLog.getPushExist())){
						Map<String,Object> map = this.resolveJson(infoLog.getPushExist());
						params.clear();
						if(map.containsKey("userId")){
							params.put("userId", Integer.parseInt(map.get("userId").toString()));
						}
						if(map.containsKey("userMobilePhone")){
							params.put("userMobilePhone", map.get("userMobilePhone"));
						}
						if(map.containsKey("userEmail")){
							params.put("userEmail", map.get("userEmail"));
						}
						if(map.containsKey("startDate")){
							params.put("startDate", DateUtils.parseDate(map.get("startDate").toString()));
						}
						if(map.containsKey("endDate")){
							params.put("endDate", DateUtils.parseDate(map.get("endDate").toString()));
						}
						
						noticeService.pushUmengInfo(request, YesOrNoStatus.YES, 
								params, notice, PushType.PART_NOTICE_PUSH, infoLog.getPushExist(), bean.getId());
					}else{
						throw new BusinessException("此条公告没有条件");
					}
				}else if(PushType.PAD_NOTICE_PUSH.equals(infoLog.getPushType())){
					if(StringUtils.isNotBlank(infoLog.getPushExist())){
						Map<String,Object> map = this.resolveJson(infoLog.getPushExist());
						params.clear();
						if(map.containsKey("padName")){
							params.put("padName", map.get("padName"));
						}
						if(map.containsKey("padCode")){
							params.put("padCode", map.get("padCode"));
						}
						if(map.containsKey("padIp")){
							params.put("padIp", map.get("padIp"));
						}
						
						noticeService.pushUmengInfo(request, YesOrNoStatus.YES, 
								params, notice, PushType.PAD_NOTICE_PUSH, infoLog.getPushExist(), bean.getId());
					}else{
						throw new BusinessException("此条公告没有条件");
					}
				}else if(PushType.ALL_NOTICE_PUSH.equals(infoLog.getPushType())){
					noticeService.pushAllUMengInfo(request, YesOrNoStatus.YES, notice, PushType.ALL_NOTICE_PUSH, bean.getId());
				}else if(PushType.EXPIRE_PAD_PUSH.equals(infoLog.getPushType())){
					if(StringUtils.isNotBlank(bean.getDeviceToken())){
						userDataService.sendOne(request,bean.getDeviceToken(), notice, bean.getId());
					}else{
						throw new BusinessException("此条公告没有设备");
					}
				}
			}else{
				throw new BusinessException("此条公告没有类型");
			}*/
		}else{
			throw new BusinessException("请选择数据");
		}
	}
	
	private Map<String,Object> resolveJson (String pushExist){
		JSONObject jsonObject = new JSONObject(pushExist);
        
        Map<String,Object> result = new HashMap<String,Object>();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        while (iterator.hasNext()) {
        	key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
	}
	
}
