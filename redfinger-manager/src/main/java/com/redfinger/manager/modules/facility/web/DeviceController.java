package com.redfinger.manager.modules.facility.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.PadList;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.SearchType;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfDeviceExample;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.RfVideo;
import com.redfinger.manager.common.domain.RfVmTask;
import com.redfinger.manager.common.domain.RfVmTaskHis;
import com.redfinger.manager.common.domain.ViewPadUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.ConfigHelper;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.mapper.RfDeviceMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.IP2LongUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.DeviceService;
import com.redfinger.manager.modules.facility.service.FacilityService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.facility.service.VideoService;
import com.redfinger.manager.modules.facility.service.VmTaskService;
import com.redfinger.manager.modules.log.service.VmTaskHisService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;
import com.redfinger.manager.modules.tasks.service.TaskBatchService;
import com.redfinger.manager.modules.tasks.web.TaskBatchController;

@Controller
@RequestMapping(value = "/facility/device")
public class DeviceController extends BaseController {

	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	DeviceService service;
	@Autowired
	ControlService controlService;
    @Autowired
    PadService padService;
    @Autowired
    RfDeviceMapper mapper;
    @Autowired
    VideoService videoService;
    @Autowired
    IdcService idcService;
    @Autowired
    VmTaskService vmTaskService;
    @Autowired 
	VmTaskHisService vmHisService;
	@Autowired
	TaskBatchController taskBatchController;
	@Autowired
	UserService userService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	TaskBatchService taskBatchService;
	@Autowired
	ProducterMessageSender pro;
	@Autowired
	private FacilityService facilityService;
    
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfFacility> facilityList = facilityService.initQuery().findDelTrue();
		model.addAttribute("facilityList", facilityList);
		List<RfIdc> idcList = idcService.initQuery().findDelTrue();
		model.addAttribute("idcList", idcList);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfDevice> list(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean,
			String userMobilePhone,String ipSearchType,String romVersion,String deviceStartIp, String deviceEndIp,Integer idcId, Integer externalUserId) throws Exception {
		List<Integer> deviceIds = new ArrayList<Integer>();
		List<Integer> arr1 = new ArrayList<>();
		if(StringUtils.isNotBlank(userMobilePhone) || externalUserId != null){
			List<RfUser> users = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).andEqualTo("externalUserId", externalUserId).findAll();
			if(null != users && users.size()>0){
				List<RfUserPad> userPads = userPadService.initQuery().andEqualTo("userId", users.get(0).getUserId()).findAll();
				if(null != userPads && userPads.size()>0){
					List<Integer> padIds = new ArrayList<Integer>();
					for (RfUserPad rfUserPad : userPads) {
						padIds.add(rfUserPad.getPadId());
					}
					
					if(null != padIds && padIds.size()>0){
						List<RfPad> pads = padService.initQuery().andIn("padId", padIds).findAll();
						if(null != pads && pads.size()>0){
							for (RfPad rfPad : pads) {
								if(null != rfPad.getDeviceId()){
									arr1.add(rfPad.getDeviceId());
								}
							}
						}
					}
				}
			}
			
			/*if(null == deviceIds || deviceIds.size() <= 0){
				deviceIds.add(0);
			}*/
			
			if(arr1.size() <= 0){
				arr1.add(0);
			}
		}
		
