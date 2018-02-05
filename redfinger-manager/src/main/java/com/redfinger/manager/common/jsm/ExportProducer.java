/*
 * com.toone.web.authfinger.commons.jms  2015-4-30
 *
 * Copyright 2010 Shenzhen TYDIC Information Technology Co.,Ltd.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.redfinger.manager.common.jsm;


import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/** 
 * @ClassName: MessageProducer 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson yuchanghong@tydic.com 
 * @date 2015-4-30 上午9:49:56 
 *  
 */
public class ExportProducer {
	
	
	@Autowired
	private ActiveMQQueue destination;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	
	//发送消息
    public void sendMessage(String text){
    	jmsTemplate.convertAndSend(destination, text);
    }

    //发送消息
    public void sendMessage(Object message){
    	jmsTemplate.convertAndSend(destination, message);
    }
	

	public ActiveMQQueue getDestination() {
		return destination;
	}

	public void setDestination(ActiveMQQueue destination) {
		this.destination = destination;
	}

	/** 
	 * @return jmsTemplate 
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/** 
	 * @param jmsTemplate 要设置的 jmsTemplate 
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
