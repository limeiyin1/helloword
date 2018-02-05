<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>系统</title>
<meta name="decorator" content="menu"/>

</head>
<body>
	<div class="menu1">
		<c:forEach items="${menuList}" var="one">
			<div style="margin-top: 5px;">
			<a href="javascript:void(0)" iconCls="icon-menu-rf" onclick="addTab('${one.url}',this)" class="easyui-linkbutton" data-options="plain:false" style="width: 100%;">${one.name }</a>
			
			</div>
		</c:forEach>
	</div> 
</body>
</html>