<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>会员管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
<script type="text/javascript">
	var pk = "betaUserId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : 'betaUserId',
		onCheck : function(row) {

		},
		columns : [ [ {
			width : 100,
			title : 'id',
			field : 'betaUserId',
			checkbox : true
		}, {
			width : 100,
			title : '用户ID',
			field : 'userId'
		}, {
			width : 100,
			title : '用户号码',
			field : 'userMobilePhone',
			sortable : true
		}, {
			width : 100,
			title : '客户端类型',
			field : 'clientType',
			sortable : true
		}, {
			width : 150,
			title : '创建时间',
			field : 'createTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 100,
			title : '状态',
			field : 'enableStatus',
			sortable : true,
			formatter : formatterStop
		} ] ]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid(
				$.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});

</script>
</head>
<body >
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
			    <div class="module_search_input">会员手机:<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /></div>
			    
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar" style="height: auto">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>			  
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button" ></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
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



