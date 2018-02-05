package com.redfinger.manager.modules.fault.web;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.ExportConstants;
import com.redfinger.manager.common.constants.MsgTemplateType;
import com.redfinger.manager.common.constants.PadClassify;
import com.redfinger.manager.common.domain.RfClass;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfExport;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfMsgTemplate;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PermissionHelper;
import com.redfinger.manager.common.helper.SpringContextHolder;
import com.redfinger.manager.common.jsm.ExportProducer;
import com.redfinger.manager.common.jsm.FingerProducer;
import com.redfinger.manager.common.jsm.WeixinProducer;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.FaultFeedbackLastHandlerType;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.Reflections;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.batch.service.ExportService;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.FacilityService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.fault.service.FaultFeedbackService;
import com.redfinger.manager.modules.fault.service.FaultHandleService;
import com.redfinger.manager.modules.fault.service.FaultMsgTemplateService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/fault/self")
public class FaultSelfController extends BaseController {

	@Autowired
	FaultFeedbackService service;
	@Autowired
	ClassService classService;
	@Autowired
	FaultHandleService handleService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	UserService userService;
	@Autowired
	PadService padService;
	@Autowired
	AdminService adminService;
	@Autowired
	IdcService idcService;
	@Autowired
	ControlService controlService;
	@Autowired
	FaultMsgTemplateService msgTemplateService;
	@Autowired
	private FingerProducer fingerProducer;
	@Autowired
	ProducterMessageSender pro;
	@Autowired
	private FacilityService facilityService;
	@Autowired
	private WeixinProducer weixinProducer;
	@Autowired
	ExportService exportService;
	@Autowired
	FilePathUtils filePathUtils;
	@Autowired
	ExportProducer exportProducer;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 故障类型
		model.addAttribute("categoryTree", classService.getStopTrueTreeAndTop(ConstantsDb.rfClassClassTypeFault()));
		// 咨询人员
		List<SysAdmin> zixunList=PermissionHelper.findAdminByRoleList(Lists.newArrayList(ConfigConstantsDb.configRoleZixun().split(",")));
		String systemCreater=ConfigConstantsDb.configFaultClientCreater();
		if(StringUtils.isNotEmpty(systemCreater)){
			zixunList.add(adminService.get(systemCreater));
		}
		model.addAttribute("zixunList", zixunList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfFaultFeedback> list(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean, String padCodeStart, String padCodeEnd,String padStatus, String vmStatus) throws Exception {
		List<String> feedbackStatusList=Lists.newArrayList(ConstantsDb.rfFaultFeedbackFeedbackStatusProcessing());
		String currentUserId = SessionUtils.getCurrentUserId(request);
		bean.setLastHandler(currentUserId);
		List<RfFaultFeedback> list = service.query(request, bean,feedbackStatusList,false, padCodeStart, padCodeEnd,padStatus,vmStatus);
		if(null != list && list.size() > 0){
			for (RfFaultFeedback faultFeedback : list) {
				faultFeedback.getMap().put("clientSourceName", ClientType.DICT_MAP.get(faultFeedback.getClientSource()));
			}
		}
		PageInfo<RfFaultFeedback> pageInfo = new PageInfo<RfFaultFeedback>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		bean=service.get(bean.getFaultFeedbackId());
		model.addAttribute("bean", bean);
		model.addAttribute("categoryTree", classService.getStopTrueTree(ConstantsDb.rfClassClassTypeFault()));
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.update(request, bean);
	}
	
	@RequestMapping(value = "fixForm")
	public String fixForm(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		model.addAttribute("bean", bean);
		List<Integer> idList=Lists.newArrayList();
		String[] idArr=bean.getIds().split(",");
		//System.out.println(idArr.length);
		for(String id:idArr){
			idList.add(Integer.parseInt(id));
		}
		List<RfFaultFeedback> list = service.initQuery().andIn("faultFeedbackId", idList).findAll();
		model.addAttribute("list", list);
		//修复类型
		List<RfClass> fixList=classService.initQuery().andEqualTo("classType", ConstantsDb.rfClassClassTypeFaultfix()).addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("fixList", fixList);
		model.addAttribute("fixListJson", new ObjectMapper().writeValueAsString(fixList));
		//建议运维修复类型
		/*List<RfClass> suggestList=classService.initQuery().andEqualTo("classType", ConstantsDb.rfClassClassTypeSuggest()).addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("suggestList", suggestList);*/
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "fixFormModule")
	public String fixFormModule(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		return this.fixForm(request, response, model, bean);
	}
	
	
	@RequestMapping(value = "fix")
	public void fix(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		
		service.fix(request, bean);
		
		if(FaultFeedbackLastHandlerType.NO_REPLY.equals(bean.getReplyType())){
			service.reply(request, bean);
			log.info("reply ids={}",new Object[]{bean.getIds()});
			if(StringUtils.isNotBlank(bean.getIds())){
				String[] ids = bean.getIds().split(",");
				log.info("reply ids={}",new Object[]{ids});
				for (String id : ids) {
					if(StringUtils.isNotBlank(id)){
						log.info("reply id={}",new Object[]{id});
						RfFaultFeedback feedback = service.selectByFaultFeedback(Integer.parseInt(id));
						if(null != feedback){
							String padCode = feedback.getPadCode();
							int faultCount = service.initQuery().andEqualTo("padCode", padCode).andNotEqualTo("feedbackStatus", ConstantsDb.rfFaultFeedbackFeedbackStatusEnd()).countDelTrue();
							log.info("reply faultCount={} padCode={}",new Object[]{faultCount,feedback.getPadCode()});
							if (faultCount == 0) {
								// 获取设备&置故障状态为正常
								RfPad pad = padService.getPadByPadCode(feedback.getPadCode());
								if (pad != null) {
									log.info("reply padCode={}",new Object[]{pad.getPadCode()});
									pad.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
									padService.update(request, pad);
								}
							}
						}
					}
				}
			}
		}
	}

	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.detail(request, model, bean);
		return this.toPage(request, response, model,"detail");
	}
	
		

