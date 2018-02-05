package com.test.task;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.redfinger.manager.common.domain.RfPadTaskBatch;
import com.redfinger.manager.modules.tasks.service.TaskBatchService;

public class TaskBatchTest { 
	@Autowired
	TaskBatchService taskBatchService;
	@Autowired
	HttpServletRequest request;
	
	
	@Before  
    public void init() throws Exception {  
		
		ApplicationContext aCtx = new FileSystemXmlApplicationContext(new String[] {  
                "classpath:spring-mvc.xml", "classpath:spring-context.xml" ,
                "classpath:spring-jms.xml"});
		
		taskBatchService = (TaskBatchService) aCtx.getBean("taskBatchService");
		
		
		System.out.println("测试开始");
    } 
	
	@After  
    public void setUp() throws Exception {  
        
		System.out.println("测试结束");
    } 
	
	@Test
    public void test() throws Exception {
		
		/*RfPadTaskBatch bean=new RfPadTaskBatch();
		bean.setId(1);
		bean.setName("1");
		bean.setCreater("admin");
		bean.setCreateTime(new Date());
		bean.setStatus("1");
		bean.setEnableStatus("1");
		bean.setGameName("九阳神功");
		bean.setPadCount(1);
		taskBatchService.saveBatch(
				request	,bean,"VM010254020023");*/
		
	}
}
