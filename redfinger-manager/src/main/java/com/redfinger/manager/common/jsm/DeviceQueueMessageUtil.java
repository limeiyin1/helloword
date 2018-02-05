package com.redfinger.manager.common.jsm;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.helper.SpringContextHolder;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.DeviceService;
import com.redfinger.manager.modules.facility.service.PadService;

public class DeviceQueueMessageUtil {
	
	private static Logger logger = (Logger) LoggerFactory.getLogger(DeviceQueueMessageUtil.class);
	private static JmsTemplate jmsTemplate;
	
	/**
	 * 
	 * 根据设备id发送节点jms
	 * 使用这方法, 必须注入SpringContextHolder
	 * @param padId 设备ID
	 * @param message jms消息
	 * @throws Exception 
	 */
	public static void sendMessage(Integer padId, String message) throws Exception{
		
		/** 初始化jsmTemplate*/
		jmsTemplate = SpringContextHolder.getBean(JmsTemplate.class);
		String deviceQueueName = getDeviceQueueNameByPadId(padId);
		if(StringUtils.isBlank(deviceQueueName)){
			logger.warn("Could not find padId [" + padId + "] on target queue name");
			throw new IllegalArgumentException("sene jsm faild. Could not find padId [" + padId + "] on target queue name");
		}
		
		jmsTemplate.convertAndSend(new ActiveMQQueue(deviceQueueName), message);
		
	}
	
		public static void sendMessageByDeviceId(Integer deviceId, String message) throws Exception{
		
		/** 初始化jsmTemplate*/
		jmsTemplate = SpringContextHolder.getBean(JmsTemplate.class);
		String deviceQueueName = getDeviceQueueNameByDeviceId(deviceId);
		if(StringUtils.isBlank(deviceQueueName)){
			logger.warn("Could not find deviceId [" + deviceId + "] on target queue name");
			throw new IllegalArgumentException("sene jsm faild. Could not find padId [" + deviceId + "] on target queue name");
		}
		
		jmsTemplate.convertAndSend(new ActiveMQQueue(deviceQueueName), message);
		
	}
	
	public static String getDeviceQueueNameByPadId(Integer padId) throws Exception{
		/** 初始化padService*/
		PadService padService = SpringContextHolder.getBean(PadService.class);
		
		/** 根据设备id查询设备 */
		RfPad pad = padService.get(padId);
		
		if(pad != null && pad.getPadControlId() != null){
			return getDeviceQueueNameByControlId(pad.getPadManageControlId());
		}else{
			throw new IllegalArgumentException("get device queue Name faild. Could not find padId [" + padId + "] on target queue name");
		}
		
	}
	
	public static String getDeviceQueueNameByControlId(Integer controlId) throws Exception{
		/** 初始化controlService */
		ControlService controlService = SpringContextHolder.getBean(ControlService.class);
		
			/** 根据节点id查询节点*/
		RfControl control = controlService.get(controlId);
		if(control != null && StringUtils.isNotBlank(control.getControlQueueName())){
			return control.getControlQueueName();
		}else{
			throw new IllegalArgumentException("get device queue Name faild. Could not find controlId [" + controlId + "] on target queue name");
		}
		
	}
	
	public static String getDeviceQueueNameByDeviceId(Integer deviceId) throws Exception{
		DeviceService deviceService = SpringContextHolder.getBean(DeviceService.class);

		/** 根据设备id查询设备 */
		RfDevice device = deviceService.get(deviceId);
		
		if(device != null && device.getDeviceManageControlId() != null){
			return getDeviceQueueNameByControlId(device.getDeviceManageControlId());
		}else{
			throw new IllegalArgumentException("get device queue Name faild. Could not find deviceId [" + device + "] on target queue name");
		}
		
	}

}
