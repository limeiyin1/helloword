package com.redfinger.manager.common.jsm;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

/**
 * 任务批次执行发送jms
 * @author liulu
 *
 */
public class TkBatchTaskProducer {
	private ActiveMQQueue destination;
	private JmsTemplate jmsTemplate;
	
	//发送消息
    public void sendMessage(String text){
    	jmsTemplate.convertAndSend(destination, text);
    }

    //发送消息
    public void sendMessage(Object message){
    	jmsTemplate.convertAndSend(destination, message);
    }
    
	/** 
	 * @return destination 
	 */
	public ActiveMQQueue getDestination() {
		return destination;
	}

	/** 
	 * @param destination 要设置的 destination 
	 */
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
