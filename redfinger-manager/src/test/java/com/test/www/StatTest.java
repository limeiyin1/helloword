package com.test.www;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redfinger.manager.common.mapper.StatLogPadMapper;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:spring-context.xml"}) 
public class StatTest {

	@Autowired
	StatLogPadMapper mapper;
	
//	@Test
//	public void stat(){
//		StatDomain bean = new StatDomain();
//		bean.setType("week");
//		bean.setBegin("2015-07-02");
//		bean.setEnd("2015-09-05");
//		bean.setWhere("category in ('bind','bindchange')");
//		List<StatDomain> list =mapper.statNumber(bean);
//		System.out.println("------------------");
//		System.out.println(list.size());
//		System.out.println("-----------------");
//	}
	
	@Test
	public void statAreadyBind(){
		int a=mapper.getBindNumberByEndDate(new Date());
		System.out.println(a);
	}
	
	
}
