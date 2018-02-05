package com.test.www;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redfinger.manager.common.domain.ResIp;
import com.redfinger.manager.common.utils.AddressUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.base.service.ResIpService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class IpTest {

	@Autowired
	ResIpService resIpService;

	public void save(String ip) throws Exception {
		Map<String, String> addres = AddressUtils.getAddresses("ip=" + ip, "utf-8");
		if (!"0:0:0:0:0:0:0:1".equals(ip) && !(addres.isEmpty())) {
			ResIp resIp = new ResIp();
			resIp.setIp(ip);
			resIp.setCountry(addres.get("country").toString());
			resIp.setRegion(addres.get("region").toString());
			resIp.setProvince(addres.get("province").toString());
			resIp.setCity(addres.get("city").toString());
			resIp.setArea(addres.get("area").toString());
			resIp.setIsp(addres.get("isp").toString());
			resIpService.save(null, resIp);
		}
	}


	@Test
	public void test() {
		int error = 0;
		String dir = "d:/ip.txt";
		File file = new File(dir);
		try {
			List<String> ipList = FileUtils.readLines(file);
			for (String ip : ipList) {
				System.out.println(ip);
				if (resIpService.get(ip) == null) {
					try {
						this.save(ip);
					} catch (Exception e) {
						error++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(error);
	}
	
	private static final String driver = "org.postgresql.Driver";
	private static final String url = "jdbc:postgresql://pengtao:5432/red_finger";
	private static final String userName="postgres";
	private static final String passwd="postgres";
	private static Connection connection = null;
	
	public static Connection getConnections() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,userName,passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void main(String[] args) throws Exception {
		Connection conn=getConnections();
		String dir = "E:/ip/20150918_ip.txt";
		File file = new File(dir);
		List<String> ipList = FileUtils.readLines(file);
		for(String ip : ipList){
			if(StringUtils.isNotEmpty(ip)){
				String sql="insert into ip values('"+ip+"')";
				Statement stmt=conn.createStatement();
				stmt.execute(sql);
			}
		}
		dir = "E:/ip/20150919_ip.txt";
		file = new File(dir);
		ipList = FileUtils.readLines(file);
		for(String ip : ipList){
			if(StringUtils.isNotEmpty(ip)){
				String sql="insert into ip values('"+ip+"')";
				Statement stmt=conn.createStatement();
				stmt.execute(sql);
			}
		}
		 dir = "E:/ip/20150920_ip.txt";
		 file = new File(dir);
		 ipList = FileUtils.readLines(file);
		for(String ip : ipList){
			if(StringUtils.isNotEmpty(ip)){
				String sql="insert into ip values('"+ip+"')";
				Statement stmt=conn.createStatement();
				stmt.execute(sql);
			}
		}
		 dir = "E:/ip/20150921_ip.txt";
		 file = new File(dir);
		 ipList = FileUtils.readLines(file);
		for(String ip : ipList){
			if(StringUtils.isNotEmpty(ip)){
				String sql="insert into ip values('"+ip+"')";
				Statement stmt=conn.createStatement();
				stmt.execute(sql);
			}
		}
		 dir = "E:/ip/20150922_ip.txt";
		 file = new File(dir);
		 ipList = FileUtils.readLines(file);
		for(String ip : ipList){
			if(StringUtils.isNotEmpty(ip)){
				String sql="insert into ip values('"+ip+"')";
				Statement stmt=conn.createStatement();
				stmt.execute(sql);
			}
		}
			
	}

}
