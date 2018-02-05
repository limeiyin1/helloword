package com.redfinger.manager.common.tools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class FieldsTranfer {

	private static final String driver = "org.postgresql.Driver";
	private static final String url = "jdbc:postgresql://localhost:5432/red_finger";
	private static final String userName="postgres";
	private static final String passwd="postgres";
	private static Connection connection = null;
	
	
	public static void main(String[] args) {
		String tableName="rf_pad";
		List<String> tableList= Lists.newArrayList();
		List<String> joinFied = Lists.newArrayList();
		
		tableList.add("rf_user_pad");
		joinFied.add("pad_id=pad_id");
		
		
		//开始
		System.out.println("select * from (select");
		connection = getConnections();
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet rs=null;
			StringBuffer sb = new StringBuffer();
			rs = dbmd.getColumns(null, "%", tableName, "%");
			List<String> fieldList=Lists.newArrayList();
			while (rs.next()) {
				fieldList.add(rs.getString("COLUMN_NAME"));
			}
			for(String field :fieldList){
				sb.append("\n");
				sb.append("t1."+field+",");
			}
			int i=2;
			for(String table:tableList){
				rs = dbmd.getColumns(null, "%", table, "%");
				fieldList=Lists.newArrayList();
				
				while (rs.next()) {
					fieldList.add(rs.getString("COLUMN_NAME"));
				}
				for(String field :fieldList){
					sb.append("\n");
					if(i==1){
						sb.append("t"+i+"."+field+",");
					}else{
						sb.append("t"+i+"."+field+" as "+field+"_t"+i+",");
					}
				}
				i++;
			}
			System.out.println(StringUtils.removeEnd(sb.toString(), ","));
			
			System.out.println("from ");
			int j=2;
			for(int k=0;k<tableList.size();k++){
				String field1=joinFied.get(k).split("=")[0];
				String field2=joinFied.get(k).split("=")[1];
				if(j==2){
					System.out.println(tableName +" t1 LEFT JOIN "+tableList.get(k)+" t"+j+" on t1."+field1+"=t"+j+"."+field2);
				}else{
					System.out.println(" LEFT JOIN "+tableList.get(k)+" t"+j+" on t1."+field1+"=t"+j+"."+field2);
				}
				
				j++;
			}
			
			System.out.println(") t");
			
			
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
}
