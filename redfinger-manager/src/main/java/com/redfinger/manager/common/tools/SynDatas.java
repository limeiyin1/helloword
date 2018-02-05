package com.redfinger.manager.common.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class SynDatas {

	public static Connection getRemoteConnections() throws Exception {
		String url = "jdbc:postgresql://10.1.254.30:5432/redfinger";
		String userName = "redfinger";
		String passwd = "Xlogin&Wlogin99";
		
//		String url = "jdbc:postgresql://test:5432/redfinger_20151110";
//		String userName = "redfinger";
//		String passwd = "redfinger";
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection(url, userName, passwd);
		return conn;
	}

	public static Connection getLocalConnections() throws Exception {
		String url = "jdbc:postgresql://pengtao:5432/red_finger";
		String userName = "postgres";
		String passwd = "postgres";
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection(url, userName, passwd);
		return conn;
	}
	
	public static void synDict(Connection localConn,Connection remoteConn) throws Exception{
		System.out.println("====开始字典表同步====");
		Statement localStmt=localConn.createStatement();
		Statement remoteStmt=remoteConn.createStatement();
		PreparedStatement remoteInsertStmt=remoteConn.prepareStatement("INSERT INTO sys_dict(dict_code, dict_name, dict_category, dict_value, reorder, enable_status,status, creater, create_time, modifier, modify_time, remark,themes) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?); 	");
		ResultSet localRs=localStmt.executeQuery("select * from sys_dict");
		ResultSet remoteRs=null;
		while(localRs.next()){
			String dict_code=localRs.getString("dict_code");
			remoteRs=remoteStmt.executeQuery("select dict_code from sys_dict where dict_code='"+dict_code+"'");
			if(!remoteRs.next()){
				System.out.println("补充数据"+dict_code);
				remoteInsertStmt.setString(1, dict_code);
				remoteInsertStmt.setString(2, localRs.getString("dict_name"));
				remoteInsertStmt.setString(3, localRs.getString("dict_category"));
				remoteInsertStmt.setString(4, localRs.getString("dict_value"));
				remoteInsertStmt.setInt(5, localRs.getInt("reorder"));
				remoteInsertStmt.setString(6, localRs.getString("enable_status"));
				remoteInsertStmt.setString(7, localRs.getString("status"));
				remoteInsertStmt.setString(8, localRs.getString("creater"));
				remoteInsertStmt.setTimestamp(9, new Timestamp(new Date().getTime()));
				remoteInsertStmt.setString(10, localRs.getString("modifier"));
				remoteInsertStmt.setTimestamp(11, new Timestamp(new Date().getTime()));
				remoteInsertStmt.setString(12, localRs.getString("remark"));
				remoteInsertStmt.setString(13, localRs.getString("themes"));
				
				remoteInsertStmt.execute();
			}
		}
		System.out.println("====结束字典表同步====");
		System.out.println();
		remoteRs.close();
		localRs.close();
		remoteInsertStmt.close();
		remoteStmt.close();
		localStmt.close();
	}
	
	public static void synConfig(Connection localConn,Connection remoteConn) throws Exception{
		System.out.println("====开始配置表同步====");
		Statement localStmt=localConn.createStatement();
		Statement remoteStmt=remoteConn.createStatement();
		PreparedStatement remoteInsertStmt=remoteConn.prepareStatement("INSERT INTO sys_config(config_code, config_label, config_value, reorder, enable_status,status, creater, create_time, modifier, modify_time, remark)     VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?);");
		ResultSet localRs=localStmt.executeQuery("select * from sys_config");
		ResultSet remoteRs=null;
		while(localRs.next()){
			String config_code=localRs.getString("config_code");
			remoteRs=remoteStmt.executeQuery("select config_code from sys_config where config_code='"+config_code+"'");
			if(!remoteRs.next()){
				System.out.println("补充数据"+config_code);
				remoteInsertStmt.setString(1, config_code);
				remoteInsertStmt.setString(2, localRs.getString("config_label"));
				remoteInsertStmt.setString(3, localRs.getString("config_value"));
				remoteInsertStmt.setInt(4, localRs.getInt("reorder"));
				remoteInsertStmt.setString(5, localRs.getString("enable_status"));
				remoteInsertStmt.setString(6, localRs.getString("status"));
				remoteInsertStmt.setString(7, localRs.getString("creater"));
				remoteInsertStmt.setTimestamp(8, new Timestamp(new Date().getTime()));
				remoteInsertStmt.setString(9, localRs.getString("modifier"));
				remoteInsertStmt.setTimestamp(10, new Timestamp(new Date().getTime()));
				remoteInsertStmt.setString(11, localRs.getString("remark"));
				
				remoteInsertStmt.execute();
			}
		}
		System.out.println("====结束配置表同步====");
		System.out.println();
		remoteRs.close();
		localRs.close();
		remoteInsertStmt.close();
		remoteStmt.close();
		localStmt.close();
	}
	
	public static void synMenu(Connection localConn,Connection remoteConn) throws Exception{
		System.out.println("====开始菜单表同步====");
		Statement localStmt=localConn.createStatement();
		Statement remoteStmt=remoteConn.createStatement();
		PreparedStatement remoteInsertStmt=remoteConn.prepareStatement("INSERT INTO sys_menu(menu_code, menu_name, parent_menu_code, menu_uri, menu_desc,  menu_layer, status, creater, create_time, modifier, modify_time,  reorder, remark, enable_status)VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?,  ?, ?, ?);");
		ResultSet localRs=localStmt.executeQuery("select * from sys_menu order by menu_layer");
		ResultSet remoteRs=null;
		while(localRs.next()){
			String menu_code=localRs.getString("menu_code");
			remoteRs=remoteStmt.executeQuery("select menu_code from sys_menu where menu_code='"+menu_code+"'");
			if(!remoteRs.next()){
				System.out.println("补充数据"+menu_code);
				remoteInsertStmt.setString(1, menu_code);
				remoteInsertStmt.setString(2, localRs.getString("menu_name"));
				remoteInsertStmt.setString(3, localRs.getString("parent_menu_code"));
				remoteInsertStmt.setString(4, localRs.getString("menu_uri"));
				remoteInsertStmt.setString(5, localRs.getString("menu_desc"));
				remoteInsertStmt.setInt(6, localRs.getInt("menu_layer"));
				remoteInsertStmt.setString(7, localRs.getString("status"));
				remoteInsertStmt.setString(8, localRs.getString("creater"));
				remoteInsertStmt.setTimestamp(9, new Timestamp(new Date().getTime()));
				remoteInsertStmt.setString(10, localRs.getString("modifier"));
				remoteInsertStmt.setTimestamp(11, new Timestamp(new Date().getTime()));
				remoteInsertStmt.setInt(12, localRs.getInt("reorder"));
				remoteInsertStmt.setString(13, localRs.getString("remark"));
				remoteInsertStmt.setString(14, localRs.getString("enable_status"));
				
				remoteInsertStmt.execute();
			}
		}
		System.out.println("====结束菜单表同步====");
		System.out.println();
		remoteRs.close();
		localRs.close();
		remoteInsertStmt.close();
		remoteStmt.close();
		localStmt.close();
	}

	public static void main(String[] args) throws Exception{
		Connection localConn=getLocalConnections();
		Connection remoteConn=getRemoteConnections();
		synDict(localConn, remoteConn);
		synConfig(localConn, remoteConn);
		synMenu(localConn, remoteConn);
		localConn.close();
		remoteConn.close();
	}
}
