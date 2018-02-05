package com.redfinger.manager.common.helper;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.redfinger.manager.common.bean.view.Join;
import com.redfinger.manager.common.bean.view.View;
import com.redfinger.manager.common.bean.view.ViewDomain;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.XmlUtils;

public class ViewCreateHelper {

	public static Logger logger = LoggerFactory.getLogger(ViewCreateHelper.class);

	public static void create(Connection conn) {
		logger.info("系统开始视图检测");
		DatabaseMetaData dbmd = null;
		Statement stmt = null;
		Multimap<String, String> viewMap = getViews(conn);
		try {
			String viewFile = SpringContextHolder.getResourceRootRealPath() + File.separator + "views.xml";
			File file = new File(viewFile);
			if(!file.exists()){
				logger.info("系统检测视图文件views.xml不存在，直接跳出！！！");
			}
			ViewDomain viewDomain = XmlUtils.stringToObject(file, ViewDomain.class);
			Multimap<String, String> tableMap = getTables(conn, viewDomain);
			Set<String> tableSet = tableMap.keySet();
			stmt=conn.createStatement();
			for (String key : tableSet) {
				Set<String> target = (Set<String>) tableMap.get(key);
				Set<String> source = (Set<String>) viewMap.get(key);
				logger.info("系统检测视图["+key+"]");
				SetView<String> diff = Sets.symmetricDifference(target, source);
				if (diff != null && diff.size() > 0) {
					View view = getViewByName(viewDomain, key);
					String sql = mixSql(target, view);
					stmt.execute("drop VIEW if EXISTS "+key);
					stmt.execute(sql);
					logger.info("视图["+key+"]更新!!!");
				}else{
					logger.info("视图["+key+"]正常");
				}
			}
			logger.info("系统视图检测完成");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
				stmt = null;
			}
			if (dbmd != null) {
				dbmd = null;
			}
		}

	}

	/**
	 * 获取已有视图和视图字段以及类型
	 * 
	 * @param conn
	 * @return
	 */
	public static Multimap<String, String> getViews(Connection conn) {
		Multimap<String, String> viewMap = HashMultimap.create();
		DatabaseMetaData dbmd = null;
		ResultSet resultSet = null;
		ResultSet rs = null;
		try {
			dbmd = conn.getMetaData();
			resultSet = dbmd.getTables(null, "%", "%", new String[] { "VIEW" });
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				rs = dbmd.getColumns(null, "%", tableName, "%");
				while (rs.next()) {
					String fieldName = rs.getString("COLUMN_NAME");
					viewMap.put(tableName, fieldName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
				rs = null;
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
				resultSet = null;
			}
			if(dbmd!=null){
				dbmd=null;
			}
		}
		return viewMap;
	}

	public static Multimap<String, String> getTables(Connection conn, ViewDomain viewDomain) {
		Multimap<String, String> tableMap = LinkedHashMultimap.create();
		DatabaseMetaData dbmd = null;
		ResultSet resultSet = null;
		List<View> viewList = viewDomain.getView();
		try {
			dbmd = conn.getMetaData();
			for (View view : viewList) {
				resultSet = dbmd.getColumns(null, "%", view.getTable(), "%");
				while (resultSet.next()) {
					String fieldName = resultSet.getString("COLUMN_NAME");
					if(StringUtils.isNotBlank(view.getInclude())){
						if(StringUtils.indexOf(view.getInclude(), fieldName)){
							tableMap.put(view.getName(), fieldName);
						}
					}else if(StringUtils.isBlank(view.getInclude()) && StringUtils.isNotBlank(view.getExclude())){
						if(!StringUtils.indexOf(view.getExclude(), fieldName)){
							tableMap.put(view.getName(), fieldName);
						}
					}else{
						tableMap.put(view.getName(), fieldName);
					}
					
				}
				int i = 2;
				for (Join join : view.getJoin()) {
					resultSet = dbmd.getColumns(null, "%", join.getTable(), "%");
					while (resultSet.next()) {
						String fieldName = resultSet.getString("COLUMN_NAME");
						if(StringUtils.isNotBlank(join.getInclude())){
							String include=","+join.getInclude()+",";
							if(StringUtils.indexOf(include, fieldName)){
								tableMap.put(view.getName(), fieldName + "_t" + i);
							}
						}else if(StringUtils.isBlank(join.getInclude()) && StringUtils.isNotBlank(join.getExclude())){
							String exclude=","+join.getExclude()+",";
							if(!StringUtils.indexOf(exclude, fieldName)){
								tableMap.put(view.getName(), fieldName + "_t" + i);
							}
						}else{
							tableMap.put(view.getName(), fieldName + "_t" + i);
						}
					}
					i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableMap;
	}

	public static void main(String[] args) {
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://10.100.0.253:5432/redfinger";
		String userName = "redfinger";
		String passwd = "";
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, passwd);
			create(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param target
	 * @param view
	 * @return
	 */
	public static String mixSql(Set<String> target, View view) {
		//开始连接表字段拼接
		boolean beginJoin=false;
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE VIEW " + view.getName() + " AS select ");
		for(String field:target){
			if(!beginJoin && StringUtils.endsWithAny(field,"_t2")){
				beginJoin=true;
			}
			if(beginJoin){
				String[] arr=field.split("_");
				sb.append(arr[arr.length-1]).append(".").append(StringUtils.left(field, field.length()-3)).append(" as ").append(field).append(",");
			}else{
				sb.append("t1.").append(field).append(",");
			}
		}
		sb = new StringBuffer(StringUtils.removeEnd(sb.toString(), ","));
		sb.append(" from ");
		int j = 2;
		for (Join join : view.getJoin()) {
			if (j == 2) {
				sb.append(view.getTable() + " t1 LEFT JOIN " + join.getTable() + " t" + j + " on t1." + join.getField() + "=t" + j + "." + join.getOn());
			} else {
				String field="t1." + join.getField();
				if(StringUtils.contains(join.getField(), ".")){
					field=join.getField();
				}
				String on="t" + j + "." + join.getOn();
				if(StringUtils.contains(join.getOn(), ".")){
					on=join.getOn();
				}
				sb.append(" LEFT JOIN " + join.getTable() + " t" + j + " on " + field + "=" + on );
			}
			j++;
		}
		return sb.toString();
	}
	
	public static View getViewByName(ViewDomain viewDomain,String viewName){
		for(View view:viewDomain.getView()){
			if(view.getName().equals(viewName)){
				return view;
			}
		}
		return null;
	}
}