package com.redfinger.manager.modules.facility.service;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.base.PadList;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfDeviceExample;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadExample;
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.domain.RfPadTaskBatch;
import com.redfinger.manager.common.domain.RfVmTask;
import com.redfinger.manager.common.domain.RfVmTaskExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.ConfigHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.jsm.DeviceQueueMessageUtil;
import com.redfinger.manager.common.mapper.RfDeviceMapper;
import com.redfinger.manager.common.mapper.RfPadMapper;
import com.redfinger.manager.common.mapper.RfVmTaskMapper;
import com.redfinger.manager.common.mapper.UpdatePadPadSNMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.Reflections;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.log.service.PadTaskService;
import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;
import com.redfinger.manager.modules.tasks.service.TaskBatchService;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "deviceId")
public class DeviceService extends BaseService<RfDevice, RfDeviceExample, RfDeviceMapper> {
    @Autowired
    PadService padService;
	@Autowired
	RfDeviceMapper mapper;
	@Autowired
	RfVmTaskMapper vmTaskMapper;
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	UpdatePadPadSNMapper updatePadPadSNMapper;
	@Autowired
	RfPadMapper padMapper;
	@Autowired
	VmTaskService vmTaskService;
	@Autowired
	PadTaskService padTaskService;
	@Autowired
	TaskBatchService taskBatchService;
	@Autowired
	ProducterMessageSender pro;
	
	public void saveIp(HttpServletRequest request, RfDevice bean, String endIp ,Integer idcId,Integer userControlId,Integer padControlId,Integer padManageControlId,Integer userVideoId,Integer padVideoId,String vmIp,String padClassify,String controlProtocol) throws Exception {
		Date time=new Date();
		String adminName = SessionUtils.getCurrentUserId(request);

        List<RfDevice> deviceList=Lists.newArrayList();
		if (null == endIp || "".equals(endIp)) {
			bean.setStatus("1");
			bean.setCreater(adminName);
			bean.setCreateTime(time);
			deviceList.add(bean);
		}else{
			String beginIp=bean.getDeviceIp();
			String[] endIps=endIp.split("\\.");
			String[] beginIps=beginIp.split("\\.");
			if(Integer.parseInt(beginIps[0])>Integer.parseInt(endIps[0])){
				throw new BusinessException("错误:001,结束IP地址参数错误,请核对参数重新输入!");
			}else{
				long ip1=getIP(InetAddress.getByName(beginIp));
				long ip2=getIP(InetAddress.getByName(endIp));
				for(long ip3=ip1;ip3<=ip2;ip3++)
				{
					RfDevice device=new RfDevice();
					device.setIdcId(idcId);
					device.setCreater(adminName);
					device.setCreateTime(time);
					device.setStatus("1");
					device.setReorder(bean.getReorder());
					device.setDeviceManageControlId(bean.getDeviceManageControlId());
					//deviceStatus默认值为空
					//device.setDeviceStatus(bean.getDeviceStatus());
					device.setDeviceIp(toIP(ip3).getHostAddress().toString());
					String ip= this.ipUtils(device.getDeviceIp());
					device.setDeviceName(bean.getDeviceName()+ip);
					device.setDeviceCode(bean.getDeviceCode()+ip);
					device.setDeviceType(bean.getDeviceType());
					device.setDeviceSource(bean.getDeviceSource());
					device.setRomVersion(bean.getRomVersion());//设备rom版本号
					device.setRamSize(bean.getRamSize());//设备rom大小
					log.info("save ip deviceCode:{}",device.getDeviceCode());
				    deviceList.add(device);
				}
				
			}
			
		}
		
		List<RfDevice> list = Lists.newArrayList();
		@SuppressWarnings("unchecked")
		List<String> codeList = Collections3.extractToList(deviceList, "deviceCode");
		list = this.initQuery(bean).andIn("deviceCode", codeList).findDelTrue();
		if (!Collections3.isEmpty(list)) {
			throw new BusinessException("错误：001，设备编号参数错误，该设备号已被占用！");
		}

		@SuppressWarnings("unchecked")
		List<String> ipList = Collections3.extractToList(deviceList, "deviceIp");
		list = this.initQuery(bean).andIn("deviceIp", ipList).findDelTrue();
		if (!Collections3.isEmpty(list)) {
			throw new BusinessException("错误：001，IP段中已有IP地址被使用，请重新确认！");
		}
	
		mapper.insertBatch(deviceList);
	  
		this.executeAopMethod(ServiceMethod.after_device_vm, this, request, time,idcId, userControlId, padControlId, padManageControlId, userVideoId, padVideoId,vmIp,padClassify,controlProtocol);
	}
	
