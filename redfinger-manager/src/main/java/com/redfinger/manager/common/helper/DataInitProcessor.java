package com.redfinger.manager.common.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.redfinger.manager.common.base.SysPermission;
import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.common.utils.PropertiesLoader;
import com.redfinger.manager.modules.base.service.ConfigService;

@Component
public class DataInitProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	ConfigService configService;
	@Resource(name = "dataSource")
	DataSource dataSource;
	@Value("${env}")
	private String env;

	// 保存所有有效的权限集合
	public static List<SysPermission> permissionList = new CopyOnWriteArrayList<SysPermission>();
	// 所有权限集合键值对，方便操作
	public static Map<String, SysPermission> permissionMap = new ConcurrentHashMap<String, SysPermission>();
	// 保存所有数据字典
	public static Map<String, List<SysDict>> dictCategoryMap = new ConcurrentHashMap<String, List<SysDict>>();
	public static Map<String, SysDict> dictKeyMap = new ConcurrentHashMap<String, SysDict>();
	// 配置文件
	public static PropertiesLoader propertiesLoader = null;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// root application context 没有parent，他就是老大.
		if (event.getApplicationContext().getParent() == null) {
			initData();
			initViews();
		}
	}

	/**
	 * 初始化权限
	 */
	public static void initData() {
		DictHelper.initDict();
		PermissionHelper.initPermission();
		propertiesLoader = new PropertiesLoader("url-mapping.properties");
	}

	public void initViews() {
		Connection conn;
		try {
			conn = dataSource.getConnection();
			ViewCreateHelper.create(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
	
	

}
