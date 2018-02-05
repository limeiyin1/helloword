package com.test.www;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redfinger.manager.modules.sys.service.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class LoadTest {

	@Autowired
	AdminService adminService;

	@Test
	public void test() {
		adminService.load("admin");
		adminService.load("admin");
	}
	@Test
	public void test2() {
		adminService.load("admin");
		adminService.load("admin1");
	}
}