		//判断ip字段查询类型(模糊还是精准)
		String deviceIp_accurate = null;
		if(SearchType.FUZZY.equals(ipSearchType)){
			
		}else{//if(SearchType.ACCURATE.equals(ipSearchType))
			deviceIp_accurate = bean.getDeviceIp();
			bean.setDeviceIp(null);
		}
		service.initQuery(bean);
		if("null".equals(bean.getDeviceStatus())) {
			service.andIsNull("deviceStatus");
		} else {
			service.andEqualTo("deviceStatus", bean.getDeviceStatus());
		}
		RfDeviceExample example =(RfDeviceExample) service.getExample();
		//iPv4的ip地址都是（1~255）.（0~255）.（0~255）.（0~255）的格式
		String regexStr = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if(StringUtils.isNotBlank(deviceStartIp)){
			deviceStartIp = deviceStartIp.trim();
			if(deviceStartIp.matches(regexStr)){
				example.getMap().put("deviceStartIp", deviceStartIp);
			}else{
				deviceIds.add(-1);
			}
		}
		if(StringUtils.isNotBlank(deviceEndIp)){
			deviceEndIp = deviceEndIp.trim();
			if(deviceEndIp.matches(regexStr)){
				example.getMap().put("deviceEndIp", deviceEndIp);
			}else{
				deviceIds.add(-1);
			}
		}
		if(example.getMap().size()==2){
			if(IP2LongUtils.ipToNumber(deviceStartIp)>IP2LongUtils.ipToNumber(deviceEndIp)){
				deviceIds.add(-1);
				example.getMap().clear();
			}
		}
		List<RfDevice> list = service
				//.initQuery(bean)
				.andLike("deviceIp", bean.getDeviceIp())
				.andLike("deviceName", bean.getDeviceName())
				.andLike("deviceCode", bean.getDeviceCode())
				.andIn("deviceId", arr1)
				.andIn("deviceId", deviceIds)
//				.andEqualTo("deviceStatus", bean.getDeviceStatus())
				.andEqualTo("deviceSource", bean.getDeviceSource())
				.andEqualTo("deviceIp", deviceIp_accurate)
				.andEqualTo("idcId", idcId)
				.andEqualTo("romVersion", romVersion)
				.andEqualTo("deviceUse", bean.getDeviceUse())
				.addOrderClause("createTime", "desc nulls last,device_id desc")
				.addOrderForce(bean.getSort(), bean.getOrder())		
            	.pageDelTrue(bean.getPage(), bean.getRows());
		List<RfFacility> facilityList = facilityService.initQuery().addOrderClause("reorder", "asc").findAll();
		Map<String, RfFacility> facilityMap = new HashMap<String, RfFacility>();
		RfFacility f = null;
		for (RfFacility rfFacility : facilityList) {
			facilityMap.put(rfFacility.getFacilityCode(), rfFacility);
		}

