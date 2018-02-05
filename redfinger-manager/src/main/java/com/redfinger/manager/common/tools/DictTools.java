package com.redfinger.manager.common.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.utils.StringUtils;

public class DictTools {

	private static final String driver = "org.postgresql.Driver";
	private static final String url = "jdbc:postgresql://pengtao:5432/red_finger";
	private static final String userName="postgres";
	private static final String passwd="postgres";
	private static Connection connection = null;
	
	public static void main(String[] args) {
		connection = getConnections();
		List<String> list=Lists.newArrayList();
		List<String> list2=Lists.newArrayList();
		String sql="select dict_code,remark from sys_dict order by dict_category asc ,reorder asc";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String code = rs.getString("dict_code") ; 
				list.add(code);
				list2.add(rs.getString("remark"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("package com.redfinger.manager.common.constants;");
		System.out.println("import com.redfinger.manager.common.helper.DictHelper;");
		System.out.println("public class ConstantsDb {");

		

		
		
		for(int i=0;i<list.size();i++){
			String code=list.get(i);
			String method=code.replaceAll("\\.", "_");
			method=method.replaceAll("@", "_");
			method=StringUtils.toCamelCase(method);
			System.out.println("/**");
			System.out.println("*"+list2.get(i));
			System.out.println("*/");
			System.out.println("public static String "+method+"(){");
			System.out.println("return DictHelper.getDictValueByKey(\""+code+"\");");
			System.out.println("}");
			System.out.println("");
		}
		
		System.out.println("}");
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
