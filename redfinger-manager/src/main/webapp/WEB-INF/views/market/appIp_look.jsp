<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>Ip详情</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">	
	<div id="tt" class="easyui-tabs">   
	    <div title="Ip详情">   
	        <table id="module_submit_table">
				<tr>
					<td class="td1">国家:</td>
					<td class="td2">${bean.country }</td>
				</tr>
				<tr>
					<td class="td1">城市:</td>
					<td class="td2">${bean.city}</td>
				</tr>
				<tr>
					<td class="td1">运营商:</td>
					<td class="td2">${bean.isp}</td>
				</tr>
	</form>
    </div>
</body>
</html>



