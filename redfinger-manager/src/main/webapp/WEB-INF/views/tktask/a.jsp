<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'a.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%
    	String remoteAddr = request.getHeader("X-Real-IP");
    	out.println("X-Real-IP="+remoteAddr+"<br/>");
       	remoteAddr = request.getHeader("X-Forwarded-For");
       	out.println("X-Forwarded-For="+remoteAddr+"<br/>");
       	remoteAddr = request.getHeader("Proxy-Client-IP");
       	out.println("Proxy-Client-IP="+remoteAddr+"<br/>");
       	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
       	out.println("WL-Proxy-Client-IP="+remoteAddr+"<br/>");
        out.println("request.getRemoteAddr="+request.getRemoteAddr());
    
     %>
  </body>
</html>
