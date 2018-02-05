package com.redfinger.manager.modules.member.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.type.StringTypeHandler;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.InviteUserIdType;
import com.redfinger.manager.common.constants.MsgTemplateType;
import com.redfinger.manager.common.constants.PadClassify;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.domain.RfMsgTemplate;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfSmsTemplate;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserData;
import com.redfinger.manager.common.domain.RfUserLabel;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.ViewPadUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.jsm.FingerProducer;
import com.redfinger.manager.common.jsm.MessageProducer;
import com.redfinger.manager.common.jsm.WeixinProducer;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.facility.dto.UserPadDto;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.FacilityService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.facility.service.VideoService;
import com.redfinger.manager.modules.facility.service.ViewPadUserService;
import com.redfinger.manager.modules.fault.service.FaultMsgTemplateService;
import com.redfinger.manager.modules.label.service.LabelService;
import com.redfinger.manager.modules.log.service.PadHandleLogService;
import com.redfinger.manager.modules.log.service.UserPadLogService;
import com.redfinger.manager.modules.member.service.UserDataService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.member.service.UserSmsService;
import com.redfinger.manager.modules.sms.bean.SendResult;
import com.redfinger.manager.modules.sms.service.SmsTemplateService;

@Controller
@RequestMapping(value = "/member/user")
public class UserController extends BaseController {
	@Autowired
	UserService service;
	@Autowired
	UserSmsService smsService;
	@Autowired
	IdcService idcService;
	@Autowired
	ControlService controlService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	VideoService videoService;
	@Autowired
	UserPadLogService userPadLogService;
	@Autowired
	PadService padService;
	@Autowired
	SmsTemplateService smsTemplateService;
	@Autowired
	PadHandleLogService padHandleLogService;
    @Autowired
    ViewPadUserService viewPadUserService;
    @Autowired
    MessageProducer messageProducer;
    @Autowired
	private FingerProducer fingerProducer;
    @Autowired
	private FacilityService facilityService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
	private WeixinProducer weixinProducer;
    @Autowired
	FaultMsgTemplateService msgTemplateService;
    
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<String> userSourceList = service.selectUserSource();
		model.addAttribute("userSourceList", userSourceList);
		List<String> clientSourceList = service.selectClientSource();
		model.addAttribute("clientSourceList", clientSourceList);
		model.addAttribute("inviteTypeMap", InviteUserIdType.DICT_MAP);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfUser> list(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfUser bean,String createTimeBegin,String createTimeEnd,String nickName) throws Exception {
		
		List<Integer> userIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(nickName)){
			List<RfUserData> userDatas = userDataService.initQuery().andEqualTo("nickName", nickName).findAll();
			if(null != userDatas && userDatas.size() > 0){
				userIds = Collections3.extractToList(userDatas, "userId");
			}else{
				userIds.add(-1);
			}
		}
		if(StringUtils.isNotEmpty(bean.getClient())){
			if(bean.getClient().equals("pc")){
				bean.setClient("win");
			}
		}
		List<RfUser> list = service.initQuery(bean)
				.andLike("userName", bean.getUserName())
				.andLike("userEmail", bean.getUserEmail())
				.andLike("userMobilePhone", bean.getUserMobilePhone())
				.andEqualTo("loginCount", bean.getLoginCount())
				.andEqualTo("userSource", bean.getUserSource())
				.andEqualTo("client", bean.getClient())
				.andEqualTo("userClassify", bean.getUserClassify()) //根据用户类型查询
				.andIn("userId", userIds)
				.andGreaterThanOrEqualTo("createTime",DateUtils.parseDate(createTimeBegin))
				.andLessThanOrEqualTo("createTime",DateUtils.parseDateAddOneDay(createTimeEnd) )
				.andGreaterThanOrEqualTo("loginTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("loginTime",DateUtils.parseDateAddOneDay(bean.getEndTimeStr()) )
				.andEqualTo("userGender", bean.getUserGender())
				.andEqualTo("externalUserId", bean.getExternalUserId())
				.andEqualTo("inviteType", bean.getInviteType())
				.addOrderClause("userId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfUser rfUser : list) {
				rfUser.getMap().put("vipPadCount", userPadService.getPadCount(rfUser.getUserId(), ConstantsDb.rfUserPadPadGradeVip()));
				rfUser.getMap().put("svipPadCount", userPadService.getPadCount(rfUser.getUserId(), ConstantsDb.rfUserPadPadGradesVip()));
				rfUser.getMap().put("showUserId",rfUser.getExternalUserId());
			}
		}
		PageInfo<RfUser> pageInfo = new PageInfo<RfUser>(list);
		return pageInfo;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "padList")
	public PageInfo<ViewPadUser> padList(HttpServletRequest request, HttpServletResponse response, 
			Model model, ViewPadUser bean,Integer userId) throws Exception {
		List<Integer> padIds = Lists.newArrayList();
	  	
  		if(null != userId){
  			List<RfUserPad> userPadList = userPadService.initQuery().
  					andEqualTo("userId", userId).findAll();
  			
  			if(!Collections3.isEmpty(userPadList)){
  				padIds=Collections3.extractToList(userPadList, "padId");
  				
  				// 查询条件
  	  			List<ViewPadUser> list = viewPadUserService.initQuery(bean)
  	  				    .andIn("padId", padIds).addOrderClause("padId", "asc")
  	  					.addOrderForce(bean.getSort(), bean.getOrder())
  	  					.pageDelTrue(bean.getPage(), bean.getRows());
  	  			for (ViewPadUser viewPadUser : list) {
  	  				if(StringUtils.isNotBlank(viewPadUser.getUserPadNameT2())){
  	  					viewPadUser.getMap().put("padName", viewPadUser.getUserPadNameT2());
  	  				}else{
  	  					viewPadUser.getMap().put("padName", viewPadUser.getPadName());
  	  				}
  	  			}
  	  			PageInfo<ViewPadUser> pageInfo = new PageInfo<ViewPadUser>(list);	
  	  			return pageInfo;
  			}
  		}
  		return null;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		if (bean.getUserId() == null) {
			throw new BusinessException("该用户当前没有绑定设备！");
		} else {
			bean = service.get(bean.getUserId());
			model.addAttribute("bean", bean);
		}

		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		/*
		 * String[] idArray = StringUtils.split(bean.getIds(), ","); for (String
		 * idStr : idArray) { RfUser user = service.get(idStr); if
		 * (ConstantsDb.FALSE.equals(user.getUserStatus())) {
		 * 
		 * user.setUserStatus(ConstantsDb.TRUE); service.update(request, user);
		 * } }
		 */
		service.start(request, bean);

	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		/*
		 * String[] idArray = StringUtils.split(bean.getIds(), ","); for (String
		 * idStr : idArray) { RfUser user = service.get(idStr); if
		 * (ConstantsDb.TRUE.equals(user.getUserStatus())) {
		 * user.setUserStatus(ConstantsDb.FALSE); service.update(request, user);
		 * } }
		 */
		service.stop(request, bean);

	}

	@RequestMapping(value = "look")
	public String look(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		bean = service.get(bean.getUserId());
		bean.setUserPwd(null);
		List<ViewPadUser> userpadlist = viewPadUserService.initQuery().andEqualTo("userIdT2", bean.getUserId()).findStopTrue();
		model.addAttribute("padList", userpadlist);
		// 数据字典实现后 某些数序需要先查数据字典 然后将要显示的名字输出到前台页面显示
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	// 用户可能存在多台设备 解绑时需要选择解绑的设备
	@RequestMapping(value = "relieve")
	public void relieve(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,String padIds) throws Exception {
		String[] idArray =padIds.split(",");
		for (String idStr : idArray) {
			if("".equals(idStr)||null==idStr){
				continue;
			}
			List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", Integer.parseInt(idStr)).findStopTrue();
			if (userPad.isEmpty())
				continue;
			// 解绑后删除USER_PAD表记录
			userPadService.relieve(request, userPad.get(0).getUserPadId());
			
			try{
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", userPad.get(0).getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	// 解绑页面
	@RequestMapping(value = "relieveForm")
	public String relieveFormu(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) {
		RfUser user = service.get(bean.getUserId());
		//判断USER_PAD表中的USERID下的数据是否正常
		userPadService.userPadStatus(request,  bean.getUserId());
		List<RfUserPad>list=userPadService.initQuery().andEqualTo("userId", bean.getUserId()).findStopTrue();
		if(Collections3.isEmpty(list)){
			throw new BusinessException("该用户当前没有绑定设备！");
		}
		model.addAttribute("user", user);
		return this.toPage(request, response, model);
	}

	// 绑定页面
	@RequestMapping(value = "userForm")
	public String userForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) {
		RfUser user = service.get(bean.getUserId());
		model.addAttribute("user", user);
		return this.toPage(request, response, model);
	}

	// 绑定
	@RequestMapping(value = "binding")
	public void binding(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean) throws Exception {
		padService.bind(request, bean);
		try{
			//向用户发送jms
			JSONObject sendMessageObj = new JSONObject();
			sendMessageObj.put("opType", "refresh");
			sendMessageObj.put("userId", bean.getUserId());
			String sendMessageStr = sendMessageObj.toString();
			log.info("sendMessageStr: "+ sendMessageStr);
			// 发送jms消息
			fingerProducer.sendMessage(sendMessageStr);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@ResponseBody
	@RequestMapping(value = "padlist")
	public PageInfo<RfPad> padlist(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String userId, String padCode,String padCode2,String padClassify) throws Exception {
		List<RfFacility> facilityList = facilityService.initQuery().findAll();
		Map<String, RfFacility> facilityMap = new HashMap<String,RfFacility>();
		for (RfFacility rfFacility : facilityList) {
			facilityMap.put(rfFacility.getFacilityCode(), rfFacility);
		}
		if (userId==null) {
			List<RfPad> padlist = padService.initQuery(bean)
					.andEqualTo("idcId", bean.getIdcId())
					.andEqualTo("userControlId", bean.getUserControlId())
					.andGreaterThanOrEqualTo("padCode", padCode)
					.andLessThanOrEqualTo("padCode", padCode2)
					.andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart())
					.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusNobind())
					.andEqualTo("padClassify",padClassify)//更换设备只能更换同设备类别的(1主营设备、2游戏设备)//TODO
					//.andEqualTo("padClassify",StringUtils.isNotEmpty(padClassify)?padClassify:PadClassify.PAD_CLASSIFY_MAIN)//更换设备只能更换同设备类别的(1主营设备、2游戏设备)//TODO
					.andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusNomarl())
					.andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOnline())
					.andEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOnline())
					/*.andIsNotNull("deviceId")
					.andIsNotNull("vmStatus")*/
					.addOrderClause("modifyTime", "asc")
					.addOrderForce(bean.getSort(), bean.getOrder())
					.pageDelTrue(bean.getPage(), bean.getRows());
			RfFacility facility;
			for (RfPad rfPad : padlist) {
				facility = facilityMap.get(rfPad.getPadSource());
				if(facility != null){
					rfPad.getMap().put("facilityName", facility.getFacilityName());
				}
			}
			PageInfo<RfPad> pageInfo = new PageInfo<RfPad>(padlist);
			return pageInfo;
		} else {
			List<Integer> padList = new ArrayList<Integer>();
			List<RfUserPad> userPadList = userPadService.initQuery().andEqualTo("userId", Integer.parseInt(userId)).findStopTrue();
	         if(Collections3.isEmpty(userPadList))
			{
				throw new BusinessException("该用户当前没有绑定设备！");
			}
			for (RfUserPad rfUserPad : userPadList) {
				padList.add(rfUserPad.getPadId());
			}
			List<RfPad> padlist =   padService.initQuery(bean)
							        .andLike("padName", bean.getPadName())
								    .andIn("padId", padList)
								    .andLike("padCode", bean.getPadCode())
									.addOrderClause("reorder", "asc")
									.addOrderForce(bean.getSort(), bean.getOrder())
									.pageDelTrue(bean.getPage(), bean.getRows());
			RfFacility facility;
			for (RfPad rfPad : padlist) {
				facility = facilityMap.get(rfPad.getPadSource());
				if(facility != null){
					rfPad.getMap().put("facilityName", facility.getFacilityName());
				}
			}
			PageInfo<RfPad> pageInfo = new PageInfo<RfPad>(padlist);
			return pageInfo;
		}
	}
	// 更换renewalForm
	@RequestMapping(value = "renewalForm")
	public String renewalForm(HttpServletRequest request, HttpServletResponse response, Model model, String userId, RfPad bean) {
		RfUser user = service.get(Integer.parseInt(userId));
		List<RfUserPad> userPadList = userPadService.initQuery().andEqualTo("userId", user.getUserId()).findStopTrue();
		if (userPadList.size() > 0) {
			List<Integer> padList = new ArrayList<Integer>();
			for (RfUserPad rfUserPad : userPadList) {
				padList.add(rfUserPad.getPadId());
			}
			List<RfPad> padlist = padService.initQuery(bean)
					.andEqualTo("padControlId", bean.getPadControlId())
					.andEqualTo("enableStatus", bean.getEnableStatus())
					.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind())
					.andLike("padName", bean.getPadName())
					.andIn("padId", padList)
					.andLike("padCode", bean.getPadCode())
					.addOrderClause("reorder", "asc")
					.addOrderForce(bean.getSort(), bean.getOrder()).findDelTrue();
			model.addAttribute("padList", padlist);
			model.addAttribute("userPad", userPadList);
		}else{
			throw new BusinessException("会员没有可更换的设备");
		}
		model.addAttribute("user", user);
		
		List<RfMsgTemplate> msgTemplates_msg = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_MSG)
				.addOrderClause("reorder", "asc")
				.findStopTrue();
		List<RfMsgTemplate> msgTemplates_weixin = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_WEIXIN)
				.addOrderClause("reorder", "asc")
				.findStopTrue();
		List<RfMsgTemplate> msgTemplates_msg_last = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_MSG)
				.addOrderClause("createTime", "desc")
				.findStopTrue();
		List<RfMsgTemplate> msgTemplates_weixin_last = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_WEIXIN)
				.addOrderClause("createTime", "desc")
				.findStopTrue();
		
		model.addAttribute("msgTemplates_msg", msgTemplates_msg);
		model.addAttribute("msgTemplates_weixin", msgTemplates_weixin);
		model.addAttribute("msgTemplates_msg_last", msgTemplates_msg_last.size()>0?msgTemplates_msg_last.get(0).getTemplateText():"");
		model.addAttribute("msgTemplates_weixin_last", msgTemplates_weixin_last.size()>0?msgTemplates_weixin_last.get(0).getTemplateText():"");
		
		List<RfIdc> idcList = idcService.initQuery().findStopTrue();
		model.addAttribute("idcList", idcList);
		
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", "USER").addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("controlList", JSONArray.fromObject(controlList).toString());
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "renewal")  
	public void renewal(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,
			String isSendMessage,String isSendWechart,String sendMessageTemplate, String sendWechartTemplate) throws Exception {
		
		//对是否发送消息先做判断
				if(StringUtils.isNotBlank(sendMessageTemplate)){
					isSendMessage=YesOrNoStatus.YES;
				}else{
					isSendMessage=YesOrNoStatus.NO;
				}
				if(StringUtils.isNotBlank(sendWechartTemplate)){
					isSendWechart=YesOrNoStatus.YES;
				}else{
					isSendWechart=YesOrNoStatus.NO;
				}
				
		String[] pad = bean.getIds().split(",");
		String[] code = bean.getRemark().split(",");
		for (int i = 0; i < pad.length; i++) {
			RfPad oldpad = padService.get(Integer.parseInt(pad[i]));
			List<RfUserPad>list=userPadService.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padId", oldpad.getPadId()).findStopTrue();
			if(Collections3.isEmpty(list)){
				throw new BusinessException("输入了错误的绑定信息，请重新进入更换页面");
			}
			if (!(oldpad.getPadCode().equals(code[i]))) {
				RfUserPad rfUserPad =list.get(0); 
				// 旧设备解绑日志
				// 删除原有绑定记录 user_pad
				RfPad newPad = padService.get( padService.initQuery().andEqualTo("padCode", code[i]).singleDelTrue().get(0).getPadId());
				
				
				if (newPad == null) {
					throw new BusinessException("设备号错误，请重新选择设备");
				}
				if (newPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
					throw new BusinessException(newPad.getPadCode()+"设备绑定中，请重新选择设备");
				}
				if (!(ConstantsDb.rfPadPadStatusOnline().equals(newPad.getPadStatus()))) {
					throw new BusinessException(newPad.getPadCode()+"设备当前状态不能绑定");
				}
				if (newPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
					throw new BusinessException(newPad.getPadCode()+"设备故障中，请重新选择设备");
				}
				if(!newPad.getPadClassify().equals(oldpad.getPadClassify())){//TODO判断新旧设备是否属于同一设备类别:1主营设备、2游戏设备
					throw new BusinessException("新旧设备类别不一致，无法更换");
				}
				Integer wechartTemplateId = userPadService.renewal(request,rfUserPad,newPad,isSendMessage,isSendWechart,sendMessageTemplate,sendWechartTemplate);
				
				try{
					//向用户发送jms
					JSONObject sendMessageObj = new JSONObject();
					sendMessageObj.put("opType", "refresh");
					sendMessageObj.put("userId", rfUserPad.getUserId());
					String sendMessageStr = sendMessageObj.toString();
					log.info("sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					fingerProducer.sendMessage(sendMessageStr);
					
					//向用户发送jms
					sendMessageObj.clear();
					sendMessageObj.put("templateId", wechartTemplateId);
					sendMessageStr = sendMessageObj.toString();
					log.info("renewal sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					weixinProducer.sendMessage(sendMessageStr);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "sendCheckbox")
	public SendResult sendCheckbox(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String smsContent, Integer templateId,String batchName) throws Exception {
		return smsService.sendSms(request, bean.getIds(), smsContent, templateId,batchName);
	}

	@ResponseBody
	@RequestMapping(value = "sendSearch")
	public SendResult sendSearch(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean, String smsContent, Integer templateId,String batchName) throws Exception {
		/*
		 * if(StringUtils.isNotBlank(userId) ||
		 * StringUtils.isNotBlank(bindMobile)){ List<RfUserPad>
		 * userPadList=userPadService.initQuery().andEqualTo("userId",
		 * userId).andEqualTo("bindMobile",bindMobile).findAll();
		 * if(!CollectionUtils.isEmpty(userPadList)){
		 * padIds=(List<Integer>)Collections3.extractToList(userPadList,
		 * "padId");//取LIST中的PADID组成串 }else{ padIds=Lists.newArrayList(-1); } }
		 */
		List<RfUser> list = service.initQuery(bean).andLike("userEmail", bean.getUserEmail()).andLike("userMobilePhone", bean.getUserMobilePhone()).andLike("userName", bean.getUserName()).addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder()).findDelTrue();
		String ids = Collections3.extractToString(list, "userId", ",");
		return smsService.sendSms(request, ids, smsContent, templateId,batchName);
		// return null;
	}

	@RequestMapping(value = "smsForm")
	public String smsForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		List<RfSmsTemplate> templateList = smsTemplateService.initQuery().addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("templateList", templateList);
		model.addAttribute("templateListJson", JsonUtils.ObjectToString(templateList));
		if (CollectionUtils.isEmpty(templateList)) {
			model.addAttribute("defaultContent", "");
		} else {
			model.addAttribute("defaultContent", templateList.get(0).getSmsTemplateContent());
		}
		return this.toPage(request, response, model);
	}

	//会员导出
	@RequestMapping(value="export")
    public String export(HttpServletRequest request,HttpServletResponse response,Model model,RfUser bean,String nickName,
    		String createTimeBegin,String createTimeEnd,String exportHead, String exportField, String exportName)throws Exception{
		exportHead=StringUtils.removeEnd(exportHead, ",");
	    exportField=StringUtils.removeEnd(exportField, ",");
	    response.setContentType("application/binary;charset=UTF-8");
	    ServletOutputStream outputStream=response.getOutputStream();
	    response.setHeader("Content-disposition", "attachment;filename="+ExcelUtils.getFileName(request, exportName)+".xls");
	    Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
	    ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		boolean keep = true;
		int page = 1;
		while (keep) {
			bean.setPage(page);
			bean.setRows(1000);
			PageInfo<RfUser> pageInfo = this.list(request, response, model, bean,createTimeBegin,createTimeEnd,nickName);
			List<RfUser> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				for (RfUser rfUser : list) {
                   rfUser.setEnableStatus(DictHelper.getLabel("global.enable_status", rfUser.getEnableStatus()));
				}
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","), pageInfo.getStartRow());
				if (pageInfo.isHasNextPage()) {
					page++;
					continue;
				}
			}
			keep = false;
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//修改红豆币
	@RequestMapping(value="rbc")
	public void rbc(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,int rbcGet) throws Exception {
		if(bean==null){
			return;
		}
		int _rbc = 0;
		if(bean.getRbcAmount()!=null){
			_rbc = bean.getRbcAmount();
		}
		if(_rbc+rbcGet<0){
			throw new BusinessException("扣除红豆时不能大于拥有红豆！");
		}
		RfUser rUser = new RfUser();
		rUser.setUserId(bean.getUserId());
		rUser.setRbcAmount(rbcGet);
		service.rbcUpdate(request, rUser);

	}
	
	//批量修改
		@RequestMapping(value="batch")
		public void batch(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,int rbcGet,String userPhone,String idType) throws Exception {
			service.updateRbcs(request,rbcGet,userPhone,idType);
		}
	@RequestMapping(value = "formRbc")
	public String formRbc(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "giveRbc")
	public String giveRbc(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value="maintainBatch")
	public void maintainBatch(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,int common,int vip,int svip) throws Exception {
		service.giveRbc(request, common, vip, svip);
	}

	/**
	 * 根据用户和设备批量新增时间form（赠送天数）
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addTimeForm")
	public String addTimeForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		if (bean.getUserId() == null) {
			throw new BusinessException("请选择用户！");
		} else {
			bean = service.get(bean.getUserId());
			model.addAttribute("bean", bean);
		}

		return this.toPage(request, response, model);
	}
	
	/**
	 * 根据用户和设备批量新增时间
	 * @param request
	 * @param response
	 * @param bean
	 * @param timeType
	 * @param expireTime
	 * @param padIds
	 */
	@RequestMapping(value="addTime")
	public void addTime(HttpServletRequest request, HttpServletResponse response, RfUser bean, String timeType,Integer expireTime,String padIds){
		log.info("赠送天数,[timeType:{},expireTime:{},padIds:{},userId:{}]",new Object[]{timeType,expireTime,padIds,bean.getUserId()});
		if(StringUtils.isBlank(padIds)){
			throw new BusinessException("请选择设备！");
		}
		if(StringUtils.isBlank(timeType)){
			throw new BusinessException("请选择时间单位！");
		}
		
		if(null == expireTime){
			throw new BusinessException("请填写赠送数量！");
		}
		if(null == bean.getUserId()){
			throw new BusinessException("请选择用户！");
		}
		service.updateExpireTime(request, response, bean, timeType, expireTime, padIds);
	}
	
	/**
	 * 根据设备批量修改form（批量修改天数）
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "batchUpdateTimeForm")
	public String batchUpdateTimeForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	/**
	 * 根据设备批量新增时间
	 * @param request
	 * @param response
	 * @param bean
	 * @param timeType
	 * @param expireTime
	 * @param padIds
	 */
	@RequestMapping(value="batchUpdateTime")
	public void batchUpdateTime(HttpServletRequest request, HttpServletResponse response, RfUser bean, String timeType,Integer expireTime,String padCodes){
		log.info("批量新增,[timeType:{},expireTime:{},userId:{}]",new Object[]{timeType,expireTime,bean.getUserId()});
		if(StringUtils.isBlank(padCodes)){
			throw new BusinessException("请填写设备！");
		}
		if(StringUtils.isBlank(timeType)){
			throw new BusinessException("请选择时间单位！");
		}
		
		if(null == expireTime){
			throw new BusinessException("请填写赠送数量！");
		}
		service.batchUpdateExpireTime(request, response, timeType, expireTime, padCodes);
	}
	
	/**
	 * 维护赠送天数form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "maintAddTimeForm")
	public String maintAddTimeForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		List<RfIdc> idcs = idcService.initQuery().findStopTrue();
		model.addAttribute("idcs", idcs);
		return this.toPage(request, response, model);
	}
	
	/**
	 * 维护赠送天数form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "maintAddTime")
	public void maintAddTimeForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,
			Integer idcId,String timeType,Integer tasteExpireTime,Integer normalExpireTime,Integer vipExpireTime,Integer svipExpireTime) throws Exception {
		log.info("维护赠送天数,[timeType:{},idcId:{}，tasteEpireTime:{},normalExpireTime:{},vipExpireTime:{},svipExpireTime:{}]",
				new Object[]{timeType,idcId,tasteExpireTime,normalExpireTime,vipExpireTime,svipExpireTime});
		if(null == idcId){
			throw new BusinessException("请选择机房！");
		}
		if(StringUtils.isBlank(timeType)){
			throw new BusinessException("请选择时间类型！");
		}
		if(null == tasteExpireTime && null == normalExpireTime 
				&& null == vipExpireTime && null == svipExpireTime ){
			throw new BusinessException("请填写其中一个赠送时间！");
		}
		service.maintAddTime(request, response, model, bean, idcId, timeType, tasteExpireTime, normalExpireTime, vipExpireTime, svipExpireTime);
	}
	
	/**
	 * 根据号码批量批量绑定设备form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "batchBindPadForm")
	public String batchBindPadForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		List<RfIdc> idcList = idcService.initQuery().findStopTrue();
		model.addAttribute("idcList", idcList);
		
		List<RfControl> controlList = controlService.initQuery().findStopTrue();
		model.addAttribute("controlList", controlList);
		return this.toPage(request, response, model);
	}
	
	/**
	 * 根据号码批量批量绑定设备
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "batchBindPad")
	public void batchBindPad(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,
			String padGrade, String timeType,Integer bindTime,String userMobilePhones,
			String idcId,String controlId,String padCode,String padCode2) throws Exception {
		if(org.apache.commons.lang.StringUtils.isBlank(padGrade)){
			throw new BusinessException("请选择设备类型！");
		}
		if(org.apache.commons.lang.StringUtils.isBlank(timeType)){
			throw new BusinessException("请选择时间类型！");
		}
		if(null == bindTime || bindTime <= 0 ){
			throw new BusinessException("请填写时间！");
		}
		if(org.apache.commons.lang.StringUtils.isBlank(userMobilePhones)){
			throw new BusinessException("请填写号码！");
		}
		
		service.batchBindPad(request, response, padGrade, timeType, bindTime, userMobilePhones,idcId,controlId,padCode,padCode2);
	}
	
	/**
	 * 根据机房id查询控制节点
	 * @param request
	 * @param response
	 * @param model
	 * @param idcId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getControlByIdcId")
	public Map<String,Object> getControlByIdcId(HttpServletRequest request, HttpServletResponse response, Model model,String idcId) throws Exception {
		log.info("getControlByIdcId idcId:{}",idcId);
		Map<String,Object> params = new HashMap<String,Object>();
		List<RfControl> controlList = null;
		if(StringUtils.isNoneBlank(idcId)){
			controlList = controlService.initQuery().andEqualTo("idcId", Integer.parseInt(idcId)).findStopTrue();
			
		}
		params.put("controlList", controlList);
		return params;
	}
	
	/**
	 * 用户批量绑定标签form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "batchBindLabelForm")
	public String batchBindLabelForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		List<RfLabel> list = labelService.initQuery().
				andEqualTo("labelType", ConstantsDb.labelTypeUser()).findStopTrue();
		model.addAttribute("list", list);
		return this.toPage(request, response, model);
	}
	
	/**
	 * 单个用户绑定标签form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bindLabelForm")
	public String bindLabelForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		List<RfLabel> list = labelService.initQuery().
				andEqualTo("labelType", ConstantsDb.labelTypeUser()).findStopTrue();
		bean = service.initQuery(bean).get(bean.getUserId());
		
		List<RfUserLabel> userLabelList = labelService.selectByUserId(bean.getUserId());
		Map<String,RfUserLabel> userLabelMap = new HashMap<String,RfUserLabel>();
		for (RfUserLabel rfUserLabel : userLabelList) {
			userLabelMap.put(rfUserLabel.getLabelId() + "_" + bean.getUserId(), rfUserLabel);
		}
		
		model.addAttribute("userLabelMap", userLabelMap);
		model.addAttribute("bean", bean);
		model.addAttribute("list", list);
		model.addAttribute("userLabelList", userLabelList);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "batchBindLabel")
	public void batchBindLabel(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,
			String labelIds,String userPhone,String idType) throws Exception {
		if(StringUtils.isBlank(labelIds)){
			throw new BusinessException("请选择标签");
		}
		
		if(StringUtils.isBlank(userPhone)){
			throw new BusinessException("请填写用户号码");
		}
		service.saveUserLabel(request, labelIds, userPhone, idType);
	}
	
	@RequestMapping(value = "bindLabel")
	public void bindLabel(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,
			String labelIds) throws Exception {
		if(StringUtils.isBlank(labelIds)){
			throw new BusinessException("请选择标签");
		}
		
		if(null == bean.getUserId()){
			throw new BusinessException("请选择用户号码");
		}
		service.saveUserLabel(request, labelIds, bean.getUserMobilePhone(),null);
	}
	
	
	/**
	 * 用户允许登录ios的form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "allowLoginIosForm")
	public String allowLoginIosForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		
		return this.toPage(request, response, model);
	}
	
	/**
	 * 记录允许登录ios的用户
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param userPhone
	 * @throws Exception
	 */
	@RequestMapping(value = "allowLoginIos")
	public void allowLoginIos(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,String userPhone) throws Exception {
		
		if(StringUtils.isBlank(userPhone)){
			throw new BusinessException("请填写用户号码");
		}
		service.saveDetaUser(request,userPhone);
	}
	
	
	/**
	 * 限制普通设备申请的Form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "limitUseForm")
	public String limitUseForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		
		return this.toPage(request, response, model);
	}
	
	/**
	 * 记录限制普通设备申请的用户
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param userPhone
	 * @throws Exception
	 */
	@RequestMapping(value = "limitUse")
	public void limitUse(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,String userPhone,String idType) throws Exception {
		
		if (StringUtils.isBlank(userPhone)) {
			throw new BusinessException("请填写用户号码");
		}
		service.saveLimitUse(request, userPhone,idType);
	}
	
	@RequestMapping(value = "classifyForm")
	public String classifyForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		model.addAttribute("userInfo",bean);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "updateClassify")
	public void updateClassify(HttpServletRequest request, HttpServletResponse response, Model model, Integer userId, String userClassify) throws Exception {

		service.updateUserClassify(request, userId, userClassify);
	}
}
