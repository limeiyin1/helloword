<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>


<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title /></title>

<%@ include file="/WEB-INF/include/taglib.jsp"%>
<script type="text/javascript" src="${ctxStatic}/js/jquery-1.7.2.min.js"></script>
<!-- EASYUI begin -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.4.1/themes/icon.css">
<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<!-- EASYUI end -->

<!-- ECHART begin -->
<script src="${ctxStatic}/echarts-2.2.1/echarts.js" type="text/javascript"></script>
<!-- ECHART end -->


<!-- customer begin -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/module.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/icons.css">
<script type="text/javascript" src="${ctxStatic}/js/json2.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/stat.js"></script>
<script type="text/javascript">var host='${ctx}/${model}/';</script>
<!-- customer end -->

<sitemesh:head />
</head>
<body>
	<sitemesh:body />
	<div class="hide">
	<form action="" id="exportForm" method="post" target="_blank">
		<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
		<input type="hidden" name="exportHead" id="exportHead" value=""/>
		<input type="hidden" name="exportField" id="exportField" value=""/>
		<input type="hidden" name="exportName" id="exportName" value=""/>
	</form>
	</div>
</body>
</html>


