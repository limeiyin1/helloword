package com.test.www;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class MqTest {

	@Autowired
	ProducterMessageSender producterMessageSender;

	@Test
	public void test() {
		producterMessageSender.send("6");
	}
	
	

}
