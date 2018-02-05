package com.redfinger.manager.modules.tasks.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.RomVersion;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadExample;
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.domain.RfPadTaskBatch;
import com.redfinger.manager.common.domain.RfPadTaskBatchExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfDeviceMapper;
import com.redfinger.manager.common.mapper.RfPadMapper;
import com.redfinger.manager.common.mapper.RfPadTaskBatchMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.Md5Utils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.TaskSource;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.facility.dto.GameDto;
import com.redfinger.manager.modules.facility.service.DeviceService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.log.service.PadTaskService;
import com.redfinger.manager.modules.log.service.VmTaskHisService;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class TaskBatchService extends BaseService<RfPadTaskBatch, RfPadTaskBatchExample, RfPadTaskBatchMapper> {
	
	@Value("#{configProperties['game.shell.host']}")
	private String gameShellHost;
	@Value("#{configProperties['game.shell.user']}")
	private String gameShellUser;
	@Value("#{configProperties['game.shell.passwd']}")
	private String gameShellPasswd;
	
	@Autowired
	PadService padService;
	@Autowired
	PadTaskService padTaskService;
	@Autowired
	IdcService idcService;
	@Autowired
	RfDeviceMapper rfDeviceMapper;
	@Autowired
	RfPadMapper padMapper;
	@Autowired
	DeviceService deviceServce;
	@Autowired
	VmTaskHisService vmTaskHisService;
	@Autowired
	ConfigService configService;

	public void saveBatch(HttpServletRequest request, RfPadTaskBatch bean, String padCodes) throws Exception {
		List<RfPad>padIds=Lists.newArrayList();
		String [] padCode = null;
		padCodes=padCodes.replaceAll(" ", "");
		padCodes=padCodes.replaceAll("\r", "");
		if(padCodes.indexOf(",")>0){
			padCode = padCodes.split(",");
		}else{
			padCode = padCodes.split("\n");
		}
		
		if(padCode.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		Set<String> set =Sets.newHashSet();
		for(String code:padCode){
		    if(null==code||"".equals(code)){
	    		throw new BusinessException("没有这个设备编号【"+code+"】");
	        }
			if(set.contains(code)){
				throw new BusinessException("设备编号【"+code+"】有重复的，请核对输入数据");
			}
			set.add(code);
		}
		Integer count=0;
	    for (String padcode : set) {
			RfPad	pad=padService.getPadByPadCode(padcode);
			if(pad==null){
				throw new BusinessException("没有这个设备编号【"+padcode+"】");
			}    
			if(null==pad.getPadIp()||"".equals(pad.getPadIp())){
				throw new BusinessException("设备编号【"+padcode+"】IP是空的");
			}
	    	 List<RfPadTask>list=padTaskService.initQuery()
	    			 .andEqualTo("padId",pad.getPadId())
	    			 .andGreaterThanOrEqualTo("createTime",new Date(System.currentTimeMillis()-Integer.parseInt(ConfigConstantsDb.configTaskTimeout())*60*1000))
	    			 .andEqualTo("taskSource", TaskSource.SCREENCAP)
	    			 .andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd())
	    			 .findStopTrue();

	    	 if(!Collections3.isEmpty(list)){
				throw new BusinessException("设备编号【"+padcode+"】的设备，当前有一个命令正在执行中");
			}	
			padIds.add(pad);
			count++;
		}
	    
	    bean.setPadCount(count);
	 	this.save(request, bean);	
	 	if(padIds==null){
	 		throw new BusinessException("设备信息不能为空");
	 	}	
 	
		for (RfPad padId : padIds) {
			String task="./";
			String url="";
			if(null!=bean.getPackageName()){
					if ("pad_install".equals(bean.getRemark())) {
						task = task + bean.getRemark() + " " + padId.getPadCode() + " " + padId.getPadIp() + " " + 22 + " " + bean.getPackageName()+".apk";
					} else {
						task = task + bean.getRemark() + " " + padId.getPadCode() + " " + padId.getPadIp() + " " + 22 + " " + bean.getPackageName();
					}
		 	}else{
		 		if (null==padId.getDeviceId()) {
		 			task=task+bean.getRemark()+" "+padId.getPadCode()+" "+padId.getPadIp()+" "+22;
				} else {
					RfDevice device= rfDeviceMapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfDevice.FD_DEVICE_IP), padId.getDeviceId());
					if("vm_screencap".equals(bean.getRemark()) && "6.0".equals(padId.getRomVersion())){
						String time = DateUtils.getDateString(new Date(),"yyyyMMddHHmmss");
						task = "./vm_screencap_6.0 " + padId.getPadCode() + " " + device.getDeviceIp() + " 22 " + time + "@" + padId.getPadSn() + " 0 "+padId.getPadIp();
					}else {
						task=task+bean.getRemark()+" "+padId.getPadCode()+" "+device.getDeviceIp()+" "+22;
					}
				}
		 	}
			if("pad_screencap".equals(bean.getRemark())||"vm_screencap".equals(bean.getRemark())){
				String time=DateUtils.getDate("yyyyMMddHHmmss");
				task=task+" "+time;
				if (null!=padId.getDeviceId()) {
		 			task=task+"@"+padId.getPadSn();
				}
				String k=Md5Utils.MD5(padId.getPadCode()+time+"screen123!@#$%^");
				
				RfIdc idc= idcService.get(padId.getIdcId());
				String userServer = "";
				/*String configKey="";
				configKey = idc.getIdcCode();
				
				if(StringUtils.isNotEmpty(configKey)){
					configKey = "config_picture_url_" + configKey;
				} else {
					throw new BusinessException("截图获取地址参数异常");
				}
				SysConfig config= configService.get(configKey);
				
				
				
				url=config.getConfigValue()+"padCode="+padId.getPadCode()+"&d="+time+"&k="+k;*/
				if(StringUtils.isNotEmpty(idc.getUserScreencapServer())){
					userServer = idc.getUserScreencapServer();
				}else{
					throw new BusinessException("截图获取地址参数异常");
				}
				url=userServer+"?padCode="+padId.getPadCode()+"&d="+time+"&k="+k;
				
			}
			RfPadTask padTask=new RfPadTask();
			padTask.setRemark(url);
			padTask.setPadId(padId.getPadId());
			padTask.setPadCode(padId.getPadCode());
			padTask.setBatchId(bean.getId());
			padTask.setTaskCommand(task);
			padTask.setCommandType(bean.getRemark());
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
			padTask.setTaskSource(TaskSource.SCREENCAP);
	        padTaskService.save(request, padTask);
		}
	}
	
	public RfPadTask saveVmScreencap(HttpServletRequest request, RfPadTaskBatch bean, RfPad pad) throws Exception {
		
		Set<String> set =Sets.newHashSet();
	 	if(pad == null){
	 		throw new BusinessException("设备信息不能为空");
	 	}	

		
		if(StringUtils.isNotEmpty(pad.getPadCode())){
			set.add(pad.getPadCode());
		}else{
			throw new BusinessException("设备编号不能为空");
		}
		
		Integer count=0;
	    for (String padcode : set) {
			if(pad == null){
				throw new BusinessException("没有这个设备编号【"+padcode+"】");
			}    
			if(null == pad.getPadIp() || "".equals(pad.getPadIp())){
				throw new BusinessException("设备编号【"+padcode+"】IP是空的");
			}
	    	List<RfPadTask>list=padTaskService.initQuery()
	    			 .andEqualTo("padId",pad.getPadId())
	    			 .andGreaterThanOrEqualTo("createTime",new Date(System.currentTimeMillis()-Integer.parseInt(ConfigConstantsDb.configTaskTimeout())*60*1000))
	    			 .andEqualTo("taskSource", TaskSource.SCREENCAP)
	    			 .andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd())
	    			 .findStopTrue();

	    	if(!Collections3.isEmpty(list)){
				throw new BusinessException("设备编号【"+padcode+"】的设备，当前有一个命令正在执行中");
			}	
			count++;
		}
	    
	    bean.setCreater(SessionUtils.getCurrentUserId(request));
	    bean.setCreateTime(new Date());
	    bean.setPadCount(count);
	 	this.save(request, bean);	
 	
		if(null != pad){
			String task="./";
			String url="";
			if(null!=bean.getPackageName()){
					if ("pad_install".equals(bean.getRemark())) {
						task = task + bean.getRemark() + " " + pad.getPadCode() + " " + pad.getPadIp() + " " + 22 + " " + bean.getPackageName()+".apk";
					} else {
						task = task + bean.getRemark() + " " + pad.getPadCode() + " " + pad.getPadIp() + " " + 22 + " " + bean.getPackageName();
					}
		 	}else{
		 		if (null == pad.getDeviceId()) {
		 			task=task+bean.getRemark()+" "+pad.getPadCode()+" "+pad.getPadIp()+" "+22;
				} else {
					RfDevice device= rfDeviceMapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfDevice.FD_DEVICE_IP), pad.getDeviceId());
					if("vm_screencap".equals(bean.getRemark()) && "6.0".equals(pad.getRomVersion())){
						String time = DateUtils.getDateString(new Date(),"yyyyMMddHHmmss");
						task = "./vm_screencap_6.0 " + pad.getPadCode() + " " + device.getDeviceIp() + " 22 " + time + "@" + pad.getPadSn() + " 0 "+pad.getPadIp();
					}else {
						task=task+bean.getRemark()+" "+pad.getPadCode()+" "+device.getDeviceIp()+" "+22;
					}
				}
		 	
		 	}
			if("pad_screencap".equals(bean.getRemark()) || "vm_screencap".equals(bean.getRemark())){
				String time=DateUtils.getDate("yyyyMMddHHmmss");
				task=task+" "+time;
				if (null!=pad.getDeviceId()) {
		 			task=task+"@"+pad.getPadSn();
				}
				String k=Md5Utils.MD5(pad.getPadCode()+time+"screen123!@#$%^");
				
				RfIdc idc= idcService.get(pad.getIdcId());
				String userServer = "";
				
				if(StringUtils.isNotEmpty(idc.getUserScreencapServer())){
					userServer = idc.getUserScreencapServer();
				}else{
					throw new BusinessException("截图获取地址参数异常");
				}
				url=userServer+"?padCode="+pad.getPadCode()+"&d="+time+"&k="+k;
			}
			RfPadTask padTask=new RfPadTask();
			padTask.setRemark(url);
			padTask.setPadId(pad.getPadId());
			padTask.setPadCode(pad.getPadCode());
			padTask.setBatchId(bean.getId());
			padTask.setTaskCommand(task);
			padTask.setCommandType(bean.getRemark());
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
			padTask.setTaskSource(TaskSource.SCREENCAP);
	        padTaskService.save(request, padTask);
	        return padTask;
		}
		return null;
	}
	
