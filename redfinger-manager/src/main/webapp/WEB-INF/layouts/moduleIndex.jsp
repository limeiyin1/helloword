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

<!-- HOTKEYS begin  -->
<script type="text/javascript" src="${ctxStatic}/js/jquery.hotkeys.js"></script>
<!-- HOTKEYS end  -->

<!-- customer begin -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/module.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/icons.css">
<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_util.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_setting.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_ajax.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_query.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_formatter.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_column_defined.js"></script>
<script type="text/javascript">var host='${ctx}/${model}/';</script>
<script type="text/javascript">var dict=${dictCategoryMap}</script>
<!-- customer end -->

<sitemesh:head />
</head>
<body>
	<sitemesh:body />
	<script type="text/javascript">
		autoWidthHeight();
		$(function() {
			$(".easyui-linkbutton:first").focus();
		});
	</script>
</body>
</html>


