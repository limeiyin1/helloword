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

<!-- JBOX begin -->
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>

<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
<!-- JBOX end -->

<!-- customer begin -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/icons.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/layout.css">
<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/layout.js"></script>
<!-- customer end -->
<sitemesh:head />
</head>
<body>
	<sitemesh:body />
</body>
</html>


