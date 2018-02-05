package com.redfinger.manager.modules.facility.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfVmTask;
import com.redfinger.manager.common.domain.RfVmTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.jsm.DeviceQueueMessageUtil;
import com.redfinger.manager.common.mapper.RfVmTaskMapper;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "vmTaskId")
public class VmTaskService extends BaseService<RfVmTask, RfVmTaskExample, RfVmTaskMapper> {

	@Autowired
	RfVmTaskMapper mapper;
	@Autowired
	JmsTemplate jmsTemplate;
	
	public Date vmTask(HttpServletRequest request, RfVmTask bean) {
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String[] idArray=StringUtils.split(bean.getIds(),",");
		try {
			for(String idStr:idArray){
				RfVmTask vmTask=this.get(Integer.parseInt(idStr));
				vmTask.setModifier(userId);
				vmTask.setModifyTime(currentDate);
				vmTask.setTaskStatus("1");
				
				vmTask.setRetryTimes(null==vmTask.getRetryTimes()?1:vmTask.getRetryTimes()+1);
				mapper.updateByPrimaryKeySelective(vmTask);
			}
		} catch (Exception e) {
			return null;
		}
	
		return currentDate;
	}


	public void jmsSend(Date time, RfVmTask bean,HttpServletRequest request) {
		String adminName = SessionUtils.getCurrentUserId(request);
		String[] idArray=StringUtils.split(bean.getIds(),",");
			for(String idStr:idArray){
				try{
				RfVmTask vmTask=this.get(Integer.parseInt(idStr));
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("opType", "command");
				map.put("taskId", vmTask.getVmTaskId());
				log.info("[opType:{},taskId{}]",new Object[]{"command",vmTask.getVmTaskId()});
				//jmsTemplate.convertAndSend("device-manage", JsonUtils.ObjectToString(map));
				DeviceQueueMessageUtil.sendMessageByDeviceId(vmTask.getDeviceId(), JsonUtils.ObjectToString(map));
				} catch (Exception e) {
					RfVmTask vm=new RfVmTask();
					vm.setVmTaskId(Integer.parseInt(idStr));
					vm.setTaskStatus("end");
					vm.setTaskResultStatus("defeated");
					vm.setTaskResultInfo("设备任务消息队列发送失败");
					vm.setModifier(adminName);
					vm.setModifyTime(time);
					mapper.updateByPrimaryKeySelective(vm);		
				}
			}

	}
	
	//更新任务状态
	public void updateVmJMS(Date time) throws Exception {
		RfVmTaskExample example = new RfVmTaskExample();
		RfVmTaskExample.Criteria criteria = example.createCriteria();
		criteria.andCreateTimeEqualToIgnoreNull(time);
		RfVmTask record = new RfVmTask();
		// 发送中
		record.setTaskStatus("1");
		this.mapper.updateByExampleSelective(record, example);
	
	}
	
	public RfVmTask insertVmTask(RfVmTask record){
		mapper.insert(record);
		return record;
	}
}