		for (RfDevice rfDevice : list) {
             rfDevice.getMap().put("manageControlName", rfDevice.getDeviceManageControlId() == null ? "--" : controlService.load(rfDevice.getDeviceManageControlId() ).getControlName());
             rfDevice.getMap().put("controlCode", rfDevice.getDeviceManageControlId() == null ? "--" : controlService.load(rfDevice.getDeviceManageControlId() ).getControlCode());
             
             f = facilityMap.get(rfDevice.getDeviceSource());
             if(f != null){
            	 rfDevice.getMap().put("deviceSourceName", f.getFacilityName());
             }
		}
		PageInfo<RfDevice> pageInfo = new PageInfo<RfDevice>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {
		if (bean.getDeviceId() != null) {
			bean = service.get(bean.getDeviceId());
			model.addAttribute("bean", bean);
		}
		List<RfVideo> userVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeUser()).findStopTrue();
		List<RfVideo> padVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeDevice()).findStopTrue();
		model.addAttribute("userVideoList", userVideoList);
		model.addAttribute("padVideoList", padVideoList);
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType",ConstantsDb.rfControlControlTypeUser()).findStopTrue();
		List<RfControl> padControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		List<RfControl> manageControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).findStopTrue();
		List<RfIdc> idcList = idcService.initQuery().findDelTrue();
		model.addAttribute("idcList", idcList);
		model.addAttribute("controlList", controlList);
		model.addAttribute("padControlList", padControlList);
		model.addAttribute("manageControlList", manageControlList);
		
		List<RfControl> deviceControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).findStopTrue();
		model.addAttribute("deviceControlList", deviceControlList);
		
		List<RfFacility> facilityList = facilityService.initQuery().findDelTrue();
		model.addAttribute("facilityList", facilityList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean,String endIp,Integer idcId,Integer userControlId,Integer padControlId,Integer padManageControlId,Integer userVideoId,Integer padVideoId,String vmIp,String padClassify,String controlProtocol) throws Exception {
	
		service.saveIp(request, bean,endIp,idcId, userControlId, padControlId, padManageControlId, userVideoId, padVideoId,vmIp,padClassify,controlProtocol);
	}
	
	@ResponseBody
	@RequestMapping(value = "getControls")
	public String getControls(HttpServletRequest request, HttpServletResponse response,Model model, Integer idcId){//TODO 
		//List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType",ConstantsDb.rfControlControlTypeUser()).findStopTrue();
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType",ConstantsDb.rfControlControlTypeUser()).andEqualTo("idcId", idcId).findStopTrue();
//		List<String> controlList = new ArrayList<>();
//		controlList.add("a");controlList.add("b");controlList.add("c");
		//model.addAllAttributes(controlList);
		String json = JsonUtils.ObjectToString(controlList);
		return json;
		//return "hahah";
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {
		RfDeviceExample example=new RfDeviceExample();
		example.or().andDeviceIpEqualToIgnoreNull(bean.getDeviceIp()).andDeviceIdNotEqualToIgnoreNull(bean.getDeviceId());
		example.or().andDeviceCodeEqualToIgnoreNull(bean.getDeviceCode()).andDeviceIdNotEqualToIgnoreNull(bean.getDeviceId());
		List<RfDevice> list = mapper.selectByExample(example);
		
		if(Collections3.isEmpty(list)){
			service.update(request, bean);
			
			List<RfPad> pads = padService.getPadByDeviceId(bean.getDeviceId());
			if(null != pads && pads.size()>0){
				for (RfPad rfPad : pads) {
					rfPad.setPadType(bean.getDeviceType());
					rfPad.setPadSource(bean.getDeviceSource());
					rfPad.setRomVersion(bean.getRomVersion());//设备rom版本号设为跟物理设备一样
					padService.update(request, rfPad);
				}
			}
			
		}else{
			RfDevice device=new RfDevice();
			device=service.get(bean.getDeviceId());
			if(bean.getDeviceIp().equals(device.getDeviceIp()) && bean.getDeviceCode().equals(device.getDeviceCode())){
				service.update(request, bean);
				if(StringUtils.equals(bean.getDeviceType(),device.getDeviceType())){
					
				}
			}else{
				throw new BusinessException("错误：001，输入的IP或者设备编号已被占用，请重新确认！");
			}
		}
	
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {
		service.startVM(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {
		service.stopVM(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {
		service.remove(request, bean);
	}
	
	@RequestMapping(value = "vmTaskForm")
	public String vmTaskForm(HttpServletRequest request, HttpServletResponse response, Model model,  RfDevice bean) throws Exception {
		bean = service.get(bean.getDeviceId());	
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "vmTasksForm")
	public String vmTasksForm(HttpServletRequest request, HttpServletResponse response, Model model,  RfDevice bean) throws Exception {
		String []ids=bean.getIds().split(",");
		List<Integer> idsList = Lists.newArrayList();  
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		List<RfDevice>list=service.initQuery().andIn("deviceId", idsList).findAll();	
		model.addAttribute("list", list);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "task")
	public void task(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean,String taskType,Long retryInterval,String accredit) throws Exception {
		if("remote-play".equals(taskType)||"gamemanage".equals(taskType)){
			String[] ids = bean.getIds().split(",");
			List<Integer> idsList = Lists.newArrayList();  
			for (String string : ids) {
				idsList.add(Integer.parseInt(string));
			}
			List<RfPad>padList = Lists.newArrayList();
			if ("".equals(accredit) || null == accredit) {
				padList = padService.initQuery().andIn("deviceId", idsList).findDelTrue();
			} else {
				padList = padService.initQuery().andIn("deviceId", idsList).andEqualTo("grantOpenStatus", accredit).findDelTrue();
			}
			String padCodes=Collections3.extractToString(padList, "padCode", "\n");
			taskBatchController.pad_kill_pid(request, response, model, padCodes, taskType);
		}else{
			Map<String,Object>  map = service.task(request,bean,taskType, retryInterval,accredit);
			Date time = (Date)map.get("time");
			List<RfVmTask> vmTaskList = (List<RfVmTask>) map.get("vmTaskList");
			if(null!=time){
			    @SuppressWarnings("unused")
				Integer result=service.jmsSendTask(vmTaskList, time, taskType,request);
			    if(null==request){
			    		throw new BusinessException("设备任务消息队列发送失败");
			    }
			}else{
				throw new BusinessException("设备"+taskType+"任务执行失败");
			}
		}
	}
	
	//虚拟管理
	@RequestMapping(value = "taskOnly")
	public void taskOnly(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean,String taskType,Long retryInterval) throws Exception {
		Map<String,Object>  map = service.taskOnly(request,bean,taskType,retryInterval);
		Date time = (Date)map.get("time");
		List<RfVmTask> vmTaskList = (List<RfVmTask>) map.get("vmTaskList");
		if(null!=time){
			 @SuppressWarnings("unused")
			Integer result=service.jmsSendTask(vmTaskList, time, taskType,request);
		     if(null==request){
		    		throw new BusinessException("设备任务消息队列发送失败");
		     }
			}else{
				throw new BusinessException("设备"+taskType+"任务执行失败");
			}
	}
	
	@RequestMapping(value = "vmForm")
	public String vmForm(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {
		bean = service.get(bean.getDeviceId());	
		List<RfPad>list=padService.initQuery().andEqualTo("deviceId", bean.getDeviceId()).andIsNotNull("vmStatus").findDelTrue();
		Integer vm=Integer.parseInt(ConfigHelper.getValueByCode("config_device_vm_max"));
		if(null==vm||vm==0){
			throw new BusinessException("请确认物理机虚拟数设置是否正确");
		}
		if(list.size()>=vm){
			throw new BusinessException("物理设备"+bean.getDeviceCode()+"下的虚拟设备已达到上限");
		}
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "rmForm")
	public String rmForm(HttpServletRequest request, HttpServletResponse response, Model model,  RfDevice bean) throws Exception {
		bean = service.get(bean.getDeviceId());	
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "padlist")
	public PageInfo<RfPad> padlist(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String deviceId) throws Exception {
		Integer id = null;
		if(null!=deviceId&&!("".equals(deviceId))){
		id=Integer.parseInt(deviceId);
		}
		List<RfPad> padlist =   padService.initQuery(bean)
		        .andLike("padIp", bean.getPadIp())
			    .andEqualTo("deviceId", id)
			    .andLike("padCode", bean.getPadCode())
				.addOrderClause("createTime", "asc ,device_id desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (RfPad rfPad : padlist) {
			rfPad.getMap().put("deviceCode",(null==rfPad.getDeviceId()||"".equals(rfPad.getDeviceId()))?"--":service.load(rfPad.getDeviceId()).getDeviceCode());
		}
		PageInfo<RfPad> pageInfo = new PageInfo<RfPad>(padlist);
		return pageInfo;
		
	}
	@ResponseBody
	@RequestMapping(value = "padList")
	public List<RfPad> padList(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String deviceId) throws Exception {
		Integer id = null;
		if(null!=deviceId&&!("".equals(deviceId))){
		id=Integer.parseInt(deviceId);
		}
		List<RfPad> padlist =   padService.initQuery(bean)
		        .andLike("padIp", bean.getPadIp())
			    .andEqualTo("deviceId", id)
			    .andLike("padCode", bean.getPadCode())
				.addOrderClause("createTime", "asc ,device_id desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (RfPad rfPad : padlist) {
			rfPad.getMap().put("deviceCode",(null==rfPad.getDeviceId()||"".equals(rfPad.getDeviceId()))?"--":service.load(rfPad.getDeviceId()).getDeviceCode());
		}
		return padlist;
	}
	@ResponseBody
	@RequestMapping(value = "padlistNotDevice")
	public PageInfo<RfPad> padlistNotDevice(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		List<RfPad> padlist =   padService.initQuery(bean)
		        .andLike("padIp", bean.getPadIp())
			    .andIsNull("deviceId")
			    .andLike("padCode", bean.getPadCode())
			    .andIsNotNull("vmStatus")
			    .andEqualTo("padSource", bean.getPadSource())
				.addOrderClause("createTime", "asc,device_id desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		for (RfPad rfPad : padlist) {
			rfPad.getMap().put("deviceCode",(null==rfPad.getDeviceId()||"".equals(rfPad.getDeviceId()))?"--":service.load(rfPad.getDeviceId()).getDeviceCode());
		}
		PageInfo<RfPad> pageInfo = new PageInfo<RfPad>(padlist);
		return pageInfo;
		
	}

	@RequestMapping(value = "addForm")
	public String addVMForm(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {
		List<RfPad>list=padService.initQuery().andEqualTo("deviceId", bean.getDeviceId()).findAll();
		bean=service.get(bean.getDeviceId());
		model.addAttribute("bean", bean);
		model.addAttribute("list", list);
		return this.toPage(request, response, model);
	}
	

	@RequestMapping(value = "updateVM")
	public void updateVM(HttpServletRequest request, HttpServletResponse response, Model model,Integer devicePadId, PadList padList) throws Exception {
		service.updateVM(request,devicePadId,padList);	
	}

	@RequestMapping(value = "addVM")
	public void addVM(HttpServletRequest request, HttpServletResponse response, Model model,RfDevice bean,String type,String deviceType) throws Exception {
		service.addVM(request, bean,type,deviceType);
	}
	
	@RequestMapping(value = "taskDevice")
	public void taskDevice(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean,String taskType,String padSn) throws Exception {
	if(taskType.equals(ConstantsDb.rfVmTaskTaskTypeUpdate())||taskType.equals(ConstantsDb.rfVmTaskTaskTypeRebootdevice())){
		Date time=service.taskDevice(request,bean,taskType);
		if(null!=time){
			Integer result=service.jmsSend(time, taskType,request);
		    if(null==result){
		    	throw new BusinessException("设备任务消息队列发送失败");
		    }
		    if(result==1){
		    	vmTaskService.updateVmJMS(time);
		    }
		}else{
			throw new BusinessException("设备"+taskType+"任务执行失败");
		}
	}else{
		service.task(request,bean,taskType,padSn);
	}
		
	}
	
	//根据CODE批量进行虚拟设备操作
	@RequestMapping(value = "batchForm")
	public String batchForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	//根据CODE批量执行命令
	@RequestMapping(value="batchPad")
	public void batchPad(HttpServletRequest request, HttpServletResponse response, Model model, String padCode,String actionType) throws Exception {
		if(ConstantsDb.rfVmTaskTaskTypeRebootdevice().equals(actionType)||ConstantsDb.rfVmTaskTaskTypeUpdate().equals(actionType)){
			List<RfVmTask>vmTaskList=service.deviceByCode(request, padCode, actionType);
			Integer result = service.jmsSend(vmTaskList, actionType, request);
			if (null == result) {
				throw new BusinessException("设备任务消息队列发送失败");
			}
		}else{
			//Map<String,Object>  map = service.taskOnly(request,bean,taskType,retryInterval);
			Map<String,Object>  map = service.batchPad(request, padCode,actionType);
			Date time = (Date)map.get("time");
			List<RfVmTask> vmTaskList = (List<RfVmTask>) map.get("vmTaskList");
			
			if(null!=time){
				Integer result=service.jmsSendTask(vmTaskList, time, actionType,request);
			    if(null==result){
			    		throw new BusinessException("设备任务消息队列发送失败");
			    }
			}else{
				throw new BusinessException("设备"+actionType+"任务执行失败");
			}
		}
	}
	//2016-3-18 虚拟设备授权开放申请
	@RequestMapping(value = "vmOpenForm")
	public String vmOpenForm(HttpServletRequest request, HttpServletResponse response, Model model,  RfDevice bean) throws Exception {
		String []ids=bean.getIds().split(",");
		List<Integer> idsList = Lists.newArrayList();  
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		List<RfDevice>list=service.initQuery().andIn("deviceId", idsList).findAll();	
		model.addAttribute("list", list);
		return this.toPage(request, response, model);
	}
	
	//虚拟设备授权命令
	@RequestMapping(value = "openForm")
	public String openForm(HttpServletRequest request, HttpServletResponse response, Model model,  RfDevice bean) throws Exception {
		String []ids=bean.getIds().split(",");
		List<Integer> idsList = Lists.newArrayList();  
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		List<RfDevice>list=service.initQuery().andIn("deviceId", idsList).findAll();	
		model.addAttribute("list", list);
		return this.toPage(request, response, model);
	}
	
	
	//虚拟设备授权
	@RequestMapping(value="openAll")
	public void openAll(HttpServletRequest request,HttpServletResponse response,Model modelrf,RfDevice bean,String sn,String open)throws Exception{
		if(open.equals(ConstantsDb.rfPadGrantOpenStatusOn())){
		if(sn.equals("all")){
        String[] ids=bean.getIds().split(",");	
        List<Integer>deviceId=Lists.newArrayList();
        for (String string : ids) {
        	deviceId.add(Integer.parseInt(string));
		}

		List<RfPad>padList=	padService.initQuery().andIn("deviceId",deviceId ).andIsNotNull("vmStatus").findAll();
			String padIds = Collections3.extractToString(padList, "padId", ",");
			RfPad pad = new RfPad();
			pad.setIds(padIds);
			padService.openOn(request, pad);
		} else {
			String[] ids = bean.getIds().split(",");
		     List<Integer>deviceId=Lists.newArrayList();
		        for (String string : ids) {
		        	deviceId.add(Integer.parseInt(string));
				}

			List<RfPad> padList = padService.initQuery().andIn("deviceId", deviceId).andIsNotNull("vmStatus").andEqualTo("padSn", sn).findAll();
			String padIds = Collections3.extractToString(padList, "padId", ",");
			RfPad pad = new RfPad();
			pad.setIds(padIds);
			padService.openOn(request, pad);
		}
		}
		if(open.equals(ConstantsDb.rfPadGrantOpenStatusOff())){
			if(sn.equals("all")){
		        String[] ids=bean.getIds().split(",");		
			     List<Integer>deviceId=Lists.newArrayList();
			        for (String string : ids) {
			        	deviceId.add(Integer.parseInt(string));
					}

				List<RfPad>padList=	padService.initQuery().andIn("deviceId",deviceId ).andIsNotNull("vmStatus").findAll();
					String padIds = Collections3.extractToString(padList, "padId", ",");
					RfPad pad = new RfPad();
					pad.setIds(padIds);
					padService.openOff(request, pad);
				} else {
					String[] ids = bean.getIds().split(",");
				     List<Integer>deviceId=Lists.newArrayList();
				        for (String string : ids) {
				        	deviceId.add(Integer.parseInt(string));
						}

					List<RfPad> padList = padService.initQuery().andIn("deviceId", deviceId).andIsNotNull("vmStatus").andEqualTo("padSn", sn).findAll();
					String padIds = Collections3.extractToString(padList, "padId", ",");
					RfPad pad = new RfPad();
					pad.setIds(padIds);
					padService.openOff(request, pad);
				}
		}
	}
	
	//获取虚拟任务列表
	@ResponseBody
	@RequestMapping(value = "vmlist")
	public PageInfo<RfVmTask> vmlist(HttpServletRequest request, HttpServletResponse response, Model model, RfVmTask bean) throws Exception {

		List<RfVmTask> list = vmTaskService.initQuery(bean)
				.andEqualTo("deviceId", bean.getDeviceId())
				.andEqualTo("deviceName", bean.getTaskResultStatus())
				.andEqualTo("taskType", bean.getTaskType())
				.andEqualTo("taskStatus", bean.getTaskStatus())
				.addOrderClause("createTime", "desc nulls last,device_id desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (RfVmTask RfVmTask : list) {
			RfVmTask.getMap().put("manageControlName", RfVmTask.getDeviceId() == null ? "--" :service.load(RfVmTask.getDeviceId())==null?"--": controlService.load(service.load(RfVmTask.getDeviceId()).getDeviceManageControlId()).getControlName());
			RfVmTask.getMap().put("deviceCode",(null==RfVmTask.getDeviceId()||"".equals(RfVmTask.getDeviceId()))?"--":service.load(RfVmTask.getDeviceId()).getDeviceCode());
			RfVmTask.getMap().put("padCode",(null==RfVmTask.getPadId()||"".equals(RfVmTask.getPadId()))?"--":padService.load(RfVmTask.getPadId()).getPadCode());
		}
		PageInfo<RfVmTask> pageInfo = new PageInfo<RfVmTask>(list);
		return pageInfo;
	}
	
	//获取虚拟任务清单列表
	@ResponseBody
	@RequestMapping(value = "vmHislist")
	public PageInfo<RfVmTaskHis> vmHislist(HttpServletRequest request, HttpServletResponse response, Model model, RfVmTaskHis bean) throws Exception {
		List<RfVmTaskHis> list = vmHisService.initQuery(bean)
				.andEqualTo("deviceId", bean.getDeviceId())
				.andEqualTo("deviceName", bean.getTaskResultStatus())
				.andEqualTo("taskType", bean.getTaskType())
				.andEqualTo("taskStatus", bean.getTaskStatus())
				.addOrderClause("createTime", "desc nulls last,device_id desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (RfVmTaskHis RfVmTaskHis : list) {
			RfVmTaskHis.getMap().put("manageControlName", RfVmTaskHis.getDeviceId() == null ? "--" :service.load(RfVmTaskHis.getDeviceId())==null?"--": controlService.load(service.load(RfVmTaskHis.getDeviceId()).getDeviceManageControlId()).getControlName());
			RfVmTaskHis.getMap().put("deviceCode",(null==RfVmTaskHis.getDeviceId()||"".equals(RfVmTaskHis.getDeviceId()))?"--":service.load(RfVmTaskHis.getDeviceId()).getDeviceCode());
			RfVmTaskHis.getMap().put("padCode",(null==RfVmTaskHis.getPadId()||"".equals(RfVmTaskHis.getPadId()))?"--":padService.load(RfVmTaskHis.getPadId()).getPadCode());
		}
		PageInfo<RfVmTaskHis> pageInfo = new PageInfo<RfVmTaskHis>(list);
		return pageInfo;
	}

	//导出EXcel数据
		@RequestMapping(value="export")
		public String export(HttpServletRequest request,HttpServletResponse response,Model model,RfDevice bean,String exportHead, String exportField, String exportName,String userMobilePhone,String ipSearchType,String romVersion,String deviceStartIp, String deviceEndIp,Integer idcId, Integer externalUserId) throws Exception{
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
			while(keep){
				bean.setPage(page);
				bean.setRows(1000);
				PageInfo<RfDevice> pageInfo=this.list(request, response, model, bean,userMobilePhone,ipSearchType,romVersion,deviceStartIp,deviceEndIp,idcId, externalUserId);
				List<RfDevice> list=pageInfo.getList();
				if(!Collections3.isEmpty(list)){
					for(RfDevice device:list){
						device.setDeviceStatus(DictHelper.getLabel("rf_device.device_status", device.getDeviceStatus()));
						device.setDeviceUse(DictHelper.getLabel("rf_device.device_use", device.getDeviceUse()));
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
	
	
	@RequestMapping(value = "device_ping")
	public void device_ping(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean,String padCodes) throws Exception {
		this.saveDevice(request, response, model, bean, padCodes);
	}
	private void saveDevice(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean, String padCodes) throws Exception {
		List<Integer> ids = service.saveBatchDevicePing(request, bean);
		if(null != ids && ids.size()>0){
			for (Integer id : ids) {
				pro.send(id.toString());
			}
		}
	}
	
	@RequestMapping(value = "addNewVMForm")
	public String addNewVMForm(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean) throws Exception {//TODO
		bean = service.get(bean.getDeviceId());	
		List<RfPad>list=padService.initQuery().andEqualTo("deviceId", bean.getDeviceId()).andIsNotNull("vmStatus").findDelTrue();
		Integer vm=Integer.parseInt(ConfigHelper.getValueByCode("config_device_vm_max"));
		if(null==vm||vm==0){
			throw new BusinessException("请确认物理机虚拟数设置是否正确");
		}
		if(list.size()>=vm){
			throw new BusinessException("物理设备"+bean.getDeviceCode()+"下的虚拟设备已达到上限");
		}
		model.addAttribute("deviceManageControlName", controlService.get(bean.getDeviceManageControlId()).getControlName());
		model.addAttribute("bean", bean);
		List<RfVideo> userVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeUser()).findStopTrue();
		List<RfVideo> padVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeDevice()).findStopTrue();
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType",ConstantsDb.rfControlControlTypeUser()).findStopTrue();
		List<RfControl> padControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		List<RfControl> manageControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).findStopTrue();
		List<RfIdc> idcList = idcService.initQuery().findDelTrue();
		model.addAttribute("idcList", idcList);
		model.addAttribute("controlList", controlList);
		model.addAttribute("padControlList", padControlList);
		model.addAttribute("manageControlList", manageControlList);
		model.addAttribute("userVideoList", userVideoList);
		model.addAttribute("padVideoList", padVideoList);
		List<RfControl> deviceControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).findStopTrue();
		model.addAttribute("deviceControlList", deviceControlList);
		
		List<RfPad> rfpads = padService.initQuery().andEqualTo("deviceId", bean.getDeviceId()).andIsNotNull("padSn").addOrderClause("padSn", "desc").findAll();
		if(rfpads.size()>0){
			RfPad lastOne = rfpads.get(0);
			model.addAttribute("lastOne", lastOne);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "addNewVM")
	public void addNewVM(HttpServletRequest request, HttpServletResponse response, Model model,RfDevice bean,Integer deviceId,Integer idcId,Integer userControlId,Integer padControlId,Integer padManageControlId,Integer userVideoId,Integer padVideoId,String vmIp) throws Exception {
		service.addNewVM(request, deviceId, idcId, userControlId, padControlId, padManageControlId, userVideoId, padVideoId, vmIp);
	}
	
	@RequestMapping("updateDeviceUse")
	public void updateDeviceUse(HttpServletRequest request, HttpServletResponse response, Model model, RfDevice bean, String deviceStartIp, String deviceEndIp) throws Exception {
		if(bean.getIds() == null){
			int count = service.initQuery().countAll();
			RfDevice rfDevice = new RfDevice();
			rfDevice.setRows(count);
			PageInfo<RfDevice> pageInfo = this.list(request, response, model, rfDevice, null, null, null, deviceStartIp, deviceEndIp, null, null);
			List<RfDevice> list=pageInfo.getList();
			String ids = "";
			if(list != null && list.size() > 0){
				ids = Collections3.extractToString(list, "deviceId", ",");
			}
			if(StringUtils.isBlank(ids)){
				throw new BusinessException("你所输入的物理IP查询不到设备");
			}
			bean.setIds(ids);
		}
		service.updateDeviceUse(request, bean);
	}
	
}