	//IP地址转十二位MAC地址
		public String MacUtils(String ip) {
			String[] ips = ip.split("\\.");
			String mac="";
			List<String> newip = Lists.newArrayList();
			for (String string : ips) {
				mac+=String.format("%03d", Integer.parseInt(string));
				//newip.add(String.format("%03d", Integer.parseInt(string)));
			}	
		      //mac地址前两位默认00 2016.3.30
		     	newip.add("00");
			for(int i=2;i<mac.length();i+=2){
				String x=String.valueOf(mac.charAt(i))+mac.charAt(i+1);	
				newip.add(DeviceService.fill(Integer.toHexString(Integer.parseInt(x)).toString(),2,'0'));
			}
      
			return Joiner.on(":").join(newip);
		}
		//补全0
		private static String fill(String input, int size, char symbol) {
			while (input.length() < size) {
				input = symbol + input;
			}
			return input;
		}
//ip补全三位199199199
	public String ipUtils(String ip) {
		String[] ips = ip.split("\\.");
		List<String> newip = Lists.newArrayList();
		for (String string : ips) {
				newip.add(String.format("%03d", Integer.parseInt(string)));
		}
		return Joiner.on("").join(newip);
	}
////ip地址补全
//	public String ipUtils(String ip) {
//		String[] ips = ip.split("\\.");
//		List<String> newip = Lists.newArrayList();
//		for (String string : ips) {
//			newip.add(String.format("%03d", Integer.parseInt(string)));
//		}
//		return Joiner.on(".").join(newip);
//	}
	
	public static long getIP(InetAddress ip)
	{
	byte[] b=ip.getAddress();
	long l= b[0]<<24L & 0xff000000L|
	       b[1]<<16L & 0xff0000L  |
	       b[2]<<8L  &  0xff00L   |
	       b[3]<<0L  &  0xffL ;
	return l;
	}
	//由低32位二进制数构成InetAddress对象
	public static InetAddress toIP(long ip) throws UnknownHostException
	{
	byte[] b=new byte[4];
	int i=(int)ip;//低３２位
	b[0]= (byte)( (i >> 24) & 0x000000ff );
	b[1]= (byte)( (i >> 16) & 0x000000ff );
	b[2]= (byte)( (i >> 8)  & 0x000000ff );
	b[3]= (byte)( (i >> 0)  & 0x000000ff );
	return InetAddress.getByAddress(b);
	}

	public Map<String,Object> task(HttpServletRequest request, RfDevice bean,String taskType,Long retryInterval,String accredit) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String adminName = SessionUtils.getCurrentUserId(request);
		Date time=new Date();
		List<RfVmTask>vmTaskList=Lists.newArrayList();
		List<RfPad> padList = Lists.newArrayList();
		String[] ids = bean.getIds().split(",");
		List<Integer> idsList = Lists.newArrayList();  
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		if ("".equals(accredit) || null == accredit) {
			padList = padService.initQuery().andIn("deviceId", idsList).findDelTrue();
		} else {
			padList = padService.initQuery().andIn("deviceId", idsList).andEqualTo("grantOpenStatus", accredit).findDelTrue();
		}
		if(padList.size()<1){
			throw new BusinessException("没有可执行的虚拟设备");
		}
		for (RfPad pad : padList) {
			RfVmTask vm=new RfVmTask();
			vm.setDeviceId(pad.getDeviceId());
			if(retryInterval!=null){
			vm.setRetryInterval(retryInterval);
			}
			vm.setPadId(pad.getPadId());
			vm.setTaskType(taskType);
			vm.setTaskStatus("0");
			vm.setStatus("1");
			vm.setCreateTime(time);
			vm.setCreater(adminName);
			//任务来源
			vm.setTaskSource(ConstantsDb.rfVmTaskTaskSourceSystem());
			RfVmTask mapper = vmTaskService.insertVmTask(vm);
			
			pad.setTaskId(mapper.getVmTaskId());
			pad.setRecoverStatus("0");
			padService.update(request, pad);
			
			vmTaskList.add(vm);
		}
		/*vmTaskMapper.insertBatch(vmTaskList);*/
		
