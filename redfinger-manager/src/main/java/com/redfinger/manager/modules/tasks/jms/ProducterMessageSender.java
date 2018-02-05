package com.redfinger.manager.modules.tasks.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class ProducterMessageSender {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination destination;
	@Autowired
	private Destination destinationPadCode;

	public void send(final String text) {
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}
		});
	}

	public void sendPadCode(final String text) {
		jmsTemplate.send(destinationPadCode, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}
		});
	}
}