	// 解绑
	@RequestMapping(value = "relieve")
	public void relieve(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String idStr : idArray) {
			RfFaultFeedback faultFee= service.get(Integer.parseInt(idStr));
			if(null==faultFee.getPadId()||"".equals(faultFee.getPadId())){
				throw new BusinessException("故障记单"+faultFee.getFaultFeedbackId()+"没有记录设备信息，无法进行解绑操作");
			}
			//判断USER_PAD表中的PADID下的数据是否正常
			userPadService.padUserStatus(request, faultFee.getPadId());
		    List<RfUserPad> userPad=userPadService.initQuery().andEqualTo("padId", faultFee.getPadId()).andEqualTo("userId", faultFee.getUserId()).findDelTrue();
			if(Collections3.isEmpty(userPad)){
				throw new BusinessException("故障记单"+faultFee.getFaultFeedbackId()+"当前没有对应的用户设备绑定记录");
			}
			
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

	@RequestMapping(value = "renewalRandom")
	public void renewalRandom(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String userId, 
			String padCode,String padCode2,int oldPadId,String deviceIp,String deviceIp2,RfUserPad userPad,Integer faultFeedbackId,
			String isSendMessage,String isSendWechart,String sendMessageTemplate,String sendWechartTemplate) throws Exception{
		
		if(faultFeedbackId == null){
			throw new BusinessException("更换设备失败, 故障工单不存在");
		}
		
		// 获取所有可更换的设备
		PageInfo<RfPad> pageInfo = this.padlist(request, response, model, bean, null, padCode, padCode2, oldPadId, deviceIp, deviceIp2);
		
		String renewalPadCode = "";
		
		boolean isRenewalSuccess = false;
		
		if(pageInfo != null && pageInfo.getTotal() > 0){
			
			for (int i = 0; i <= pageInfo.getPages(); i++) {
				// 获取可用设备列表
				List<RfPad> padList = pageInfo.getList();
				
				if(!Collections3.isEmpty(padList)){
					for (RfPad rfPad : padList) {
						if(rfPad != null){
							try{
								
								// 更换设备
								this.renewalOne(request, response, model, userPad, oldPadId, rfPad.getPadId(), isSendMessage, isSendWechart, sendMessageTemplate, sendWechartTemplate);
								// 更换后的设备编码
								renewalPadCode = rfPad.getPadCode();
								// 标记为更换成功
								isRenewalSuccess = true;
								
								break;
							}catch(Exception e){}
						}
					}
				}
				
				if(isRenewalSuccess){
					break;
				}
				
				// 设置下一页
				bean.setPage(i);
				// 查询下一页可用设备列表
				pageInfo = this.padlist(request, response, model, bean, userId, padCode, padCode2, oldPadId, deviceIp, deviceIp2);
				
			}
			
		}
		
		if(!isRenewalSuccess){
			throw new BusinessException("更换设备失败, 无设备可更换");
		}
		
		RfFaultFeedback rfFaultFeedback = new RfFaultFeedback();
		
		rfFaultFeedback.setFaultFeedbackId(faultFeedbackId);
		
		// 设置更换后的设备编号
		rfFaultFeedback.setRenewalPadCode(renewalPadCode);
		// 更新故障工单
		service.update(request, rfFaultFeedback);
		
	}


	@ResponseBody
	@RequestMapping(value = "padlist")
	public PageInfo<RfPad> padlist(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String userId, String padCode,String padCode2,int oldPadId,String deviceIp,String deviceIp2) throws Exception {
		List<RfFacility> facilityList = facilityService.initQuery().findAll();
		Map<String, RfFacility> facilityMap = new HashMap<String,RfFacility>();
		for (RfFacility rfFacility : facilityList) {
			facilityMap.put(rfFacility.getFacilityCode(), rfFacility);
		}
		//物理设备ip
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(deviceIp)){
			//第一个物理ip字段
			String[] deviceIpArr = deviceIp.split("\\.");
			StringBuilder sb = new StringBuilder(16);
			if(deviceIpArr.length == 1){
				sb.append(deviceIp).append(".0.0.0");
			} else if(deviceIpArr.length == 2){
				sb.append(deviceIp).append(".0.0");
			} else if(deviceIpArr.length == 3){
				sb.append(deviceIp).append(".0");
			} else if(deviceIpArr.length > 4){
				throw new BusinessException("请输入正确的ip地址！");
			}
			params.put("deviceIp", StringUtils.isNotEmpty(sb.toString())?sb.toString():deviceIp);
		} 
		if(StringUtils.isNotEmpty(deviceIp2)){
			//第二个物理ip字段
			String[] deviceIpArr = deviceIp2.split("\\.");
			StringBuilder sb = new StringBuilder(16);
			if(deviceIpArr.length == 1){
				sb.append(deviceIp2).append(".255.255.255");
			} else if(deviceIpArr.length == 2){
				sb.append(deviceIp2).append(".255.255");
			} else if(deviceIpArr.length ==3){
				sb.append(deviceIp2).append(".255");
			} else if(deviceIpArr.length > 4){
				throw new BusinessException("请输入正确的ip地址！");
			} 
			params.put("deviceIp2", StringUtils.isNotEmpty(sb.toString())?sb.toString():deviceIp2);
		}
		// 根据旧设备id查询设备
		RfPad oldPad = padService.get(oldPadId);
		if (userId==null) {
			// 机房ID
			params.put("idcId", bean.getIdcId()!=null?bean.getIdcId():null);
			// 用户控制节点ID
			params.put("userControlId", bean.getUserControlId());
			// 设备类别
			params.put("padClassify", oldPad!=null?oldPad.getPadClassify():PadClassify.PAD_CLASSIFY_MAIN);
			// 启用状态
			params.put("enableStatus", ConstantsDb.rfPadEnableStatusStart());
			// 绑定状态
			params.put("bindStatus", ConstantsDb.rfPadBindStatusNobind());
			// 故障状态
			params.put("faultStatus", ConstantsDb.rfPadFaultStatusNomarl());
			// 设备状态(在线 离线 控制)
			params.put("padStatus",  ConstantsDb.rfPadPadStatusOnline());
			// 虚拟机状态(在线 离线)
			params.put("vmStatus", ConstantsDb.rfPadVmStatusOnline());//添加了虚拟在线这个限定
			//编码段
			String regexStr = "^[0-9a-zA-Z]+$";
			int count =0;
			if(StringUtils.isNotBlank(padCode)){
				if(!padCode.matches(regexStr)){
					params.put("padCode","-1");
					params.put("padCode2","-1");
					count+=1;
				}else{
					params.put("padCode",padCode);
				}
			}
			if(StringUtils.isNotBlank(padCode2)){
				if(!padCode2.matches(regexStr)){
					params.put("padCode","-1");
					params.put("padCode2","-1");
				}else{
					if(count>0){
						params.put("padCode","-1");
						params.put("padCode2","-1");
					}else{
						params.put("padCode2",padCode2);
					}
				}
			}
			params.put("page",  (bean.getPage()-1)*bean.getRows());
			params.put("rows",  bean.getRows());
			// 查询所有可用的设备列表
			log.info("seleteByParamsMap params={}",new Object[]{params});
			Page<RfPad> padlist= padService.seleteByParamsMap(params);
			// 查询可用设备的数量
			Integer total = padService.seleteByParamsCount(params);
			// 设置总数
			padlist.setTotal(total!=null?total:0);
			// 设置当前页
			padlist.setPageNum(bean.getPage()!=null?bean.getPage():1);
			
			PageInfo<RfPad> pageInfo = new PageInfo<RfPad>(padlist);
			// 查询节点类型为用户的可用设备
			List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", "USER").findStopTrue();
			Map<Integer, RfControl> controlMap = new HashMap<Integer, RfControl>();	
			for (RfControl control : controlList) {
				controlMap.put(control.getControlId(), control);
			}
			RfControl control;
			RfFacility facility;
			for (RfPad rfPad : padlist) {
				control = controlMap.get(rfPad.getUserControlId());
				if(control != null){
					rfPad.getMap().put("controlName", control.getControlName());
				}
				
				facility = facilityMap.get(rfPad.getPadSource());
				if(facility != null){
					rfPad.getMap().put("facilityName", facility.getFacilityName());
				}
			}
			return pageInfo;
		} else {
			List<Integer> padList = new ArrayList<Integer>();
			// 根据用户id查询用户所有绑定的正常设备
			List<RfUserPad> userPadList = userPadService.initQuery().andEqualTo("userId", Integer.parseInt(userId)).findStopTrue();
	         if(Collections3.isEmpty(userPadList))
			{
				throw new BusinessException("该用户当前没有绑定设备！");
			}
			for (RfUserPad rfUserPad : userPadList) {
				padList.add(rfUserPad.getPadId());
			}
			// 机房ID
			params.put("idcId", bean.getIdcId()!=null?bean.getIdcId():null);
			// 设备编码1
			params.put("padCode", StringUtils.isNotBlank(padCode)?padCode:null);
			// 设备编码2
			params.put("padCode2", StringUtils.isNotBlank(padCode2)?padCode2:null);
			// 当前页
			params.put("page",  bean.getPage());
			// 每页记录数
			params.put("rows",  bean.getRows());
			// 排序字段
			params.put("sort",  bean.getSort()!=null?bean.getSort():null);
			// 排序方式
			params.put("order", bean.getOrder()!=null?bean.getOrder():null);
			if(padList.size() > 0){
				params.put("padList", padList);
			}
			List<RfPad> padlist= padService.seleteByParamsMap(params);
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
	public String renewalForm(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception{
	    bean=service.get(bean.getFaultFeedbackId());
	    RfPad pad = padService.getPadByPadCode(bean.getPadCode());
		if(pad.getBindStatus().equals("0")){
			throw new BusinessException("设备未绑定");
		}
	    if(null==bean.getPadId()||"".equals(bean.getPadId())){
			throw new BusinessException("故障记单"+bean.getFaultFeedbackId()+"没有记录设备信息，无法进行解绑操作");
		}
		RfUser user = userService.get(bean.getUserId());
		if(null==user){
			throw new BusinessException(bean.getPadCode()+"设备当前没有绑定记录");
		}else{
			 pad = padService.get(bean.getPadId());
			model.addAttribute("pad", pad);
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
	}
	
	@RequestMapping(value = "renewalFormModule")
	public String renewalFormModule(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception{
		model.addAttribute("faultFeedbackId", bean.getFaultFeedbackId());
		return renewalForm(request, response, model, bean);
	}
	
	
	@RequestMapping(value = "renewalOne")  
	public void renewalOne(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,Integer oldPadId,
			Integer newPadId,String isSendMessage,String isSendWechart,String sendMessageTemplate, String sendWechartTemplate) throws Exception {
		
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
		// 获取旧设备信息
		RfPad oldpad = padService.get(oldPadId);
		// 根据用户id和设备id查询所有正常的设备
		List<RfUserPad>list=userPadService.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padId", oldpad.getPadId()).findStopTrue();
		if(Collections3.isEmpty(list)){
			throw new BusinessException("会员与"+oldpad.getPadCode()+"没有绑定记录");
		}
		if (!oldpad.getPadCode().equals(newPadId)) {
			RfUserPad rfUserPad =list.get(0); 
			// 旧设备解绑日志
			// 删除原有绑定记录 user_pad
			RfPad newPad = padService.get( padService.initQuery().andEqualTo("padId", newPadId).singleDelTrue().get(0).getPadId());
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
			Integer wechartTemplateId = userPadService.renewal(request,rfUserPad,newPad,isSendMessage,isSendWechart,sendMessageTemplate,sendWechartTemplate);
			
			try{
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				
				String sendMessageStr = "";
				
				// 发送短信
				if(YesOrNoStatus.YES.equals(isSendMessage)){
					sendMessageObj.put("opType", "refresh");
					sendMessageObj.put("userId", rfUserPad.getUserId());
					sendMessageStr = sendMessageObj.toString();
					log.info("sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					fingerProducer.sendMessage(sendMessageStr);
				}
				
				// 发送微信
				if(YesOrNoStatus.YES.equals(isSendWechart)){
					//向用户发送jms
					sendMessageObj.clear();
					sendMessageObj.put("templateId", wechartTemplateId);
					sendMessageStr = sendMessageObj.toString();
					log.info("renewal sendMessageStr: "+ sendMessageStr);
					// 发送jms消息(weixin-remind)
					weixinProducer.sendMessage(sendMessageStr);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "renewal")  
	public void renewal(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,
			String isSendMessage,String isSendWechart,String sendMessageTemplate, String sendWechartTemplate) throws Exception {
		String[] pad = bean.getIds().split(",");
		String[] code = bean.getRemark().split(",");
		for (int i = 0; i < pad.length; i++) {
			RfPad oldpad = padService.get(Integer.parseInt(pad[i]));
			List<RfUserPad>list=userPadService.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padId", oldpad.getPadId()).findStopTrue();
			if(Collections3.isEmpty(list)){
				throw new BusinessException("会员与"+oldpad.getPadCode()+"没有绑定记录");
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
				userPadService.renewal(request,rfUserPad,newPad,isSendMessage,isSendWechart,sendMessageTemplate,sendWechartTemplate);
				
				try{
					//向用户发送jms
					JSONObject sendMessageObj = new JSONObject();
					sendMessageObj.put("opType", "refresh");
					sendMessageObj.put("userId", rfUserPad.getUserId());
					String sendMessageStr = sendMessageObj.toString();
					log.info("sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					fingerProducer.sendMessage(sendMessageStr);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	@RequestMapping(value="formPad")
	public String formPad(HttpServletRequest request,HttpServletResponse response,Model model,RfFaultFeedback bean){
	    bean= service.get(bean.getFaultFeedbackId());	
	    if(null==bean.getPadId()){
		   throw new BusinessException("故障记单"+bean.getFaultFeedbackId()+"没有记录设备信息，无法进行解绑操作");
	    }
	    RfUserPad userPad=userPadService.getUserPadByPadId(bean.getPadId());
	    if(null==userPad){
		  	throw new BusinessException("故障记单"+bean.getFaultFeedbackId()+"没有记录设备绑定信息,设备Id:"+bean.getPadId()+"，无法进行解绑操作");
	    }
	    if(!userPad.getUserId().equals(bean.getUserId())){
	  		throw new BusinessException("故障记单"+bean.getFaultFeedbackId()+"记录的用户和设备信息与当前设备绑定信息不符合，工单记录用户id:"+bean.getUserId()+"工单记录设备id:"+bean.getPadId()+"当前该设备绑定的用户id:"+userPad.getUserId());
	    }
	    RfUser user = userService.get(bean.getUserId());
	    RfPad pad = padService.get(bean.getPadId());
		model.addAttribute("user", user);
		model.addAttribute("pad", pad);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value="padTask")
	@ResponseBody
	public  PageInfo<RfPadTask> padTask(HttpServletRequest request,HttpServletResponse response,Model model,String type,Integer padId) throws Exception{
		Class<?> cls_service = null;
		try {
			cls_service = Class.forName("com.redfinger.manager.modules.fault.service.FaultFeedbackService");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object service = SpringContextHolder.getBean(cls_service);
		@SuppressWarnings("unchecked")
		List<RfPadTask> data= (List<RfPadTask>) Reflections.invokeMethodByName(service,type, new Object[] { request,response, type, padId });
		if(null==data){
			  throw new BusinessException("命令执行失败");
		}
		pro.sendPadCode(String.valueOf(data.get(0).getTaskId()));
		PageInfo<RfPadTask>rows=new PageInfo<RfPadTask>(data);
	    return   rows;
	}
	
	// 导出
	@RequestMapping(value="export")
	public String export(HttpServletRequest request, HttpServletResponse response, 
			Model model, RfFaultFeedback bean, String padCodeStart, String padCodeEnd,
			String exportHead, String exportField, String exportName,String padStatus, String vmStatus)throws Exception{
		exportHead=StringUtils.removeEnd(exportHead, ",");
		exportField=StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xls");
		// 创建一个workbook 对应一个excel应用文件
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		// 构建表头
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		// 构建表体
		boolean keep=true;
		int page=1;
		String padCode="";
		if(!StringUtils.isEmpty(bean.getPadCode())){
			padCode=bean.getPadCode();
		}
		while(keep){
			bean.setPage(page);
			bean.setRows(1000);
			if(!StringUtils.isEmpty(padCode)){
				bean.setPadCode(padCode);
			}
			PageInfo<RfFaultFeedback> pageInfo=this.list(request, response, model, bean, padCodeStart, padCodeEnd,padStatus,vmStatus);
			List<RfFaultFeedback> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				for (RfFaultFeedback faultFeedback : list) {
					faultFeedback.setFeedbackStatus(DictHelper.getLabel("rf_fault_feedback.feedback_status", faultFeedback.getFeedbackStatus()));
					if(null != faultFeedback.getMap().get("padGrade")){
						faultFeedback.getMap().put("padGrade", DictHelper.getLabel("rf_user_pad.pad_grade", faultFeedback.getMap().get("padGrade").toString()));
					}
					if(null != faultFeedback.getMap().get("bindStatus")){
						faultFeedback.getMap().put("bindStatus", DictHelper.getLabel("rf_pad.bind_status", faultFeedback.getMap().get("bindStatus").toString()));
					}
					if(null !=  faultFeedback.getMap().get("padStatus")){
						faultFeedback.getMap().put("padStatus", faultFeedback.getMap().containsKey("padStatus")?DictHelper.getLabel("rf_pad.pad_status", faultFeedback.getMap().get("padStatus").toString()):"--" );
					}
					if(null !=  faultFeedback.getMap().get("vmStatus")){
						faultFeedback.getMap().put("vmStatus", faultFeedback.getMap().containsKey("vmStatus")?DictHelper.getLabel("rf_pad.vm_status", faultFeedback.getMap().get("vmStatus").toString()):"--" );
					}
					faultFeedback.setFeedbackSource(DictHelper.getLabel("rf_fault_feedback.feedback_source", faultFeedback.getFeedbackSource()));
				}
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list),sheet,bodyStyle,exportField.split(","),pageInfo.getStartRow());
				if(pageInfo.isHasNextPage()){
					page++;
					continue;
				}
			}
			keep=false;
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
    }  
	
	
	@RequestMapping(value = "asyncExport")
	public void asyncExport(HttpServletRequest request, HttpServletResponse response, 
			Model model, String exportHead, String exportField, String exportName,String exportTaskName, String queryParams) throws Exception{
		String queryJson = URLDecoder.decode(queryParams, "utf-8");
		
		List<RfExport> rfExportts = exportService.initQuery()
				.andEqualTo("parm", queryJson)
				.andEqualTo("type", ExportConstants.TYPE_FAULT_SELF)
				.addOrderClause("createTime", "desc")
				.findAll();
				Date now = new Date();
				
		// 已有导出任务
		if (null != rfExportts && rfExportts.size() > 0
				&& DateUtils.getDistanceOfTwoDateM(rfExportts.get(0).getCreateTime(), now) < 3) {
				throw new BusinessException("已经有该条件的导出任务, 请在"
					+ (int) (3 - DateUtils.getDistanceOfTwoDateM(rfExportts.get(0).getCreateTime(), now)) + "分钟后再试试");
		}
				
		String baseFilePath = filePathUtils.getFilePath()+"/batchTask/export";
    	String baseFileLinkUrl = filePathUtils.getFileLinkUrl()+"/batchTask/export";
    	String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());				//日作为文件夹
		String dateTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());		//年月日时分秒作为文件名
		int randomNumber = new Random().nextInt(999999);
		createFilePath(baseFilePath + File.separator + dateStr);
		String fileName = dateTimeStr + randomNumber+".xlsx";
		String filePath = baseFilePath + File.separator + dateStr + File.separator + fileName;//无斜杠结尾
		String fileUrl = baseFileLinkUrl + File.separator + dateStr + File.separator + fileName;
    	
		RfExport export = new RfExport();
		export.setPath(filePath);
		export.setUrl(fileUrl);
		export.setParm(queryJson);
		export.setTaskName(exportTaskName);
		export.setType(ExportConstants.TYPE_FAULT_SELF);
		export.setExportStatus(ExportConstants.EXPORT_STATUS_MAKE);//任务创建未执行
		exportService.save(request, export);
		HashMap<Object, String> exportMap = Maps.newHashMap();
		exportMap.put("exportId", String.valueOf(export.getExportId()));
		exportMap.put("exportHead", exportHead);
		exportMap.put("exportField", exportField);
		System.out.println(JsonUtils.ObjectToString(exportMap));
		exportProducer.sendMessage(JsonUtils.ObjectToString(exportMap));
	}
	
	private void createFilePath(String foldName){
		if(StringUtils.isNotEmpty(foldName)){
			File file = new File(foldName);
			if(file.exists()){
				if(file.isFile()){
					file.deleteOnExit();
					file.mkdir();
				}
			}else{
				file.mkdirs();
			}
		}
	}
	
	
}
