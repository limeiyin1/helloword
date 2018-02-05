package com.test.www;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redfinger.manager.modules.facility.service.ViewPadUserService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:spring-context.xml"}) 
public class ViewTest {

	@Autowired
	ViewPadUserService service;
	
	@Test
	public void test(){
		service.initQuery().andLessThanOrEqualTo("bindTimeT2", new Date()).andIsNotNull("bindTimeT2").findAll();
	}
}