public RfPadTask saveDeviceGetInfo(HttpServletRequest request, RfPadTaskBatch bean, RfPad pad, Map<String, Object> map) throws Exception {
		
		Set<String> set =Sets.newHashSet();
	 	if(pad == null){
	 		throw new BusinessException("设备信息不能为空");
	 	}
	 	
	 	//判断设备是4.4还是6.0，在remark后面添加"_4.4"或"_6.0"  只针对"获取设备属性"
	 	String remarkTemp = bean.getRemark();
	 	if("device_get_info".equals(bean.getRemark())&&RomVersion.ROM_VERSION_4_4.equals(pad.getRomVersion())){
	 		bean.setRemark(bean.getRemark()+"_4.4");
	 	}else if("device_get_info".equals(bean.getRemark())&&RomVersion.ROM_VERSION_6_0.equals(pad.getRomVersion())){
	 		bean.setRemark(bean.getRemark()+"_6.0");
	 	}

		
		if(StringUtils.isNotEmpty(pad.getPadCode())){
			set.add(pad.getPadCode());
		}else{
			throw new BusinessException("设备编号不能为空");
		}
		
		Integer count=0;
	    for (String padcode : set) {
			if(pad == null){
				throw new BusinessException("没有这个设备编号【"+padcode+"】");
			}    
			if(null == pad.getPadIp() || "".equals(pad.getPadIp())){
				throw new BusinessException("设备编号【"+padcode+"】IP是空的");
			}
	    	List<RfPadTask>list=padTaskService.initQuery()
	    			 .andEqualTo("padId",pad.getPadId())
	    			 .andGreaterThanOrEqualTo("createTime",new Date(System.currentTimeMillis()-Integer.parseInt(ConfigConstantsDb.configTaskTimeout())*60*1000))
	    			 .andEqualTo("taskSource", TaskSource.DEVICEINFO)
	    			 .andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd())
	    			 .findStopTrue();

	    	if(!Collections3.isEmpty(list)){
				throw new BusinessException("设备编号【"+padcode+"】的设备，当前有一个命令正在执行中");
			}	
			count++;
		}
	    
	    bean.setCreater(SessionUtils.getCurrentUserId(request));
	    bean.setCreateTime(new Date());
	    bean.setPadCount(count);
	 	this.save(request, bean);	
 	
		if(null != pad){
			String task="./";
			String url="";
			if(null!=bean.getPackageName()){
					if ("pad_install".equals(bean.getRemark())) {
						task = task + bean.getRemark() + " " + pad.getPadCode() + " " + pad.getPadIp() + " " + 22 + " " + bean.getPackageName()+".apk";
					} else {
						task = task + bean.getRemark() + " " + pad.getPadCode() + " " + pad.getPadIp() + " " + 22 + " " + bean.getPackageName();
					}
		 	}else{
		 		if (null == pad.getDeviceId()) {
		 			task=task+bean.getRemark()+" "+pad.getPadCode()+" "+pad.getPadIp()+" "+22;
				} else {
					RfDevice device= rfDeviceMapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfDevice.FD_DEVICE_IP), pad.getDeviceId());
					
					task=task+bean.getRemark()+" "+pad.getPadCode()+" "+device.getDeviceIp()+" "+22;
					
				}
		 	
		 	}
			if("device_get_info_4.4".equals(bean.getRemark())||"device_get_info_6.0".equals(bean.getRemark())){
				String time=DateUtils.getDate("yyyyMMddHHmmss");
				task=task+" "+time;
				if (null!=pad.getDeviceId()) {
		 			task=task+"@"+pad.getPadSn();
				}

			}
			RfPadTask padTask=new RfPadTask();
			padTask.setRemark(url);
			padTask.setPadId(pad.getPadId());
			padTask.setPadCode(pad.getPadCode());
			padTask.setBatchId(bean.getId());
			padTask.setTaskCommand(task);
			padTask.setCommandType(remarkTemp);
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
			padTask.setTaskSource(TaskSource.DEVICEINFO);
			
			//String  str = "{\"TOTAL_MEM\":\"2061996\",\"UPTIME\":\"24:07:04:04\",\"TEMP\":\"63\",\"CUP_FREQ\":\"1992000\",\"BUFFERS_USED\":\"1890988\",\"BUFFERS_FREE\":\"171008\",\"SWAP_TOTAL\":\"0\",\"SWAP_USED\":\"0\",\"SWAP_FREE\":\"0\",\"DATA_DISK_TOTAL\":\"3.4G\",\"DATA_DISK_USED\":\"1.5G\",\"DATA_DISK_FREE\":\"1.9G\",\"SD_DISK_TOTAL\":\"2.4G\",\"SD_DISK_USED\":\"498.6M\",\"SD_DISK_FREE\":\"1.9G\"}";
			//padTask.setTaskResultInfo(str);
	       
			padTaskService.save(request, padTask);
	        
	        map.put("taskId", padTask.getTaskId());
	        map.put("IP", pad.getPadIp());
	        
	        return padTask;
		}
		return null;
	}
	
	public RfPadTask getPadTaskByTaskId(Integer taskId){
		return padTaskService.get(taskId);
	}

	
	//获取设备属性
	public Map<String, Object> getDeviceInfo(Integer taskId,Map<String, Object> map){
		RfPadTask padTask = padTaskService.get(taskId);
		String taskResultInfo =  padTask.getTaskResultInfo();
		if(!StringUtils.isBlank(taskResultInfo)){
			try {
				Map info = Params2Map(taskResultInfo);
				long MEM_TOTAL = Long.parseLong(info.get("MEM_TOTAL")==null?"0":(String)info.get("MEM_TOTAL"));
				long MEM_FREE = Long.parseLong(info.get("MEM_FREE")==null?"0":(String)info.get("MEM_FREE"));
				long BUFFERS_USED = Long.parseLong(info.get("BUFFERS_USED")==null?"0":(String)info.get("BUFFERS_USED"));
				long BUFFERS_FREE = Long.parseLong(info.get("BUFFERS_FREE")==null?"0":(String)info.get("BUFFERS_FREE"));
				DecimalFormat df = new DecimalFormat("0.00");
				info.put("MEM_TOTAL", df.format(MEM_TOTAL/1.0)+"M");
				info.put("MEM_FREE", df.format(MEM_FREE/1.0)+"M");
				info.put("BUFFERS_USED",  df.format(BUFFERS_USED/1.0)+"M");
				info.put("BUFFERS_FREE", df.format(BUFFERS_FREE/1.0)+"M");
				map.putAll(info);
			} catch (Exception e) {
				map.put("defail_msg", taskResultInfo);
			}
		}
		return map;
	}
	//获取设备游戏情况
		public Map<String, Object> getGameInfo(Integer taskId,Map<String, Object> map){
			RfPadTask padTask = padTaskService.get(taskId);
			String taskResultInfo =  padTask.getTaskResultInfo();
			if(!StringUtils.isBlank(taskResultInfo)){
				List<GameDto> list = Params2Object(taskResultInfo);
				if(list!=null){
					
					for (int i = 0; i < list.size()-1; i++) {
			            for (int j = list.size()-1; j > i; j--) {
			                if (list.get(j).getPadIp() .equals( list.get(i).getPadIp())
			                		&&list.get(j).getPackgeName() .equals( list.get(i).getPackgeName())
			                		) {
			                	     list.remove("1".equals(list.get(j).getRun())?j:i);
			                }
			            }
			        }
					
				}
				map.put("gameList", list);
			}
			return map;
		}
		
		private static List<GameDto> Params2Object(String param) {
			List<GameDto> gameDtoList=new ArrayList<GameDto>();
			if (StringUtils.isBlank(param)||"Operation_failed".equals(param)) {
				return gameDtoList;
			}
			String[] params = param.split("\\|");
			for (int i = 0; i < params.length; i++) {
				JSONObject parms = JSONObject.fromObject("{"+params[i]+"}");
				String ip=parms.optString("IP");
				String results=parms.optString("RESULT");
				String[] detail = results.split("\\,");
				if(detail!=null&&detail.length>0){
					String result=detail[0];
					String[] resultInfo = result.split("\\:");
						if (resultInfo.length == 2&&resultInfo[1].equals("success")) {
							results=results.indexOf("pkgName")!=-1?results.substring(results.indexOf("pkgName")):"";
							String[] games = results.split("\\,");
							List list=new ArrayList();
							for(int j=0;j<games.length;j++){
								String[] game = games[j].split("\\:");
								if(game.length==2){
								    list.add(game[1]);
								}else{
									GameDto gameDto=new GameDto(ip,"","","","目前没有正在运行的游戏");
									gameDtoList.add(gameDto);
									break;
								}
							}
							for(int m=0;m<list.size();m=m+2){
								GameDto gameDto=new GameDto(ip,list.get(m).toString(),list.get(m+1).toString(),"0".equals(list.get(m+1).toString())?"前台":"后台","");
								gameDtoList.add(gameDto);
								
							}
						}else{
							
							results=results.indexOf("resultInfo")!=-1?results.substring(results.indexOf("resultInfo")+11):"";
							GameDto gameDto=new GameDto(ip,"","","",results);
							gameDtoList.add(gameDto);
						}
					}
				}
				
				
			
			return gameDtoList;
		}
	private Map<String, Object> Params2Map(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("\\|\\|");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	//2016.3.30 重启remote-play 重启gamemanage
	public void saveKillBatch(HttpServletRequest request,RfPadTaskBatch bean, String padCodes,String... name) throws Exception {
		String type= bean.getRemark();
		List<RfPad>padIds=Lists.newArrayList();
		padCodes=padCodes.replaceAll(" ", "");
		padCodes=padCodes.replaceAll("\r", "");
		String [] padCode=padCodes.split("\n");
		if(padCode.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		Set<String> set =Sets.newHashSet();
		for(String code:padCode){
			if(null==code||"".equals(code)){
		    	throw new BusinessException("没有这个设备编号【"+code+"】");
		    }
			if(set.contains(code)){
				throw new BusinessException("设备编号【"+code+"】有重复的，请核对输入数据");
			}
			set.add(code);
		}
		Integer count=0;
	    for (String padcode : set) {
			RfPad	pad=padService.getPadByPadCode(padcode);
			if(pad==null){
				throw new BusinessException("没有这个设备编号【"+padcode+"】");
			}    
			if(null==pad.getPadIp()||"".equals(pad.getPadIp())){
				throw new BusinessException("设备编号【"+padcode+"】IP是空的");
			}
	    	 List<RfPadTask>list=padTaskService.initQuery().andEqualTo("padId",pad.getPadId()).andGreaterThanOrEqualTo("createTime",new Date(System.currentTimeMillis()-Integer.parseInt(ConfigConstantsDb.configTaskTimeout())*60*1000)).andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd()).findStopTrue();
			if(!Collections3.isEmpty(list)){
				throw new BusinessException("设备编号【"+padcode+"】的设备，当前有一个命令正在执行中");
			}	
			padIds.add(pad);
			count++;
		}
		if (name.length > 0) {
			bean.setName(name[0]);
		} else {
			bean.setName(DateUtils.getDateTime() + type);
		}
	    bean.setPadCount(count);
	    bean.setRemark(type);
	 	this.save(request, bean);
	 	if(padIds==null){
	 		throw new BusinessException("设备信息不能为空");
	 	}
	 	
		for (RfPad padId : padIds) {
			String task="./";
			String url="";
			
		 	task=task+"pad_kill_pid"+" "+padId.getPadCode()+" "+padId.getPadIp()+" "+22;
		 	if("remote-play".equals(type)){task=task+" "+type;}
		 	if("gamemanage".equals(type)){task=task+" "+type;}
		 	
			RfPadTask padTask=new RfPadTask();
			padTask.setRemark(url);
			padTask.setPadId(padId.getPadId());
			padTask.setPadCode(padId.getPadCode());
			padTask.setBatchId(bean.getId());
			padTask.setTaskCommand(task);
			padTask.setCommandType(type);
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
	        padTaskService.save(request, padTask);
		}
	}

	public void saveBatchDevicePing(HttpServletRequest request, RfPadTaskBatch bean, String padCodes) throws Exception {
		List<RfDevice> padIds = Lists.newArrayList();
		padCodes = padCodes.replaceAll(" ", "");
		padCodes = padCodes.replaceAll("\r", "");
		String[] padCode = padCodes.split("\n");
		if (padCode.length < 1) {
			throw new BusinessException("错误：001 输入了无效参数");
		}
		Set<String> set = Sets.newHashSet();
		for (String code : padCode) {
			if (null == code || "".equals(code)) {
				throw new BusinessException("设备号不能为空");
			}
			if (set.contains(code)) {
				throw new BusinessException("设备编号【" + code + "】有重复的，请核对输入数据");
			}
			set.add(code);
		}
		Integer count = 0;
		for (String padcode : set) {
			RfDevice pad = deviceServce.getPadByPadCode(padcode);
			if (pad == null) {
				throw new BusinessException("没有这个物理设备编号【" + padcode + "】");
			}
			if (null == pad.getDeviceIp() || "".equals(pad.getDeviceIp())) {
				throw new BusinessException("物理设备编号【" + padcode + "】IP是空的");
			}
			List<RfPadTask> list = padTaskService.initQuery().andEqualTo("commandType", "device_ping").andEqualTo("padId", pad.getDeviceId())
					.andGreaterThanOrEqualTo("createTime", new Date(System.currentTimeMillis() - Integer.parseInt(ConfigConstantsDb.configTaskTimeout()) * 60 * 1000))
					.andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd()).findStopTrue();
			if (!Collections3.isEmpty(list)) {
				throw new BusinessException("物理设备编号【" + padcode + "】的设备，当前有一个命令正在执行中");
			}
			padIds.add(pad);
			count++;
		}
		bean.setPadCount(count);
		this.save(request, bean);
		if (padIds == null) {
			throw new BusinessException("设备信息不能为空");
		}

		for (RfDevice padId : padIds) {
			String task = "./pad_ping";
			String url = "";
				RfDevice device = rfDeviceMapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfDevice.FD_DEVICE_IP), padId.getDeviceId());
				String deviceIp = device.getDeviceIp();
				task = task  + " " + padId.getDeviceCode() + " " + deviceIp + " " + 22;
		
			RfPadTask padTask = new RfPadTask();
			padTask.setRemark(url);
			padTask.setPadId(padId.getDeviceId());
			padTask.setPadCode(padId.getDeviceCode());
			padTask.setBatchId(bean.getId());
			padTask.setTaskCommand(task);
			padTask.setCommandType("device_ping");
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
			padTaskService.save(request, padTask);
		}

	}

	public void saveKillAgent(HttpServletRequest request, RfPadTaskBatch bean,String padCodes, String[] name) throws Exception {
		String type= bean.getRemark();
		List<RfPad>padIds=Lists.newArrayList();
		padCodes=padCodes.replaceAll(" ", "");
		padCodes=padCodes.replaceAll("\r", "");
		String [] padCode=padCodes.split("\n");
		if(padCode.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		Set<String> set =Sets.newHashSet();
		for(String code:padCode){
		  if(null==code||"".equals(code)){
	    		throw new BusinessException("没有这个设备编号【"+code+"】");
	      }
		if(set.contains(code)){
			throw new BusinessException("设备编号【"+code+"】有重复的，请核对输入数据");
		}
		set.add(code);
		}
		Integer count=0;
     for (String padcode : set) {
		RfPad	pad=padService.getPadByPadCode(padcode);
		if(pad==null){
			throw new BusinessException("没有这个设备编号【"+padcode+"】");
		}    
		if(null==pad.getPadIp()||"".equals(pad.getPadIp())){
			throw new BusinessException("设备编号【"+padcode+"】IP是空的");
		}
    	 List<RfPadTask>list=padTaskService.initQuery().andEqualTo("padId",pad.getPadId()).andGreaterThanOrEqualTo("createTime",new Date(System.currentTimeMillis()-Integer.parseInt(ConfigConstantsDb.configTaskTimeout())*60*1000)).andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd()).findStopTrue();
		if(!Collections3.isEmpty(list)){
			throw new BusinessException("设备编号【"+padcode+"】的设备，当前有一个命令正在执行中");
		}	
		padIds.add(pad);
		count++;
	}
	if (name.length > 0) {
		bean.setName(name[0]);
	} else {
		bean.setName(DateUtils.getDateTime() + type);
	}
    bean.setPadCount(count);
    bean.setRemark(type);
 	this.save(request, bean);
 	if(padIds==null){
 		throw new BusinessException("设备信息不能为空");
 	}
 	
	for (RfPad padId : padIds) {
		String task="./";
		String url="";
		String deviceIp="";
		try {
			deviceIp=deviceServce.get(padId.getDeviceId()).getDeviceIp();
			System.out.println(deviceIp);
		} catch (Exception e) {
		throw new BusinessException("虚拟设备"+padId.getPadCode()+"获取物理机IP地址失败");
		}
		
	 	task=task+"pad_kill_agent"+" "+padId.getPadCode()+" "+deviceIp+" "+22;
	
	 	
		RfPadTask padTask=new RfPadTask();
		padTask.setRemark(url);
		padTask.setPadId(padId.getPadId());
		padTask.setPadCode(padId.getPadCode());
		padTask.setBatchId(bean.getId());
		padTask.setTaskCommand(task);
		padTask.setCommandType(type);
		padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
        padTaskService.save(request, padTask);
	}
		
	}

   public RfPadTask saveGameInfo(HttpServletRequest request, RfPadTaskBatch bean, RfPad pad) throws Exception {
		
	 	if(pad == null){
	 		throw new BusinessException("设备信息不能为空");
	 	}	
	 	List<RfPad>  padList=null;
	 	//查找当前物理设备下所有的虚拟设备
		if(StringUtils.isNotEmpty(pad.getPadCode())){
			RfPadExample padExample = new RfPadExample();
			padExample.or().andDeviceIdEqualTo(pad.getDeviceId());
		    padList = padMapper.selectByExample(padExample);
		}else{
			throw new BusinessException("设备编号不能为空");
		}
		
		Integer count=0;
		StringBuffer ips=new StringBuffer ();
		if(padList!=null&&padList.size()>0){
		    for (RfPad pads : padList) {
		    	String padcode =pads.getPadCode();
				if(pad == null){
					throw new BusinessException("没有这个设备编号【"+padcode+"】");
				}    
				if(null == pad.getPadIp() || "".equals(pad.getPadIp())){
					throw new BusinessException("设备编号【"+padcode+"】IP是空的");
				}
		    	List<RfPadTask>list=padTaskService.initQuery()
		    			 .andEqualTo("padId",pad.getPadId())
		    			 .andGreaterThanOrEqualTo("createTime",new Date(System.currentTimeMillis()-Integer.parseInt(ConfigConstantsDb.configTaskTimeout())*60*1000))
		    			 .andEqualTo("taskSource", TaskSource.GAMEINFO)
		    			 .andNotEqualTo("taskStatus", ConstantsDb.rfPadTaskTaskStatusEnd())
		    			 .findStopTrue();
	
		    	if(!Collections3.isEmpty(list)){
					throw new BusinessException("设备编号【"+padcode+"】的设备，当前有一个命令正在执行中");
				}	
		    	ips.append(pads.getPadIp()+" ");
				count++;
			}
		}else{
			throw new BusinessException("设备编号不能为空");
		}
	    
	    bean.setCreater(SessionUtils.getCurrentUserId(request));
	    bean.setCreateTime(new Date());
	    bean.setPadCount(count);
	 	this.save(request, bean);	
		
		String task="./"+bean.getRemark()+ " "+ips;
		RfPadTask padTask=new RfPadTask();
		padTask.setRemark("");
		padTask.setPadId(pad.getPadId());
		padTask.setPadCode(pad.getPadCode());
		padTask.setBatchId(bean.getId());
		padTask.setTaskCommand(task);
		padTask.setCommandType(bean.getRemark());
		padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
		padTask.setTaskSource(TaskSource.GAMEINFO);
        padTaskService.save(request, padTask);
        return padTask;
		
	}	


}