		map.put("time", time);
		map.put("padList", padList);
		map.put("vmTaskList", vmTaskList);
		return map;
	}
	
	public Map<String,Object> taskOnly(HttpServletRequest request, RfDevice bean,String taskType,Long retryInterval) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String adminName = SessionUtils.getCurrentUserId(request);
		Date time=new Date();
		List<RfVmTask> vmTaskList = Lists.newArrayList();
		List<RfPad> padList = Lists.newArrayList();
		String[] ids = bean.getIds().split(",");
		List<Integer> idsList = Lists.newArrayList();  
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		padList = padService.initQuery().andIn("padId", idsList).findDelTrue();
		for (RfPad pad : padList) {
			RfVmTask vm=new RfVmTask();
			vm.setDeviceId(pad.getDeviceId());
			if(retryInterval!=null){
				vm.setRetryInterval(retryInterval);
				}
			vm.setPadId(pad.getPadId());
			vm.setTaskType(taskType);
			vm.setTaskStatus("0");
			vm.setCreateTime(time);
			vm.setCreater(adminName);
			vm.setStatus("1");
			//任务来源
			vm.setTaskSource(ConstantsDb.rfVmTaskTaskSourceSystem());
			RfVmTask mapper = vmTaskService.insertVmTask(vm);
			
			pad.setTaskId(mapper.getVmTaskId());
			pad.setRecoverStatus("0");
			padService.update(request, pad);
			
			
			vmTaskList.add(mapper);
		}
		/*vmTaskMapper.insertBatch(vmTaskList);*/
		map.put("time", time);
		map.put("padList", padList);
		map.put("vmTaskList", vmTaskList);
		return map;
	}
	
	public Integer jmsSend(Date time, String taskType,HttpServletRequest request) {
		String adminName = SessionUtils.getCurrentUserId(request);
		List<RfVmTask> vmTaskList = Lists.newArrayList();
		RfVmTaskExample example = new RfVmTaskExample();
		RfVmTaskExample.Criteria criteria = example.createCriteria();
		criteria.andCreateTimeEqualToIgnoreNull(time);
		vmTaskList = vmTaskMapper.selectByExampleShowField(Lists.newArrayList(RfVmTask.FD_VM_TASK_ID,RfVmTask.FD_DEVICE_ID), example);
		try {
		for (RfVmTask vm : vmTaskList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("opType", "command");
				map.put("taskId", vm.getVmTaskId());
				//jmsTemplate.convertAndSend("device-manage", JsonUtils.ObjectToString(map));
				DeviceQueueMessageUtil.sendMessageByDeviceId(vm.getDeviceId(), JsonUtils.ObjectToString(map));
		}
		} catch (Exception e) {
			e.printStackTrace();
			RfVmTask vm=new RfVmTask();
			vm.setTaskStatus("end");
			vm.setTaskResultStatus("defeated");
			vm.setTaskResultInfo("设备任务消息队列发送失败");
			vm.setModifier(adminName);
			vm.setModifyTime(time);
			vmTaskMapper.updateByExampleSelective(vm, example);
			return null;//throw new BusinessException("设备启动任务消息队列发送失败"); 抛出异常将导致无法更新数据
		}
		return 1;
	}
	
	public Integer jmsSendTask(List<RfVmTask> vmTaskList, Date time, String taskType,HttpServletRequest request) {
		String adminName = SessionUtils.getCurrentUserId(request);
		if(null != vmTaskList && vmTaskList.size()>0){
			for (RfVmTask vm : vmTaskList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("opType", "command");
				map.put("taskId", vm.getVmTaskId());
				try{
					log.info("[opType:{},taskId:{}]",new Object[]{"command",vm.getVmTaskId()});
					//jmsTemplate.convertAndSend("device-manage", JsonUtils.ObjectToString(map));
					DeviceQueueMessageUtil.sendMessageByDeviceId(vm.getDeviceId(), JsonUtils.ObjectToString(map));
				}catch(Exception e){
					e.printStackTrace();
					
					vm.setTaskStatus("end");
					vm.setTaskResultStatus("defeated");
					vm.setTaskResultInfo("设备任务消息队列发送失败");
					vm.setModifier(adminName);
					vm.setModifyTime(time);
					vmTaskMapper.updateByPrimaryKey(vm);
					
					RfPad pad = new RfPad();
					pad.setPadId(vm.getPadId());
					pad.setRecoverInfo("设备"+taskType+"任务执行失败");
					return null;
				}
				
			}
		}
		
		return 1;
	}
	
	
	/*//自定义添加切点织入方法
	@ServiceAnnotation(name = ServiceMethod.after_vm_task)
	public String afterVmTask(HttpServletRequest request, List<RfVmTask> vmTaskList,String taskType) {
			try {
				for (RfVmTask vm : vmTaskList) {
					Map<String ,Object> map = new HashMap<String ,Object>();
					map.put("opType", taskType);
					map.put("taskId", vm.getVmTaskId());
					Map<String ,Object> mapTask = new HashMap<String ,Object>();
					mapTask.put("message", map);
					jmsTemplate.convertAndSend("device-manage",JsonUtils.ObjectToString(mapTask));	
					System.out.println(JsonUtils.ObjectToString(mapTask));	
				}
			} catch (Exception e) {
				return "启用日志写入失败";
				e.printStackTrace();
			}
	
		return null;
	}
	
	private void executeAopMethod(ServiceMethod serviceMethod, Object obj, HttpServletRequest request, List<RfVmTask> bean,Object taskType) {
		Method method = Reflections.getAnnotationMethod(this, serviceMethod);
		if (method != null) {
			Object result = null;
			try {
				result = method.invoke(this, request, bean,taskType);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			if (result != null) {
				throw new BusinessException(result.toString());
			}
		}
	}*/

	public void addVM(HttpServletRequest request, RfDevice bean,String type,String deviceType) throws Exception {
		String[]padIds=bean.getIds().split(",");
	    List<RfPad>list= padService.initQuery(bean).andEqualTo("deviceId", bean.getDeviceId()).findAll();
		Integer vm=Integer.parseInt(ConfigHelper.getValueByCode("config_device_vm_max"));
		if(type.equals("add")){
			if((padIds.length+list.size())>vm){
				throw new BusinessException("每台物理设备下只能虚拟"+vm+"台虚拟设备");
			}
		}
		String adminName = SessionUtils.getCurrentUserId(request);
		Date time =new Date();
		for (String string : padIds) {
			RfPadExample example=new RfPadExample();
			RfPadExample.Criteria criteria=example.createCriteria();
			criteria.andPadIdEqualToIgnoreNull(Integer.parseInt(string));
			RfPad pad=  padMapper.selectOneByExampleShowField(Lists.newArrayList(RfPad.FD_PAD_ID,RfPad.FD_PAD_IP ),example);
			if(type.equals("add")){
				pad.setDeviceId(bean.getDeviceId());
				pad.setModifier(adminName);
				pad.setModifyTime(time);
				pad.setVmStatus(ConstantsDb.rfDeviceDeviceStatusOffline());
				if(null==pad.getVmMac()||"".equals(pad.getVmMac())){
					pad.setVmMac(this.MacUtils(pad.getPadIp()));
				}
				pad.setPadType(deviceType);
			}
			if (type.equals("remove")) {
				pad.setDeviceId(null);
				pad.setPadSn("");
				pad.setModifier(adminName);
				pad.setModifyTime(time);
				pad.setPadType("");
			}
			
			updatePadPadSNMapper.updateByPrimaryKey(pad);
		}

	}
	
	@Transactional(readOnly = true)
	public List<RfDevice> getDeviceByPadCodes(String deviceCode){
		List<RfDevice> list = this.initQuery().andLike("deviceCode", deviceCode).singleAll();
		if(!Collections3.isEmpty(list)){
			return list;
		}else{
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public RfDevice getDeviceByPadCode(String deviceCode){
		List<RfDevice> list = this.initQuery().andEqualTo("deviceCode", deviceCode).singleAll();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	public void updateVM(HttpServletRequest request,Integer devicePadId, PadList padList) throws Exception {
		List<RfPad> list=padList.getPads();
		String string="";
	     for (int i = 0; i < list.size() - 1; i++)
	        {
	    	 string = list.get(i).getPadSn();
	            for (int j = i + 1; j <  list.size(); j++)
	            {
				if (string.equals(list.get(j).getPadSn())) {
					throw new BusinessException("第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + string);
				}
			}
		}
		for (RfPad rfPad : list) {
			padService.update(request, rfPad);
		}
		RfDevice bean=new RfDevice();
		bean.setDeviceId(devicePadId);
		this.update(request, bean);
	}
	

	public void startVM(HttpServletRequest request,RfDevice bean) throws Exception {
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			RfDevice device=new RfDevice();
			device.setModifier(userId);
			device.setModifyTime(currentDate);
			device.setDeviceId(Integer.parseInt(idStr));
			device.setStatus(ConstantsDb.globalStatusNomarl());
			mapper.updateByPrimaryKeySelective(device);
		}

	}
	public void stopVM(HttpServletRequest request,RfDevice bean) throws Exception {
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			RfDevice device=new RfDevice();
			device.setModifier(userId);
			device.setModifyTime(currentDate);
			device.setDeviceId(Integer.parseInt(idStr));
			device.setStatus(ConstantsDb.globalStatusDelete());
			mapper.updateByPrimaryKeySelective(device);
		}
	}
	
//	//新增物理机的时候自动添加虚拟机
	@ServiceAnnotation(name = ServiceMethod.after_device_vm)
	public String afterDeviceVm(HttpServletRequest request, Date time,Integer idcId,Integer userControlId,Integer padControlId,Integer padManageControlId,Integer userVideoId,Integer padVideoId,String vmIp,String padClassify,String controlProtocol) {
		List<RfDevice> deviceList=this.initQuery().andEqualTo("createTime", time).addOrderClause("deviceId", "asc").findAll();
		String userId = SessionUtils.getCurrentUserId(request);
		Integer vm=Integer.parseInt(ConfigHelper.getValueByCode("config_device_vm"));
		if(null==vm||vm==0){
			throw new BusinessException("请确认物理机虚拟数设置是否正确");
		}
		try {
			List<RfPad>padList=Lists.newArrayList();
		    Integer ipEnd=0;
		    String[] ip= vmIp.split("\\.");
		    Integer ip3=Integer.parseInt(ip[2]);
			for (RfDevice device : deviceList) {
				//根据配置循环编写新的虚拟设备信息并报存数据库
				for(int i=0;i<vm;i++){
					Integer ip4=Integer.parseInt(ip[3])+ipEnd;
	
					ipEnd++;	
					if(ip4==0){
						return "输入了非法的虚拟IP地址";
						// throw new BusinessException("输入了非法的虚拟IP地址");
					}
					if(ip4>250){ 
						if(ip4%250==0){
							ip4=250; 
						 }else{
							 ip4=ip4%250;
						 }			
						 if(ip3%4==0){
							 return "虚拟设备IP开始地址不合法，请查询虚拟IP第三位规则后输入";
							 //throw new BusinessException("虚拟设备IP开始地址不合法，请重试，第三位不能跨4位新增");
						 }
					}	 
					RfPad pad=new RfPad();
					List<String> padip = Lists.newArrayList();
					padip.add(ip[0]);
					padip.add(ip[1]);
					padip.add(String.valueOf(ip3));
					padip.add(String.valueOf(ip4));
					if(ip4%250==0){
						ip3++;	 
					}
					String newPadIp=Joiner.on(".").join(padip);
					pad.setPadIp(newPadIp);
					pad.setDeviceId(device.getDeviceId());
					//设备状态初始化：虚拟状态，状态，启用状态，故障状态，虚拟设备没有设备状态，绑定状态
					pad.setVmStatus("0");
					pad.setStatus(ConstantsDb.globalStatusNomarl());
					pad.setEnableStatus(ConstantsDb.globalEnableStatusStop());
					pad.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
					pad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
				 
					 //初始化设备配置信息:机房，节点，
					 pad.setIdcId(idcId);
					 pad.setUserControlId(userControlId);
					 pad.setPadControlId(padControlId);
					 pad.setPadManageControlId(padManageControlId);
					 pad.setUserVideoId(userVideoId);
					 pad.setPadVideoId(padVideoId);
					 pad.setMaintStatus(ConstantsDb.rfPadMaintStatusOff());//设置为正常
				 
					 //虚拟设备的编号用IP地址补全三位+VM
					 String ipAddress=this.ipUtils(newPadIp);
					 pad.setPadCode("VM"+ipAddress);
					 pad.setImei("000"+ipAddress);
					 pad.setPadName(device.getDeviceCode()+i);
					 pad.setPadSn(String.valueOf(i));
					 pad.setCreater(userId);
					 //虚拟设备默认未授权
					 pad.setGrantOpenStatus(ConstantsDb.rfPadGrantOpenStatusOff());
					 pad.setCreateTime(device.getCreateTime());
					 pad.setPadSource(device.getDeviceSource());
					 pad.setPadType(device.getDeviceType());
					 pad.setRomVersion(device.getRomVersion());//设备rom版本号设为跟物理设备一样
					 pad.setPadClassify(padClassify);//设备类别跟物理设备一致
					 pad.setControlProtocol(controlProtocol);//设置控制协议
					 padList.add(pad);
					 RfPad rfPad= padService.getPadByPadCode(pad.getPadCode());
				     if(null!=rfPad){
				    	 return "设备编号"+rfPad.getPadCode()+"已被使用";
				    	// throw new BusinessException("设备编号"+pad.getPadCode()+"已被使用");
				     } 
				     RfPad padIp= padService.getPadByPadIp(pad.getPadIp());
				     if(null!=padIp){
				    	 return "设备IP"+padIp.getPadIp()+"已被使用";
				    	// throw new BusinessException("设备IP"+pad.getPadIp()+"已被使用");
				     } 
				     String mac=this.MacUtils(newPadIp);
				     RfPad padMac= padService.getPadByVmMac(mac);
				     if(null!=padMac){
				    	 return "设备MAC"+padMac.getVmMac()+"已被使用";
				    	// throw new BusinessException("设备IP"+pad.getPadIp()+"已被使用");
				     } 
					 pad.setVmMac(mac); 
				}
			}
		    padMapper.insertBatch(padList);
		} catch (Exception e) {
	    	e.printStackTrace();
			//return "生成虚拟设备时出错";
		}
		return null;
	}
	

	private void executeAopMethod(ServiceMethod serviceMethod, Object obj, HttpServletRequest request, Date bean,Integer idcId,Integer userControlId,Integer padControlId,Integer padManageControlId,Integer userVideoId,Integer padVideoId,String vmIp,String padClassify,String controlProtocol) {
		Method method = Reflections.getAnnotationMethod(this, serviceMethod);
		if (method != null) {
			Object result = null;
			try {
				result = method.invoke(this, request, bean,idcId, userControlId, padControlId, padManageControlId, userVideoId, padVideoId,vmIp,padClassify,controlProtocol);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			if (result != null) {
				throw new BusinessException(result.toString());
			}
		}
	}

	public Date taskDevice(HttpServletRequest request, RfDevice bean,String taskType) {
		String adminName = SessionUtils.getCurrentUserId(request);
		Date time=new Date();
		List<RfVmTask>vmTaskList=Lists.newArrayList();
		String[] ids = bean.getIds().split(",");
		List<Integer> idsList = Lists.newArrayList();  
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		for (String string : ids) {
			RfVmTask vm=new RfVmTask();
			vm.setDeviceId(Integer.parseInt(string));
			vm.setTaskType(taskType);
			//未发送 0
			vm.setTaskStatus("0");
			vm.setCreateTime(time);
			vm.setCreater(adminName);
			vm.setStatus("1");
			vmTaskList.add(vm);
		}
		vmTaskMapper.insertBatch(vmTaskList);
		return time;
	}

	public Map<String,Object> batchPad(HttpServletRequest request, String padCode,String actionType) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		Date time=new Date();
		String adminName = SessionUtils.getCurrentUserId(request);
		padCode=padCode.replaceAll(" ", "");
		padCode=padCode.replaceAll("\r", "");
		String [] ips=padCode.split("\n");
		if(ips.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		List<RfPad>padList=Lists.newArrayList();
		List<RfVmTask>vmTaskList=Lists.newArrayList();
		for (String string : ips) {
			if (null != string &&!"".equals(string)) {
				RfPad pad = padService.getDevicePadByPadCodes(string);
				if (pad == null) {
					throw new BusinessException("没有这个设备编号:" + string);
				}
				padList.add(pad);
			}
		}
		for (RfPad pad : padList) {
			RfVmTask vm=new RfVmTask();
			vm.setDeviceId(pad.getDeviceId());
			vm.setPadId(pad.getPadId());
			vm.setTaskType(actionType);
			vm.setTaskStatus("0");
			vm.setCreateTime(time);
			vm.setCreater(adminName);
			vm.setStatus("1");
			//任务来源
			vm.setTaskSource(ConstantsDb.rfVmTaskTaskSourceSystem());
			RfVmTask mapper = vmTaskService.insertVmTask(vm);
			
			pad.setTaskId(mapper.getVmTaskId());
			pad.setRecoverStatus("0");
			padService.update(request, pad);
			
			vmTaskList.add(vm);
		}
		/*vmTaskMapper.insertBatch(vmTaskList);*/
		
		map.put("time", time);
		map.put("padList", padList);
		map.put("vmTaskList", vmTaskList);
		return map;
	}
	
	//padSn 和授权启用状态字段
	public void task(HttpServletRequest request, RfDevice bean,String taskType,String padSn) throws Exception {
		if(taskType.equals(ConstantsDb.rfVmTaskTaskTypeOpensn())){
			if(null==padSn||"".equals(padSn)){
				throw new BusinessException("请输入要启动的虚拟设备的SN");
			}
		}
		List<RfPad> padList = Lists.newArrayList();
		String[] ids = bean.getIds().split(",");
		List<Integer> idsList = Lists.newArrayList();  
		for (String string : ids) {
			idsList.add(Integer.parseInt(string));
		}
		padList = padService.initQuery().andIn("deviceId", idsList).andEqualTo("padSn", padSn).andEqualTo("grantOpenStatus", ConstantsDb.rfPadGrantOpenStatusOn()).findDelTrue();
		if(padList.size()<1){
			throw new BusinessException("该批次物理设备下没有可发布的虚拟设备");
		}
		/*for (RfPad pad : padList) {
			RfVmTask vm=new RfVmTask();
			vm.setDeviceId(pad.getDeviceId());
			vm.setPadId(pad.getPadId());
			vm.setTaskType(taskType);
			vm.setTaskStatus("0");
			vm.setStatus("1");
			vm.setCreateTime(time);
			vm.setCreater(adminName);
			vmTaskList.add(vm);
		}*/
		@SuppressWarnings("unchecked")
		List<Integer>padIds=Collections3.extractToList(padList,"padId");
		String enable=ConstantsDb.rfPadEnableStatusStart();
		for (Integer id : padIds) {
			RfPad record=new RfPad();
			record.setPadId(id);
			record.setEnableStatus(enable);
			padService.update(request, record);
		}
		
		
	}

	/**
	 * 根据padCode获取pad,因为padCode有唯一约束，所以只能是返回一条记录
	 * @param deviceCode
	 * @return RfDevice
	 */
	@Transactional(readOnly = true)
	public RfDevice getPadByPadCode(String deviceCode){
		List<RfDevice> list = this.initQuery().andEqualTo("deviceCode", deviceCode).singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * @throws Exception 
	 * 
	* @Title: deviceByCode
	* @Description: 根据物理设备的CODE执行命令操作
	* @param @param request
	* @param @param codes  物理设备编号
	* @param @param type   操作类型
	* @return void    返回类型
	* @throws
	 */
	public List<RfVmTask> deviceByCode(HttpServletRequest request, String padCode,String actionType) throws Exception {
		Date time=new Date();
		String adminName = SessionUtils.getCurrentUserId(request);
		padCode=padCode.replaceAll(" ", "");
		padCode=padCode.replaceAll("\r", "");
		String [] ips=padCode.split("\n");
		if(ips.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		List<RfVmTask>vmTaskList=Lists.newArrayList();
		for (String string : ips) {
			if (null != string &&!"".equals(string)) {
				RfDevice device = this.getPadByPadCode(string);
				if (device == null) {
					throw new BusinessException("没有这个物理设备编号:" + string);
				}
				RfVmTask vm=new RfVmTask();
				vm.setDeviceId(device.getDeviceId());
				vm.setTaskType(actionType);
				vm.setTaskStatus("0");
				vm.setCreateTime(time);
				vm.setCreater(adminName);
				vm.setStatus("1");
				vmTaskService.save(request, vm);
				vmTaskList.add(vm);
			}
		}
		if(vmTaskList.size()<1){
			throw new BusinessException("错误：0012输入了无效参数");
		}
		return vmTaskList;
	}

	
	public Integer jmsSend(List<RfVmTask> list, String taskType,HttpServletRequest request) throws Exception {
		String adminName = SessionUtils.getCurrentUserId(request);
		try {
		for (RfVmTask vm : list) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("opType", "command");
				map.put("taskId", vm.getVmTaskId());
				log.info("[opType:{},taskId{}]",new Object[]{"command",vm.getVmTaskId()});
				//jmsTemplate.convertAndSend("device-manage", JsonUtils.ObjectToString(map));
				DeviceQueueMessageUtil.sendMessageByDeviceId(vm.getDeviceId(), JsonUtils.ObjectToString(map));
		}
		} catch (Exception e) {
			e.printStackTrace();
			RfVmTask vm=new RfVmTask();
			vm.setTaskStatus("end");
			vm.setTaskResultStatus("defeated");
			vm.setTaskResultInfo("设备任务消息队列发送失败");
			vm.setModifier(adminName);
			vm.setModifyTime(new Date());
			vmTaskService.update(request, vm);
			return null;//throw new BusinessException("设备启动任务消息队列发送失败"); 抛出异常将导致无法更新数据
		}
		return 1;
	}
	/**
	 * 
	* @Title: deviceCodes
	* @Description: 查找批量物理设备
	* @param @param codes（以逗号分隔的物理设备编号组成的字符串）
	* @param @return    设定文件
	* @return List<RfDevice>    返回类型
	* @throws
	 */
	public List<RfDevice> deviceCodes(String codes){
		List<RfDevice> list=Lists.newArrayList();
		List<String>strlist=Lists.newArrayList();
		if(StringUtils.isNotEmpty(codes)){
		strlist=Arrays.asList(codes.split(","));
		}else{
			return null;
		}
		list=this.initQuery().andIn("deviceCode", strlist).findAll();
		if(Collections3.isEmpty(list)){
			return null;
		}
		return list;
	}
	
	public List<Integer> saveBatchDevicePing(HttpServletRequest request, RfDevice bean) throws Exception {
		List<Integer> deviceIdList = Lists.newArrayList();
		RfPadTaskBatch batchBean = new RfPadTaskBatch();
		List<RfDevice> padIds = Lists.newArrayList();
		List<Integer> deviceIds = Lists.newArrayList();
		String ids = bean.getIds();
		if (ids.isEmpty()) {
			throw new BusinessException("错误：001 输入了无效参数");
		}
		
		String id[] = ids.split(",");
		for (String str : id) {
			deviceIds.add(Integer.parseInt(str));
		}
		
		Integer count = 0;
		
		for (Integer deviceId : deviceIds) {
			RfDevice pad = this.get(deviceId);
			if (pad == null) {
				throw new BusinessException("没有这个物理设备编号【" + pad.getDeviceCode() + "】");
			}
			if (null == pad.getDeviceIp() || "".equals(pad.getDeviceIp())) {
				throw new BusinessException("物理设备编号【" + pad.getDeviceCode() + "】IP是空的");
			}
			List<RfPadTask> list = padTaskService.initQuery().andEqualTo("commandType", "device_ping").andEqualTo("padId", pad.getDeviceId())
					.andGreaterThanOrEqualTo("createTime", new Date(System.currentTimeMillis() - Integer.parseInt(ConfigConstantsDb.configTaskTimeout()) * 60 * 1000))
					.andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd()).findStopTrue();
			if (!Collections3.isEmpty(list)) {
				throw new BusinessException("物理设备编号【" + pad.getDeviceCode() + "】的设备，当前有一个命令正在执行中");
			}
			padIds.add(pad);
			count++;
		}
		batchBean.setName("ping");
		batchBean.setCreater(SessionUtils.getCurrentUserId(request));
		batchBean.setCreateTime(new Date());
		batchBean.setPadCount(count);
		batchBean.setRemark("device_ping");
		taskBatchService.save(request, batchBean);
		if (padIds == null) {
			throw new BusinessException("设备信息不能为空");
		}
		for (RfDevice padId : padIds) {
			String task = "./pad_ping";
			String url = "";
				RfDevice device = mapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfDevice.FD_DEVICE_IP), padId.getDeviceId());
				String deviceIp = device.getDeviceIp();
				task = task  + " " + padId.getDeviceCode() + " " + deviceIp + " " + 22;
		
			RfPadTask padTask = new RfPadTask();
			padTask.setRemark(url);
			padTask.setPadId(padId.getDeviceId());
			padTask.setPadCode(padId.getDeviceCode());
			padTask.setBatchId(batchBean.getId());
			padTask.setTaskCommand(task);
			padTask.setCommandType("device_ping");
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
			padTaskService.save(request, padTask);
			deviceIdList.add(padId.getDeviceId());
		}
		return deviceIdList;
	}
	
	public String addNewVM(HttpServletRequest request,Integer deviceId,Integer idcId,Integer userControlId,Integer padControlId,Integer padManageControlId,Integer userVideoId,Integer padVideoId,String vmIp) {
		String userId = SessionUtils.getCurrentUserId(request);
		Date now = new Date();
		Integer vm=Integer.parseInt(ConfigHelper.getValueByCode("config_device_vm"));
		if(null==vm||vm==0){
			throw new BusinessException("请确认物理机虚拟数设置是否正确");
		}
		try {
			List<RfPad>padList=Lists.newArrayList();
		    RfDevice device = this.get(deviceId);
				//根据配置循环编写新的虚拟设备信息并报存数据库
					RfPad pad=new RfPad();
					pad.setPadIp(vmIp);
					pad.setDeviceId(device.getDeviceId());
					//设备状态初始化：虚拟状态，状态，启用状态，故障状态，虚拟设备没有设备状态，绑定状态
					pad.setVmStatus("0");
					pad.setStatus(ConstantsDb.globalStatusNomarl());
					pad.setEnableStatus(ConstantsDb.globalEnableStatusStop());
					pad.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
					pad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
				 
					 //初始化设备配置信息:机房，节点，
					 pad.setIdcId(idcId);
					 pad.setUserControlId(userControlId);
					 pad.setPadControlId(padControlId);
					 pad.setPadManageControlId(padManageControlId);
					 pad.setUserVideoId(userVideoId);
					 pad.setPadVideoId(padVideoId);
					 pad.setMaintStatus(ConstantsDb.rfPadMaintStatusOff());//设置为正常
				 
					 //虚拟设备的编号用IP地址补全三位+VM
					 String ipAddress=this.ipUtils(vmIp);
					 pad.setPadCode("VM"+ipAddress);
					 pad.setImei("000"+ipAddress);
					 List<RfPad> rfpads = padService.initQuery().andEqualTo("deviceId", deviceId).andIsNotNull("padSn")
							 .addOrderClause("padSn", "desc").findAll();
					 String i = "0";
					 if(null!=rfpads&&rfpads.size()>0){
						 i=rfpads.get(0).getPadSn();
						 i=""+(Integer.valueOf(i)+1);
					 }
					 
					 pad.setPadName(device.getDeviceCode()+i);
					 pad.setPadSn(String.valueOf(i));
					 pad.setCreater(userId);
					 //虚拟设备默认未授权
					 pad.setGrantOpenStatus(ConstantsDb.rfPadGrantOpenStatusOff());
					 pad.setCreateTime(now);
					 pad.setPadSource(device.getDeviceSource());
					 pad.setPadType(device.getDeviceType());
					 pad.setRomVersion(device.getRomVersion());//设备rom版本号设为跟物理设备一样
					 padList.add(pad);
					 RfPad rfPad= padService.getPadByPadCode(pad.getPadCode());
				     if(null!=rfPad){
				    	 //return "设备编号"+rfPad.getPadCode()+"已被使用";
				    	throw new BusinessException("设备编号"+pad.getPadCode()+"已被使用");
				     } 
				     RfPad padIp= padService.getPadByPadIp(pad.getPadIp());
				     if(null!=padIp){
				    	 //return "设备IP"+padIp.getPadIp()+"已被使用";
				    	throw new BusinessException("设备IP"+pad.getPadIp()+"已被使用");
				     } 
				     String mac=this.MacUtils(vmIp);
				     RfPad padMac= padService.getPadByVmMac(mac);
				     if(null!=padMac){
				    	 //return "设备MAC"+padMac.getVmMac()+"已被使用";
				    	throw new BusinessException("设备IP"+pad.getPadIp()+"已被使用");
				     } 
					 pad.setVmMac(mac); 
					 
		    padMapper.insertBatch(padList);
		} catch (Exception e) {
	    	e.printStackTrace();
			//return "生成虚拟设备时出错";
	    	throw new BusinessException("生成虚拟设备时出错");
		}
		return null;
	}
	
	
	public void updateDeviceUse(HttpServletRequest request, RfDevice bean) throws Exception{
		
		if(bean == null || StringUtils.isBlank(bean.getIds())){
			throw new BusinessException("你所输入的物理IP查询不到设备!");
		}
		
		if(StringUtils.isBlank(bean.getDeviceUse())){
			throw new BusinessException("使用的项目不能为空!");
		}
		
		if(!bean.getDeviceUse().equals(ConstantsDb.deviceUseMain()) && !bean.getDeviceUse().equals(ConstantsDb.deviceUseOther())){
			throw new BusinessException("你输入的引用的项目["+bean.getDeviceUse()+"]不存在!");
		}
		
		String[] ids = StringUtils.split(bean.getIds(),",");
		
		for (String id : ids) {
			RfDevice device = this.load(NumberUtils.toInt(id));
			if(device == null){
				throw new BusinessException("物理设备ID["+id+"]不存在!");
			}
			device.setDeviceUse(bean.getDeviceUse());
			//device.setModifier(SessionUtils.getCurrentUserId(request));
			//device.setModifyTime(new Date());
			this.update(request, device);
		}
	}
}
