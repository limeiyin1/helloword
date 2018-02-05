package com.redfinger.manager.common.tools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DbHelpInfo {
	/**
	 *
	 * 这里是MySQL连接方法
	 */
	private static final String driver = "org.postgresql.Driver";
//	private static final String url = "jdbc:postgresql://pengtao:5432/red_finger";
//	private static final String userName="postgres";
//	private static final String passwd="postgres";
	private static final String url = "jdbc:postgresql://10.100.0.253:5432/redfinger";
	private static final String userName="redfinger";
	private static final String passwd="redfinger";
	private static Connection connection = null;

	public static void main(String[] args) {
		connection = getConnections();
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String primaryKey="";
				//获取主键
				ResultSet pkRSet = dbmd.getPrimaryKeys(null, null, tableName);
				while( pkRSet.next() ) { 
					primaryKey=pkRSet.getString("COLUMN_NAME"); 
				} 
				
				String str="<table tableName=\""+tableName+"\" domainObjectName=\""+transfer(tableName)+"\" ><generatedKey column=\""+primaryKey+"\" sqlStatement=\"select nextval('seq_"+tableName+"')\"  identity=\"false\" type=\"pre\"/></table>";
				String str2="<table tableName=\""+tableName+"\" domainObjectName=\""+transfer(tableName)+"\" />";
				ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
				String result=str2;
				while (rs.next() && !tableName.equals("rf_user_data")) {
//					System.out.println("字段名：" + rs.getString("COLUMN_NAME") + "\t字段注释：" + rs.getString("REMARKS") + "\t字段数据类型：" + rs.getString("TYPE_NAME"));
					String field=rs.getString("COLUMN_NAME");
					String type=rs.getString("TYPE_NAME");
					if(field.equals(primaryKey) && type.contains("int")){
						result=str;
						break;
					}
				}
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnections() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,userName,passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static String transfer(String str){
		StringBuffer sb = new StringBuffer("");
		String[] arr=str.split("_");
		for(String s:arr){
			String n=s.substring(0, 1).toUpperCase()+s.substring(1, s.length());
			sb.append(n);
		}
		return sb.toString();
		
	}
}
