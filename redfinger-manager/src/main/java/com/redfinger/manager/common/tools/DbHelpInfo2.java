package com.redfinger.manager.common.tools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DbHelpInfo2 {
	/**
	 *
	 * 这里是MySQL连接方法
	 */
	private static final String driver = "org.postgresql.Driver";
	private static final String url = "jdbc:postgresql://pengtao:5432/red_finger";
	private static final String userName = "postgres";
	private static final String passwd = "postgres";
	private static Connection connection = null;

	public static void main(String[] args) {
		connection = getConnections();
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "VIEW" });
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String str="<table tableName=\""+tableName+"\" domainObjectName=\""+transfer(tableName)+"\" "
						+ "enableInsert=\"false\""
						+ "	enableUpdateByExample=\"false\""
						+ "	enableUpdateByPrimaryKey=\"false\""
						+ "	enableDeleteByPrimaryKey=\"false\""
						+ "	enableDeleteByExample=\"false\""		
						+ ">"
						+ "</table>";
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnections() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static String transfer(String str) {
		StringBuffer sb = new StringBuffer("");
		String[] arr = str.split("_");
		for (String s : arr) {
			String n = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
			sb.append(n);
		}
		return sb.toString();

	}
}
