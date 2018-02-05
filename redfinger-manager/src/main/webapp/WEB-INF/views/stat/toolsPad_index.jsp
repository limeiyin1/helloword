<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>Ip故障查询</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'padIp',
		onCheck: function(row){
			
		},
		columns:[[
			{width:15,title:'IP地址',field:'padIp',sortable:true},
			{width:100,title:'故障类型',field:'className',sortable:true},
			{width:300,title:'故障描述',field:'feedbackContent',sortable:true},
		]]
	};
	var dialogParamObj={
		
	};
	var dataGridParamNoPage = {
			fitColumns : true,
			pagination : false,
			striped : true,
			rownumbers : true,
			singleSelect : false,
			idField : 'padIp',
			loadFilter : loadFilterForDataGridNoPage,
			onDblClickRow:specific,
			loadMsg : "处理中，请稍后..."
		};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParamNoPage,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="add()">录入IP清单</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton"data-options="iconCls:'icon-ok-rf'">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



